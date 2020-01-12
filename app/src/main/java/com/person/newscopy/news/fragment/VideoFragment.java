package com.person.newscopy.news.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.google.gson.Gson;
import com.person.newscopy.R;
import com.person.newscopy.api.Api;
import com.person.newscopy.common.ColorClipTabLayout;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.MyTranslucentUtil;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.VideoFragmentAdapter;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.search.SearchActivity;
import com.person.newscopy.type.Types;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class VideoFragment extends Fragment {

    ColorClipTabLayout tabLayout;
    ViewPager pager;
    //ImageView more;
    TextView search;
    ImageView release;
    public static final String HOT_VIDEO_KEY = "hot_video_key";
    int videoSearchIndex = 0;
    ContentResult videoRecommend = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NewsActivity activity = (NewsActivity) getActivity();
        MyTranslucentUtil.setTranslucent(activity, Color.parseColor("#fecc11"), (int) (25* ScreenFitUtil.getDensity()));
        View view = inflater.inflate(R.layout.fragment_main_video,container,false);
        tabLayout=view.findViewById(R.id.tab);
        pager=view.findViewById(R.id.pager);
        //more=view.findViewById(R.id.more);
        search=view.findViewById(R.id.search);
//        release=view.findViewById(R.id.release);
//        release.setOnClickListener(v->createPop());
//        activity.getVideoRecommend().observe(this,videoResult -> {
//            this.videoRecommend = videoResult;
//            SharedPreferences sharedPreferences = activity.getSharedPreferences(HOT_VIDEO_KEY,0);
//            Gson gson = new Gson();
//            sharedPreferences.edit().putString(SearchActivity.SEARCH_ID,gson.toJson(videoResult)).apply();
//        });
        search.setText("搜你想搜...");
        search.setOnClickListener(v -> {
            Toast.makeText(activity, "此功能暂不可用", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(activity, SearchActivity.class);
//            intent.putExtra(SearchActivity.SEARCH_KEY,HOT_VIDEO_KEY);
//            activity.startActivity(intent);
        });

        for (int i = 0; i < Config.TYPE.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(Config.TYPE[i]));
        }
        tabLayout.setupWithViewPager(pager);
        pager.setAdapter(new VideoFragmentAdapter(getChildFragmentManager(),Config.TYPE));
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
        pager.setCurrentItem(0);
        return view;
    }

    private void createPop(){
        float d = ScreenFitUtil.getDensity();
        Log.d("==============","d = "+d);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_view,null);
        PopupWindow popupWindow = new PopupWindow(view, (int)(d*160),(int)(d*260));
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(release,-(int)(d*105),0);
    }


}
