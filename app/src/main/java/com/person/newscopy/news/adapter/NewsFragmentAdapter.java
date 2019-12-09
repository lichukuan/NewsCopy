package com.person.newscopy.news.adapter;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.person.newscopy.news.fragment.ItemNormalNewsFragment;
import com.person.newscopy.type.Types;

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
            ItemNormalNewsFragment fragment = new ItemNormalNewsFragment();
            fragment.setNameAndType(name);
            return fragment;
    }

//    private NewsType getType(String name){
//       if (name.equals(title[0]))
//           return NewsType.All;
//       else if (name.equals(title[1]))
//           return NewsType.HOT;
//       else if (name.equals(title[2]))
//           return NewsType.TECH;
//       else if (name.equals(title[3]))
//           return NewsType.WORLD;
//       else if (name.equals(title[4]))
//           return NewsType.POLITICS;
//       else if (name.equals(title[5]))
//           return NewsType.LOTTERY;
//       else if (name.equals(title[6]))
//           return NewsType.SPORTS;
//       else if (name.equals(title[7]))
//           return NewsType.SOCIETY;
//       else if (name.equals(title[8]))
//           return NewsType.HOME;
//       else if (name.equals(title[9]))
//           return NewsType.INTERNET;
//       else if (name.equals(title[10]))
//           return NewsType.SOFTWARE;
//       else if (name.equals(title[11]))
//           return NewsType.ENTERTAINMENT;
//       else if(name.equals(title[12]))
//           return NewsType.MOVIE;
//       else if (name.equals(title[13]))
//           return NewsType.TELEPLAY;
//       else if (name.equals(title[14]))
//           return NewsType.SHOWS;
//       else if (name.equals(title[15]))
//           return NewsType.GOSSIP;
//       else if (name.equals(title[16]))
//           return NewsType.GAME;
//       else if (name.equals(title[17]))
//           return NewsType.NBA;
//       else if (name.equals(title[18]))
//           return NewsType.CAR;
//       else if (name.equals(title[19]))
//           return NewsType.FINANCE;
//       else if (name.equals(title[20]))
//           return NewsType.STOCK;
//       else if (name.equals(title[21]))
//           return NewsType.FUNNY;
//       else if (name.equals(title[22]))
//           return NewsType.MILITARY;
//       else if (name.equals(title[23]))
//           return NewsType.BABY;
//       else if(name.equals(title[24]))
//           return NewsType.FOOD;
//       else if (name.equals(title[25]))
//           return NewsType.FASHION;
//       else if (name.equals(title[26]))
//           return NewsType.DISCOVERY;
//       else if (name.equals(title[27]))
//           return NewsType.REGIMEN;
//       else if (name.equals(title[28]))
//           return NewsType.HISTORY;
//       else if (name.equals(title[29]))
//           return NewsType.ESSAY;
//       else if (name.equals(title[30]))
//           return NewsType.TRAVEL;
//       else if (name.equals(title[31]))
//           return NewsType.HOT_GALLERY;
//       else if (name.equals(title[32]))
//           return NewsType.OLD_PICTURE;
//       else if (name.equals(title[33]))
//           return NewsType.GALLERY_PHOTOGRATHY;
//       else
//           return NewsType.OTHERS;
//    }

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
