package com.person.newscopy.news.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.person.newscopy.R;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.NewsAdapter;
import com.person.newscopy.news.depository.NewsRequirement;
import com.person.newscopy.news.network.NewsType;
import com.person.newscopy.news.network.bean.NewsBean;

public class ItemNormalNewsFragment extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    NewsActivity newsActivity;
    String name=null;
    NewsType type=null;
    LiveData<NewsBean> newsBeanLiveData;
    NewsAdapter adapter;

    public String getName() {
        return name;
    }

    public void setNameAndType(String name,NewsType type) {
        this.name = name;
        this.type = type;
        if (newsActivity!=null){
           pullData(0,1,type);
        }
    }

    public NewsType getType() {
        return type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_news_normal,container,false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView=view.findViewById(R.id.recycler_news_show);
        recyclerView.setLayoutManager(linearLayoutManager);
        refreshLayout=view.findViewById(R.id.news_refresh);
        adapter=new NewsAdapter();
        adapter.setFragment(this);
        recyclerView.setAdapter(adapter);
        newsActivity= (NewsActivity) getActivity();
        if (name!=null&type!=null)
           pullData(0,1,type);
        Log.d("===ItemNormalNews==",type+"  "+name+" onCreateView");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("===ItemNormalNews==",name+" onDestroyView");
    }

    private void pullData(int time, int widen,NewsType type){
        NewsRequirement requirement=new NewsRequirement();
        requirement.setNews(true);
        requirement.setType(type);
        requirement.setTime(time);
        requirement.setWiden(widen);
        newsBeanLiveData=newsActivity.getNewsBean(name,requirement);
        newsBeanLiveData.observe(this, newsBean -> adapter.setDataBeanList(newsBean.getData()));
    }
}
