package com.person.newscopy.edit;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.easy.generaltool.common.ScreenFitUtil;
import com.person.newscopy.R;
import com.person.newscopy.common.util.KeywordFilteringUtil;
import com.person.newscopy.edit.fragment.AddItemFragment;
import com.person.newscopy.edit.fragment.EditFragment;
import com.person.newscopy.edit.fragment.PreviewFragment;
import com.person.newscopy.image.ImageActivity;
import com.person.newscopy.user.UserViewModel;
import com.person.newscopy.user.Users;
import com.person.newscopy.user.net.bean.BaseResult;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    EditFragment editFragment = null;
    PreviewFragment previewFragment = null;
    UserViewModel userViewModel;
    KeywordFilteringUtil keywordFilteringUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenFitUtil.fit(getApplication(),this,ScreenFitUtil.FIT_WIDTH);
        setContentView(R.layout.activity_edit);
        editFragment = new EditFragment();
        previewFragment = new PreviewFragment();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment,editFragment)
                .commit();
        keywordFilteringUtil = new KeywordFilteringUtil();
        getLifecycle().addObserver(keywordFilteringUtil);
        initData();
    }

    private void initData(){
        keywordFilteringUtil.createAcTree(keywordFilteringUtil.getSensitiveWord(getApplication()));
    }

    public void preview(String html){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment,previewFragment)
                .commit();
        previewFragment.setHtml(html);
    }

    public KeywordFilteringUtil getKeywordFilteringUtil() {
        return keywordFilteringUtil;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(keywordFilteringUtil);
    }

    public void edit(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment,editFragment)
                .commit();
    }

    public void upload(){
        AddItemFragment addItemFragment = new AddItemFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment,addItemFragment)
                .commit();
    }

    public void setTag(String tag){
        editFragment.upload(tag);
        edit();
    }

    public LiveData<BaseResult> release(String content, String title,
                                        String image, String imageList, String tag, String rec){
        return userViewModel.releaseArticle(Users.userId,content,title,image,imageList,tag,rec);
    }
}
