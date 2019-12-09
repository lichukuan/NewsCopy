package com.person.newscopy.news;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.person.newscopy.news.depository.ContentDepository;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.user.net.bean.BaseResult;
import java.io.File;
import java.util.List;

public class ContentViewModel extends AndroidViewModel {
    
    private ContentDepository contentDepository;
    
    public ContentViewModel(@NonNull Application application) {
        super(application);
        contentDepository = new ContentDepository();
    }

    public LiveData<ContentResult> feedNews(String tag, int time, String type){
        contentDepository.feedNews(tag,time,type);
        return contentDepository.getNewsData(tag);
    }

    public LiveData<ContentResult> feedUserCareData(String userId,int time,String type){
        contentDepository.feedCareUserArticleAndVideo(userId,time,type);
        return contentDepository.getCareUserData();
    }

    public LiveData<ContentResult> feedVideo(String tag, int time, String type){
        contentDepository.feedVideo(tag,time,type);
        return contentDepository.getVideoData(tag);
    }


    public LiveData<BaseResult> uploadArticle(String userId,String content, String title, String image, String imageList, String tag, String rec){
        contentDepository.uploadArticle(userId,content,title,image,imageList,tag,rec);
        return contentDepository.pickRequireData(ContentDepository.ARTICLE_TYPE);
    }


    public LiveData<BaseResult> uploadArticleImage(List<String> paths){
        contentDepository.uploadArticleImage(paths);
        return contentDepository.pickRequireData(ContentDepository.ARTICLE_IMAGE_TYPE);
    }


    public LiveData<ContentResult> feedNesRecommend(){
        contentDepository.feedNewsRecommend();
        return contentDepository.getNewsRecommendData();
    }

    public LiveData<ContentResult> feedVideoRecommend(){
        contentDepository.feedVideoRecommend();
        return contentDepository.getVideoRecommendData();
    }
}
