package com.person.newscopy.my.fragment;

import android.arch.lifecycle.Observer;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.generaltool.common.ScreenFitUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.BaseResult;
import com.person.newscopy.user.net.bean.VersionBean;

public class SettingFragment extends Fragment {

    MyActivity myActivity;
    Button outLogin;
    View parent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_setting,container,false);
        myActivity = (MyActivity) getActivity();
        parent = view.findViewById(R.id.parent);
        view.findViewById(R.id.back).setOnClickListener(v -> myActivity.finish());
        view.findViewById(R.id.edit_info).setOnClickListener(v -> myActivity.changeFragment(MyActivity.MY_INFO_TYPE,true));
        view.findViewById(R.id.change_pas).setOnClickListener(v -> myActivity.changeFragment(MyActivity.CHANGE_PASSWORD_TYPE,true));
        outLogin = view.findViewById(R.id.out_login);
        view.findViewById(R.id.update).setOnClickListener(v -> {
            myActivity.findNewVersion().observe(SettingFragment.this, versionBean -> {
                  createDownloadPop(versionBean.getResult().getInfo());
            });
        });
        view.findViewById(R.id.get_back_pas).setOnClickListener(v -> {
          myActivity.changeFragment(MyActivity.GET_USER_PAS,false);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        outLogin.setOnClickListener(v -> {
            Users.reset();
            deleteOldUserInfo();
            Toast.makeText(myActivity, "已经退出登陆", Toast.LENGTH_SHORT).show();
            myActivity.finish();
        });
    }

    private void deleteOldUserInfo(){
        SharedPreferences sharedPreferences = myActivity.getSharedPreferences(Config.USER_INFO_STORE_KEY,0);
        sharedPreferences.edit().putBoolean("isLogin",false)
                .putString("userId","")
                .putString("userIcon","")
                .putString("userName","")
                .putString("userRecommend","")
                .putString("email","")
                .putInt("care",-1)
                .putInt("fans",-1)
                .putInt("work",-1)
                .apply();
    }

    private void createDownloadPop(String recommend){
        float d = ScreenFitUtil.getDensity();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_new_version,null);
        PopupWindow popupWindow = new PopupWindow(view, (int)(d*300),(int)(d*200));
        TextView t = view.findViewById(R.id.recommend);
        t.setText(recommend);
        view.findViewById(R.id.download).setOnClickListener(v -> BaseUtil.downloadApk("正在下载","news.apk"));
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(parent, Gravity.CENTER,0,0);
        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(() -> {
            backgroundAlpha(1);
        });
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }
}
