package com.person.newscopy.news;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.easy.generaltool.common.ScreenFitUtil;
import com.person.newscopy.R;
import com.person.newscopy.news.fragment.CareFragment;
import com.person.newscopy.news.fragment.NewsFragment;
import com.person.newscopy.news.fragment.UserFragment;
import com.person.newscopy.news.fragment.VideoFragment;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.user.net.bean.BaseResult;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    LinearLayout fragmentLayout;
    BottomNavigationBar bottomNavigationBar;
    ContentViewModel contentViewModel;
    boolean isRefresh=false;
    private List<BottomNavigationItem> bottomNavigationItems=new ArrayList<>(4);
    private List<Fragment> fragments=new ArrayList<>(4);
    private static int heightResult = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenFitUtil.fit(getApplication(),this,ScreenFitUtil.FIT_WIDTH);
        setContentView(R.layout.activity_main);
        fragmentLayout=findViewById(R.id.fragment);
        dealBottomNavigation();
        dealFragment();
        contentViewModel = ViewModelProviders.of(this).get(ContentViewModel.class);
    }


    private void dealFragment(){
        Fragment fragment1=new CareFragment();
        Fragment fragment2 = new NewsFragment();
        Fragment fragment3 = new VideoFragment();
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
        bottomNavigationBar.setActiveColor(R.color.main_color);
        //BottomNavigationItem item=new BottomNavigationItem(R.drawable.news_refresh_final,R.string.main_home_label);
        BottomNavigationItem item1=new BottomNavigationItem(R.drawable.main_home,R.string.main_home_label);
        BottomNavigationItem item2=new BottomNavigationItem(R.drawable.main_video,R.string.main_video_label);
        BottomNavigationItem item3=new BottomNavigationItem(R.drawable.main_short_video,R.string.main_short_video_label);
        BottomNavigationItem item4=new BottomNavigationItem(R.drawable.main_person,R.string.main_person_label);
        //item.setBadgeItem()
        bottomNavigationItems.add(item1);
        //bottomNavigationItems.add(item);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);
        addAll(bottomNavigationBar,bottomNavigationItems);
        bottomNavigationBar.initialise();
        bottomNavigationBar.setFirstSelectedPosition(0);
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Log.d("==onTabSelected==","position : "+position);
                replaceFragment(fragments.get(position));
//                if (isRefresh&&position!=0){
//                    bottomNavigationBar.clearAll();
//                    addAll(bottomNavigationBar,bottomNavigationItems,1);
//                    bottomNavigationBar.initialise();
//                    isRefresh=false;
//                    bottomNavigationBar.selectTab(position);
//                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                Log.d("==onTabReselected==","position : "+position);
//                if (position==0) {
//                    bottomNavigationBar.clearAll();
//                    addAll(bottomNavigationBar,bottomNavigationItems,0);
//                    bottomNavigationBar.initialise();
//                    isRefresh=true;
//                }
            }
        });
    }


    private void addAll(BottomNavigationBar bar, List<BottomNavigationItem> list){
        for ( int i = 0; i <list.size() ; i++) {
            bar.addItem(list.get(i));
        }
    }

    public LiveData<ContentResult> queryNewsData(String tag, int time, String type){
        return contentViewModel.feedNews(tag,time,type);
    }

    public LiveData<ContentResult> queryVideoData(String tag, int time, String type){
        return contentViewModel.feedVideo(tag,time,type);
    }


    public LiveData<ContentResult> getNewsRecommend(){
        return contentViewModel.feedNesRecommend();
    }

    public LiveData<ContentResult> getVideoRecommend(){
        return contentViewModel.feedVideoRecommend();
    }

    public LiveData<BaseResult> uploadArticle(String userId, String content, String title, String image, String imageList, String tag, String rec){
        return contentViewModel.uploadArticle(userId,content,title,image,imageList,tag,rec);
    }

    public LiveData<BaseResult> uploadArticleImage(List<String> paths){
        return contentViewModel.uploadArticleImage(paths);
    }

    public LiveData<ContentResult> feedUserCareData(String userId,int time,String type){
        return contentViewModel.feedUserCareData(userId,time,type);
    }
}
