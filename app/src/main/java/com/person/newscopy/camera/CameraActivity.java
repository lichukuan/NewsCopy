package com.person.newscopy.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.person.newscopy.R;

public class CameraActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    ViewPager viewPager;

    TabLayout tabLayout;

    TextView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenFitUtil.fit(getApplication(),this,ScreenFitUtil.FIT_WIDTH);
        TranslucentUtil.setTranslucent(this, Color.TRANSPARENT, 0);
        setContentView(R.layout.activity_camera);
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> finish());
        tabLayout.addTab(tabLayout.newTab().setText("拍摄"),0);
        tabLayout.addTab(tabLayout.newTab().setText("相册"),1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),true);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_DENIED )
            //第二步 申请权限(注意为了兼容，建议使用 ActivityCompat)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO},REQUEST_CODE);
        else
            viewPager.setAdapter(new CameraFragmentAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                viewPager.setAdapter(new CameraFragmentAdapter(getSupportFragmentManager()));
            }else{
                Toast.makeText(this, "没有权限无法使用该功能", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


}
