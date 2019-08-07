package com.person.newscopy.show;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.person.newscopy.R;
import com.person.newscopy.common.ShortVideoView;
import com.person.newscopy.news.network.shortBean.ShortInfoBean;

import retrofit2.converter.gson.GsonConverterFactory;

public class ShortVideoActivity extends AppCompatActivity {

    public static final String SHORT_VIDEO_INFO_KEY = "short_video_info_key";

    ShortVideoView videoView;
    ImageView close;
    TextView title;
    ImageView authorIcon;
    TextView authorName;
    TextView like;
    boolean isStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_video);
        videoView = findViewById(R.id.video);
        close=findViewById(R.id.short_cancel);
        title=findViewById(R.id.short_video_title);
        authorIcon=findViewById(R.id.author_icon);
        authorName=findViewById(R.id.author_name);
        like=findViewById(R.id.short_like);
        Intent intent = getIntent();
        Gson gson =new Gson();
        ShortInfoBean bean = gson.fromJson(intent.getStringExtra(SHORT_VIDEO_INFO_KEY),ShortInfoBean.class);
        title.setText(bean.getTitle());
        like.setText(bean.getLike());
        Glide.with(this)
                .load(bean.getIconUrl())
                .into(authorIcon);
        authorName.setText(bean.getAuthor());
        close.setOnClickListener(v -> finish());
        Log.d("===========",bean.getVideoUrl());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        videoView.setVideoSize(screenWidth,screenHeight);
        videoView.setVideoPath(bean.getVideoUrl());
        videoView.requestFocus();
        videoView.start();
        videoView.setOnClickListener(v->{
            if(isStart)
                videoView.pause();
            else
                videoView.start();
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
