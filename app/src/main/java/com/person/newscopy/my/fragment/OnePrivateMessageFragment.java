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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.person.newscopy.R;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.adapter.PrivateTalkInfoAdapter;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.BaseResult;
import com.person.newscopy.user.net.bean.ContentsBean;

//用于显示一个具体的私聊
public class OnePrivateMessageFragment extends Fragment {
    RecyclerView recyclerView;
    ImageView image;
    MyActivity myActivity;
    EditText content;
    Button send;
    PrivateTalkInfoAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_one_private_talk,container,false);
        myActivity = (MyActivity) getActivity();
        recyclerView = view.findViewById(R.id.recycler);
        image = view.findViewById(R.id.no_private_image);
        content = view.findViewById(R.id.talk_content);
        send = view.findViewById(R.id.talk_send);
        myActivity.queryAllPrivateTalk(Users.userId,myActivity.getPrivateTalkRequireId()).observe(this, allPrivateTalkInfoBean -> {
            adapter = new PrivateTalkInfoAdapter(allPrivateTalkInfoBean,OnePrivateMessageFragment.this);
            recyclerView.setAdapter(adapter);
        });
        send.setOnClickListener(v -> {
             send.setClickable(false);
             String talkContent = content.getText().toString();
            ContentsBean b = new ContentsBean();
            b.setContent(talkContent);
            b.setTime((int) BaseUtil.getTime());
            b.setType("right");
            myActivity.addPrivateTalk(Users.userId,myActivity.getPrivateTalkRequireId(),talkContent).observe(OnePrivateMessageFragment.this, baseResult -> {
                 if (baseResult.getCode() == Config.SUCCESS)adapter.addTalk(b);
                 else Toast.makeText(myActivity, "发送失败", Toast.LENGTH_SHORT).show();
            });
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myActivity.setPrivateTalkRequireId(null);
    }
}
