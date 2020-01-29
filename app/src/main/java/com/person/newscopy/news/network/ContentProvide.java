package com.person.newscopy.news.network;

import com.person.newscopy.api.Api;
import com.person.newscopy.common.MyApplication;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.user.net.bean.BaseResult;
import java.io.File;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ContentProvide {

    public static final String CONTENT_CACHE_STORAGE = "content_cache";

    public static final int MAX_CACHE_SPACE = 1024*1024*20;

    private ContentProvide(){}

    private static ContentProvide provide = new ContentProvide();

    private static ContentInterface contentInterface = null;

    private OnFeedContentCallback callback = null;

    public static ContentProvide getInstance(){
        if (contentInterface == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .client(new OkHttpClient()
                            .newBuilder()
                            .cache(new Cache(new File(MyApplication.getContext().getCacheDir(),CONTENT_CACHE_STORAGE),MAX_CACHE_SPACE))
                            .proxy(Proxy.NO_PROXY)
                            .addInterceptor(new BaseInterceptor())
                            .addNetworkInterceptor(new BaseInterceptor())
                            .build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            contentInterface = retrofit.create(ContentInterface.class);
        }
        return provide;
    }

    public void setCallback(OnFeedContentCallback callback) {
        this.callback = callback;
    }

    public void feedNews(String tag, int time, String type){
        contentInterface.feedNews(tag,time,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ContentResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(ContentResult newsResult) {
                       if (callback!=null)callback.onFeedNews(tag,newsResult);
                    }
                });
    }

    public void feedVideo(String tag,int time,String type){
        contentInterface.feedVideo(tag,time,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ContentResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                      if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(ContentResult videoResult) {
                      if (callback!=null)callback.onFeedVideo(tag,videoResult);
                    }
                });
    }


    public void uploadArticle(String userId,String content, String title, String image, String imageList, String tag, String rec){
        contentInterface.uploadArticle(userId,content,title,image,imageList,tag,rec)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                      if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (callback!=null)callback.onUploadArticle(baseResult);
                    }
                });
    }

//    @Deprecated
//    public void uploadShortVideoContent(String userId ,String content,String videoUrl,String videoTime,String iconUrl){
//         contentInterface.uploadShortVideoContent(userId,content,videoUrl,videoTime,iconUrl)
//                 .subscribeOn(Schedulers.io())
//                 .observeOn(AndroidSchedulers.mainThread())
//                 .subscribe(new Subscriber<BaseResult>() {
//                     @Override
//                     public void onCompleted() {
//
//                     }
//
//                     @Override
//                     public void onError(Throwable e) {
//                         if (callback!=null)callback.error(e);
//                     }
//
//                     @Override
//                     public void onNext(BaseResult baseResult) {
//                         if (callback!=null)callback.onUploadShortVideoContent(baseResult);
//                     }
//                 });
//    }


    public void uploadArticleContentImage(List<String> paths){
        Map<String, RequestBody> map = new HashMap<>();
        int i = 1;
        for (String path:paths){
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            //注意这里
            map.put("image"+i+"\""+"; filename=\"\"",requestBody);
            i++;
        }
        contentInterface.uploadArticleImage(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(BaseResult baseResult) {
                        if (callback!=null)callback.onUploadArticleImage(baseResult);
                    }
                });
    }

    public void feedNewsRecommend(){
        contentInterface.feedNewsRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsResult -> {
                  callback.onNewsRecommend(newsResult);
                },throwable -> {
                  callback.error(throwable);
                });
    }

    public void feedVideoRecommend(){
        contentInterface.feedVideoRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(videoResult -> {
                    callback.onVideoRecommend(videoResult);
                },throwable -> {
                    callback.error(throwable);
                });
    }

    public void feedCareUserArticleAndVideo(String userId,int time,String type){
        contentInterface.feedCareUserArticleAndVideo(userId,time,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(videoResult -> {
                    callback.onFeedCareUserArticleAndVideo(videoResult);
                },throwable -> {
                    callback.error(throwable);
                });

    }

//    public void uploadShortVideo(File file){
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("short", file.getName(), requestBody);
//        contentInterface.uploadShortVideo(body)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<BaseResult>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (callback!=null)callback.error(e);
//                    }
//
//                    @Override
//                    public void onNext(BaseResult baseResult) {
//                        if (callback!=null)callback.onUploadShortVideo(baseResult);
//                    }
//                });
//    }

    public interface OnFeedContentCallback{

        void onFeedCareUserArticleAndVideo(ContentResult contentResult);

        void onFeedNews(String tag,ContentResult newsResult);

        void onFeedVideo(String tag,ContentResult videoResult);

        void error(Throwable e);

        void onUploadArticle(BaseResult baseResult);

        void onUploadArticleImage(BaseResult baseResult);

        void onNewsRecommend(ContentResult newsResult);

        void onVideoRecommend(ContentResult videoResult);
    }

}
