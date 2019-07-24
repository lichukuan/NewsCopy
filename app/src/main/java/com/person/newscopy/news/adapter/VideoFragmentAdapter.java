package com.person.newscopy.news.adapter;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.person.newscopy.news.fragment.ItemNormalVideoFragment;

public class VideoFragmentAdapter extends FragmentStatePagerAdapter {

    private String[] types;

    public VideoFragmentAdapter(FragmentManager fm,String[] types) {
        super(fm);
        this.types=types;
    }

    @Override
    public Fragment getItem(int position) {
        ItemNormalVideoFragment fragment = new ItemNormalVideoFragment();
        fragment.setType(types[position]);
        return fragment;
    }

    @Override
    public int getCount() {
        return types.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return types[position];
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

}
