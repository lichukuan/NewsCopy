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

import com.person.newscopy.R;
import com.person.newscopy.my.adapter.ItemOperateCommentAdapter;
import com.person.newscopy.my.adapter.ItemOperateReadAdapter;
import com.person.newscopy.user.net.bean.CommentsBean;
import com.person.newscopy.user.net.bean.ReadBean;

import java.util.List;

public class ItemOperateFragment extends Fragment {

    private List<ReadBean> readBeans;
    private String type;
    private RecyclerView recyclerView;
    private List<CommentsBean> comment;

    public void setReadData(List<ReadBean> readBeans,String type){
        this.readBeans = readBeans;
        this.type = type;
    }

    public void setCommentData(List<CommentsBean> comment, String type){
        this.comment = comment;
        this.type = type;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_my_operate,container,false);
        recyclerView = view.findViewById(R.id.operate_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        if (type!=null){
              if(type.equals(getContext().getResources().getString(R.string.content_type_comment))){
                  recyclerView.setAdapter(new ItemOperateCommentAdapter(comment,this));
              }else {
                 // recyclerView.setAdapter(new ItemOperateReadAdapter(readBeans,this,type));
              }
        }
        return view;
    }
}
