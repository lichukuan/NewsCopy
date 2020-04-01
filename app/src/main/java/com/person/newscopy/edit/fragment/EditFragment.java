package com.person.newscopy.edit.fragment;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.person.newscopy.R;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.MyGlideEngine;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.view.ShapeImageView;
import com.person.newscopy.edit.EditActivity;
import com.person.newscopy.edit.adapter.DefaultEditAdapter;
import com.person.newscopy.edit.adapter.HtmlEditAdapter;
import com.person.newscopy.image.ImageActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class EditFragment extends Fragment implements DefaultEditAdapter.LoadCallback,SetContentAndLinkFragment.OnOkListener {

    RecyclerView content;
    ConstraintLayout parent;
    HtmlEditAdapter defaultEditAdapter;
    public static final int REQUEST_CODE_CHOOSE = 10;
    Map<String,String> imageMap = new HashMap<>();
    private int location = -1;
    FloatingActionButton all,show,delete,upload,add;
    boolean isEditMode = true;
    EditActivity editActivity;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit,container,false);
        content = view.findViewById(R.id.content);
        parent = view.findViewById(R.id.parent);
        all = view.findViewById(R.id.all);
        show = view.findViewById(R.id.show);
        delete = view.findViewById(R.id.delete);
        upload = view.findViewById(R.id.upload);
        progressBar = view.findViewById(R.id.progress);
        editActivity = (EditActivity) getActivity();
        add = view.findViewById(R.id.add);
        return view;
    }

    public void setDefaultEditAdapter() {
        defaultEditAdapter = new HtmlEditAdapter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = editActivity.getIntent();
        if (intent != null){
            String type = intent.getStringExtra("type");
            if (type != null&&type.equals("cut")){
                location = 0;
                String filePath = intent.getStringExtra("data");
                String n = BaseUtil.buildImageName();
                String url = Config.DEFAULT_IMAGE_BASE_URL + n;
                pushImage(filePath,url,n);
            }
        }
        if(defaultEditAdapter == null){
            setDefaultEditAdapter();
            defaultEditAdapter.setLoadImageCallback(this);
        }
        add.setOnClickListener(v -> {
            defaultEditAdapter.createNewItem(defaultEditAdapter.getItemCount()-1);
            hideItem();
        });
        delete.setOnClickListener(v -> {defaultEditAdapter.clear();hideItem();});
        show.setOnClickListener(v -> {
            ((EditActivity)getActivity()).preview(defaultEditAdapter.toHtml());
            hideItem();
        });
        upload.setOnClickListener(v -> {
            List<String> l = editActivity.getKeywordFilteringUtil().match(defaultEditAdapter.toFormatContent());
            if (l.size() <= 0)
            createListDialog();
            else {
                ShowSensitiveWordsFragment fragment = new ShowSensitiveWordsFragment();
                fragment.setData(l);
                fragment.show(getChildFragmentManager(),ShowSensitiveWordsFragment.class.getName());
            }
        });
        all.setOnClickListener(v -> {
            if(isEditMode){
                showItem();
            }else {
               hideItem();
            }
        });
        content.setAdapter(defaultEditAdapter);
        content.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void showItem(){
        ObjectAnimator l1 = ObjectAnimator.ofFloat(all,"rotation",0,45).setDuration(800);
        ObjectAnimator l2 = ObjectAnimator.ofFloat(upload,"translationY",0,-(158*defaultEditAdapter.getDensity())).setDuration(600);
        ObjectAnimator l3 = ObjectAnimator.ofFloat(show,"translationY",0,-(108*defaultEditAdapter.getDensity())).setDuration(400);
        ObjectAnimator l4 = ObjectAnimator.ofFloat(delete,"translationY",0,-(58*defaultEditAdapter.getDensity())).setDuration(200);
        ObjectAnimator l5 = ObjectAnimator.ofFloat(add,"translationY",0,-(208*defaultEditAdapter.getDensity())).setDuration(800);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(l1,l2,l3,l4,l5);
        animatorSet.start();
        isEditMode = false;
    }

    private void hideItem(){
        isEditMode = true;
        ObjectAnimator l1 = ObjectAnimator.ofFloat(all,"rotation",45,0).setDuration(800);
        ObjectAnimator l2 = ObjectAnimator.ofFloat(upload,"translationY",-(158*defaultEditAdapter.getDensity()),0).setDuration(400);
        ObjectAnimator l3 = ObjectAnimator.ofFloat(show,"translationY",-(108*defaultEditAdapter.getDensity()),0).setDuration(600);
        ObjectAnimator l4 = ObjectAnimator.ofFloat(delete,"translationY",-(58*defaultEditAdapter.getDensity()),0).setDuration(800);
        ObjectAnimator l5 = ObjectAnimator.ofFloat(add,"translationY",-(208*defaultEditAdapter.getDensity()),0).setDuration(200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(l1,l2,l3,l4,l5);
        animatorSet.start();
    }

    private void takePicture(){
        Matisse.from(this)
                .choose(MimeType.allOf()) // 选择 mime 的类型
                .countable(true)
                .maxSelectable(1) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new MyGlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> uris = Matisse.obtainResult(data);
            for (int i = 0; i < uris.size(); i++) {
                String n = BaseUtil.buildImageName();
                String url = Config.DEFAULT_IMAGE_BASE_URL + n;
                String path = BaseUtil.getImagePath(uris.get(i));
                pushImage(path,url,n);
            }
        }
    }

    private void pushImage(String path,String url,String n){
        if (!imageMap.containsKey(path)){
            imageMap.put(path,url);
            BaseUtil.pushImageToQiNiu(path,n,(key, info, res) -> {
                //res包含hash、key等信息，具体字段取决于上传策略的设置
                if (info.isOK()) {
                    if(location != 0)
                        defaultEditAdapter.insertImage(url,location);
                    else
                        defaultEditAdapter.insertCover(url,location);
                    Log.i("=====", "Upload Success 访问地址为：" + url);
                } else {
                    Toast.makeText(getContext(), "网络出错了", Toast.LENGTH_SHORT).show();
                    Log.i("七牛云上传失败：", info.error);
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            });
        }else{
            if(location != 0)
                defaultEditAdapter.insertImage(url,location);
            else defaultEditAdapter.insertCover(url,location);
        }
    }


    public void upload(String tag){
        progressBar.setVisibility(View.VISIBLE);
        editActivity.release(defaultEditAdapter.toHtml(),defaultEditAdapter.getTitle(),defaultEditAdapter.getCover(),BaseUtil.getGson().toJson(defaultEditAdapter.getImages())
        ,tag,null).observe(this, baseResult -> {
            progressBar.setVisibility(View.GONE);
            if(baseResult.getCode() == Config.FAIL){
                Toast.makeText(editActivity, "上传失败", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(editActivity, "上传成功", Toast.LENGTH_SHORT).show();
                editActivity.finish();
            }
        });
    }

    public void createListDialog(){
        AlertDialog.Builder listDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        //创建存储数据的数组
        String[] d = getResources().getStringArray(R.array.tag);
        //显示列表，并为列表增加点击事件
        listDialog.setItems(d, (dialogInterface, i) -> {
            upload(d[i]);
        });
        listDialog.show();
    }

    @Override
    public void loadImage(int position) {
        if (checkPermission()) {
            takePicture();
        }
        location = position;
    }

    @Override
    public void loadCover(int position) {
            Intent intent  = new Intent(getContext(), ImageActivity.class);
            intent.putExtra(ImageActivity.REQUIRE_TYPE,ImageActivity.TYPE_CUT);
            intent.putExtra(ImageActivity.FROM_ACTIVITY,EditActivity.class.getName());
            intent.putExtra(ImageActivity.CUT_SHAPE, ShapeImageView.SHAPE_RECTANGLE);
            intent.putExtra(ImageActivity.REQUIRE_CODE,20);
            startActivity(intent);
    }



    @Override
    public void loadLink(int position) {
        SetContentAndLinkFragment frag = new SetContentAndLinkFragment();
        frag.setListener(this);
        frag.show(getChildFragmentManager(),"SetContentAndLinkFragment");
        location = position;
    }

    @Override
    public void ok(String tag, String l) {
        defaultEditAdapter.insertLink(tag,l,location);
    }

}
