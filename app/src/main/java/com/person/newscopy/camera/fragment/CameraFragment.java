package com.person.newscopy.camera.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;
import com.easy.generaltool.ViewUtil;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.easy.generaltool.media.camera.CameraHelper;
import com.person.newscopy.R;

public class CameraFragment extends Fragment {

    ImageView start;
    SurfaceView cameraView;
    ImageView changeCamera;
    boolean isStart = false;
    CameraHelper helper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item_camera, container, false);

        changeCamera = view.findViewById(R.id.camera);
        cameraView = view.findViewById(R.id.camera_view);
        start = view.findViewById(R.id.start);
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                helper.openCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        helper = CameraHelper.getInstance(getContext(),cameraView,CameraHelper.CAMERA_FRONT);
        changeCamera.setOnClickListener(v ->{
            helper.change();
        });
        start.setOnClickListener(v->{
            if (isStart){
                start.setImageResource(R.drawable.camera_start);
                changeCamera.setClickable(true);
                helper.stopVideoRecord();
                Toast.makeText(getContext(), "录制完成", Toast.LENGTH_SHORT).show();
            }else {
                start.setImageResource(R.drawable.camera_pause);
                helper.startVideoRecord(getActivity().getCacheDir().getAbsolutePath(), SystemClock.currentThreadTimeMillis()+".mp4");
                changeCamera.setClickable(false);
            }
            isStart = !isStart;
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        helper.closeCamera();
    }
}
