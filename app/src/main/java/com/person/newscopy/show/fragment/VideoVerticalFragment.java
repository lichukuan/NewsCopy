package com.person.newscopy.show.fragment;

import android.arch.lifecycle.Observer;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.MySoftKeyBoardListener;
import com.person.newscopy.common.RedCircleImageView;
import com.person.newscopy.common.SmallLoadView;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.show.ShowVideoActivity;
import com.person.newscopy.show.adapter.CommentAdapter;
import com.person.newscopy.show.net.bean.CommentBean;
import com.person.newscopy.show.net.bean.MessageCommentBean;
import com.person.newscopy.show.net.bean.MessageSaveAndLikeBean;
import com.person.newscopy.show.net.bean.MessageUserBean;
import com.person.newscopy.show.net.bean.NewAttitudeBean;
import com.person.newscopy.user.Users;

import java.io.IOException;
import java.util.List;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoVerticalFragment extends Fragment implements ShowVideoActivity.TimeListener{

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
    ShowVideoActivity activity;
    private TextView title;
    private ImageView icon;
    private TextView author;
    private Button care;
    private View normalCommentView;
    private View sendCommentView;
    private ResultBean bean;
    RedCircleImageView commentIcon;
    RedCircleImageView saveIcon;
    RedCircleImageView likeIcon;
    LinearLayout commentLayout;
    private Button send;
    private int isLike = -1;
    private int isSave = -1;
    private int isCare = -1;
    private TextView noCommentFlag;
    private EditText commentContent;
    private CommentAdapter commentAdapter;
    private SmallLoadView smallLoadView;
    CardView commentParent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_vertical,container,false);
        TranslucentUtil.setTranslucent(getActivity(), Color.TRANSPARENT, 0);
        videoView = view.findViewById(R.id.video_view);
        commentParent = view.findViewById(R.id.comment_parent);
        activity = (ShowVideoActivity) getActivity();
        activity.setListener(this);
        normalCommentView = inflater.inflate(R.layout.comment_normal_view,null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (120* ScreenFitUtil.getDensity()),(int) (40*ScreenFitUtil.getDensity()));
        params.rightMargin = (int) (20*ScreenFitUtil.getDensity());
        params.gravity = Gravity.CENTER_VERTICAL;
        normalCommentView.setLayoutParams(params);
        sendCommentView = inflater.inflate(R.layout.comment_send_view,null);
        layout = view.findViewById(R.id.video_control);
        play =view.findViewById(R.id.pause);
        smallLoadView = view.findViewById(R.id.load);
        throughTime =view.findViewById(R.id.time_current);
        allTime=view.findViewById(R.id.time);
        toHor=view.findViewById(R.id.to_hor);
        noCommentFlag = view.findViewById(R.id.no_comment_flag);
        seekBar=view.findViewById(R.id.seek_bar);
        care = view.findViewById(R.id.care);
        title = view.findViewById(R.id.title);
        icon=view.findViewById(R.id.icon);
        author=view.findViewById(R.id.author);
        comment=view.findViewById(R.id.comments);
        bean = activity.getBean();
        commentLayout = view.findViewById(R.id.comment_view);
        commentIcon = normalCommentView.findViewById(R.id.comment_icon);
        likeIcon = normalCommentView.findViewById(R.id.like_icon);
        saveIcon = normalCommentView.findViewById(R.id.save_icon);
        send = sendCommentView.findViewById(R.id.comment_send);
        commentContent = view.findViewById(R.id.comment_content);
        if (bean.getUserId().equals(Users.userId))care.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Users.LOGIN_FLAG)

        icon.setOnClickListener(v -> activity.showUserInfo());
        commentIcon.setIcon(R.drawable.pinglun,R.drawable.pinglun_un);
        commentIcon.setShowNumber(true);
        commentIcon.setNumber(bean.getCommentCount());

        likeIcon.setIcon(R.drawable.like,R.drawable.like_un);
        likeIcon.setShowNumber(true);
        likeIcon.setNumber(bean.getLikeCount());
        likeIcon.setOnStateChangeListener(plus -> {
            if (Users.LOGIN_FLAG){
                String isLike = "true";
                if (plus < 0 )//点赞
                    isLike = "false";
                activity.like(isLike,Users.userId,bean.getId(),bean.getUserId()).observe(this, baseResult -> {
                    if (baseResult.getCode() != 1)
                        Toast.makeText(getContext(), "出错了", Toast.LENGTH_SHORT).show();
                    else {
                        MessageSaveAndLikeBean messageSaveAndLikeBean = new MessageSaveAndLikeBean();
                        messageSaveAndLikeBean.setData(BaseUtil.getGson().toJson(bean));
                        messageSaveAndLikeBean.setIcon(Users.userIcon);
                        messageSaveAndLikeBean.setUserId(Users.userId);
                        messageSaveAndLikeBean.setRecommend(Users.userRecommend);
                        activity.sendMessage(Users.userName+"赞了您的视频 "+bean.getTitle(),Config.MESSAGE.LIKE_TYPE,BaseUtil.getGson().toJson(messageSaveAndLikeBean));
                    }
                });
            }else Toast.makeText(getContext(), "请先登陆", Toast.LENGTH_SHORT).show();
        });
        saveIcon.setIcon(R.drawable.enjoy,R.drawable.enjoy_un);
        saveIcon.setOnStateChangeListener(plus -> {
            if (Users.LOGIN_FLAG){
                String isSave = "true";
                if (plus < 0 )//点赞
                    isSave = "false";
                activity.save(isSave,Users.userId,bean.getId()).observe(this,baseResult -> {
                    if (baseResult.getCode() != 1)
                        Toast.makeText(getContext(), "出错了", Toast.LENGTH_SHORT).show();
                    else {
                        MessageSaveAndLikeBean messageSaveAndLikeBean = new MessageSaveAndLikeBean();
                        messageSaveAndLikeBean.setData(BaseUtil.getGson().toJson(bean));
                        messageSaveAndLikeBean.setIcon(Users.userIcon);
                        messageSaveAndLikeBean.setUserId(Users.userId);
                        messageSaveAndLikeBean.setRecommend(Users.userRecommend);
                        activity.sendMessage(Users.userName+"收藏了您的视频 "+bean.getTitle(),Config.MESSAGE.SAVE_TYPE,BaseUtil.getGson().toJson(messageSaveAndLikeBean));
                    }
                });
            }else
                Toast.makeText(getContext(), "请先登陆", Toast.LENGTH_SHORT).show();

        });
        care.setOnClickListener(v -> {
            care.setClickable(false);
            if (Users.LOGIN_FLAG) {
                if (isCare != -1){
                    switch (isCare){
                        case 1://已关注
                            isCare = 0;
                            activity.careOrNot(Users.userId,bean.getUserId(),false).observe(this,baseResult -> {
                                if (baseResult.getCode() != Config.SUCCESS){
                                    Toast.makeText(getContext(), "出错了", Toast.LENGTH_SHORT).show();
                                }else
                                    care.setText("关注");
                                care.setClickable(true);
                            });
                            break;
                        case 0:
                            isCare = 1;
                            activity.careOrNot(Users.userId,bean.getUserId(),true).observe(this,baseResult -> {
                                if (baseResult.getCode() != Config.SUCCESS){
                                    Toast.makeText(getContext(), "出错了", Toast.LENGTH_SHORT).show();
                                }else {
                                    care.setText("已关注");
                                    MessageUserBean messageUserBean = new MessageUserBean();
                                    messageUserBean.setUserId(Users.userId);
                                    messageUserBean.setIcon(Users.userIcon);
                                    messageUserBean.setRecommend(Users.userRecommend);
                                    activity.sendMessage(Users.userName+"关注了您",Config.MESSAGE.CARE_TYPE,BaseUtil.getGson().toJson(messageUserBean));
                                }
                                care.setClickable(true);
                            });
                            break;
                    }
                }
            }else {
                Toast.makeText(getContext(), "请先登陆", Toast.LENGTH_SHORT).show();
                care.setClickable(true);
            }

        });
        activity.queryNewAttitude(Users.userId,bean.getId(),bean.getUserId()).observe(this, newAttitudeBean -> {
            isCare = newAttitudeBean.getResult().getIsCare();
            isLike = newAttitudeBean.getResult().getIsLike();
            isSave = newAttitudeBean.getResult().getIsSave();
            if (isLike == 1)likeIcon.changeIcon();
            if (isCare == 1)care.setText("已关注");
            if (isSave == 1)saveIcon.changeIcon();
        });
        commentContent.setOnFocusChangeListener((v, hasFocus) -> {
            layout.removeViewAt(1);
            if (hasFocus){
                layout.addView(sendCommentView,1);
            }else {
                layout.addView(normalCommentView,1);
            }
        });
        commentLayout.addView(normalCommentView);
        commentContent.setOnFocusChangeListener((v, hasFocus) -> {
            commentLayout.removeViewAt(1);
            if (hasFocus){
                commentLayout.addView(sendCommentView,1);
            }else {
                commentLayout.addView(normalCommentView,1);
            }
        });
        send.setOnClickListener(v -> {
            if (Users.LOGIN_FLAG){
                final String val = commentContent.getText().toString();
                if (val.equals("")) Toast.makeText(getContext(), "不能为空", Toast.LENGTH_SHORT).show();
                else
                    activity.addComment(Users.userId,bean.getId(),commentContent.getText().toString(),Config.CONTENT.COMMENT_TYPE,bean.getId()).observe(this,baseResult -> {
                        send.setClickable(false);
                        if (baseResult.getCode() == Config.SUCCESS){
                            CommentBean commentBean = new CommentBean();
                            commentBean.setContent(val);
                            commentBean.setContentId(bean.getId());
                            commentBean.setIcon(Users.userIcon);
                            commentBean.setId(baseResult.getResult());
                            commentBean.setLikeNum(0);
                            commentBean.setName(Users.userName);
                            commentBean.setReplyNum(0);
                            commentBean.setUserId(Users.userId);
                            commentBean.setToType(Config.CONTENT.NEWS_TYPE);
                            commentBean.setReleaseTime((int) BaseUtil.getTime());
                            commentBean.setTime(BaseUtil.getFormatTime());
                            commentAdapter.addComment(commentBean);
                            commentContent.setText("");
                            noCommentFlag.setVisibility(View.GONE);
                            MessageCommentBean messageCommentBean = new MessageCommentBean();
                            messageCommentBean.setUserId(Users.userId);
                            messageCommentBean.setIcon(Users.userIcon);
                            messageCommentBean.setArticleData(BaseUtil.getGson().toJson(bean));
                            messageCommentBean.setCommentContent(val);
                            activity.sendMessage(Users.userName+"评论了您的视频 "+bean.getTitle(),Config.MESSAGE.COMMENT_TYPE,BaseUtil.getGson().toJson(messageCommentBean));
                        }else
                            Toast.makeText(getContext(), "发送失败", Toast.LENGTH_SHORT).show();
                        send.setClickable(true);
                    });
            }else
                Toast.makeText(getContext(), "请先登陆", Toast.LENGTH_SHORT).show();

        });
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (ijkMediaPlayer!=null&&fromUser){
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
        comment.setLayoutManager(new LinearLayoutManager(getContext()));
        activity.queryComment(activity.getBean().getId()).observe(this,commentResult -> {
            List<CommentBean> l =  commentResult.getResult();
            if (commentAdapter == null)
                commentAdapter = new CommentAdapter(l,this);
            if (l.size() > 0){
                noCommentFlag.setVisibility(View.GONE);
                commentIcon.setNumber(l.size());
            }
            comment.setAdapter(commentAdapter);//评论
            for (int i = 0; i < l.size(); i++) {
                if (l.get(i).getUserId().equals(Users.userId)){
                    commentIcon.changeIcon();
                    break;
                }
            }
        });
        ijkMediaPlayer = activity.getIjkMediaPlayer();
        ijkMediaPlayer.setOnPreparedListener(iMediaPlayer -> {
             smallLoadView.setVisibility(View.GONE);
             smallLoadView.cancel();
        });
        url = bean.getVideoUrl();
        duration = bean.getSecondTime();
        videoView.getHolder().addCallback(callback);
        allTime.setText(bean.getTime());
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
        title.setText(bean.getTitle());
        MySoftKeyBoardListener.setListener(activity, new MySoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                commentParent.setTranslationY(-height);
            }

            @Override
            public void keyBoardHide(int height) {
                commentParent.setTranslationY(0);
                commentContent.setFocusable(false);
                commentContent.setFocusableInTouchMode(true);
            }
        });
        Glide.with(this)
                .load(bean.getUserIcon())
                .asBitmap()
                .into(icon);
        author.setText(bean.getUserName());
        new newInit().start();
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
