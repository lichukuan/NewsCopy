package com.person.newscopy.show;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.easy.generaltool.common.ScreenFitUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.show.fragment.VideoHorizontalFragment;
import com.person.newscopy.show.fragment.VideoVerticalFragment;
import com.person.newscopy.show.net.bean.CommentResult;
import com.person.newscopy.show.net.bean.NewAttitudeBean;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.BaseResult;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class ShowVideoActivity extends AppCompatActivity {

    public static final String SHORT_VIDEO_INFO_KEY = "short_video_info_key";
    VideoHorizontalFragment horizontalFragment;
    VideoVerticalFragment verticalFragment;
    public static int state = 2;
    public static final int STATE_HORIZONTAL = 1;
    public static final int STATE_VERTICAL = 2;
    private IjkMediaPlayer ijkMediaPlayer;
    private ResultBean bean;
    Handler handler = new Handler();
    private int now_duration = 0;
    private TimeListener listener;
    private ShowViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenFitUtil.fit(getApplication(),this,ScreenFitUtil.FIT_WIDTH);
        setContentView(R.layout.activity_short_video);
        viewModel = ViewModelProviders.of(this).get(ShowViewModel.class);
        initIjk();
        Intent intent = getIntent();
        bean = BaseUtil.getGson().fromJson(intent.getStringExtra(SHORT_VIDEO_INFO_KEY),ResultBean.class);
        if (Users.LOGIN_FLAG)
            viewModel.addHistory(bean.getUserId(),bean.getId(),bean.getType(),Users.userId).observe(this,baseResult -> {
                if (baseResult.getCode() != 1)Log.d("==ShowNewsActivity","上传历史纪录出错了");
            });
        horizontalFragment = new VideoHorizontalFragment();
        verticalFragment = new VideoVerticalFragment();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (!ijkMediaPlayer.isPlaying())ijkMediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ijkMediaPlayer.isPlaying())
        ijkMediaPlayer.pause();
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

    public ResultBean getBean() {
        return bean;
    }

    private void addFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.name,fragment);
        transaction.commit();
    }

    private void replace(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.name,fragment);
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

    public LiveData<CommentResult> queryComment(String contentId){
        return viewModel.feedComment(contentId);
    }

    public LiveData<BaseResult> addComment(String userId,String contentId,
                                           String content,int contentType,String toId){
        return viewModel.addComment(userId, contentId,content,contentType,toId);
    }

    public LiveData<BaseResult> careOrNot(String userId,String careUserId,boolean isCare){
        return viewModel.careOrNot(userId,careUserId,isCare);
    }

    public void sendMessage(String title,int type,String content){
        viewModel.addMessage(bean.getUserId(),type,Users.userId,content,title).observe(this,baseResult -> {
            if (baseResult.getCode() != Config.SUCCESS)Log.d("==ShowVideoActivity","上传消息出错了");
        });
    }

    public  LiveData<BaseResult> like(String isLike, String userId, String contentId, String releaseUserId){
      return viewModel.like(isLike,userId,contentId,releaseUserId);
    }

    public LiveData<BaseResult> save(String isSave, String userId, String contentId){
        return viewModel.save(isSave,userId,contentId);
    }

    public LiveData<NewAttitudeBean> queryNewAttitude(String userId,String contentId,String contentUserId){
        return viewModel.queryNewAttitude(userId,contentId,contentUserId);
    }

    public void showUserInfo(){
        Intent intent = new Intent(this,MyActivity.class);
        intent.putExtra(MyActivity.MY_TYPE,MyActivity.USER_WORK_TYPE);
        intent.putExtra(MyActivity.SEARCH_KEY,bean.getUserId());
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        change();
        super.onConfigurationChanged(newConfig);
    }
}
