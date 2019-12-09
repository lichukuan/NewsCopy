package com.person.newscopy.news.depository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.util.SparseArray;

import com.person.newscopy.news.network.ContentProvide;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.user.net.bean.BaseResult;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentDepository implements ContentProvide.OnFeedContentCallback{

    private ContentProvide provide;

    private Map<String,MutableLiveData<ContentResult>> articleMap = new HashMap<>(20);
    private Map<String,MutableLiveData<ContentResult>> videoMap = new HashMap<>(20);
    private SparseArray<MutableLiveData<BaseResult>> arrayData = new SparseArray<>();
    private MutableLiveData<ContentResult> careUserData  = new MutableLiveData<>();
    private MutableLiveData<ContentResult> newsRecommendData = new MutableLiveData<>();
    private MutableLiveData<ContentResult> videoRecommendData = new MutableLiveData<>();

    public static final int ARTICLE_TYPE = 0;

    @Deprecated
    public static final int SHORT_VIDEO_CONTENT_TYPE = 1;

    public static final int ARTICLE_IMAGE_TYPE = 2;

    public ContentDepository() {
        provide = ContentProvide.getInstance();
        provide.setCallback(this);
    }

    private void updateData(int type,BaseResult baseResult){
        MutableLiveData<BaseResult> r = arrayData.get(type);
        r.setValue(baseResult);
        arrayData.setValueAt(type,r);
    }

    public void feedNews(String tag, int time, String type){
       if (articleMap.get(tag) == null)
           articleMap.put(tag, new MutableLiveData<>());
       provide.feedNews(tag,time,type);
    }

    public void feedVideo(String tag,int time,String type){
      if (videoMap.get(tag) == null)
          videoMap.put(tag, new MutableLiveData<>());
      provide.feedVideo(tag,time,type);
    }


    public MutableLiveData<ContentResult> getNewsData(String tag) {
        return articleMap.get(tag);
    }

    public MutableLiveData<ContentResult> getVideoData(String tag) {
        return videoMap.get(tag);
    }


    public MutableLiveData<BaseResult> pickRequireData(int type){
        MutableLiveData<BaseResult> r = arrayData.get(type);
        if (r == null){
            r = new MutableLiveData<>();
            arrayData.put(type,r);
        }
        return r;
    }

    public void feedCareUserArticleAndVideo(String userId,int time,String type){
        provide.feedCareUserArticleAndVideo(userId,time,type);
    }

    @Override
    public void onFeedCareUserArticleAndVideo(ContentResult contentResult) {
        careUserData.setValue(contentResult);
    }

    public MutableLiveData<ContentResult> getCareUserData() {
        return careUserData;
    }

    @Override
    public void onFeedNews(String tag,ContentResult newsResult) {
        articleMap.get(tag).setValue(newsResult);
    }

    @Override
    public void onFeedVideo(String tag,ContentResult videoResult) {
       videoMap.get(tag).setValue(videoResult);
    }

    public void uploadArticle(String userId,String content, String title, String image, String imageList, String tag, String rec){
        provide.uploadArticle(userId,content,title,image,imageList,tag,rec);
    }

    public void uploadArticleImage(List<String> paths){
        provide.uploadArticleContentImage(paths);
    }


    @Override
    public void error(Throwable e) {
        Log.d("===ContentDepository",e.getMessage());
    }

    @Override
    public void onUploadArticle(BaseResult baseResult) {
        updateData(ARTICLE_TYPE,baseResult);
    }

    @Override
    public void onUploadArticleImage(BaseResult baseResult) {
       updateData(ARTICLE_IMAGE_TYPE,baseResult);
    }

    public void feedNewsRecommend(){
        provide.feedNewsRecommend();
    }

    public void feedVideoRecommend(){
        provide.feedVideoRecommend();
    }

    @Override
    public void onNewsRecommend(ContentResult newsResult) {
        newsRecommendData.setValue(newsResult);
    }

    @Override
    public void onVideoRecommend(ContentResult videoResult) {
       videoRecommendData.setValue(videoResult);
    }

    public MutableLiveData<ContentResult> getNewsRecommendData() {
        return newsRecommendData;
    }

    public MutableLiveData<ContentResult> getVideoRecommendData() {
        return videoRecommendData;
    }
}
