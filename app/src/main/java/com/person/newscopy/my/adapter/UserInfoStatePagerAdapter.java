package com.person.newscopy.my.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.person.newscopy.my.fragment.ItemMyInfoFragment;
import com.person.newscopy.user.net.bean.ContentBean;

import java.util.List;

public class UserInfoStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<String> title;
    private List<ContentBean> release;
    private List<ContentBean> article;
    private List<ContentBean> video;

    public UserInfoStatePagerAdapter(FragmentManager fm, List<String> title, List<ContentBean> release, List<ContentBean> article, List<ContentBean> video) {
        super(fm);
        this.title = title;
        this.release = release;
        this.article = article;
        this.video = video;
    }

    @Override
    public Fragment getItem(int i) {
        ItemMyInfoFragment item = new ItemMyInfoFragment();
        switch (i){
            case 0:
                item.setData(release);
                break;
            case 1:
                if (article.size()>0)
                    item.setData(article);
                else
                    item.setData(video);
                break;
            case 2:
                item.setData(video);
                break;
        }
        return item;
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
