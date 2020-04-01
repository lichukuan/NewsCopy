package com.person.newscopy;

import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.easy.generaltool.common.TranslucentUtil;
import com.person.newscopy.api.Api;
import com.person.newscopy.camera.CameraActivity;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.edit.EditActivity;
import com.person.newscopy.image.ImageActivity;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.BaseResult;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeActivity extends AppCompatActivity {

    static {
        System.loadLibrary("little");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        TranslucentUtil.setTranslucent(this, Color.WHITE,0);
        OkHttpClient client = new OkHttpClient();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationLifeListener());
        Request request = new Request.Builder()
                .url(Api.BASE_URL+"phone/get/key")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(WelcomeActivity.this, "网络出错了", Toast.LENGTH_SHORT).show();
                jumpToMain();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BaseResult result = BaseUtil.getGson().fromJson(response.body().string(),BaseResult.class);
                Users.key = result.getResult();
                Log.d("========","key = "+Users.key);
                jumpToMain();
            }
        });

    }

    private void jumpToMain(){
        SharedPreferences sharedPreferences = getSharedPreferences(Config.USER_INFO_STORE_KEY,0);
        if (sharedPreferences.getBoolean("isLogin",false)){
            Users.LOGIN_FLAG = true;
            Users.userIcon = sharedPreferences.getString("userIcon","");
            Users.userRecommend = sharedPreferences.getString("userRecommend","");
            Users.userName = sharedPreferences.getString("userName","");
            Users.email = sharedPreferences.getString("email","");
            Users.userId = sharedPreferences.getString("userId","");
            Users.userWork = sharedPreferences.getInt("work",-1);
            Users.userCare = sharedPreferences.getInt("care",-1);
            Users.userFans = sharedPreferences.getInt("fans",-1);
            MiPushClient.setAlias(getApplicationContext(),Users.userId,null);
        }
        startActivity(new Intent(this, NewsActivity.class));
        finish();
    }
    public native String decode(String code);

    public native String encode(String code);
}
