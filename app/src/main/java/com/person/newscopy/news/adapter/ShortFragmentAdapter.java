package com.person.newscopy.news.adapter;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.person.newscopy.news.fragment.ItemNormalShortFragment;

public class ShortFragmentAdapter extends FragmentPagerAdapter {

    public static final String[] TYPE = {"科技","娱乐","金融","新知"};

    public  Fragment[] fragments = new Fragment[TYPE.length];

    public ShortFragmentAdapter(FragmentManager fm) {
        super(fm);
        for (int i = 0; i <4 ; i++) {
            ItemNormalShortFragment fragment = new ItemNormalShortFragment();
            fragment.setType(i);
            fragments[i]  = fragment;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return TYPE.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TYPE[position];
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }
}
