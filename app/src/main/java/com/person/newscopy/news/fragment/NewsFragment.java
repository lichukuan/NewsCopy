package com.person.newscopy.news.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.person.newscopy.R;
import com.person.newscopy.news.adapter.NewsFragmentAdapter;

public class NewsFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager pager;
    ImageView more;
    TextView search;
    ImageView release;
    public static final String[] values={"推荐","热点","科技","国际","时政","彩票","运动","社会","家居","互联网","软件","娱乐"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_news,container,false);
        tabLayout=view.findViewById(R.id.tab);
        pager=view.findViewById(R.id.pager);
        more=view.findViewById(R.id.more);
        search=view.findViewById(R.id.search);
        release=view.findViewById(R.id.release);
        for (int i = 0; i <values.length ; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(values[i]));
        }
        tabLayout.setupWithViewPager(pager);
        pager.setAdapter(new NewsFragmentAdapter(getChildFragmentManager(),values,null));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager.setCurrentItem(1);
        Log.d("==NewsFragment==","onCreateView");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("==NewsFragment==","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("==NewsFragment==","onDestroy");
    }
}
