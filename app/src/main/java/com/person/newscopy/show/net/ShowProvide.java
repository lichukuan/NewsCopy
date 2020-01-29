package com.person.newscopy.show.net;

import com.person.newscopy.api.Api;
import com.person.newscopy.common.MyApplication;
import com.person.newscopy.news.network.BaseInterceptor;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.show.net.bean.ArticleDetail;
import com.person.newscopy.show.net.bean.AttitudeBean;
import com.person.newscopy.show.net.bean.CommentResult;
import com.person.newscopy.show.net.bean.NewAttitudeBean;
import com.person.newscopy.user.net.bean.BaseResult;

import java.io.File;
import java.net.Proxy;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.person.newscopy.news.network.ContentProvide.CONTENT_CACHE_STORAGE;
import static com.person.newscopy.news.network.ContentProvide.MAX_CACHE_SPACE;

public class ShowProvide {

    private static ShowProvide provide = new ShowProvide();

    private static ShowInterface showInterface = null;

    private OnShowCallback callback = null;

    private ShowProvide(){}

    public static ShowProvide getInstance(){
        if (showInterface == null){
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
            showInterface = retrofit.create(ShowInterface.class);
        }
        return provide;
    }

    public void setCallback(OnShowCallback callback) {
        this.callback = callback;
    }

    public void addHistory(String userId, String contentId, int contentType,String lookId){
        showInterface.addReadHistory(userId,contentId,contentType,lookId)
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
                       if (callback!=null)callback.onHistoryLoad(baseResult);
                    }
                });
    }

    public void addMessage(String userId, int messageType, String fromUserId, String content,
                           String title){
        showInterface.addMessage(userId,messageType,fromUserId,content,title)
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
                       if (callback!=null)callback.onMessageLoad(baseResult);
                    }
                });
    }

    public void queryNewAttitude(String userId,String contentId,String contentUserId){
        showInterface.queryNewAttitude(userId,contentId,contentUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.onQueryNewAttitude(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });
    }


    public void feedNewsRecommend( int contentType,String tag){
        showInterface.feedNewsRecommend(contentType,tag)
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
                        if (callback!=null)callback.onNewsRecommendLoad(newsResult);
                    }
                });
    }

    public void feedVideoRecommend(int contentType){
        showInterface.feedVideoRecommend(contentType)
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
                       if (callback!=null)callback.onVideoRecommendLoad(videoResult);
                    }
                });
    }

    public void feedComment(String contentId){
        showInterface.feedComment(contentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommentResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                     if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(CommentResult commentResultBean) {
                        if (callback!=null)callback.onCommentLoad(commentResultBean);
                    }
                });
    }

    public void careOrNot(String userId,String careUserId,boolean isCare){
        showInterface.careOrNot(userId,careUserId,String.valueOf(isCare))
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
                        if (callback!=null)callback.onCareOrNot(baseResult);
                    }
                });
    }

    public void like(String isLike, String userId, String contentId, String releaseUserId){
        showInterface.like(isLike,userId,contentId,releaseUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.like(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });
    }

    public void queryContentAttitude(String contentId,String userId,String contentUserId){
        showInterface.queryContentAttitude(contentId,userId,contentUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(attitudeBean -> {
                    callback.onQueryAttitude(attitudeBean);
                },throwable -> {
                    callback.error(throwable);
                });

    }



    public void save(String isSave, String userId, String contentId){
        showInterface.save(isSave,userId,contentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.save(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });
    }

    public void queryArticleDetail(String contentId,String userId,String contentUserId){
        showInterface.queryArticleDetail(contentId,userId,contentUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.queryArticleDetail(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });
    }

    public void addComment(String userId,String contentId,
                           String content,int contentType,String toId){
        showInterface.addComment(userId,contentId,content,contentType,toId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callback.onAddComment(baseResult);
                },throwable -> {
                    callback.error(throwable);
                });
    }


    public interface OnShowCallback{

        void onHistoryLoad(BaseResult baseResult);

        void like(BaseResult baseResult);

        void onQueryAttitude(AttitudeBean bean);

        void save(BaseResult baseResult);

        void onMessageLoad(BaseResult baseResult);

        void onNewsRecommendLoad(ContentResult newsResult);

        void onVideoRecommendLoad(ContentResult videoResult);

        void onCommentLoad(CommentResult commentResultBean);

        void onCareOrNot(BaseResult baseResult);

        void error(Throwable e);

        void queryArticleDetail(ArticleDetail detail);

        void onAddComment(BaseResult baseResult);

        void onQueryNewAttitude(NewAttitudeBean newAttitudeBean);
    }

}
