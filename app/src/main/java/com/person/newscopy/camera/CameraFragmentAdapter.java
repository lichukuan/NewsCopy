package com.person.newscopy.camera;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.person.newscopy.camera.fragment.CameraFragment;
import com.person.newscopy.camera.fragment.VideoListFragment;

public class CameraFragmentAdapter extends FragmentPagerAdapter {

    CameraFragment cameraFragment;
    VideoListFragment videoListFragment;
    public static final String[] title = {"拍摄","相册"};


    public CameraFragmentAdapter(FragmentManager fm) {
        super(fm);
        cameraFragment = new CameraFragment();
        videoListFragment = new VideoListFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)return cameraFragment;
        else return videoListFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
