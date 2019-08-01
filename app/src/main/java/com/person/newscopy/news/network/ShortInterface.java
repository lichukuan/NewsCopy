package com.person.newscopy.news.network;

import com.person.newscopy.api.ShortVideoApi;
import com.person.newscopy.news.network.shortBean.ShortInfoBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ShortInterface {

    @GET(ShortVideoApi.SHORT_VIDEO_INFO)
    Observable<List<ShortInfoBean>> getShortInfo(@Query("type") int type);

}
