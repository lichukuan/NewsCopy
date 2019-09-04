package com.person.newscopy.news.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easy.generaltool.ViewUtil;
import com.person.newscopy.R;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.MyListViewLayout;

public class UserFragment extends Fragment {

    public static final String[] content = {"消息通知","私信","我的收藏","阅读纪录","我的钱包","用户反馈","免流量服务","系统设置","作品代理"};

    public static final int[] res = {R.drawable.my_message,R.drawable.my_private_message,R.drawable.my_shoucang,R.drawable.my_history
            ,R.drawable.my_qianbao,R.drawable.my_fankui,R.drawable.my_liuliang,R.drawable.my_set,R.drawable.my_shu};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)//如果为Android 5之后的版本
        ViewUtil.Translucent.applyGradualTranslucent(getActivity(), R.color.welcome_while);
        View view = inflater.inflate(R.layout.fragment_main_person,container,false);
        MyListViewLayout listViewLayout = view.findViewById(R.id.my_list);
        listViewLayout.setContent(content,res);
        view.findViewById(R.id.all).setOnClickListener(v -> startActivity(new Intent(getContext(), MyActivity.class)));
        return view;
    }
}
