package com.person.newscopy.image;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.easy.generaltool.common.ScreenFitUtil;
import com.google.android.exoplayer.C;
import com.person.newscopy.R;
import com.person.newscopy.common.MyGlideEngine;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.view.CutShapeImageView;
import com.person.newscopy.common.view.ShapeImageView;
import com.person.newscopy.image.bean.ImageBean;
import com.person.newscopy.image.fragment.CutImageFragment;
import com.person.newscopy.image.fragment.ImagesPickFragment;
import com.person.newscopy.image.fragment.ShowAllImageFragment;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ImageActivity extends AppCompatActivity  {

    CutImageFragment cutImageFragment;
    ShowAllImageFragment showAllImageFragment;
    ImagesPickFragment imagesPickFragment;
    public static String pick_result = null;

    public static final int TYPE_CUT = 1;
    public static final int TYPE_SHOW = 2;
    public static final int TYPE_PICK = 3;

    public static final String REQUIRE_CODE = "require_code";
    public static final String REQUIRE_TYPE = "require_type";
    public static final String CUT_SHAPE = "cut_shape";
    public static final String SHOW_ARRAY_DATA = "show_array_data";
    public static final String SHOW_IMAGE_INDEX = "show_image_index";
    public static final String FROM_ACTIVITY = "from_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenFitUtil.fit(getApplication(),this,ScreenFitUtil.FIT_WIDTH);
        setContentView(R.layout.activity_image);
        cutImageFragment = new CutImageFragment();
        showAllImageFragment = new ShowAllImageFragment();
        imagesPickFragment = new ImagesPickFragment();
        Intent intent = getIntent();
        int type = intent.getIntExtra(REQUIRE_TYPE,TYPE_CUT);
        switch (type){
            case TYPE_CUT:
                cutImageFragment.setRequireCode(intent.getIntExtra(REQUIRE_CODE,-1));
                cutImageFragment.setShape(intent.getIntExtra(CUT_SHAPE, ShapeImageView.SHAPE_CIRCLE));
                cutImageFragment.setTo(intent.getStringExtra(FROM_ACTIVITY));
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment,cutImageFragment)
                        .commit();
                break;
            case TYPE_SHOW:
                showAllImageFragment.setData(intent.getStringArrayListExtra(SHOW_ARRAY_DATA));
                showAllImageFragment.setNowIndex(intent.getIntExtra(SHOW_IMAGE_INDEX,0));
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment,showAllImageFragment)
                        .commit();
                break;
            case TYPE_PICK:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment,imagesPickFragment)
                        .commit();
                break;
        }
    }

    public void pickImages(int maxCount){
        imagesPickFragment.setMaxCount(maxCount);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment,imagesPickFragment)
                .addToBackStack(null)
                .commit();
    }

    public List<ImageBean> getPickedImages(){
        return imagesPickFragment.getPickImage();
    }


    public void showImages(List<String> l,int nowIndex){
        showAllImageFragment.setData(l);
        showAllImageFragment.setNowIndex(nowIndex);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment,showAllImageFragment)
                .commit();
    }


    public String saveImageToGallery(Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"little");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = BaseUtil.buildImageName() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return file.getAbsolutePath();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setResultData(int code,Intent intent){
        setResult(code,intent);
        finish();
    }

//
//    private void takePicture(int maxSelectedImageNum,int code){
//        Matisse.from(this)
//                .choose(MimeType.allOf()) // 选择 mime 的类型
//                .countable(true)
//                .maxSelectable(maxSelectedImageNum) // 图片选择的最多数量
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                .thumbnailScale(0.85f) // 缩略图的比例
//                .imageEngine(new MyGlideEngine()) // 使用的图片加载引擎
//                .forResult(code); // 设置作为标记的请求码
//    }

    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

        }else{
            Toast.makeText(this, "您禁用了该权限,该功能不可用", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
