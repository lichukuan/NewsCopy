package com.person.newscopy.news.network;

import com.person.newscopy.api.VideoApi;
import com.person.newscopy.news.network.bean.VideoChannelBean;
import com.person.newscopy.news.network.bean.VideoHotBean;
import com.person.newscopy.news.network.bean.VideoLiveBean;
import com.person.newscopy.news.network.bean.VideoSearchBean;
import com.person.newscopy.news.network.bean.VideoType;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface VideoInterface {

    @GET(VideoApi.VIDEO.VIDEO_LIVE)
    Observable<VideoLiveBean> feedLive();

    @GET(VideoApi.VIDEO.VIDEO_TYPE)
    Observable<VideoType> feedVideoType();

    @GET(VideoApi.VIDEO.VIDEO_SEARCH_HOT_SEARCH)
    Observable<VideoHotBean> feedHotSearch();

    @GET(VideoApi.VIDEO.VIDEO_SEARCH_RECOMMEND)
    Observable<VideoSearchBean> feedSearchInfo();

    @GET(VideoApi.VIDEO.FEED_REQUEST_VIDEO_INFO)
    Observable<VideoChannelBean> feedVideoChannel(@Query("channelId") String channelId, @Query("maxTime") String maxTime, @Query("count") int count);
}
