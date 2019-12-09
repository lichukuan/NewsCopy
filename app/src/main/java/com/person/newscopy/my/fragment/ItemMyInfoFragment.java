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
import com.person.newscopy.my.adapter.ItemUserInfoAdapter;
import com.person.newscopy.user.net.bean.ContentBean;

import java.util.List;

public class ItemMyInfoFragment extends Fragment {

    RecyclerView recycler;
    private List<ContentBean> data;

    public void setData(List<ContentBean> data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_my_user_info,container,false);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(new ItemUserInfoAdapter(data,this));
        return view;
    }
}
