package com.person.newscopy.news.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.person.newscopy.common.ColorClipTabLayout;
import com.person.newscopy.common.Config;
import com.person.newscopy.common.MyTranslucentUtil;
import com.person.newscopy.news.NewsActivity;
import com.person.newscopy.news.adapter.NewsFragmentAdapter;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.news.network.bean.ResultBean;
import com.person.newscopy.search.SearchActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class NewsFragment extends Fragment {
    ColorClipTabLayout tabLayout;
    ViewPager pager;
    //ImageView more;
    TextView search;
    ImageView release;
    NewsActivity activity;
    public static final String HOT_NEWS_KEY = "hot_news_key";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (NewsActivity) getActivity();
        MyTranslucentUtil.setTranslucent(activity, Color.parseColor("#fecc11"), (int) (25* ScreenFitUtil.getDensity()));
        View view = inflater.inflate(R.layout.fragment_main_news,container,false);
        tabLayout=view.findViewById(R.id.tab);
        pager=view.findViewById(R.id.pager);
        //more=view.findViewById(R.id.more);
        search=view.findViewById(R.id.search);
        //release=view.findViewById(R.id.release);
        //release.setOnClickListener(v->createPop());
        for (int i = 0; i < Config.TYPE.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(Config.TYPE[i]));
        }
        search.setText("搜你想搜...");
        search.setOnClickListener(v->{
            Intent intent = new Intent(activity, SearchActivity.class);
            activity.startActivity(intent);
});
        tabLayout.setupWithViewPager(pager);
        pager.setAdapter(new NewsFragmentAdapter(getChildFragmentManager(),Config.TYPE,null));
        //pager.setCurrentItem(1);
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
