package com.person.newscopy.my.fragment;



import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easy.generaltool.common.ScreenFitUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.MyGlideEngine;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.view.ShapeImageView;
import com.person.newscopy.my.ChangeEmailDialogFragment;
import com.person.newscopy.my.ChangeNameDialogFragment;
import com.person.newscopy.my.ChangeRecommendDialogFragment;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.user.Users;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 用来设置头像，用户名，介绍，性别，生日，地区等详细资料
 */
public class MyInfoFragment extends Fragment {

    LinearLayout myIcon;
    LinearLayout myName;
    LinearLayout myRecommend;
    LinearLayout email;
    ImageView back;
    LinearLayout parent;
    MyActivity myActivity;
    ChangeNameDialogFragment changeNameDialogFragment;
    ChangeRecommendDialogFragment changeRecommendDialogFragment;
    ChangeEmailDialogFragment changeEmailDialogFragment;
    public static final int REQUEST_CODE_CHOOSE = 0;
    ProgressBar progressBar;
    ShapeImageView icon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info,container,false);
        myActivity = (MyActivity) getActivity();
        back = view.findViewById(R.id.back);
        myIcon = view.findViewById(R.id.my_icon);
        myName = view.findViewById(R.id.my_name);
        progressBar = view.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        email = view.findViewById(R.id.my_email);
        myRecommend = view.findViewById(R.id.my_recommend);
        parent = view.findViewById(R.id.parent);
        back.setOnClickListener(v ->myActivity.back());
        icon = myIcon.findViewById(R.id.icon);
        Glide.with(this)
                .asBitmap()
                .load(Users.userIcon)
                .into(icon);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((TextView)myName.findViewById(R.id.release_user_name)).setText(Users.userName);
        ((TextView)myRecommend.findViewById(R.id.user_recommend)).setText(Users.userRecommend);
        ((TextView)email.findViewById(R.id.user_email)).setText(Users.email);
        myIcon.setOnClickListener(v -> {
            if (checkPermission()){
                takePicture();
            }
        });
        myName.setOnClickListener(v -> changeNameDialog());
        myRecommend.setOnClickListener(v -> changeRecDialog());
        email.setOnClickListener(v -> changeEmailDialog());
    }

    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            progressBar.setVisibility(View.VISIBLE);
            List<Uri> uris = Matisse.obtainResult(data);
            String n = BaseUtil.buildImageName();
            String url = Config.DEFAULT_IMAGE_BASE_URL + n;
            String path = BaseUtil.getImagePath(uris.get(0));
            BaseUtil.pushImageToQiNiu(path,n,(key, info, res) -> {
                //res包含hash、key等信息，具体字段取决于上传策略的设置
                if (info.isOK()) {
                    Log.i("qiniu", "Upload Success 访问地址为：" + url);
                    myActivity.changeUserIcon(url).observe(this,baseResult -> {
                        if (baseResult.getCode() == Config.SUCCESS){
                            Glide.with(this)
                                    .asBitmap()
                                    .load(url)
                                    .into(icon);
                            Users.userIcon = url;
                        }
                        else Toast.makeText(myActivity, "出错了,请检测网络", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    });

                } else {
                    progressBar.setVisibility(View.GONE);
                    Log.i("七牛云上传失败：", info.error);
                    Toast.makeText(myActivity, "出错了,请检测网络", Toast.LENGTH_SHORT).show();
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            });
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

    private void changeNameDialog(){
        if (changeNameDialogFragment == null)
        changeNameDialogFragment = new ChangeNameDialogFragment();
        changeNameDialogFragment.show(getChildFragmentManager(),"ChangeNameDialogFragment");
    }

    private void changeRecDialog(){
        if (changeRecommendDialogFragment == null)
            changeRecommendDialogFragment = new ChangeRecommendDialogFragment();
        changeRecommendDialogFragment.show(getChildFragmentManager(),"ChangeRecommendDialogFragment");
    }

    private void changeEmailDialog(){
        if (changeEmailDialogFragment == null)
            changeEmailDialogFragment = new ChangeEmailDialogFragment();
        changeEmailDialogFragment.show(getChildFragmentManager(),"ChangeEmailDialogFragment");
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    private void createPhotoPopupWindows(){
        float d = ScreenFitUtil.getDensity();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_my_change_icon_view,null);
        PopupWindow popupWindow = new PopupWindow(view, (int)(d*200),(int)(d*150));
        popupWindow.setTouchable(true);
        popupWindow.setOnDismissListener(()->backgroundAlpha(1));
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        backgroundAlpha(0.5f);
        popupWindow.showAtLocation(parent, Gravity.CENTER,0,0);
        view.findViewById(R.id.from_photo_album).setOnClickListener(v -> {

        });
        view.findViewById(R.id.from_photo).setOnClickListener(v -> {

        });
        view.findViewById(R.id.cancel).setOnClickListener(v -> popupWindow.dismiss());
    }

}
