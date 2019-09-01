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

public class UserFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)//如果为Android 5之后的版本
        ViewUtil.Translucent.applyGradualTranslucent(getActivity(), R.color.welcome_while);
        View view = inflater.inflate(R.layout.fragment_main_person,container,false);
        view.findViewById(R.id.all).setOnClickListener(v -> startActivity(new Intent(getContext(), MyActivity.class)));
        return view;
    }
}
