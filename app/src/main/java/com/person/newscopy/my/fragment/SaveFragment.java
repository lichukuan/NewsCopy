package com.person.newscopy.my.fragment;

import android.arch.lifecycle.Observer;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.person.newscopy.R;
import com.person.newscopy.common.Config;
import com.person.newscopy.my.MyActivity;
import com.person.newscopy.my.adapter.ItemOperateReadAdapter;
import com.person.newscopy.my.adapter.SaveAdapter;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.BaseResult;
import com.person.newscopy.user.net.bean.ReadContent;

import java.util.ArrayList;
import java.util.List;

public class SaveFragment extends Fragment {

    MyActivity myActivity;
    TextView edit;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    SaveAdapter saveAdapter;
    boolean isDelete = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_save,container,false);
        myActivity = (MyActivity) getActivity();
        view.findViewById(R.id.back).setOnClickListener(v -> myActivity.finish());
        recyclerView = view.findViewById(R.id.recycler);
        edit = view.findViewById(R.id.edit);
        progressBar = view.findViewById(R.id.progress);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        myActivity.querySave(Users.userId).observe(this, contentResult -> {
            saveAdapter = new SaveAdapter(contentResult.getResult(),SaveFragment.this);
            recyclerView.setAdapter(saveAdapter);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        edit.setOnClickListener(v -> {
            edit.setClickable(false);
            if (saveAdapter != null){
                if (isDelete){
                    saveAdapter.deleteSave();
                    edit.setText("编辑");
                } else{
                    saveAdapter.changeToEditMode();
                    edit.setText("删除");
                }
            }
            isDelete = !isDelete;
            edit.setClickable(true);
        });
    }

    public void deleteSaveData(String contentId){
        myActivity.deleteSave(Users.userId,contentId).observe(this, baseResult -> {
            if (baseResult.getCode() == Config.FAIL)
                Toast.makeText(myActivity, baseResult.getResult(), Toast.LENGTH_SHORT).show();
        });
    }

    public void waitDelete(){
        progressBar.setVisibility(View.VISIBLE);
    }
    public void deleteCompleted(){
        progressBar.setVisibility(View.GONE);
    }
}
