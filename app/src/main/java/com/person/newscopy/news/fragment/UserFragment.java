package com.person.newscopy.news.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.MyTranslucentUtil;
import com.person.newscopy.common.ShapeImageView;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.MyListViewLayout;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.user.Users;

public class UserFragment extends Fragment {

    public static final String[] content = {"消息通知","我的收藏","发布文章","阅读纪录","私信"};

    public static final int[] res = {R.drawable.ic_notification,R.drawable.user_collectionset,R.drawable.bianxie,R.drawable.history
    ,R.drawable.tab_feidian};

    public static final String[] content2 = {"用户反馈","设置"};

    public static final int[] res2 = {R.drawable.icon_feed_back,R.drawable.settings};

    View loginView;
    View unloginView;
    LinearLayout head;
    boolean beforeFlag = false;
    ShapeImageView shape ;
    TextView userName ;
    TextView userRelease ;
    TextView userCare ;
    TextView userFans ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_person_base_login,container,false);
        MyTranslucentUtil.setTranslucent(getActivity(),Color.WHITE, (int) (25* ScreenFitUtil.getDensity()));
        head = view.findViewById(R.id.head);
        loginView = inflater.inflate(R.layout.login_head_view,container,false);
        unloginView = inflater.inflate(R.layout.un_login_view,container,false);
        unloginView.findViewById(R.id.to_login).setOnClickListener(v ->showUser(MyActivity.LOGIN_TYPE,null));
        MyListViewLayout listViewLayout = view.findViewById(R.id.my_list);
        listViewLayout.setContent(content,res);
        MyListViewLayout listViewLayout1 = view.findViewById(R.id.my_list_2);
        listViewLayout1.setContent(content2,res2);
        shape = loginView.findViewById(R.id.icon);
        userName = loginView.findViewById(R.id.release_user_name);
        userRelease = loginView.findViewById(R.id.user_work);
        userCare = loginView.findViewById(R.id.user_care);
        userFans = loginView.findViewById(R.id.user_fans);
        if (Users.LOGIN_FLAG) {//已经登陆
           head.addView(loginView);
           beforeFlag = true;
           initLoginData();
        }else {
            head.addView(unloginView);
            beforeFlag = false;
        }
        return view;
    }

    private void initLoginData(){
        shape.setOnClickListener(v->showUser(MyActivity.MY_INFO_TYPE,null));
        userName.setOnClickListener(v -> showUser(MyActivity.MY_INFO_TYPE,null));
        userRelease.setOnClickListener(v -> showUser(MyActivity.USER_WORK_TYPE,Users.userId));
        userCare.setOnClickListener(v -> showUser(MyActivity.CARE_TYPE,null));
        userFans.setOnClickListener(v -> showUser(MyActivity.FANS_TYPE,null));
        loginView.setOnClickListener(v->showUser(MyActivity.USER_WORK_TYPE,Users.userId));
        Glide.with(this)
                .load(Users.userIcon)
                .asBitmap()
                .into(shape);
        userName.setText(Users.userName);
        userCare.setText(Users.userCare+"关注");
        userFans.setText(Users.userFans+"粉丝");
        userRelease.setText(Users.userWork+"投稿");
    }

    private void showUser(int type,String value){
        Intent i = new Intent(getContext(), MyActivity.class);
        i.putExtra(MyActivity.MY_TYPE,type);
        if (value !=null)
            i.putExtra(MyActivity.SEARCH_KEY,value);
        startActivity(i);
    }

    private static final String TAG = "UserFragment";
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume()调用");
        if (beforeFlag != Users.LOGIN_FLAG){
            head.removeAllViews();
            beforeFlag=!beforeFlag;
            if (Users.LOGIN_FLAG)
                head.addView(loginView);
            else head.addView(unloginView);
        }
        if (Users.LOGIN_FLAG){
            initLoginData();
        }
    }
}
