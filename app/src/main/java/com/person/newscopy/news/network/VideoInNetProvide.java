package com.person.newscopy.news.network;

import com.person.newscopy.api.VideoApi;
import com.person.newscopy.common.MyApplication;
import com.person.newscopy.news.network.bean.VideoChannelBean;
import com.person.newscopy.news.network.bean.VideoHotBean;
import com.person.newscopy.news.network.bean.VideoLiveBean;
import com.person.newscopy.news.network.bean.VideoSearchBean;
import com.person.newscopy.news.network.bean.VideoType;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VideoInNetProvide {

    public static final String VIDEO_CACHE_STORAGE = "video_cache";

    public static final int VIDEO_MAX_CACHE_SPACE = 1024*1024*20;

    private static final VideoInNetProvide ourInstance = new VideoInNetProvide();

    private static VideoInterface provide=null;

    private VideoInfoLoadCallback callback;

    public static VideoInNetProvide getInstance() {
        if (provide==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(VideoApi.BASE_URL)
                    .client(new OkHttpClient()
                            .newBuilder()
                            .cache(new Cache(new File(MyApplication.getContext().getCacheDir(),VIDEO_CACHE_STORAGE),VIDEO_MAX_CACHE_SPACE))
                            .addInterceptor(new BaseInterceptor())
                            .build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            provide=retrofit.create(VideoInterface.class);
        }
        return ourInstance;
    }

    private VideoInNetProvide() {
    }

    public void setCallback(VideoInfoLoadCallback callback) {
        this.callback = callback;
    }

    public void feedLiveVideoInfo(){
        provide.feedLive()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<VideoLiveBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                  if (callback!=null)
                      callback.error(e);
            }

            @Override
            public void onNext(VideoLiveBean videoLiveBean) {
                    if (callback!=null)
                        callback.success(videoLiveBean);
            }
        });
    }

    public void feedVideoType(){
        provide.feedVideoType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VideoType>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)
                         callback.error(e);
                    }

                    @Override
                    public void onNext(VideoType videoType) {
                          if (callback!=null)
                              callback.success(videoType);
                    }
                });
    }

    public void feedVideoSearch(){
        provide.feedSearchInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VideoSearchBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                         if (callback!=null)
                             callback.error(e);
                    }

                    @Override
                    public void onNext(VideoSearchBean videoSearchBean) {
                          if (callback!=null)
                              callback.success(videoSearchBean);
                    }
                });
    }

    public void feedVideoHot(){
        provide.feedHotSearch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VideoHotBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                           if (callback!=null)
                               callback.error(e);
                    }

                    @Override
                    public void onNext(VideoHotBean videoHotBean) {
                           if (callback!=null)
                               callback.success(videoHotBean);
                    }
                });
    }

    public void feedVideoChannel(final String channelId, String maxTime, int count){
        provide.feedVideoChannel(channelId,maxTime,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VideoChannelBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)
                            callback.error(e);
                    }

                    @Override
                    public void onNext(VideoChannelBean videoChannelBean) {
                        if (callback!=null)
                            callback.success(videoChannelBean);
                    }
                });
    }

    public interface VideoInfoLoadCallback{

        void success(VideoLiveBean videoLiveBean);

        void error(Throwable e);

        void success(VideoType videoType);

        void success(VideoSearchBean videoSearchBean);

        void success(VideoHotBean videoHotBean);

        void success(VideoChannelBean videoChannelBean);
    }

}
