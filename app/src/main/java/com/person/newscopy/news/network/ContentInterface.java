package com.person.newscopy.news.network;

import com.person.newscopy.api.Api;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.user.net.bean.BaseResult;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

public interface ContentInterface {

    @GET(Api.CONTENT.NEWS)
    Observable<ContentResult> feedNews(@Query("tag") String tag, @Query("time") int time, @Query("type") String type);

    @GET(Api.CONTENT.VIDEO)
    Observable<ContentResult> feedVideo(@Query("tag") String tag, @Query("time") int time, @Query("type") String type);

    @FormUrlEncoded
    @POST(Api.CONTENT.ADD_ARTICLE)
    Observable<BaseResult> uploadArticle(@Field("userId") String userId,@Field("content")String content,@Field("title")String title,
                                         @Field("image")String image,@Field("imageList")String imageList,@Field("tag")String tag,
                                         @Field("rec")String rec);

//    @FormUrlEncoded
//    @POST(Api.CONTENT.ADD_SHORT_VIDEO)
//    Observable<BaseResult> uploadShortVideoContent(@Field("userId") String userId,@Field("content")String content,@Field("videoUrl")String videoUrl
//    ,@Field("videoTime") String videoTime,@Field("iconUrl")String iconUrl);

    @Multipart
    @POST(Api.CONTENT.UPLOAD_ARTICLE_IMAGE)
    Observable<BaseResult> uploadArticleImage(@PartMap Map<String, RequestBody> map);

    @GET(Api.CONTENT.FEED_NEWS_RECOMMEND)
    Observable<ContentResult> feedNewsRecommend();

    @GET(Api.CONTENT.FEED_VIDEO_RECOMMEND)
    Observable<ContentResult> feedVideoRecommend();

    @GET(Api.CONTENT.FEED_CARE_USER_ARTICLE_AND_VIDEO)
    Observable<ContentResult> feedCareUserArticleAndVideo(@Query("userId") String userId, @Query("time") int time, @Query("type") String type);

}
