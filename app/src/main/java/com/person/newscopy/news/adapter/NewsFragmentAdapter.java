package com.person.newscopy.news.adapter;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.person.newscopy.news.fragment.ItemNormalNewsFragment;
import com.person.newscopy.news.network.NewsType;

import java.util.List;

public class NewsFragmentAdapter extends FragmentStatePagerAdapter {

    private String[] title;
    public NewsFragmentAdapter(FragmentManager fm,String[] title,List<Fragment> fragments) {
        super(fm);
        this.title=title;
    }

    @Override
    public Fragment getItem(int position) {
        String name=title[position];
        Log.d("======name======",name);
            ItemNormalNewsFragment fragment=new ItemNormalNewsFragment();
            fragment.setNameAndType(name,getType(name));
            return fragment;
    }

    private NewsType getType(String name){
       if (name.equals(title[0]))
           return NewsType.All;
       else if (name.equals(title[1]))
           return NewsType.HOT;
       else if (name.equals(title[2]))
           return NewsType.TECH;
       else if (name.equals(title[3]))
           return NewsType.WORLD;
       else if (name.equals(title[4]))
           return NewsType.POLITICS;
       else if (name.equals(title[5]))
           return NewsType.LOTTERY;
       else if (name.equals(title[6]))
           return NewsType.SPORTS;
       else if (name.equals(title[7]))
           return NewsType.SOCIETY;
       else if (name.equals(title[8]))
           return NewsType.HOME;
       else if (name.equals(title[9]))
           return NewsType.INTERNET;
       else if (name.equals(title[10]))
           return NewsType.SOFTWARE;
       else if (name.equals(title[11]))
           return NewsType.ENTERTAINMENT;
       else
           return NewsType.All;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }
}
