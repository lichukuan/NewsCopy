package com.person.newscopy.show;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easy.generaltool.ViewUtil;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.easy.generaltool.common.ViewInfoUtil;
import com.person.newscopy.R;
import com.person.newscopy.api.Api;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.MySoftKeyBoardListener;
import com.person.newscopy.common.RedCircleImageView;
import com.person.newscopy.common.ShapeImageView;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.show.adapter.CommentAdapter;
import com.person.newscopy.show.adapter.RecommendAdapter;
import com.person.newscopy.show.net.CommentBean;
import com.person.newscopy.user.Users;
import com.zzhoujay.richtext.RichText;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ShowNewsActivity extends SwipeBackActivity {

    private RecyclerView recommend;

    private RecyclerView comment;

    public static final String SHOW_WEB_INFO="article_info";

    ImageView back;
    ShowViewModel showViewModel;
    Button care;
    TextView detail;
    ResultBean b;
    RedCircleImageView commentIcon;
    RedCircleImageView saveIcon;
    RedCircleImageView likeIcon;
    LinearLayout layout;
    TextView title;
    CardView commentParent;
    TextView noCommentFlag;
    private int isLike = 0;
    private int isSave = 0;
    private int isCare = 0;
    private View normalCommentView;
    private View sendCommentView;
    private EditText commentContent;
    private Button send;
    private CommentAdapter commentAdapter;
    private ShapeImageView shapeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenFitUtil.fit(getApplication(),this,ScreenFitUtil.FIT_WIDTH);
        TranslucentUtil.setTranslucent(this,Color.WHITE, (int) (25*ScreenFitUtil.getDensity()));
        setContentView(R.layout.activity_show);
        commentParent = (CardView) findViewById(R.id.comment_parent);
        SwipeBackLayout mSwipeBackLayout  = getSwipeBackLayout();
        //设置可以滑动的区域，推荐用屏幕像素来指定
        mSwipeBackLayout.setEdgeSize((int) (ViewInfoUtil.ScreenInfo.getScreenWidth(this)/2));
        //设定滑动关闭的方向，SwipeBackLayout.EDGE_ALL表示向下、左、右滑动均可。EDGE_LEFT，EDGE_RIGHT，EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        RichText.initCacheDir(this);
        normalCommentView = getLayoutInflater().inflate(R.layout.comment_normal_view,null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (120*ScreenFitUtil.getDensity()),(int) (40*ScreenFitUtil.getDensity()));
        params.rightMargin = (int) (20*ScreenFitUtil.getDensity());
        params.gravity = Gravity.CENTER_VERTICAL;
        normalCommentView.setLayoutParams(params);
        sendCommentView = getLayoutInflater().inflate(R.layout.comment_send_view,null);
        showViewModel = ViewModelProviders.of(this).get(ShowViewModel.class);
        Intent intent = getIntent();
        b = BaseUtil.getGson().fromJson(intent.getStringExtra(SHOW_WEB_INFO),ResultBean.class);
        layout = (LinearLayout) findViewById(R.id.comment_view);
        send = sendCommentView.findViewById(R.id.comment_send);
        TextView name = (TextView) findViewById(R.id.name);
        shapeImageView = (ShapeImageView) findViewById(R.id.icon);
        noCommentFlag = (TextView) findViewById(R.id.no_comment_flag);
        detail = (TextView) findViewById(R.id.article_detail);
        recommend = (RecyclerView) findViewById(R.id.recommend);
        comment = (RecyclerView) findViewById(R.id.comment);
        commentContent = (EditText) findViewById(R.id.comment_content);
        commentIcon = normalCommentView.findViewById(R.id.comment_icon);
        likeIcon = normalCommentView.findViewById(R.id.like_icon);
        saveIcon = normalCommentView.findViewById(R.id.save_icon);
        title = (TextView) findViewById(R.id.title);
        recommend.setLayoutManager(new LinearLayoutManager(this));
        comment.setLayoutManager(new LinearLayoutManager(this));
        name.setText(b.getUserName());
        Glide.with(this)
                .load(b.getUserIcon())
                .asBitmap()
                .into(shapeImageView);
        title.setText(b.getTitle());
        shapeImageView.setOnClickListener(v->{
            Intent intent1 = new Intent(this, MyActivity.class);
            intent1.putExtra(MyActivity.MY_TYPE,MyActivity.USER_WORK_TYPE);
            startActivity(intent1);
        });
        if (Users.LOGIN_FLAG)
            showViewModel.addHistory(b.getUserId(),b.getId(),b.getType(),Users.userId).observe(this,baseResult -> {
                if (baseResult.getCode() != 1)Log.d("==ShowNewsActivity","上传历史纪录出错了");
            });
        care = (Button) findViewById(R.id.care);
        layout.addView(normalCommentView,1);
        if (b.getUserId().equals(Users.userId))care.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shapeImageView.setOnClickListener(v -> showUserInfo());
        commentIcon.setIcon(R.drawable.pinglun,R.drawable.pinglun_un);
        commentIcon.setShowNumber(true);
        commentIcon.setNumber(b.getCommentCount());

        likeIcon.setIcon(R.drawable.like,R.drawable.like_un);
        likeIcon.setShowNumber(true);
        likeIcon.setNumber(b.getLikeCount());

        likeIcon.setOnStateChangeListener(plus -> {
            if (Users.LOGIN_FLAG){
                String isLike = "true";
                if (plus < 0 )//点赞
                    isLike = "false";
                final String result = isLike;
                showViewModel.like(isLike,Users.userId,b.getId(),b.getUserId()).observe(ShowNewsActivity.this,baseResult -> {
                    if (baseResult.getCode() != 1)
                        Toast.makeText(this, "出错了", Toast.LENGTH_SHORT).show();
                    else if (result.equals("true"))
                        sendMessage(Config.MESSAGE.LIKE_TYPE,Users.userName+"点赞了你的文章《"+b.getTitle()+"》");
                });
            }else
                Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();
        });
        saveIcon.setIcon(R.drawable.enjoy,R.drawable.enjoy_un);
        saveIcon.setOnStateChangeListener(plus -> {
            if (Users.LOGIN_FLAG){
                String isSave = "true";
                if (plus < 0 )
                    isSave = "false";
                final String result = isSave;
                showViewModel.save(isSave,Users.userId,b.getId()).observe(ShowNewsActivity.this,baseResult -> {
                    if (baseResult.getCode() != 1)
                        Toast.makeText(this, "出错了", Toast.LENGTH_SHORT).show();
                    else if (result.equals("true"))
                        sendMessage(Config.MESSAGE.SAVE_TYPE,Users.userName+"收藏了你的文章《"+b.getTitle()+"》");
                });
            }else
                Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();

        });
        care.setOnClickListener(v -> {
            care.setClickable(false);
            if (Users.LOGIN_FLAG) {
                if (isCare != -1){
                    switch (isCare){
                        case 1://已关注
                            isCare = 0;
                            showViewModel.careOrNot(Users.userId,b.getUserId(),false).observe(ShowNewsActivity.this,baseResult -> {
                                if (baseResult.getCode() != Config.SUCCESS){
                                    Toast.makeText(this, "出错了", Toast.LENGTH_SHORT).show();
                                }else
                                    care.setText("关注");
                                care.setClickable(true);
                            });
                            break;
                        case 0:
                            isCare = 1;
                            showViewModel.careOrNot(Users.userId,b.getUserId(),true).observe(ShowNewsActivity.this,baseResult -> {
                                if (baseResult.getCode() != Config.SUCCESS){
                                    Toast.makeText(this, "出错了", Toast.LENGTH_SHORT).show();
                                }else {
                                    care.setText("已关注");
                                    sendMessage(Config.MESSAGE.CARE_TYPE,Users.userName+"关注了你");
                                }
                                care.setClickable(true);
                            });
                            break;
                    }
                }
            }else Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();

        });
        showViewModel.queryArticleDetail(b.getId(), Users.userId == null?"no":Users.userId,b.getUserId()).observe(this, baseResult -> {
            RichText.fromHtml(baseResult.getResult().getDetail())
                    .bind(ShowNewsActivity.this)
                    .into(detail);
            isLike = baseResult.getResult().getIsLike();
            isSave = baseResult.getResult().getIsSave();
            isCare = baseResult.getResult().getIsCare();
            Log.d("====","isLike = "+isLike+"  isSave = "+isSave);
            if (isLike == 1)likeIcon.changeIcon();
            if (isCare == 1)care.setText("已关注");
            if (isSave == 1)saveIcon.changeIcon();
        });
        showViewModel.feedNewsRecommend(b.getType(),b.getTag()).observe(this,contentData->{
            RecommendAdapter adapter = new RecommendAdapter(contentData.getResult(),ShowNewsActivity.this);
            recommend.setAdapter(adapter);
        });
        showViewModel.feedComment(b.getId()).observe(this,commentResult -> {
            final List<CommentBean> result = commentResult.getResult();
            if (result.size() > 0)noCommentFlag.setVisibility(View.GONE);
            commentIcon.setNumber(result.size());
            commentAdapter = new CommentAdapter(result,this);
            comment.setAdapter(commentAdapter);
            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).getUserId().equals(Users.userId)){
                    commentIcon.changeIcon();
                    break;
                }
            }
            commentIcon.setFixed(true);
        });
        commentContent.setOnFocusChangeListener((v, hasFocus) -> {
            layout.removeViewAt(1);
            if (hasFocus){
                layout.addView(sendCommentView,1);

            }else {
                layout.addView(normalCommentView,1);
            }
        });
        send.setOnClickListener(v -> {
            if (Users.LOGIN_FLAG){
                final String val = commentContent.getText().toString();
                if (val.equals("")) Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                else
                    showViewModel.addComment(Users.userId,b.getId(),commentContent.getText().toString(),Config.CONTENT.COMMENT_TYPE,b.getId()).observe(this,baseResult -> {
                        send.setClickable(false);
                        if (baseResult.getCode() == Config.SUCCESS){
                            CommentBean commentBean = new CommentBean();
                            commentBean.setContent(commentContent.getText().toString());
                            commentBean.setContentId(b.getId());
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
                            sendMessage(Config.MESSAGE.COMMENT_TYPE,val);
                            noCommentFlag.setVisibility(View.GONE);
                        }else
                            Toast.makeText(this, "发送失败", Toast.LENGTH_SHORT).show();
                        send.setClickable(true);
                    });
            }else
                Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();

        });
        MySoftKeyBoardListener.setListener(this, new MySoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
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
    }

    private void sendMessage(int type,String content){
       showViewModel.addMessage(b.getUserId(),type,Users.userId,content,b.getImage(),b.getId()).observe(this,baseResult -> {
            if (baseResult.getCode() != Config.SUCCESS)Log.d("==ShowNewsActivity","上传消息出错了");
       });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichText.clear(ShowNewsActivity.this);
    }

    private void showUserInfo(){
        Intent intent = new Intent(this,MyActivity.class);
        intent.putExtra(MyActivity.MY_TYPE,MyActivity.USER_WORK_TYPE);
        intent.putExtra(MyActivity.SEARCH_KEY,b.getUserId());
        startActivity(intent);
    }
}
