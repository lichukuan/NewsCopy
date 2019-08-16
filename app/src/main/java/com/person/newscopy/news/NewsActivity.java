package com.person.newscopy.news;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.easy.generaltool.ViewUtil;
import com.person.newscopy.R;
import com.person.newscopy.news.adapter.NewsFragmentAdapter;
import com.person.newscopy.news.depository.NewsRequirement;
import com.person.newscopy.news.fragment.NewsFragment;
import com.person.newscopy.news.fragment.ShortVideoFragment;
import com.person.newscopy.news.fragment.UserFragment;
import com.person.newscopy.news.fragment.VideoFragment;
import com.person.newscopy.news.network.bean.CommentBean;
import com.person.newscopy.news.network.bean.HotNewsBean;
import com.person.newscopy.news.network.bean.NewsBean;
import com.person.newscopy.news.network.bean.ReplyBean;
import com.person.newscopy.news.network.bean.VideoChannelBean;
import com.person.newscopy.news.network.bean.VideoHotBean;
import com.person.newscopy.news.network.bean.VideoLiveBean;
import com.person.newscopy.news.network.bean.VideoSearchBean;
import com.person.newscopy.news.network.shortBean.ShortInfoBean;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    LinearLayout fragmentLayout;
    BottomNavigationBar bottomNavigationBar;
    NewsViewModel newsViewModel;
    VideoViewModel videoViewModel;
    ShortViewModel shortViewModel;
    boolean isRefresh=false;
    private List<BottomNavigationItem> bottomNavigationItems=new ArrayList<>(4);
    private List<Fragment> fragments=new ArrayList<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)//如果为Android 5之后的版本
            ViewUtil.Translucent.applyGradualTranslucent(this,R.color.tool_bar_red);
//        ViewUtil.FitScreen.setCustomDensity(this,getApplication());
//        ViewUtil.FitScreen.setCustomActivityDensity(this);
        setContentView(R.layout.activity_main);

        fragmentLayout=findViewById(R.id.fragment);
        dealBottomNavigation();
        dealFragment();
        newsViewModel= ViewModelProviders.of(this).get(NewsViewModel.class);
        videoViewModel=ViewModelProviders.of(this).get(VideoViewModel.class);
        shortViewModel=ViewModelProviders.of(this).get(ShortViewModel.class);
    }

    public LiveData<NewsBean> getNewsBean(String name, NewsRequirement require){
        return newsViewModel.getNews(name,require);
    }

    private void dealFragment(){
        Fragment fragment1 = new NewsFragment();
        Fragment fragment2 = new VideoFragment();
        Fragment fragment3=new ShortVideoFragment();
        Fragment fragment4=new UserFragment();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment,fragments.get(0));
        transaction.commit();
    }


    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment,fragment);
        transaction.commit();
    }

    private void dealBottomNavigation(){
        bottomNavigationBar=findViewById(R.id.navigation);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setActiveColor(android.R.color.holo_red_light);
        BottomNavigationItem item=new BottomNavigationItem(R.drawable.news_refresh_final,R.string.main_home_label);
        BottomNavigationItem item1=new BottomNavigationItem(R.drawable.main_home,R.string.main_home_label);
        BottomNavigationItem item2=new BottomNavigationItem(R.drawable.main_video,R.string.main_video_label);
        BottomNavigationItem item3=new BottomNavigationItem(R.drawable.main_short_video,R.string.main_short_video_label);
        BottomNavigationItem item4=new BottomNavigationItem(R.drawable.main_person,R.string.main_person_label);
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);
        addAll(bottomNavigationBar,bottomNavigationItems,1);
        bottomNavigationBar.initialise();
        bottomNavigationBar.setFirstSelectedPosition(0);
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Log.d("==onTabSelected==","position : "+position);
                replaceFragment(fragments.get(position));
                if (isRefresh&&position!=0){
                    bottomNavigationBar.clearAll();
                    addAll(bottomNavigationBar,bottomNavigationItems,1);
                    bottomNavigationBar.initialise();
                    isRefresh=false;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                Log.d("==onTabReselected==","position : "+position);
                if (position==0) {
                    bottomNavigationBar.clearAll();
                    addAll(bottomNavigationBar,bottomNavigationItems,0);
                    bottomNavigationBar.initialise();
                    isRefresh=true;
                }
            }
        });
    }


    private void addAll(BottomNavigationBar bar,List<BottomNavigationItem> list,int out){
        for ( int i = 0; i <list.size() ; i++) {
            if (i!=out)
            bar.addItem(list.get(i));
        }
    }

    public LiveData<CommentBean> getComment(String name, NewsRequirement requirement){
        return newsViewModel.getComment(name,requirement);
    }

    public LiveData<ReplyBean> getReply(String name,NewsRequirement requirement){
        return newsViewModel.getReply(name,requirement);
    }

    public void pullData(String name,NewsRequirement requirement){
        newsViewModel.pullData(name,requirement);
    }

    public LiveData<HotNewsBean> getHotNews(String name, NewsRequirement requirement){
        return newsViewModel.getHotNews(name,requirement);
    }

    public LiveData<VideoChannelBean> getChannel(String type,String maxTime,int count){
        return videoViewModel.getChannel(type,maxTime,count);
    }

    public LiveData<VideoHotBean> getHot(String type, String maxTime, int count){
        return videoViewModel.getHot(type,maxTime,count);
    }

    public LiveData<VideoLiveBean> getLive( String maxTime, int count){
        return videoViewModel.getLiveInfo(maxTime,count);
    }

    public LiveData<VideoSearchBean> getSearchInfo(){
        return videoViewModel.getSearchInfo();
    }

    public LiveData<List<ShortInfoBean>> getShortData(int type){
        return shortViewModel.getShortData(type);
    }



}
