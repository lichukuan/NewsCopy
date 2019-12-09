package com.person.newscopy.my.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.person.newscopy.R;
import com.person.newscopy.my.MyActivity;

public class GetUserPasFragment extends Fragment {

    EditText emailView;
    Button send;
    ProgressBar progressBar;
    MyActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_my_get_user_pas,container,false);
      activity = (MyActivity) getActivity();
      progressBar = view.findViewById(R.id.progressBar);
      emailView = view.findViewById(R.id.email);
      send = view.findViewById(R.id.get);
      return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        send.setOnClickListener(v -> {
            send.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);
            String email = emailView.getText().toString();
            if (email.equals("")) Toast.makeText(activity, "不能为空", Toast.LENGTH_SHORT).show();
            else {
                activity.getUserPas(email).observe(this,baseResult -> {
                    Toast.makeText(activity, baseResult.getResult(), Toast.LENGTH_SHORT).show();
                    send.setClickable(true);
                    progressBar.setVisibility(View.GONE);
                });
            }
        });
    }
}
