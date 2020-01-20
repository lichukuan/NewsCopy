package com.person.newscopy.my.fragment;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.easy.generaltool.common.ViewInfoUtil;
import com.person.newscopy.R;
import com.person.newscopy.api.Api;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.MoreResourceEditText;
import com.person.newscopy.common.MyRichEditText;
import com.person.newscopy.common.SoftKeyBoardListener;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.SetContentAndLinkFragment;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.BaseResult;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.richeditor.RichEditor;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class PushArticleFragment extends Fragment implements SetContentAndLinkFragment.OnOkListener{

    ImageView back;
    TextView release;
    EditText title;
    MyRichEditText richEditor;
    ImageView takePicture;
    String tag = null;
    ImageView takeLink;
    Spinner tagSpinner;
    ArrayAdapter<CharSequence> tagAdapter;
    MyActivity myActivity;
    public static final int REQUEST_CODE_CHOOSE = 10;
    ProgressBar progressBar;
    private Map<String,String> imageMap = new HashMap<>();
    View parent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_push_article, container, false);
        myActivity = (MyActivity) getActivity();
        back = view.findViewById(R.id.back);
        release = view.findViewById(R.id.release);
        title = view.findViewById(R.id.title);
        progressBar = view.findViewById(R.id.progress);
        richEditor = view.findViewById(R.id.editor);
        takePicture = view.findViewById(R.id.take_pic);
        tagSpinner = view.findViewById(R.id.tag_spinner);
        takeLink = view.findViewById(R.id.take_link);
        parent = view.findViewById(R.id.parent);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        takeLink.setOnClickListener(v -> {
            SetContentAndLinkFragment fragment = new SetContentAndLinkFragment();
            fragment.setListener(PushArticleFragment.this);
            fragment.show(getChildFragmentManager(),"SetContentAndLinkFragment");
        });
        tagAdapter = ArrayAdapter.createFromResource(getContext(), R.array.tag, android.R.layout.simple_spinner_item);
        // android.R.layout.simple_spinner_item是系统默认布局，用于已选中时的布局
        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //android.R.layout.simple_spinner_dropdown_item是系统默认布局，用于下拉时的布局
        tagSpinner.setAdapter(tagAdapter);
        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag = tagAdapter.getItem(position).toString();
                Log.d("tag = ", tag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        back.setOnClickListener(v -> {
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
            }
            else {
                String result = richEditor.getHtml();
                Log.d("======result = ",result);
                String image = null;
                String imageList = BaseUtil.getGson().toJson(richEditor.getImages());
                myActivity.releaseArticle(Users.userId, result, title.getText().toString(), image, imageList, tag, null).observe(this, baseResult -> {
                    if (baseResult.getCode() != Config.SUCCESS)
                        Toast.makeText(myActivity, "上传失败，请重试", Toast.LENGTH_SHORT).show();
                    release.setClickable(true);
                    progressBar.setVisibility(View.GONE);
                });
            }

        });
        takePicture.setOnClickListener(v -> {
            if (checkPermission()) {
                takePicture();
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
                String url = Config.DEFACULT_IMAGE_BASE_URL + n;
                String path = BaseUtil.getImagePath(uris.get(i));
                if (imageMap.containsKey(path)){
                    richEditor.insertImage(path,imageMap.get(path));
                }else {
                    imageMap.put(path,url);
                    richEditor.insertImage(path,url);
                    BaseUtil.pushImageToQiNiu(path,n,(key, info, res) -> {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Log.i("qiniu", "Upload Success 访问地址为：" + url);
                            progressBar.setVisibility(View.GONE);
                        } else {
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
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
    }

    @Override
    public void ok(String tag, String l) {
        if (richEditor != null)richEditor.insertUrl(tag,l);
    }
}
