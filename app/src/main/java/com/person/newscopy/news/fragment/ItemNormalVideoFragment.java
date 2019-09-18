package com.person.newscopy.news.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.person.newscopy.R;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.LoadView;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.VideoAdapter;

public class ItemNormalVideoFragment extends Fragment {

    RecyclerView recyclerView;
    NewsActivity newsActivity;
    String type;
    VideoAdapter adapter;
    private int refreshNum = 1;
    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;
    LoadView loadView;

    public void setType(String type) {
        this.type = type;
        if (newsActivity==null)
            return;
        if ("直播".equals(type))
            adapter=new VideoAdapter(VideoAdapter.TYPE_LIVE,this);
        else
            adapter = new VideoAdapter(VideoAdapter.TYPE_NORMAL,this);
        recyclerView.setAdapter(adapter);
        pullVideoData(type,BaseUtil.getTime()+"",20);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_video_normal,container,false);
        recyclerView=view.findViewById(R.id.recycler_video_show);
        loadView = view.findViewById(R.id.load);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        newsActivity= (NewsActivity) getActivity();
        if ("直播".equals(type)) {
            adapter = new VideoAdapter(VideoAdapter.TYPE_LIVE, this);
            pullVideoData(type,BaseUtil.getTime()+"",20);
        }else if (type!=null) {
            adapter = new VideoAdapter(VideoAdapter.TYPE_NORMAL, this);
            pullVideoData(type, BaseUtil.getTime()+"",20);
        }
        if (adapter!=null)
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
                        pullVideoData(type,BaseUtil.getTime()+"",(++refreshNum)*20);
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingUpward = dy > 0;
            }
        });
        return view;
    }

    private void pullVideoData(String type,String maxTime,int count){
        if ("直播".equals(type))
            newsActivity.getLive(maxTime,count).observe(this, videoLiveBean -> {
                  adapter.setLiveBeans(videoLiveBean.getData().getLivingFeed().getCards());
            });
        else
            newsActivity.getChannel(type,maxTime,count).observe(this,videoChannelBean -> {
                adapter.setChannelBeans(videoChannelBean.getData().getChannelFeed().getData());
            });
        loadView.cancel();
        loadView.setVisibility(View.GONE);
    }

}
