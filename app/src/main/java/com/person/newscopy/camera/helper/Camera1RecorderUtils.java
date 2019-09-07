package com.person.newscopy.camera.helper;

import android.graphics.Point;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.IOException;

class Camera1RecorderUtils {
    private MediaRecorder mediaRecorder;
    private Camera camera;
    private SurfaceHolder.Callback callback;
    private SurfaceView surfaceView;
    private int height, width;
    public static Point WH_2160X1080 = new Point(2160, 1080);
    public static Point WH_1920X1080 = new Point(1920, 1080);
    public static Point WH_1280X960 = new Point(1280, 960);
    public static Point WH_1440X720 = new Point(1440, 720);
    public static Point WH_1280X720 = new Point(1280, 720);
    public static Point WH_864X480 = new Point(864, 480);
    public static Point WH_800X480 = new Point(800, 480);
    public static Point WH_720X480 = new Point(720, 480);
    public static Point WH_640X480 = new Point(640, 480);
    public static Point WH_352X288 = new Point(352, 288);
    public static Point WH_320X240 = new Point(320, 240);
    public static Point WH_176X144 = new Point(176, 144);

    public void create(SurfaceView surfaceView,Point point,Camera camera) {
        this.surfaceView = surfaceView;
        this.camera = camera;
        mediaRecorder = new MediaRecorder();
        surfaceView.setKeepScreenOn(true);
        callback = new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
                width = point.x;
                height = point.y;
            }
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                doChange(holder);
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        };
        surfaceView.getHolder().addCallback(callback);
    }
    private void doChange(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stopRecord() {
        mediaRecorder.release();
        mediaRecorder = null;
        mediaRecorder = new MediaRecorder();
        doChange(surfaceView.getHolder());
    }

    public void destroy() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
    /**
     * @param path 保存的路径
     * @param name 录像视频名称
     */
    public void startRecord(String path, String name) {
        camera.unlock();
        mediaRecorder.setCamera(camera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setVideoEncodingBitRate(700 * 1024);
        mediaRecorder.setVideoSize(720, 480);
        mediaRecorder.setVideoFrameRate(24);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        mediaRecorder.setOutputFile(path + File.separator + name);
        File file1 = new File(path + File.separator + name);
        if (file1.exists()) {
            file1.delete();
        }
        mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
        mediaRecorder.setOrientationHint(0);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}