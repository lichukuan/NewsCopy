package com.person.newscopy.news.adapter;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.person.newscopy.news.fragment.ItemNormalShortFragment;

public class ShortFragmentAdapter extends FragmentStatePagerAdapter {

    public static final String[] TYPE = {"科技","娱乐","金融","新知"};

    public ShortFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        ItemNormalShortFragment fragment = new ItemNormalShortFragment();
        fragment.setType(position);
        return fragment;
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
