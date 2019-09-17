package com.person.newscopy.news.fragment;

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
import com.person.newscopy.R;
import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.ShortAdapter;
import com.person.newscopy.news.depository.NewsRequirement;
import com.person.newscopy.news.network.shortBean.ShortInfoBean;

import java.util.ArrayList;
import java.util.List;

public class ItemNormalShortFragment extends Fragment {

    RecyclerView recyclerView;
    int type = -1;
    NewsActivity activity = null;
    ShortAdapter adapter = null;
    private int start = 10;
    public static final int ADD_COUNT = 10;

    private List<ShortInfoBean> data = new ArrayList<>();
    public void setType(int type) {
        this.type = type;
        if (activity != null) {
            adapter = new ShortAdapter(getContext());
            activity.getShortData(type).observe(this, beans -> {
                data.addAll(beans);
                adapter.addData(beans.subList(0,10));
            });
            if (recyclerView!=null)
                recyclerView.setAdapter(adapter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("============","ItemNormalShortFragment type = "+type);
        View view = inflater.inflate(R.layout.fragment_item_short_normal,container,false);
        recyclerView = view.findViewById(R.id.recycler_short_show);
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
                    if (lastItemPosition == (itemCount - 1) ) {
                       adapter.addData(data.subList(start,start+ADD_COUNT));
                       start+=ADD_COUNT;
                    }
                }
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        activity = (NewsActivity) getActivity();
        if (type!=-1){
            adapter = new ShortAdapter(getContext());
            activity.getShortData(type).observe(this,beans -> {
                data.addAll(beans);
                adapter.addData(beans.subList(0,10));
            });
        }
        recyclerView.setLayoutManager(manager);
        if (adapter!=null)
            recyclerView.setAdapter(adapter);
        return view;
    }
}
