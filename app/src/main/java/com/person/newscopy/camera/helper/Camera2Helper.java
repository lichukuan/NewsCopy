package com.person.newscopy.camera.helper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.CAMERA_SERVICE;

class Camera2Helper implements CameraControlInterface{

    SurfaceView videoView;
    CameraManager cameraManager;
    String frontCameraId;
    String backCameraId;
    Handler handler = new Handler();
    CameraDevice frontCamera;
    CameraDevice backCamera;
    CaptureRequest.Builder previewBuilder;
    CameraCaptureSession cameraCaptureSession;
    boolean isFront = true;
    ImageReader imageReader;
    MediaRecorder mMediaRecorder;
    boolean isVideoStart = false;
    Integer mSensorOrientationFront;
    Integer mSensorOrientationBack;
    CaptureRequest request;
    Context context;
    int state;
    Handler mainHandler;
    Handler childHandler;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Camera2Helper(SurfaceView videoView, Context context, int state) {
        this.videoView = videoView;
        this.context = context;
        this.state = state;
        HandlerThread handlerThread = new HandlerThread("Camera2");//线程名，随意
        handlerThread.start();
        mainHandler = new Handler(context.getMainLooper());//主线程Handler
        childHandler = new Handler(handlerThread.getLooper());//子线程Handler
        init();
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void takePhoto(CameraHelper.OnTakePictureFinishCallback callback){
        try {
            CaptureRequest.Builder builder = null;
            if (isFront){
                builder = frontCamera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            }else {
                builder = backCamera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            }
            imageReader.setOnImageAvailableListener(reader -> {
                Image image = reader.acquireLatestImage();
                ByteBuffer byteBuffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[byteBuffer.remaining()];
                callback.finish(bytes);
            },handler);
            builder.addTarget(imageReader.getSurface());
            CaptureRequest request = builder.build();
            cameraCaptureSession.stopRepeating();
            cameraCaptureSession.capture(request,captureCallback, handler);
            previewBuilder.addTarget(videoView.getHolder().getSurface());
            CaptureRequest request1 = previewBuilder.build();
            cameraCaptureSession.capture(request1, captureCallback, handler);
            cameraCaptureSession.setRepeatingRequest(request1,captureCallback,handler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void startVideoRecord(String path, String name) {
        try {
            takeVideo(path,name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void stopVideoRecord() {
       stopTakeVideo();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void closeCamera() {
        cameraManager.unregisterAvailabilityCallback(availabilityCallback);
        cameraCaptureSession.close();
        if (frontCamera!=null)frontCamera.close();
        if (backCamera!=null)backCamera.close();
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void openCamera() {
        cameraManager.registerAvailabilityCallback(availabilityCallback, mainHandler);
        try {
            if (state == CameraHelper.CAMERA_FRONT){
                isFront = true;
                cameraManager.openCamera(frontCameraId, stateCallback, mainHandler);
            }
            else {
                isFront = false;
                cameraManager.openCamera(backCameraId,stateCallback,mainHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("MissingPermission")
    public void change(){
        if (isVideoStart)return;
        try {
            cameraCaptureSession.stopRepeating();
            if (isFront){
                frontCamera.close();
                cameraManager.openCamera(backCameraId, stateCallback, mainHandler);
            }else{
                backCamera.close();
                cameraManager.openCamera(frontCameraId,stateCallback,mainHandler);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void takeVideo(String path,String name) throws IOException {
        isVideoStart = true;
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setOutputFile(path + File.separator + name);
        mMediaRecorder.setVideoEncodingBitRate(700 * 1024);
        mMediaRecorder.setVideoFrameRate(24);
        mMediaRecorder.setVideoSize(720, 480);
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mMediaRecorder.prepare();
        mMediaRecorder.start();
        try {
            cameraCaptureSession.stopRepeating();
            cameraCaptureSession.abortCaptures();
            cameraCaptureSession.close();
            CameraDevice cameraDevice = null;
            if (isFront) {
                previewBuilder = frontCamera.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
                cameraDevice = frontCamera;
            } else {
                previewBuilder = backCamera.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
                cameraDevice = backCamera;
            }
            List<Surface> surfaces = new ArrayList<>();
            // Set up Surface for the camera preview
            Surface previewSurface = videoView.getHolder().getSurface();
            surfaces.add(previewSurface);
            previewBuilder.addTarget(previewSurface);
            // Set up Surface for the MediaRecorder
            Surface recorderSurface = mMediaRecorder.getSurface();
            surfaces.add(recorderSurface);
            previewBuilder.addTarget(recorderSurface);
            cameraDevice.createCaptureSession(surfaces
                    , new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession session) {
                            cameraCaptureSession = session;
                            try {
                                request = previewBuilder.build();
                                cameraCaptureSession.setRepeatingRequest(request,null,childHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                                Log.d("==========","执行onConfigured,出错了");
                            }
                            Log.d("==========","执行onConfigured");

                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                        }
                    }, childHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void stopTakeVideo(){
        try {
            cameraCaptureSession.stopRepeating();
            cameraCaptureSession.abortCaptures();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        cameraCaptureSession.close();
        openCamera();
        mMediaRecorder.setOnErrorListener(null);
        mMediaRecorder.setOnInfoListener(null);
        mMediaRecorder.setPreviewDisplay(null);
        mMediaRecorder.stop();
        mMediaRecorder.release();
        isVideoStart = false;
    }

    CameraCaptureSession.CaptureCallback captureCallback =  new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
            super.onCaptureStarted(session, request, timestamp, frameNumber);
        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            super.onCaptureProgressed(session, request, partialResult);
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
        }

        @Override
        public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
        }

        @Override
        public void onCaptureSequenceCompleted(@NonNull CameraCaptureSession session, int sequenceId, long frameNumber) {
            super.onCaptureSequenceCompleted(session, sequenceId, frameNumber);

        }

        @Override
        public void onCaptureSequenceAborted(@NonNull CameraCaptureSession session, int sequenceId) {
            super.onCaptureSequenceAborted(session, sequenceId);
        }

        @Override
        public void onCaptureBufferLost(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull Surface target, long frameNumber) {
            super.onCaptureBufferLost(session, request, target, frameNumber);
        }
    };

    CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            if (camera.getId().equals(frontCameraId))frontCamera=camera;
            else if (camera.getId().equals(backCameraId))backCamera=camera;
            try {
                previewBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                previewBuilder.addTarget(videoView.getHolder().getSurface());
                imageReader = ImageReader.newInstance(200,200, ImageFormat.JPEG,1);
                CaptureRequest request = previewBuilder.build();
                camera.createCaptureSession(Arrays.asList(videoView.getHolder().getSurface(),imageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@NonNull CameraCaptureSession session) {
                        try {
                            cameraCaptureSession = session;
                            cameraCaptureSession.setRepeatingRequest(request,captureCallback, childHandler);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                    }
                }, childHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            Log.d("=============","摄像机断开连接");
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            Log.d("=============","连接出错");
        }
    };

    CameraManager.AvailabilityCallback availabilityCallback =new CameraManager.AvailabilityCallback() {
        @Override
        public void onCameraAvailable(@NonNull String cameraId) {
            super.onCameraAvailable(cameraId);
            Log.d("==========",cameraId+"准备好了");
        }

        @Override
        public void onCameraUnavailable(@NonNull String cameraId) {
            super.onCameraUnavailable(cameraId);
            Log.d("==========",cameraId+"没准备好了");
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
        try {
            String[] list = cameraManager.getCameraIdList();
            for(String id:list){
                CameraCharacteristics cameraInfo = cameraManager.getCameraCharacteristics(id);
                int lensFacing = cameraInfo.get(CameraCharacteristics.LENS_FACING);//获取相机的方向
                if (lensFacing == CameraCharacteristics.LENS_FACING_FRONT){//前置摄像头
                    frontCameraId = id;
                    mSensorOrientationBack = cameraInfo.get(CameraCharacteristics.SENSOR_ORIENTATION);
                }else if (lensFacing == CameraCharacteristics.LENS_FACING_BACK){//后置摄像头
                    mSensorOrientationFront = cameraInfo.get(CameraCharacteristics.SENSOR_ORIENTATION);
                    backCameraId = id;
                }else {//外部摄像头

                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

}
