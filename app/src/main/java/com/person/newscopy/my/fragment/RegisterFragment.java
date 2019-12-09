package com.person.newscopy.my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.person.newscopy.R;
import com.person.newscopy.my.MyActivity;

public class RegisterFragment extends Fragment {

    EditText userName;
    EditText userPas;
    Button register;
    ImageView back;
    MyActivity activity;
    EditText userEmail;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_register,container,false);
        activity = (MyActivity) getActivity();
        userName = view.findViewById(R.id.release_user_name);
        userPas = view.findViewById(R.id.user_pas);
        register = view.findViewById(R.id.register);
        back = view.findViewById(R.id.back);
        userEmail = view.findViewById(R.id.email);
        progressBar=view.findViewById(R.id.progress);
        back.setOnClickListener(v -> activity.getSupportFragmentManager().popBackStack());
        register.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            register.setClickable(false);
            String name = userName.getText().toString();
            String pas = userPas.getText().toString();
            String email = userEmail.getText().toString();
            activity.register(name,email,pas).observe(RegisterFragment.this, baseResult -> {
                Toast.makeText(activity, baseResult.getResult(), Toast.LENGTH_SHORT).show();
                if(baseResult.getCode() == 1){
                    Log.d("=====","退出");
                    progressBar.setVisibility(View.GONE);
                    activity.getSupportFragmentManager().popBackStack();
                }
                register.setClickable(true);
            });
        });
        return view;
    }
}
