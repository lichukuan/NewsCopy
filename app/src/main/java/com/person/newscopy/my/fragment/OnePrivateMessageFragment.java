package com.person.newscopy.my.fragment;


import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.generaltool.common.ScreenFitUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.MyTranslucentUtil;
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
    ImageView back;
    TextView title;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_one_private_talk,container,false);
        myActivity = (MyActivity) getActivity();
        MyTranslucentUtil.setTranslucent(myActivity, Color.parseColor("#fecc11"), (int) (25* ScreenFitUtil.getDensity()));
        recyclerView = view.findViewById(R.id.recycler);
        image = view.findViewById(R.id.no_private_image);
        content = view.findViewById(R.id.talk_content);
        send = view.findViewById(R.id.talk_send);
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        back.setOnClickListener(v -> myActivity.back());
        myActivity.queryAllPrivateTalk(Users.userId,myActivity.getPrivateTalkRequireId()).observe(this, allPrivateTalkInfoBean -> {
            if (allPrivateTalkInfoBean == null)return;
            adapter = new PrivateTalkInfoAdapter(allPrivateTalkInfoBean,OnePrivateMessageFragment.this);
            title.setText(allPrivateTalkInfoBean.getResult().getLeftName());
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
                 if (baseResult.getCode() == Config.SUCCESS){
                     adapter.addTalk(b);
                     myActivity.sendMessage(Users.userName+"私信了您",myActivity.getPrivateTalkRequireId(),Users.userId,Config.MESSAGE.PRIVATE_TALK_TYPE,talkContent).observe(this, baseResult1 -> {
                           if (baseResult1.getCode() == Config.FAIL) Log.d("====","发送消息失败");
                     });
                 }
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
