
package com.person.newscopy.news.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.person.newscopy.R;
import com.person.newscopy.news.adapter.ShortFragmentAdapter;

public class ShortVideoFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_short_video,container,false);
        tabLayout = view.findViewById(R.id.tab);
        pager = view.findViewById(R.id.pager);
        for (int i = 0; i < ShortFragmentAdapter.TYPE.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(ShortFragmentAdapter.TYPE[i]));
        }
        tabLayout.setupWithViewPager(pager);
        pager.setAdapter(new ShortFragmentAdapter(getChildFragmentManager()));
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
        return view;
    }
}
