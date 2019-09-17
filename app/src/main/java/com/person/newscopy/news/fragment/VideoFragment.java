package com.person.newscopy.news.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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

import com.easy.generaltool.ViewUtil;
import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.google.gson.Gson;
import com.person.newscopy.R;
import com.person.newscopy.common.ColorClipTabLayout;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.NewsFragmentAdapter;
import com.person.newscopy.news.adapter.VideoFragmentAdapter;
import com.person.newscopy.news.depository.VideoDepository;
import com.person.newscopy.news.network.bean.DataBeanX;
import com.person.newscopy.news.network.bean.DataBeanXXXXXX;
import com.person.newscopy.news.network.bean.DataBeanXXXXXXXXXXXX;
import com.person.newscopy.news.network.bean.VideoSearchBean;
import com.person.newscopy.search.SearchActivity;

import java.util.Arrays;
import java.util.HashSet;
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
    ImageView more;
    TextView search;
    ImageView release;
    Set<String> types=new HashSet<>(23);
    VideoSearchBean videoSearch;
    public static final String HOT_VIDEO_KEY = "hot_video_key";
    Subscription subscription;
    int videoSearchIndex = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NewsActivity activity = (NewsActivity) getActivity();
        TranslucentUtil.setTranslucent(activity,Color.parseColor("#ffff4444"), (int) (20* ScreenFitUtil.getDensity()));
        View view = inflater.inflate(R.layout.fragment_main_video,container,false);
        tabLayout=view.findViewById(R.id.tab);
        pager=view.findViewById(R.id.pager);
        more=view.findViewById(R.id.more);
        search=view.findViewById(R.id.search);
        release=view.findViewById(R.id.release);
        release.setOnClickListener(v->createPop());
        activity.getSearchInfo().observe(this, videoSearchBean -> {
            videoSearch = videoSearchBean;
            SharedPreferences sharedPreferences = activity.getSharedPreferences(HOT_VIDEO_KEY,0);
            Gson gson = new Gson();
            sharedPreferences.edit().putString(SearchActivity.SEARCH_ID,gson.toJson(videoSearchBean)).apply();
        });
        search.setText("搜你想搜...");
        subscription = Observable.timer(30, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (videoSearch!=null){
                        final List<DataBeanXXXXXX> data = videoSearch.getData().getData();
                        if(videoSearchIndex>data.size())videoSearchIndex=0;
                        search.setText(data.get(videoSearchIndex).getWord());
                        videoSearchIndex++;
                    }
                });
        search.setOnClickListener(v -> {
            Intent intent = new Intent(activity, SearchActivity.class);
            intent.putExtra(SearchActivity.SEARCH_KEY,HOT_VIDEO_KEY);
            activity.startActivity(intent);
        });
        Set<String> other = getActivity().getSharedPreferences(VideoDepository.VIDEO_TYPE,0)
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

    private String[] changeSetToArray(Set<String> sets){
        String[] strs = new String[sets.size()+2];
        strs[0]="推荐";
        strs[1]="直播";
        Iterator<String> iterator = sets.iterator();
        int i = 2;
        while (iterator.hasNext()) {
            strs[i] = iterator.next();
            i++;
        }
        return strs;
    }

}
