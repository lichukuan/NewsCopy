package com.person.newscopy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.easy.generaltool.common.TranslucentUtil;
import com.person.newscopy.api.Api;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.BaseResult;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WelcomeActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.

    static {
        System.loadLibrary("little");
    }
    //TypeViewModel typeViewModel;

//    boolean flag1 = false;
//    boolean flag2 = false;
//    boolean flag3 = false;
//    boolean flag4 = false;
//    boolean flag5 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        TranslucentUtil.setTranslucent(this, Color.WHITE,0);
        OkHttpClient client = new OkHttpClient();
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

        Log.d("==========encode",encode("12345"));
        Log.d("==========decode",decode("qwerty"));
//        typeViewModel = ViewModelProviders.of(this).get(TypeViewModel.class);
//        typeViewModel.feedContentType().observe(this, contentType -> {
//            for(ResultBeanX b :contentType.getResult())
//                 Types.contentType.put(b.getValue(),b.getTag());
//            flag1 = true;
//            check();
//        });
//        typeViewModel.feedMessageType().observe(this, messageType -> {
//            for(ResultBeanX b :messageType.getResult())
//                Types.messageType.put(b.getValue(),b.getTag());
//            flag2 = true;
//            check();
//        });
//        typeViewModel.feedNewsType().observe(this,newsType -> {
//                Types.newsType.addAll(newsType.getResult());
//                flag3 = true;
//                check();
//        });
//        typeViewModel.feedShortVideoType().observe(this,shortVideoType -> {
//              Types.shortType.addAll(shortVideoType.getResult());
//              flag4 = true;
//              check();
//        });
//        typeViewModel.feedVideoType().observe(this,videoType -> {
//            for (ResultBean b :videoType.getResult())
//                Types.videoType.put(b.getName(),b.getChannelId());
//            flag5 = true;
//            check();
//        });

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
        }
        startActivity(new Intent(this, NewsActivity.class));
        finish();
    }

    //    private void check(){
//        if(flag1&&flag2&&flag3&&flag4&&flag5)finish();
//    }

    public native String decode(String code);

    public native String encode(String code);
}
