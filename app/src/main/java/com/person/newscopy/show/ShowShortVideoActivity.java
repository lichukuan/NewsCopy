package com.person.newscopy.show;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.easy.generaltool.ViewUtil;
import com.google.gson.Gson;
import com.person.newscopy.R;
import com.person.newscopy.news.network.shortBean.ShortInfoBean;
import com.person.newscopy.show.fragment.ShortVideoHorizontalFragment;
import com.person.newscopy.show.fragment.ShortVideoVerticalFragment;

import java.io.IOException;

import retrofit2.converter.gson.GsonConverterFactory;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class ShowShortVideoActivity extends AppCompatActivity {

    public static final String SHORT_VIDEO_INFO_KEY = "short_video_info_key";
    ShortVideoHorizontalFragment horizontalFragment;
    ShortVideoVerticalFragment verticalFragment;
    public static int state = 2;

    public static final int STATE_HORIZONTAL = 1;

    public static final int STATE_VERTICAL = 2;
    private IjkMediaPlayer ijkMediaPlayer;
    ShortInfoBean bean;
    Handler handler = new Handler();
    private int now_duration = 0;
    private TimeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)//如果为Android 5之后的版本
            ViewUtil.Translucent.applyGradualTranslucent(this,R.color.tool_bar_red);
        ViewUtil.FitScreen.setCustomActivityDensity(this);
        setContentView(R.layout.activity_short_video);
        initIjk();
        Intent intent = getIntent();
        Gson gson =new Gson();
        bean = gson.fromJson(intent.getStringExtra(SHORT_VIDEO_INFO_KEY),ShortInfoBean.class);
        horizontalFragment = new ShortVideoHorizontalFragment();
        verticalFragment = new ShortVideoVerticalFragment();
        addFragment(verticalFragment);
    }
    Runnable time = new Runnable() {
        @Override
        public void run() {
            if (ijkMediaPlayer!=null&&ijkMediaPlayer.isPlaying()){
                listener.now(++now_duration);
            }
            handler.postDelayed(this,1000);
        }
    };

    public void setListener(TimeListener listener) {
        this.listener = listener;
    }

    private void initIjk(){
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        IjkMediaPlayer.native_profileBegin("libijkffmpeg.so");
        IjkMediaPlayer.native_profileBegin("libijksdl.so");
        ijkMediaPlayer = new IjkMediaPlayer();
        handler.postDelayed(time,1000);
    }


    public interface TimeListener{
        void now(int now);
    }

    public void setNowDuration(int now_duration) {
        this.now_duration = now_duration;
    }

    public void release() {
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.reset();
            ijkMediaPlayer.release();
            ijkMediaPlayer = null;
        }
        handler.removeCallbacks(time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        release();
    }

    public IjkMediaPlayer getIjkMediaPlayer() {
        return ijkMediaPlayer;
    }

    public ShortInfoBean getBean() {
        return bean;
    }

    private void addFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content,fragment);
        transaction.commit();
    }

    private void replace(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }

    public void change(){
        if (state == STATE_HORIZONTAL){
            replace(verticalFragment);
            state=STATE_VERTICAL;
        }else{
            replace(horizontalFragment);
            state=STATE_HORIZONTAL;
        }
    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("===============","onConfigurationChanged调用");
        change();
        super.onConfigurationChanged(newConfig);
    }

}
