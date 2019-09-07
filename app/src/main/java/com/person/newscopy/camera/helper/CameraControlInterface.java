package com.person.newscopy.camera.helper;

public interface CameraControlInterface {

    void openCamera();

    void takePhoto(CameraHelper.OnTakePictureFinishCallback callback);

    void startVideoRecord(String path, String name);

    void stopVideoRecord();

    void closeCamera();

    void change();
}
