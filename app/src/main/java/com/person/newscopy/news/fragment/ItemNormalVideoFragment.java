package com.person.newscopy.news.fragment;

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
import com.person.newscopy.common.LoadView;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.VideoAdapter;
import com.person.newscopy.type.Types;

public class ItemNormalVideoFragment extends Fragment {

    RecyclerView recyclerView;
    NewsActivity newsActivity;
    String type;
    VideoAdapter adapter;
    private int refreshNum = 1;
    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;
    LoadView loadView;
    private boolean isInit = false;
    SwipeRefreshLayout refreshLayout;

    public void setType(String type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_video_normal,container,false);
        recyclerView=view.findViewById(R.id.recycler_video_show);
        loadView = view.findViewById(R.id.load);
        refreshLayout = view.findViewById(R.id.refresh);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsActivity= (NewsActivity) getActivity();
        adapter = new VideoAdapter(VideoAdapter.TYPE_NORMAL, this);
        recyclerView.setAdapter(adapter);
        if (!isInit){
            newsActivity.queryVideoData(type,0,"up").observe(this, videoResult -> {
                adapter.setChannelBeans(videoResult.getResult(),true);
                loadView.cancel();
                loadView.setVisibility(View.GONE);
            });
            isInit = true;
        }
        refreshLayout.setColorSchemeResources(R.color.main_color);//设置刷新进度条的颜色
        //设置监听
        refreshLayout.setOnRefreshListener(() -> {//当下拉时，会调用这个方法
            newsActivity.queryNewsData(type,adapter.getTopTime(),"up").observe(this, contentResult -> {
                adapter.addTopData(contentResult.getResult());
                refreshLayout.setRefreshing(false);//设置刷新进度条是否隐藏，false表示隐藏
            });
        });
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
                        newsActivity.queryVideoData(type,adapter.getDownTime(),"down").observe(ItemNormalVideoFragment.this, videoResult -> {
                            adapter.setChannelBeans(videoResult.getResult(),false);
                        });
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingUpward = dy > 0;
            }
        });
    }
}
