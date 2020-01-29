package com.person.newscopy.type;

import com.person.newscopy.api.Api;
import com.person.newscopy.type.bean.ContentType;
import com.person.newscopy.type.bean.MessageType;
import com.person.newscopy.type.bean.NewsType;
import com.person.newscopy.type.bean.ShortVideoType;
import com.person.newscopy.type.bean.VideoType;
import com.person.newscopy.user.net.bean.OtherUserInfo;

import java.net.Proxy;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TypeProvide {

    private static TypeProvide typeProvide = new TypeProvide();

    private static TypeInterface typeInterface = null;

    private OnTypeLoadCallback callback = null;

    private TypeProvide(){}

    public void setCallback(OnTypeLoadCallback callback) {
        this.callback = callback;
    }

    public static TypeProvide getInstance() {
        if (typeInterface == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .client(new OkHttpClient()
                            .newBuilder()
                            .build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            typeInterface = retrofit.create(TypeInterface.class);
        }
        return typeProvide;
    }

    public void feedNewsType(){
        typeInterface.feedNewsType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsType>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                      if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(NewsType newsType) {
                        if (callback!=null)callback.onNewsTypeLoad(newsType);
                    }
                });
    }

    public void feedVideoType(){
        typeInterface.feedVideoType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VideoType>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(VideoType videoType) {
                        if (callback!=null)callback.onVideoTypeLoad(videoType);
                    }
                });
    }

    public void feedShortVideoType(){
        typeInterface.feedShortVideoType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ShortVideoType>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(ShortVideoType shortVideoType) {
                        if (callback!=null)callback.onShortVideoTypeLoad(shortVideoType);
                    }
                });
    }

    public void feedMessageType(){
        typeInterface.feedMessageType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MessageType>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(MessageType messageType) {
                        if (callback!=null)callback.onMessageTypeLoad(messageType);
                    }
                });
    }

    public void feedContentType(){
       typeInterface.feedContent()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Subscriber<ContentType>() {
                   @Override
                   public void onCompleted() {

                   }

                   @Override
                   public void onError(Throwable e) {
                       if (callback!=null)callback.error(e);
                   }

                   @Override
                   public void onNext(ContentType contentType) {
                       if (callback!=null)callback.onContentTypeLoad(contentType);
                   }
               });
    }

    public void login(String name,String pas,String salt){
        typeInterface.login(name,pas,salt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OtherUserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback!=null)callback.error(e);
                    }

                    @Override
                    public void onNext(OtherUserInfo userInfoBean) {
                        if (callback!=null)callback.onLogin(userInfoBean);
                    }
                });
    }

    public interface OnTypeLoadCallback{
        void onNewsTypeLoad(NewsType newsType);

        void onVideoTypeLoad(VideoType videoType);

        void onShortVideoTypeLoad(ShortVideoType shortVideoType);

        void onMessageTypeLoad(MessageType messageType);

        void onContentTypeLoad(ContentType contentType);

        void onLogin(OtherUserInfo userInfoBean);

        void error(Throwable e);
    }

}
