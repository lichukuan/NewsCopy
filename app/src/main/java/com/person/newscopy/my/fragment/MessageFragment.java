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
import android.widget.ImageButton;
import com.person.newscopy.R;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.adapter.MessageAdapter;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.MessageBean;

import java.util.List;

public class MessageFragment extends Fragment {

    MyActivity myActivity;
    MessageAdapter messageAdapter = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_message,container,false);
        myActivity = (MyActivity) getActivity();
        ImageButton back = view.findViewById(R.id.back);
        back.setOnClickListener(v -> myActivity.finish());
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        myActivity.queryMessage(Users.userId).observe(this, messageBeans -> {
            if (messageAdapter == null){
                messageAdapter = new MessageAdapter(messageBeans.getResult(),MessageFragment.this);
                recyclerView.setAdapter(messageAdapter);
            }else
                messageAdapter.setMessageBeans(messageBeans.getResult());
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        return view;
    }
}
