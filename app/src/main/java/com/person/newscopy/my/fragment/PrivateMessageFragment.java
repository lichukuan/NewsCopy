package com.person.newscopy.my.fragment;

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

import com.person.newscopy.R;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.adapter.PrivateTalkAllAdapter;
import com.person.newscopy.user.Users;

//显示所有的私聊
public class PrivateMessageFragment extends Fragment {
    RecyclerView recyclerView;
    ImageView back;
    ImageView messageImage;//没有私信时，显示图片
    MyActivity myActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_private_talk,container,false);
        myActivity = (MyActivity) getActivity();
        recyclerView = view.findViewById(R.id.recycler);
        back = view.findViewById(R.id.back);
        messageImage = view.findViewById(R.id.message_image);
        back.setOnClickListener(v -> myActivity.finish());
        myActivity.querySimpleTalk(Users.userId).observe(this,simpleTalkBean -> {
            recyclerView.setAdapter(new PrivateTalkAllAdapter(simpleTalkBean.getResult(),PrivateMessageFragment.this));
        });
        LinearLayoutManager manager = new LinearLayoutManager(myActivity);
        recyclerView.setLayoutManager(manager);
        return view;
    }
}
