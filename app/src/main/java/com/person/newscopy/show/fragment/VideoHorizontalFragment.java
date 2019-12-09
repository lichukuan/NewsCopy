package com.person.newscopy.show.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.person.newscopy.R;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.show.ShowVideoActivity;
import java.io.IOException;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoHorizontalFragment extends Fragment implements ShowVideoActivity.TimeListener{

    private String url;
    private IjkMediaPlayer ijkMediaPlayer;
    private SurfaceView videoView;
    private LinearLayout layout;
    private ImageView play;
    private TextView allTime;
    private TextView throughTime;
    private ImageView toVer;
    private ImageView back;
    private SeekBar seekBar;
    private TextView title;
    private boolean isPlaying = true;
    private boolean isShow = false;
    private int duration;
    private LinearLayout info;
    ShowVideoActivity activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_horizontal,container,false);
        activity = (ShowVideoActivity) getActivity();
        activity.setListener(this);
        videoView = view.findViewById(R.id.video_view);
        layout = view.findViewById(R.id.video_control);
        info=view.findViewById(R.id.video_info);
        play =view.findViewById(R.id.pause);
        throughTime =view.findViewById(R.id.time_current);
        allTime=view.findViewById(R.id.time);
        back =view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        toVer=view.findViewById(R.id.to_ver);
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
        toVer.setOnClickListener(v -> activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT));
        back.setOnClickListener(v -> activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT));
        ResultBean bean = activity.getBean();
        ijkMediaPlayer=activity.getIjkMediaPlayer();
        url = bean.getVideoUrl();
        duration = bean.getSecondTime();
        title.setText(bean.getTitle());
        videoView.getHolder().addCallback(callback);
        allTime.setText(bean.getTitle());
        videoView.setOnClickListener(v -> {
            Log.d("===========","点击");
            if (isShow) {
                layout.setVisibility(View.INVISIBLE);
                info.setVisibility(View.INVISIBLE);
            } else {
                layout.setVisibility(View.VISIBLE);
                info.setVisibility(View.VISIBLE);
            }
            isShow=!isShow;
        });
        new newInit().start();
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
        //隐藏状态栏
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        return view;
    }
    private int toSecondTime(String t){
        String[] p = t.split(":");
        int minute = Integer.valueOf(p[0]);
        int second = Integer.valueOf(p[1]);
        //Log.d("===========","t = "+t+"  minute = "+minute+"  second = "+second);
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
        //Log.d("=========","now_duration = "+now_duration+"  nowProgress = "+progress+"  duration = "+duration);
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
