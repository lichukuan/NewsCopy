package com.person.newscopy.search.net;

import com.person.newscopy.api.Api;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.user.net.bean.OneContentResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SearchInterface {

    @GET(Api.SEARCH.QUERY_HOT_CONTENT)
    Observable<ContentResult> queryHot();

    @GET(Api.SEARCH.SEARCH_REQUIREED_CONTENT)
    Observable<ContentResult> querySearchContent(@Query("content") String content);
}
