package com.person.newscopy.news.network;

import com.person.newscopy.api.NewsApi;
import com.person.newscopy.news.network.bean.CommentBean;
import com.person.newscopy.news.network.bean.HotNewsBean;
import com.person.newscopy.news.network.bean.NewsBean;
import com.person.newscopy.news.network.bean.ReplyBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface NewsInterface {

    @GET(NewsApi.NEWS.FEED_NEWS_ALL)
    Observable<NewsBean> feedAllNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_HOT)
    Observable<NewsBean> feedHomeHotNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_TECH)
    Observable<NewsBean> feedTechNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_WORLD)
    Observable<NewsBean> feedWorldNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_POLITICS)
    Observable<NewsBean> feedPoliticsNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_LOTTERY)
    Observable<NewsBean> feedLotteryNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_SPORTS)
    Observable<NewsBean> feedSportsNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_SOCIETY)
    Observable<NewsBean> feedSocietyNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_HOME)
    Observable<NewsBean> feedHomeNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_INTERNET)
    Observable<NewsBean> feedInternetNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_SOFTWARE)
    Observable<NewsBean> feedSoftwareNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_SMART_HOME)
    Observable<NewsBean> feedSmartHomeNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_ENTERTAINMENT)
    Observable<NewsBean> feedEntertainmentNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_MOVIE)
    Observable<NewsBean> feedMovieNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_TELEPLAY)
    Observable<NewsBean> feedTeleplayNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_SHOWS)
    Observable<NewsBean> feedShowsNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_GOSSIP)
    Observable<NewsBean> feedGossipNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_GAME)
    Observable<NewsBean> feedGameNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_NBA)
    Observable<NewsBean> feedNBANews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_CAR)
    Observable<NewsBean> feedCARNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_FINANCE)
    Observable<NewsBean> feedFinanceNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_STOCK)
    Observable<NewsBean> feedStockNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_FUNNY)
    Observable<NewsBean> feedFunnyNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_MILITARY)
    Observable<NewsBean> feedMilitaryNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_BABY)
    Observable<NewsBean> feedBabyNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_FOOD)
    Observable<NewsBean> feedFoodNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_FASHION)
    Observable<NewsBean> feedFashionNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_DISCOVERY)
    Observable<NewsBean> feedDiscoveryNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_REGIMEN)
    Observable<NewsBean> feedRegimenNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_HISTORY)
    Observable<NewsBean> feedHistoryNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_ESSAY)
    Observable<NewsBean> feedEssayNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_TRAVEL)
    Observable<NewsBean> feedTravelNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_HOT_GALLERY)
    Observable<NewsBean> feedHotGalleryNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_GALLERY_OLD_PICTURE)
    Observable<NewsBean> feedOldPictureGalleryNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);
    @GET(NewsApi.NEWS.FEED_NEWS_GALLERY_PHOTOGRATHY)
    Observable<NewsBean> feedPhotographyGalleryNews(@Query("max_behot_time_tmp") int time1,@Query("max_behot_time") int time,@Query("widen")int widen);

    @GET(NewsApi.COMMENT_AND_REPLY.COMMENT_REPLY)
    Observable<ReplyBean> feedReply(@Query("comment_id")String commentId,@Query("dongtai_id")String dongtaiId,@Query("offset")int offset,@Query("count")int count);

    @GET(NewsApi.COMMENT_AND_REPLY.NEWS_COMMENT)
    Observable<CommentBean> feedComment(@Query("group_id")String groupId,@Query("item_id")String itemId,@Query("offset")int offset,@Query("count")int count);

    @GET(NewsApi.NEWS.HOT_NEWS)
    Observable<HotNewsBean> feedHotNews();
}
