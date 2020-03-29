package com.person.newscopy.my.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.person.newscopy.R;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.user.Users;

public class LoginFragment extends Fragment {

    EditText userName;
    EditText userPas;
    ImageButton login;
    MyActivity activity;
    TextView toRegister;
    TextView forgetPas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_login,container,false);
        activity = (MyActivity) getActivity();
        toRegister = view.findViewById(R.id.to_register);
        userName = view.findViewById(R.id.release_user_name);
        userPas = view.findViewById(R.id.user_pas);
        login = view.findViewById(R.id.login);
        forgetPas = view.findViewById(R.id.forget_pas);
        forgetPas.setOnClickListener(v -> {
            activity.changeFragment(MyActivity.GET_USER_PAS,true);
        });
        login.setOnClickListener(v -> {
            login.setClickable(false);
            String name = userName.getText().toString();
            String pas = userPas.getText().toString();
            activity.login(name,pas,createSalt()).observe(LoginFragment.this, userInfoBean -> {
                if (userInfoBean==null||userInfoBean.getCode() == 0){
                    Toast.makeText(activity, "登陆失败", Toast.LENGTH_SHORT).show();
                    login.setClickable(true);
                }else {
                    Users.LOGIN_FLAG = true;
                    BaseUtil.updateUserInfo(userInfoBean.getResult(),getActivity().getApplication());
                    userInfoBean = null;
                    Toast.makeText(activity, "登陆成功", Toast.LENGTH_SHORT).show();
                    activity.startActivity(new Intent(activity, NewsActivity.class));
                    activity.finish();
                }
            });
        });
        toRegister.setOnClickListener(v -> {
             activity.addFragmentToBackStack(new RegisterFragment());
        });
        return view;
    }

    private String createSalt(){
        return "";
    }
}
