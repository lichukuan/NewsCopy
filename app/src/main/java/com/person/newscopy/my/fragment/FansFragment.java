package com.person.newscopy.my.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.person.newscopy.R;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.ShapeImageView;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.adapter.AllFansAdapter;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.AllCareOrFans;

public class FansFragment extends Fragment {


    RecyclerView fansRecycler;
    ImageView back;
    MyActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_fans,container,false);
        activity = (MyActivity) getActivity();
        fansRecycler = view.findViewById(R.id.recycler);
        back = view.findViewById(R.id.back);
        back.setOnClickListener(v -> activity.back());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fansRecycler.setLayoutManager(new LinearLayoutManager(activity));
        activity.queryAllFans(Users.userId).observe(this, careOrFans -> {
            if (careOrFans.getCode() == Config.SUCCESS)
            fansRecycler.setAdapter(new AllFansAdapter(careOrFans.getResult(),FansFragment.this));
            else
                Toast.makeText(activity, "出错了", Toast.LENGTH_SHORT).show();
        });
    }
}
