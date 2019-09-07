package com.person.newscopy.camera.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceView;

public class CameraHelper {

    public static final int CAMERA_FRONT = 0;

    public static final int CAMERA_BACK = 1;

    public static final int CAMERA_TAKE_PICTURE = 2;

    public static final int CAMERA_TAKE_VIDEO = 3;

    private static CameraControlInterface camera;

    private static CameraHelper helper =  new CameraHelper();

    private CameraHelper() {
    }

    @SuppressLint("NewApi")
    public static  CameraHelper getInstance(Context context, SurfaceView surfaceView, int state){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED||
                ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
           return null;
        }else {
            if (hasCamera2(context))camera = new Camera2Helper(surfaceView,context,state);
            else camera = new Camera1Helper(state,surfaceView);
            return helper;
        }
    }

    public void openCamera(){
        camera.openCamera();
    }

    public void takePhoto(CameraHelper.OnTakePictureFinishCallback callback){
        camera.takePhoto(callback);
    }

    public void startVideoRecord(String path,String name){
        camera.startVideoRecord(path,name);
    }

    public void stopVideoRecord(){
        camera.stopVideoRecord();
    }

    public void closeCamera(){
        camera.closeCamera();
    }

    public void change(){
        camera.change();
    }

    private static boolean hasCamera2(Context mContext) {
        if (mContext == null) return false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return false;
        try {
            CameraManager manager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
            String[] idList = manager.getCameraIdList();
            boolean notFull = true;
            if (idList.length == 0) {
                notFull = false;
            } else {
                for (final String str : idList) {
                    if (str == null || str.trim().isEmpty()) {
                        notFull = false;
                        break;
                    }
                    final CameraCharacteristics characteristics = manager.getCameraCharacteristics(str);

                    final int supportLevel = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
                    if (supportLevel == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
                        notFull = false;
                        break;
                    }
                }
            }
            return notFull;
        } catch (Throwable ignore) {
            return false;
        }
    }

    public interface OnTakePictureFinishCallback{
        void finish(byte[] data);
    }

}
