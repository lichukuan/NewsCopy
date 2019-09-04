package com.person.newscopy.show.fragment;


import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.person.newscopy.R;
import com.person.newscopy.news.network.shortBean.ShortInfoBean;
import com.person.newscopy.show.ShowShortVideoActivity;
import com.person.newscopy.show.adapter.CommentAdapter;

import java.io.IOException;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class ShortVideoVerticalFragment extends Fragment implements ShowShortVideoActivity.TimeListener{

    private String url;
    private IjkMediaPlayer ijkMediaPlayer;
    private SurfaceView videoView;
    private LinearLayout layout;
    private ImageView play;
    private TextView allTime;
    private TextView throughTime;
    private ImageView toHor;
    private SeekBar seekBar;
    private boolean isPlaying = true;
    private boolean isShow = false;
    private int duration;
    private RecyclerView comment;
    ShowShortVideoActivity activity;
    private TextView title;
    private ImageView icon;
    private TextView author;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_vertical,container,false);
        videoView = view.findViewById(R.id.video_view);
        activity = (ShowShortVideoActivity) getActivity();
        activity.setListener(this);
        layout = view.findViewById(R.id.video_control);
        play =view.findViewById(R.id.pause);
        throughTime =view.findViewById(R.id.time_current);
        allTime=view.findViewById(R.id.time);
        toHor=view.findViewById(R.id.to_hor);
        seekBar=view.findViewById(R.id.seek_bar);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (ijkMediaPlayer!=null&&fromUser){
                    Log.d("=========","progress"+progress);
                    long newTime = progress*ijkMediaPlayer.getDuration()/100+ijkMediaPlayer.getCurrentPosition();
                    ijkMediaPlayer.seekTo(newTime);
                    activity.setNowDuration(progress*duration/100);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        toHor.setOnClickListener(v->activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE));
        ShortInfoBean bean = activity.getBean();
        comment=view.findViewById(R.id.comments);
        comment.setLayoutManager(new LinearLayoutManager(getContext()));
        comment.setAdapter(new CommentAdapter(bean.getComments(),this));
        ijkMediaPlayer = activity.getIjkMediaPlayer();
        url = bean.getVideoUrl();
        duration = toSecondTime(bean.getDuration());
        videoView.getHolder().addCallback(callback);
        allTime.setText(bean.getDuration());
        layout.setVisibility(View.INVISIBLE);
        videoView.setOnClickListener(v -> {
            if (isShow) layout.setVisibility(View.INVISIBLE);
            else layout.setVisibility(View.VISIBLE);
            isShow=!isShow;
        });
        play.setOnClickListener(v ->{
            if (isPlaying){
                pause();
                play.setImageResource(R.drawable.show_video_play);
            }
            else {
                start();
                play.setImageResource(R.drawable.show_video_pause);
            }
            isPlaying = !isPlaying;
        });
        title = view.findViewById(R.id.title);
        icon=view.findViewById(R.id.icon);
        author=view.findViewById(R.id.author);
        title.setText(bean.getTitle());
        Glide.with(this)
                .load(bean.getIconUrl())
                .asBitmap()
                .into(icon);
        author.setText(bean.getAuthor());
        new newInit().start();
        return view;
    }

  private int toSecondTime(String t){
        String[] p = t.split(":");
        int minute = Integer.valueOf(p[0]);
        int second = Integer.valueOf(p[1]);
        Log.d("===========","t = "+t+"  minute = "+minute+"  second = "+second);
        return minute*60+second;
  }

    private String formatTime(int time){
        int minute = time/60;
        StringBuilder res = new StringBuilder();
        if(minute<9)res.append("0"+minute);else res.append(minute);
        int second = time%60;
        res.append(":");
        if(second<9)res.append("0"+second);else res.append(second);
        return res.toString();
    }

    private void InitIjk(){
        if (ijkMediaPlayer.getDataSource()!=null)return;
        try {
            ijkMediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ijkMediaPlayer.prepareAsync();
        ijkMediaPlayer.setDisplay(videoView.getHolder());  //将视频画面输出到surface上
        ijkMediaPlayer.start();
    }

    public void start() {
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.start();
        }
    }


    public void pause() {
        if (ijkMediaPlayer != null) {
            ijkMediaPlayer.pause();
        }
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            ijkMediaPlayer.setDisplay(surfaceHolder);
        }
        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {         }
        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {         }
    };

    @Override
    public void now(int now) {
        throughTime.setText(formatTime(now));
        int progress = now*100/duration;
        seekBar.setProgress(progress);
    }

    private  class  newInit extends  Thread{
        @Override
        public void run() {
            InitIjk();
            super.run();
        }
    }
}
