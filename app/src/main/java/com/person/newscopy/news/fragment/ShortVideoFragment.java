
package com.person.newscopy.news.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easy.generaltool.ViewUtil;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.person.newscopy.R;
import com.person.newscopy.camera.CameraActivity;
import com.person.newscopy.common.ColorClipTabLayout;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.ShortFragmentAdapter;

public class ShortVideoFragment extends Fragment {

    private ColorClipTabLayout tabLayout;
    private ViewPager pager;
    private CardView release;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NewsActivity activity = (NewsActivity) getActivity();
        TranslucentUtil.setTranslucent(activity,Color.WHITE, (int) (20* ScreenFitUtil.getDensity()));
        View view = inflater.inflate(R.layout.fragment_main_short_video,container,false);
        tabLayout = view.findViewById(R.id.tab);
        pager = view.findViewById(R.id.pager);
        release = view.findViewById(R.id.release);
        release.setOnClickListener(v -> startActivity(new Intent(getContext(), CameraActivity.class)));
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
