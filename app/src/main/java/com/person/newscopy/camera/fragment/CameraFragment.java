package com.person.newscopy.camera.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.media.CamcorderProfile;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.easy.generaltool.ViewUtil;
import com.person.newscopy.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class CameraFragment extends Fragment {

    private boolean isSelf = true;
    ImageView start;
    SurfaceView cameraView;
    ImageView changeCamera;
    MediaRecorder mediaRecorder;
    boolean isStart = false;
    //camera2
    int numberOfCameras;
    CameraManager mCameraManager;
    int faceFrontCameraOrientation;
    private String faceBackCameraId;
    private String faceFrontCameraId;
    CameraCharacteristics frontCameraCharacteristics;
    int faceBackCameraOrientation;
    CameraCharacteristics backCameraCharacteristics;
    CameraDevice mCameraDevice;
    Handler handler = new Handler();
    CaptureRequest.Builder previewRequestBuilder;
    CameraCaptureSession captureSession;
    //camera1

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item_camera, container, false);
        changeCamera = view.findViewById(R.id.camera);
        cameraView = view.findViewById(R.id.camera_view);
        start = view.findViewById(R.id.start);
        //changeCamera.setOnClickListener(v ->change());
        //start.setOnClickListener(v->takeVideo());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            init();
            try {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    mCameraManager.openCamera(faceFrontCameraId, stateCallback, handler);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }else {

        }
        return view;
    }


    @SuppressLint("MissingPermission")
    private void change(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                try {
                    if (isSelf)mCameraManager.openCamera(faceBackCameraId, stateCallback, handler);
                    else mCameraManager.openCamera(faceFrontCameraId, stateCallback, handler);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            isSelf=!isSelf;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        mCameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
        try {
            final String[] ids = mCameraManager.getCameraIdList();
            numberOfCameras = ids.length;
            for (String id : ids) {
                final CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(id);

                final int orientation = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (orientation == CameraCharacteristics.LENS_FACING_FRONT) {
                    faceFrontCameraId = id;
                    faceFrontCameraOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                    frontCameraCharacteristics = characteristics;
                } else {
                    faceBackCameraId = id;
                    faceBackCameraOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                    backCameraCharacteristics = characteristics;
                }
            }
        } catch (Exception e) {

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void closePreviewSession(){
        if (captureSession != null) {
            captureSession.close();
            try {
                captureSession.abortCaptures();
            } catch (Exception ignore) {
            } finally {
                captureSession = null;
            }
        }
    }


    private void takeVideo(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if (isStart)stopVideo();
            else takeVideo2();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void stopVideo(){
        //关闭预览会话
        if (captureSession != null) {
            captureSession.close();
            try {
                captureSession.abortCaptures();
            } catch (Exception ignore) {
            } finally {
                captureSession = null;
            }
        }
//停止mediaRecorder
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
            } catch (Exception ignore) {
            }
        }
//释放mediaRecorder
        try {
            if (mediaRecorder != null) {
                mediaRecorder.reset();
                mediaRecorder.release();
            }
        } catch (Exception ignore) {

        } finally {
            mediaRecorder = null;
        }
    }

    protected boolean preparemediaRecorder() {
        mediaRecorder = new MediaRecorder();
        CamcorderProfile camcorderProfile = CamcorderProfile.get(0);
        try {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            //输出格式
            mediaRecorder.setOutputFormat(camcorderProfile.fileFormat);
            //视频帧率
            mediaRecorder.setVideoFrameRate(camcorderProfile.videoFrameRate);
            //视频比特率
            mediaRecorder.setVideoEncodingBitRate(camcorderProfile.videoBitRate);
            //视频编码器
            mediaRecorder.setVideoEncoder(camcorderProfile.videoCodec);
            //音频编码率
            mediaRecorder.setAudioEncodingBitRate(camcorderProfile.audioBitRate);
            //音频声道
            mediaRecorder.setAudioChannels(camcorderProfile.audioChannels);
            //音频采样率
            mediaRecorder.setAudioSamplingRate(camcorderProfile.audioSampleRate);
            //音频编码器
            mediaRecorder.setAudioEncoder(camcorderProfile.audioCodec);
            //输出路径
            mediaRecorder.setOutputFile(getActivity().getCacheDir().getPath());
//            //设置视频输出的最大尺寸
//            if (mCameraConfigProvider.getVideoFileSize() > 0) {
//                mediaRecorder.setMaxFileSize(mCameraConfigProvider.getVideoFileSize());
//                mediaRecorder.setOnInfoListener(this);
//            }
//
//            //设置视频输出的最大时长
//            if (mCameraConfigProvider.getVideoDuration() > 0) {
//                mediaRecorder.setMaxDuration(mCameraConfigProvider.getVideoDuration());
//                mediaRecorder.setOnInfoListener(this);
//            }
//            mediaRecorder.setOrientationHint(getVideoOrientation(mCameraConfigProvider.getSensorPosition()));
            //准备
            mediaRecorder.prepare();
            return true;
        } catch (IllegalStateException error) {
            Log.e(TAG, "IllegalStateException preparing MediaRecorder: " + error.getMessage());
        } catch (IOException error) {
            Log.e(TAG, "IOException preparing MediaRecorder: " + error.getMessage());
        } catch (Throwable error) {
            Log.e(TAG, "Error during preparing MediaRecorder: " + error.getMessage());
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void takeVideo2(){
     //先关闭预览，因为需要添加一个预览输出的Surface，也就是mediaRecorder.getSurface()
        closePreviewSession();
       //初始化MediaRecorder，设置相关参数
        if (preparemediaRecorder()) {
            try {
                //构建视频录制aptureRequest
                previewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
                final List<Surface> surfaces = new ArrayList<>();
                Surface workingSurface = cameraView.getHolder().getSurface();
                //设置预览Surface
                final Surface previewSurface = workingSurface;
                surfaces.add(previewSurface);
                previewRequestBuilder.addTarget(previewSurface);

                //设置预览输出Surface
                workingSurface = mediaRecorder.getSurface();
                surfaces.add(workingSurface);
                previewRequestBuilder.addTarget(workingSurface);

                mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                    @Override
                    public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                        captureSession = cameraCaptureSession;

                        previewRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                        try {
                            //持续发送Capture请求，实现实时预览。
                            captureSession.setRepeatingRequest(previewRequestBuilder.build(), null, handler);
                        } catch (Exception e) {
                        }
                        try {
                            //开始录像
                            mediaRecorder.start();
                        } catch (Exception ignore) {
                            Log.e(TAG, "mediaRecorder.start(): ", ignore);
                        }
                    }
                    @Override
                    public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                        Log.d(TAG, "onConfigureFailed");
                    }
                }, handler);
            } catch (Exception e) {
                Log.e(TAG, "startVideoRecord: ", e);
            }
        }
    }

    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            //获取CameraDevice
            mCameraDevice = cameraDevice;
            try {
                previewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            previewRequestBuilder.addTarget(cameraView.getHolder().getSurface());
            CaptureRequest previewRequest = previewRequestBuilder.build();
            //注意这里除了预览的Surface，我们还添加了imageReader.getSurface()它就是负责拍照完成后用来获取数据的
            try {
                mCameraDevice.createCaptureSession(Arrays.asList(cameraView.getHolder().getSurface()),
                        new CameraCaptureSession.StateCallback() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                                captureSession = cameraCaptureSession;
                                try {
                                    cameraCaptureSession.setRepeatingRequest(previewRequest, null, handler);
                                } catch (CameraAccessException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                            }
                        }, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            //关闭CameraDevice
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            //关闭CameraDevice
            cameraDevice.close();
        }
    };

