package com.person.newscopy.show.fragment;

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

import com.google.gson.reflect.TypeToken;
import com.person.newscopy.R;
import com.person.newscopy.common.util.BaseUtil;
import com.person.newscopy.edit.bean.EditBean;
import com.person.newscopy.show.adapter.ShowArticleAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowArticleFragment extends Fragment {

    RecyclerView recyclerView;
    List<EditBean> data = null;
    ShowArticleAdapter showArticleAdapter = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_article_fragment,container,false);
        recyclerView = view.findViewById(R.id.recycler);
        return view;
    }

    public void setJson(String json) {
        data = BaseUtil.getGson().fromJson(json.replaceAll("<json>",""),new TypeToken<List<EditBean>>(){}.getType());
        data.remove(data.size()-1);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (showArticleAdapter == null){
//            showArticleAdapter = new ShowArticleAdapter(data,this);
//        }
//        recyclerView.setAdapter(showArticleAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
