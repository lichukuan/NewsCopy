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
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.adapter.ItemOperateReadAdapter;
import com.person.newscopy.user.Users;


public class HistoryFragment extends Fragment {

    MyActivity myActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myActivity = (MyActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_my_history,container,false);
        view.findViewById(R.id.back).setOnClickListener(v -> myActivity.finish());
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        myActivity.queryHistory(Users.userId).observe(this,result -> {
            recyclerView.setAdapter(new ItemOperateReadAdapter(result.getResult(),HistoryFragment.this));
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(myActivity));
        return view;
    }
}
