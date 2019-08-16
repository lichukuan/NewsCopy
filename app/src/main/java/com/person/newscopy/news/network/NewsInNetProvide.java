package com.person.newscopy.news.network;

import com.person.newscopy.api.NewsApi;
import com.person.newscopy.common.MyApplication;
import com.person.newscopy.news.network.bean.CommentBean;
import com.person.newscopy.news.network.bean.HotNewsBean;
import com.person.newscopy.news.network.bean.NewsBean;
import com.person.newscopy.news.network.bean.ReplyBean;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewsInNetProvide {

    public static final String NEWS_CACHE_STORAGE = "news_cache";

    public static final int MAX_CACHE_SPACE = 1024*1024*20;

    private static final NewsInNetProvide ourInstance = new NewsInNetProvide();

    private static NewsInterface provide=null;

    private onNewsLoadCallback callback=null;

    public static NewsInNetProvide getInstance() {
       if (provide == null){
           Retrofit retrofit=new Retrofit.Builder()
                   .baseUrl(NewsApi.BASE_URL)
                   .client(new OkHttpClient()
                   .newBuilder()
                   .cache(new Cache(new File(MyApplication.getContext().getCacheDir(),NEWS_CACHE_STORAGE),MAX_CACHE_SPACE))
                   .addInterceptor(new BaseInterceptor())
                   .addNetworkInterceptor(new BaseInterceptor())
                   .build())
                   .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
           provide=retrofit.create(NewsInterface.class);
       }
        return ourInstance;
    }

    private NewsInNetProvide() {
    }

    public void setCallback(onNewsLoadCallback callback) {
        this.callback = callback;
    }

    public void feedNews(final NewsType type, int time, int widen){
       Observable<NewsBean> observable = null;
       switch (type){
           case All:
               observable=provide.feedAllNews(time,time,widen);
               break;
           case HOT:
               observable=provide.feedHomeHotNews(time,time,widen);
               break;
           case TECH:
               observable=provide.feedTechNews(time,time,widen);
               break;
           case WORLD:
               observable=provide.feedWorldNews(time,time,widen);
               break;
           case POLITICS:
               observable=provide.feedPoliticsNews(time,time,widen);
               break;
           case LOTTERY:
               observable=provide.feedLotteryNews(time,time,widen);
               break;
           case SPORTS:
               observable=provide.feedSportsNews(time,time,widen);
               break;
           case SOCIETY:
               observable=provide.feedSocietyNews(time,time,widen);
               break;
           case HOME:
               observable=provide.feedHomeNews(time,time,widen);
               break;
           case INTERNET:
               observable=provide.feedInternetNews(time,time,widen);
               break;
           case SOFTWARE:
               observable=provide.feedSoftwareNews(time,time,widen);
               break;
           case SMART_HOME:
               observable=provide.feedSmartHomeNews(time,time,widen);
               break;
           case ENTERTAINMENT:
               observable=provide.feedEntertainmentNews(time,time,widen);
               break;
           case MOVIE:
               observable=provide.feedMovieNews(time,time,widen);
               break;
           case TELEPLAY:
               observable=provide.feedTeleplayNews(time,time,widen);
               break;
           case SHOWS:
               observable=provide.feedShowsNews(time,time,widen);
               break;
           case GOSSIP:
               observable=provide.feedGossipNews(time,time,widen);
               break;
           case NBA:
              observable=provide.feedNBANews(time,time,widen);
              break;
           case GAME:
               observable=provide.feedGameNews(time,time,widen);
               break;
           case CAR:
               observable=provide.feedCARNews(time,time,widen);
               break;
           case FINANCE:
               observable=provide.feedFinanceNews(time,time,widen);
               break;
           case STOCK:
               observable=provide.feedStockNews(time,time,widen);
               break;
           case FUNNY:
               observable=provide.feedFunnyNews(time,time,widen);
               break;
           case MILITARY:
               observable=provide.feedMilitaryNews(time,time,widen);
               break;
           case BABY:
               observable=provide.feedBabyNews(time,time,widen);
               break;
           case FOOD:
               observable=provide.feedFoodNews(time,time,widen);
               break;
           case FASHION:
               observable=provide.feedFashionNews(time,time,widen);
               break;
           case DISCOVERY:
               observable=provide.feedDiscoveryNews(time,time,widen);
               break;
           case REGIMEN:
               observable=provide.feedRegimenNews(time,time,widen);
               break;
           case HISTORY:
               observable=provide.feedHistoryNews(time,time,widen);
               break;
           case ESSAY:
               observable=provide.feedEssayNews(time,time,widen);
               break;
           case TRAVEL:
               observable=provide.feedTravelNews(time,time,widen);
               break;
           case HOT_GALLERY:
               observable=provide.feedHotGalleryNews(time,time,widen);
               break;
           case OLD_PICTURE:
               observable=provide.feedOldPictureGalleryNews(time,time,widen);
               break;
           case GALLERY_PHOTOGRATHY:
               observable=provide.feedPhotographyGalleryNews(time,time,widen);
               break;
           default:
               return ;
       }
       observable.subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Subscriber<NewsBean>() {
                     @Override
                     public void onCompleted() {

                     }

                     @Override
                     public void onError(Throwable e) {
                         if (callback!=null)
                         callback.error(type,e);
                     }

                     @Override
                     public void onNext(NewsBean newsBean) {
                          if (callback!=null)
                              callback.success(type,newsBean);
                     }
                 });
   }

    public void feedHotNews(){
        provide.feedHotNews().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HotNewsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)
                        callback.error(e);
                    }

                    @Override
                    public void onNext(HotNewsBean hotNewsBean) {
                        if (callback!=null)
                            callback.success(hotNewsBean);
                    }
                });
    }

    public void feedComments(String groupId,String itemId,int offset,int count){
        provide.feedComment(groupId,itemId,offset,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                         if(callback!=null)
                             callback.error(e);
                    }

                    @Override
                    public void onNext(CommentBean bean) {
                        if(callback!=null)
                            callback.success(bean);
                    }
                });
    }

    public void feedReply(final String commentId, String dongtaiId, int offset, int count){
        provide.feedReply(commentId,dongtaiId,offset,count)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReplyBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)
                        callback.error(e);
                    }

                    @Override
                    public void onNext(ReplyBean bean) {
                        if (callback!=null)
                            callback.success(bean);
                    }
                });
    }

   public interface onNewsLoadCallback{

       void error(NewsType code,Throwable e);

       void success(NewsType code,NewsBean bean);

       void error(Throwable e);

       void success(HotNewsBean bean);

       void success(ReplyBean bean);

       void success(CommentBean bean);
   }
}
