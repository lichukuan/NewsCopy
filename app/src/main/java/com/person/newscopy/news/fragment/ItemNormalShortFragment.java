package com.person.newscopy.news.fragment;

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
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.ShortAdapter;

public class ItemNormalShortFragment extends Fragment {

    RecyclerView recyclerView;
    int type = -1;
    NewsActivity activity = null;
    ShortAdapter adapter = null;

    public void setType(int type) {
        this.type = type;
        if (activity != null) {
            adapter = new ShortAdapter(getContext());
            activity.getShortData(type).observe(this, beans -> adapter.setData(beans));
            if (recyclerView!=null)
                recyclerView.setAdapter(adapter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_short_normal,container,false);
        recyclerView = view.findViewById(R.id.recycler_short_show);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        activity = (NewsActivity) getActivity();
        if (type!=-1){
            adapter = new ShortAdapter(getContext());
            activity.getShortData(type).observe(this,beans -> adapter.setData(beans));
        }
        recyclerView.setLayoutManager(manager);
        if (adapter!=null)
            recyclerView.setAdapter(adapter);
        return view;
    }
}
