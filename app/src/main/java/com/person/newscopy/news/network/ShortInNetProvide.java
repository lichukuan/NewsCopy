package com.person.newscopy.news.network;

import com.person.newscopy.api.NewsApi;
import com.person.newscopy.api.ShortVideoApi;
import com.person.newscopy.common.MyApplication;
import com.person.newscopy.news.network.shortBean.ShortInfoBean;

import java.io.File;
import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.person.newscopy.news.network.NewsInNetProvide.MAX_CACHE_SPACE;
import static com.person.newscopy.news.network.NewsInNetProvide.NEWS_CACHE_STORAGE;

public class ShortInNetProvide {

    private static final ShortInNetProvide ourInstance = new ShortInNetProvide();

    private static  ShortInterface provide = null;

    public static ShortInNetProvide getInstance() {
        if (provide==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(ShortVideoApi.BASE_URL)
                    .client(new OkHttpClient()
                            .newBuilder()
                            .cache(new Cache(new File(MyApplication.getContext().getCacheDir(),NEWS_CACHE_STORAGE),MAX_CACHE_SPACE))
                            .addInterceptor(new BaseInterceptor())
                            .addNetworkInterceptor(new BaseInterceptor())
                            .build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            provide=retrofit.create(ShortInterface.class);
        }
        return ourInstance;
    }

    private OnShortLoadListener loadListener = null;

    public void setLoadListener(OnShortLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    private ShortInNetProvide() {

    }

    public void getShortInfo(int type){
        provide.getShortInfo(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ShortInfoBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                          if (loadListener!=null)
                              loadListener.error(e);
                    }

                    @Override
                    public void onNext(List<ShortInfoBean> shortInfoBeans) {
                        if (loadListener!=null)
                            loadListener.success(type,shortInfoBeans);
                    }
                });
    }

    public interface OnShortLoadListener{
        void success(int type,List<ShortInfoBean> beans);

        void error(Throwable e);
    }

}
