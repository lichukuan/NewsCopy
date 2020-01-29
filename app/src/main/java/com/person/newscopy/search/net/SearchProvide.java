package com.person.newscopy.search.net;

import com.person.newscopy.api.Api;
import com.person.newscopy.common.MyApplication;
import com.person.newscopy.news.network.BaseInterceptor;
import com.person.newscopy.news.network.ContentProvide;
import com.person.newscopy.news.network.bean.ContentResult;

import java.io.File;
import java.net.Proxy;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchProvide {

    private SearchProvide(){

    }

    public static final String USER_CACHE_STORAGE = "search_cache";

    static boolean isInit = false;

    private CallBackListener callBackListener = null;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    private static class SearchProvideHolder{
        final static SearchProvide INSTANCE = new SearchProvide();
    }

    private static SearchInterface searchInterface;

    public static SearchProvide getInstance(){
        if (!isInit){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .client(new OkHttpClient()
                            .newBuilder()
                            .cache(new Cache(new File(MyApplication.getContext().getCacheDir(),USER_CACHE_STORAGE), ContentProvide.MAX_CACHE_SPACE))
                            .proxy(Proxy.NO_PROXY)
                            .addInterceptor(new BaseInterceptor())
                            .addNetworkInterceptor(new BaseInterceptor())
                            .build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            searchInterface = retrofit.create(SearchInterface.class);
        }
        return SearchProvideHolder.INSTANCE;
    }

    public void querySearchContent(String content){
        searchInterface.querySearchContent(content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callBackListener.onQuerySearchContent(baseResult);
                },throwable -> {
                    callBackListener.error(throwable);
                });
    }

    public void queryHot(){
        searchInterface.queryHot()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResult -> {
                    callBackListener.onQueryHotData(baseResult);
                },throwable -> {
                    callBackListener.error(throwable);
                });
    }

    public interface CallBackListener{
        void onQuerySearchContent(ContentResult contentResult);

        void onQueryHotData(ContentResult contentResult);

        void error(Throwable e);
    }

}