//    private void init(){
//        int numberOfCameras = Camera.getNumberOfCameras();
//        for (int i = 0; i < numberOfCameras; ++i) {
//            final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//            Camera.getCameraInfo(i, cameraInfo);
//            //后置摄像头
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
//                faceBackCameraId = i;
//            }
//            //前置摄像头
//            else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                faceFrontCameraId = i;
//            }
//        }
//    }

//    private void startPreview(SurfaceHolder surfaceHolder,int currentCameraId ) {
//        try {
//            final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//            Camera.getCameraInfo(currentCameraId, cameraInfo);
//            int cameraRotationOffset = cameraInfo.orientation;
//            //获取相机参数
//            final Camera.Parameters parameters = old.getParameters();
//
//            final int rotation = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
//            int degrees = 0;
//            switch (rotation) {
//                case Surface.ROTATION_0:
//                    degrees = 0;
//                    break; // Natural orientation
//                case Surface.ROTATION_90:
//                    degrees = 90;
//                    break; // Landscape left
//                case Surface.ROTATION_180:
//                    degrees = 180;
//                    break;// Upside down
//                case Surface.ROTATION_270:
//                    degrees = 270;
//                    break;// Landscape right
//            }
//            float displayRotation = 0;
//            //根据前置与后置摄像头的不同，设置预览方向，否则会发生预览图像倒过来的情况。
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                displayRotation = (cameraRotationOffset + degrees) % 360;
//                displayRotation = (360 - displayRotation) % 360; // compensate
//            } else {
//                displayRotation = (cameraRotationOffset - degrees + 360) % 360;
//            }
//            old.setDisplayOrientation((int) displayRotation);
//            final int width = (int) ViewUtil.ScreenInfo.getScreenWidth(getContext());
//            final int height = (int) ViewUtil.ScreenInfo.getScreenHeight(getContext());
//            //设置预览大小
//            parameters.setPreviewSize(width,height);
//            parameters.setPictureSize(width,height);
//            //设置相机参数
//            old.setParameters(parameters);
//            //设置surfaceHolder
//            old.setPreviewDisplay(surfaceHolder);
//            //开启预览
//            old.startPreview();
//        } catch (Exception ignore) {
//
//        }
//    }


}
