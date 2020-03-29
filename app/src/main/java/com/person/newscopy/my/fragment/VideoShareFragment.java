package com.person.newscopy.my.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.person.newscopy.R;
import com.person.newscopy.common.MyGlideEngine;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.view.MyRichEditText;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.edit.fragment.SetContentAndLinkFragment;
import com.person.newscopy.user.Users;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class VideoShareFragment extends Fragment implements SetContentAndLinkFragment.OnOkListener{

    ImageView back;
    EditText title;
    MyRichEditText richEditor;
    ImageView takePicture;
    String tag = null;
    MyActivity myActivity;
    public static final int REQUEST_CODE_CHOOSE = 10;
    ProgressBar progressBar;
    private Map<String,String> imageMap = new HashMap<>();
    View parent;
    EditText secondTime,rec,videoLink,coverLink;
    Button release;
    FrameLayout frameLayout;
    ImageView cover;
    TextView tagSelect;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_push_article, container, false);
        myActivity = (MyActivity) getActivity();
        back = view.findViewById(R.id.back);
        release = view.findViewById(R.id.release);
        title = view.findViewById(R.id.title);
        progressBar = view.findViewById(R.id.progress);
        parent = view.findViewById(R.id.parent);
        secondTime = view.findViewById(R.id.time_second);
        rec = view.findViewById(R.id.rec);
        videoLink = view.findViewById(R.id.video_link);
        frameLayout = view.findViewById(R.id.frame);
        cover = view.findViewById(R.id.cover);
        tagSelect = view.findViewById(R.id.tag);
        coverLink = view.findViewById(R.id.cover_link);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        frameLayout.setOnClickListener(v -> {
            if (checkPermission()) {
                takePicture();
            }
        });
        back.setOnClickListener(v -> {
            getActivity().finish();
        });
        tagSelect.setOnClickListener(v -> {
            AlertDialog.Builder listDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
            //创建存储数据的数组
            String[] d = getResources().getStringArray(R.array.tag);
            //显示列表，并为列表增加点击事件
            listDialog.setItems(d, (dialogInterface, i) -> {
                tagSelect.setText("当前选择的标签为："+d[i]);
                tag = d[i];
            });
            listDialog.show();
        });
        release.setOnClickListener(v -> {
            release.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);
            if (tag == null) {
                Toast.makeText(getContext(), "请选择标签", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                release.setClickable(true);
                return;
            }
            else if (title.getText().toString().equals("")){
                Toast.makeText(myActivity, "请设置标题", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                release.setClickable(true);
                return;
            }else if (videoLink.getText().toString().equals("")){
                Toast.makeText(myActivity, "请设置视频地址", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                release.setClickable(true);
                return;
            }else if (secondTime.getText().toString().equals("")){
                Toast.makeText(myActivity, "请设置视频时长", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                release.setClickable(true);
                return;
            }
            else {
                String res = (String) cover.getTag();
                if(!coverLink.getText().toString().equals(""))res = coverLink.getText().toString();
                int second = Integer.valueOf(secondTime.getText().toString());
                myActivity.addVideo(Users.userId,videoLink.getText().toString(),
                        title.getText().toString(),
                        res,BaseUtil.createFormatTime(second),tag, rec.getText().toString(),second).observe(this, baseResult -> {
                    if (baseResult.getCode() != Config.SUCCESS)
                        Toast.makeText(myActivity, "上传失败，请重试", Toast.LENGTH_SHORT).show();
                    release.setClickable(true);
                    progressBar.setVisibility(View.GONE);
                });
            }
        });

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
            Toast.makeText(myActivity, "您禁用了该权限,无法获取图片", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            progressBar.setVisibility(View.VISIBLE);
            List<Uri> uris = Matisse.obtainResult(data);
            for (int i = 0; i < uris.size(); i++) {
                String n = BaseUtil.buildImageName();
                String url = Config.DEFAULT_IMAGE_BASE_URL + n;
                String path = BaseUtil.getImagePath(uris.get(i));
                Glide.with(this)
                        .load(path)
                        .into(cover);
                if (imageMap.containsKey(path)){
                    BaseUtil.pushImageToQiNiu(path,n,(key, info, res) -> {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        progressBar.setVisibility(View.GONE);
                        if (info.isOK()) {
                            cover.setTag(url);
                            Log.i("=====", "Upload Success 访问地址为：" + url);
                        } else {
                            Toast.makeText(myActivity, "上传图片失败", Toast.LENGTH_SHORT).show();
                            Log.i("七牛云上传失败：", info.error);
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                    });
                }
            }
        }
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

    @Override
    public void ok(String tag, String l) {
        if (richEditor != null)richEditor.insertUrl(tag,l);
    }
}
