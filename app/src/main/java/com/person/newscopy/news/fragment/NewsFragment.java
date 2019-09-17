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

import com.easy.generaltool.common.ScreenFitUtil;
import com.easy.generaltool.common.TranslucentUtil;
import com.google.gson.Gson;
import com.person.newscopy.R;
import com.person.newscopy.common.ColorClipTabLayout;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.NewsFragmentAdapter;
import com.person.newscopy.news.depository.NewsRequirement;
import com.person.newscopy.news.network.bean.DataBeanX;
import com.person.newscopy.news.network.bean.HotNewsBean;
import com.person.newscopy.search.SearchActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class NewsFragment extends Fragment {
    ColorClipTabLayout tabLayout;
    ViewPager pager;
    ImageView more;
    TextView search;
    ImageView release;
    NewsActivity activity;
    HotNewsBean hotNews=null;
    int hotIndex = 0;
    boolean isContinue = true;

    public static final String HOT_NEWS_KEY = "hot_news_key";

    public static final String[] values={"推荐","热点","科技","国际","时政","彩票","运动","社会","家居","互联网","软件","娱乐",
            "电影","电视剧","综艺","八卦","游戏",
            "NBA","汽车","财经","股票","搞笑","军事",
            "育儿","美食","时尚","探索","养生","历史","美文","旅行","热点图片","老照片","摄影集"};
    Subscription subscription;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (NewsActivity) getActivity();
        TranslucentUtil.setTranslucent(activity,Color.parseColor("#ffff4444"), (int) (20* ScreenFitUtil.getDensity()));
        View view = inflater.inflate(R.layout.fragment_main_news,container,false);
        tabLayout=view.findViewById(R.id.tab);
        pager=view.findViewById(R.id.pager);
        more=view.findViewById(R.id.more);
        search=view.findViewById(R.id.search);
        release=view.findViewById(R.id.release);
        release.setOnClickListener(v->createPop());
        NewsRequirement requirement = new NewsRequirement();
        requirement.setHotNews(true);
        activity.getHotNews("热点新闻",requirement).observe(this, hotNewsBean -> {
            hotNews = hotNewsBean;
            SharedPreferences sharedPreferences = activity.getSharedPreferences(HOT_NEWS_KEY,0);
            Gson gson = new Gson();
            sharedPreferences.edit().putString(SearchActivity.SEARCH_ID,gson.toJson(hotNewsBean)).apply();
        });
        for (int i = 0; i <values.length ; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(values[i]));
        }
        search.setText("搜你想搜...");
        delayCircle(5);
        search.setOnClickListener(v->{
            Intent intent = new Intent(activity, SearchActivity.class);
            intent.putExtra(SearchActivity.SEARCH_KEY,HOT_NEWS_KEY);
            activity.startActivity(intent);});
        tabLayout.setupWithViewPager(pager);
        pager.setAdapter(new NewsFragmentAdapter(getChildFragmentManager(),values,null));
        pager.setCurrentItem(1);
        Log.d("==NewsFragment==","onCreateView");
        return view;
    }

    private void delayCircle(int time){
        subscription = Observable.timer(time, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (hotNews!=null){
                        final List<DataBeanX> data = hotNews.getData();
                        if(hotIndex>data.size())hotIndex=0;
                        search.setText(data.get(hotIndex).getTitle());
                        hotIndex++;
                    }
                    if (isContinue)
                    delayCircle(time);
                });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("==NewsFragment==","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
        isContinue = false;
        Log.d("==NewsFragment==","onDestroy");
    }
}
