package com.person.newscopy.news.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.person.newscopy.R;
import com.person.newscopy.common.LoadView;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.NewsAdapter;
import com.person.newscopy.news.network.bean.ContentResult;

public class ItemNormalNewsFragment extends Fragment {

    RecyclerView recyclerView;
    NewsActivity newsActivity;
    String name=null;
    NewsAdapter adapter;
    private int refreshNum = 1;
    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;
    NestedScrollView nestedScrollView;
    SwipeRefreshLayout refreshLayout;
    LoadView loadView;
    private boolean isInit = false;

    public String getName() {
        return name;
    }

    public void setNameAndType(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_news_normal,container,false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView=view.findViewById(R.id.recycler_news_show);
        recyclerView.setLayoutManager(linearLayoutManager);
        refreshLayout = view.findViewById(R.id.refresh);
        loadView = view.findViewById(R.id.load);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter == null)
           adapter = new NewsAdapter();
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
                        newsActivity.queryNewsData(name,adapter.getDownTime(),"down").observe(ItemNormalNewsFragment.this,newsResult -> {
                            adapter.setDataBeanList(newsResult.getResult(),false);
                        });
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
        newsActivity = (NewsActivity) getActivity();
        refreshLayout.setColorSchemeResources(R.color.main_color);//设置刷新进度条的颜色
        //设置监听
        refreshLayout.setOnRefreshListener(() -> {//当下拉时，会调用这个方法
            newsActivity.queryNewsData(name,adapter.getTopTime(),"up").observe(this, contentResult -> {
                adapter.addTopData(contentResult.getResult());
                refreshLayout.setRefreshing(false);//设置刷新进度条是否隐藏，false表示隐藏
            });
        });
        if (!adapter.isInit()){
            newsActivity.queryNewsData(name,0,"up").observe(this, contentResult -> {
                adapter.setDataBeanList(contentResult.getResult(),true);
                loadView.cancel();
                loadView.setVisibility(View.GONE);
            });
            isInit = true;
        }
    }
}
