package com.person.newscopy.image.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.person.newscopy.image.fragment.ShowOneImageFragment;
import com.person.newscopy.show.fragment.ShowArticleFragment;

import java.util.ArrayList;
import java.util.List;

public class ShowAllImageFragmentAdapter extends FragmentPagerAdapter {

    private List<String> data = null;

    private List<ShowOneImageFragment> showOneImageFragments = null;

    public ShowAllImageFragmentAdapter(FragmentManager fm,List<String> data) {
        super(fm);
        this.data = data;
        showOneImageFragments = new ArrayList<>(data.size());
        for (int i = 0; i <data.size() ; i++) {
            showOneImageFragments.add(null);
        }
    }

    @Override
    public Fragment getItem(int i) {
        if (data == null)return null;
        ShowOneImageFragment oneImageFragment = showOneImageFragments.get(i);
        if (oneImageFragment == null){
            oneImageFragment = new ShowOneImageFragment();
            oneImageFragment.setData(data.get(i),getCount(),i);
            showOneImageFragments.set(i,oneImageFragment);
        }
        return oneImageFragment;
    }

    public void save(int i){
        ShowOneImageFragment oneImageFragment = showOneImageFragments.get(i);
        oneImageFragment.save();
    }

    @Override
    public int getCount() {
        if (data == null)return 0;
        return data.size();
    }
}
