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
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.NewsAdapter;
import com.person.newscopy.news.depository.NewsRequirement;
import com.person.newscopy.news.network.NewsType;
import com.person.newscopy.news.network.bean.NewsBean;

import rx.Observable;

public class ItemNormalNewsFragment extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    NewsActivity newsActivity;
    String name=null;
    NewsType type=null;
    LiveData<NewsBean> newsBeanLiveData;
    NewsAdapter adapter;
    private int refreshNum = 1;
    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;


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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的itemPosition
                    int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount();
                    // 判断是否滑动到了最后一个item，并且是向上滑动
                    if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                        //加载更多
                        adapter.refresh();
                        ++refreshNum;
                        int time = (int) (BaseUtil.getTime()-refreshNum*60*30);
                        Log.d("===========","time = "+time);
                        NewsRequirement requirement=new NewsRequirement();
                        requirement.setNews(true);
                        requirement.setType(type);
                        requirement.setTime(time);
                        requirement.setWiden(1);
                        newsActivity.pullData(name,requirement);
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
                isSlidingUpward = dy > 0;
            }
        });
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
        newsBeanLiveData.observe(this, beans -> {
            adapter.setDataBeanList(beans.getData());
        }
        );
    }
}
