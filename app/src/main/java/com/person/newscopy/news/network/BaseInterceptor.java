package com.person.newscopy.news.network;

import com.person.newscopy.common.BaseUtil;
import com.person.newscopy.common.NetState;
import com.person.newscopy.news.depository.NewsDepository;

import java.io.IOException;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (BaseUtil.getNetType()!= NetState.NOT_NET) {
            Response response = chain.proceed(request);
            int maxTime = 2;
            if (NewsDepository.isloadCache)
                maxTime = 20;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxTime)
                    .build();
        } else {
            //无网络时强制使用缓存数据
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached")
                    .build();
        }
    }
}
