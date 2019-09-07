package com.person.newscopy.camera.helper;

import android.hardware.Camera;
import android.view.SurfaceView;
import com.easy.generaltool.SimpleException;

import java.io.IOException;

class Camera1Helper implements CameraControlInterface{

    private int numberOfCameras;

    private int faceFrontCameraId;

    private int faceBackCameraId;

    private int faceBackCameraOrientation;

    private int faceFrontCameraOrientation;

    private int state;

    private Camera camera;

    private SurfaceView surfaceView;

    private boolean isTakeVideo = false;

    private Camera1RecorderUtils utils;

    public Camera1Helper(int state, SurfaceView surfaceView) {
        this.state = state;
        this.surfaceView = surfaceView;
        utils = new Camera1RecorderUtils();
        init();
    }

    private void init(){
        //有多少个摄像头
        numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; ++i) {
            final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            //后置摄像头
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                faceBackCameraId = i;
                faceBackCameraOrientation = cameraInfo.orientation;
            }
            //前置摄像头
            else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                faceFrontCameraId = i;
                faceFrontCameraOrientation = cameraInfo.orientation;
            }
        }
    }

    public void change(){
        if (isTakeVideo)return;
        if (state == CameraHelper.CAMERA_BACK)
            state = CameraHelper.CAMERA_FRONT;
        else
            state = CameraHelper.CAMERA_BACK;
        camera.stopPreview();
        camera.release();
        openCamera();
    }

    public void openCamera(){
        camera = null;
        if (state == CameraHelper.CAMERA_BACK)
            camera = Camera.open(faceBackCameraId);
        else if (state == CameraHelper.CAMERA_FRONT)
            camera = Camera.open(faceFrontCameraId);
        else{
            throw new SimpleException("can not find what you need camera");
        }
        try {
            camera.setPreviewDisplay(surfaceView.getHolder());
            camera.setDisplayOrientation(90);
            Camera.Parameters mParameters = camera.getParameters();
            mParameters.setPictureSize(720, 480);//设置图片尺寸
            mParameters.setPreviewSize(720, 480);//设置预览尺寸
            camera.setParameters(mParameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }

    @Override
    public void takePhoto(CameraHelper.OnTakePictureFinishCallback callback) {
        if (camera!=null)
            camera.takePicture(null, null, (data, camera) -> {
                callback.finish(data);
                camera.startPreview();
            });
    }

    @Override
    public void startVideoRecord(String path, String name) {
        isTakeVideo = true;
        utils.create(surfaceView,Camera1RecorderUtils.WH_720X480,camera);
        utils.startRecord(path,name);
    }

    @Override
    public void stopVideoRecord() {
        utils.stopRecord();
        isTakeVideo = false;
    }

    public void closeCamera(){
        if (camera==null)return;
        camera.stopPreview();
        camera.release();
        utils.destroy();
    }

}
