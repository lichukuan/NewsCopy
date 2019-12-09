package com.person.newscopy.my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.person.newscopy.R;
import com.person.newscopy.my.MyActivity;

//显示收藏、评论、点赞、历史、推送
@Deprecated
public class MyOperateFragment extends Fragment{

    public static final String[] TAB = {"收藏","评论","点赞","历史","推送"};
    TextView edit;
    ImageButton back;
    TabLayout tab;
    ViewPager pager;
    MyActivity myActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_operate,container,false);
        myActivity = (MyActivity) getActivity();
        edit = view.findViewById(R.id.edit);
        back = view.findViewById(R.id.back);
        tab = view.findViewById(R.id.my_tab);
        pager = view.findViewById(R.id.my_pager);
        for (int i = 0; i < TAB.length; i++) {
            tab.addTab(tab.newTab().setText(TAB[i]));
        }
        Gson gson = new Gson();
//        UserInfoBean userInfoBean = gson.fromJson(BaseUtil.readFromFile(LoginFragment.USER_ALL_INFO_STORE_FILE_NAME),UserInfoBean.class);
//        List<ReadBean> readBeans = userInfoBean.getResult().getRead();
//        List<CommentsBean> commentsBeans = userInfoBean.getResult().getComments();
//        userInfoBean = null;
//        tab.setupWithViewPager(pager);
//        pager.setAdapter(new OperateFragmentAdapter(getChildFragmentManager(),readBeans,commentsBeans));
//        pager.setCurrentItem(0);
//        back.setOnClickListener(v->myActivity.finish());
        return view;
    }
}
