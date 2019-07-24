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
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.VideoAdapter;

public class ItemNormalVideoFragment extends Fragment {

    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    NewsActivity newsActivity;
    String type;
    VideoAdapter adapter;

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
        refreshLayout=view.findViewById(R.id.video_refresh);
        recyclerView=view.findViewById(R.id.recycler_video_show);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        newsActivity= (NewsActivity) getActivity();
        if ("直播".equals(type))
           adapter=new VideoAdapter(VideoAdapter.TYPE_LIVE,this);
        else if (type!=null) {
            adapter = new VideoAdapter(VideoAdapter.TYPE_NORMAL, this);
            pullVideoData(type, BaseUtil.getTime()+"",20);
        }
        if (adapter!=null)
            recyclerView.setAdapter(adapter);
        return view;
    }

    private void pullVideoData(String type,String maxTime,int count){
        if ("直播".equals(type))
            newsActivity.getLive(type,maxTime,count).observe(this, videoLiveBean -> {
                  adapter.setLiveBeans(videoLiveBean.getData().getLivingFeed().getCards());
            });
        else
            newsActivity.getChannel(type,maxTime,count).observe(this,videoChannelBean -> {
                adapter.setChannelBeans(videoChannelBean.getData().getChannelFeed().getData());
            });
    }

}
