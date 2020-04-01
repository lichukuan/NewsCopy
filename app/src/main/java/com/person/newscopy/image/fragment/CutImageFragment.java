package com.person.newscopy.image.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.easy.generaltool.ViewUtil;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.MyGlideEngine;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.util.MyTranslucentUtil;
import com.person.newscopy.common.view.CutShapeImageView;
import com.person.newscopy.edit.EditActivity;
import com.person.newscopy.image.ImageActivity;
import com.person.newscopy.image.bean.ImageBean;
import com.person.newscopy.user.Users;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static android.app.Activity.RESULT_OK;

public class CutImageFragment extends Fragment {

    private CutShapeImageView cutShapeImageView = null;
    private TextView select;
    private TextView out;
    private TextView save;
    private int requireCode;
    private String to;
    private int shape = -1;
    public static final int REQUEST_CODE_CHOOSE = 1;
    ImageActivity activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cut_image,container,false);
        cutShapeImageView = view.findViewById(R.id.cut_shape_image_view);
        select = view.findViewById(R.id.select);
        out = view.findViewById(R.id.out);
        save = view.findViewById(R.id.save);
        activity = (ImageActivity) getActivity();
        if (checkPermission()){
            takePicture();
        }
        select.setOnClickListener(v -> {
            if (checkPermission()){
                takePicture();
            }
        });
        save.setOnClickListener(v -> {
            Bitmap bm = cutShapeImageView.getResult();
            String path = activity.saveImageToGallery(bm);
            if(path!=null) Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
            else Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra(ImageActivity.REQUIRE_CODE,requireCode);
            intent.putExtra("type","cut");
            intent.putExtra("data",path);
            activity.setResultData(requireCode,intent);
        });
        if (shape != -1)
        cutShapeImageView.setShape(shape);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        TranslucentUtil.setTranslucent(activity,Color.BLACK, (int) (ScreenFitUtil.getDensity()*25));
        if (activity.getPickedImages() != null){
            List<ImageBean> l = activity.getPickedImages();
            if (l.size() == 0){
                activity.finish();
                return;
            }
            RequestOptions requestOptions = new RequestOptions()
                    .centerInside();
            Glide.with(this)
                    .asBitmap()
                    .load(l.get(0).getPath())
                    .apply(requestOptions)
                    .into(cutShapeImageView);
        }
    }

    public void setShape(int shape) {
        this.shape = shape;
        if (cutShapeImageView != null)
            cutShapeImageView.setShape(shape);
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setRequireCode(int requireCode) {
        this.requireCode = requireCode;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            List<Uri> uris = Matisse.obtainResult(data);
//            Uri uri = uris.get(0);
//
//        }
//    }

    private void takePicture(){
//        Matisse.from(this)
//                .choose(MimeType.allOf()) // 选择 mime 的类型
//                .countable(true)
//                .maxSelectable(1) // 图片选择的最多数量
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                .thumbnailScale(0.85f) // 缩略图的比例
//                .imageEngine(new MyGlideEngine()) // 使用的图片加载引擎
//                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
        if (activity.getPickedImages() == null)
        activity.pickImages(1);
    }



    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            takePicture();
        }else{
            Toast.makeText(getContext(), "您禁用了该权限,无法获取图片", Toast.LENGTH_SHORT).show();
        }
    }
}
