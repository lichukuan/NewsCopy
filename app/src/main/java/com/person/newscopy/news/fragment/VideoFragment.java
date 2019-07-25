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
import com.person.newscopy.news.adapter.VideoFragmentAdapter;
import com.person.newscopy.news.depository.VideoDepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class VideoFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager pager;
    ImageView more;
    TextView search;
    ImageView release;
    Set<String> types=new HashSet<>(23);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_video,container,false);
        tabLayout=view.findViewById(R.id.tab);
        pager=view.findViewById(R.id.pager);
        more=view.findViewById(R.id.more);
        search=view.findViewById(R.id.search);
        release=view.findViewById(R.id.release);
        types.add("推荐");
        types.add("直播");
        Set<String> other=getActivity().getSharedPreferences(VideoDepository.VIDEO_TYPE,0)
                .getStringSet(VideoDepository.VIDEO_ALL_TYPE,new HashSet<>());
        types.addAll(other);
        String[] s = changeSetToArray(types);
        Log.d("====types====", Arrays.toString(s));
        for (int i = 0; i < s.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(s[i]));
        }
        tabLayout.setupWithViewPager(pager);
        pager.setAdapter(new VideoFragmentAdapter(getChildFragmentManager(),s));
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

    private String[] changeSetToArray(Set<String> sets){
        String[] strs = new String[sets.size()];
        Iterator<String> iterator = sets.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            strs[i] = iterator.next();
            i++;
        }
        return strs;
    }

}
