package com.person.newscopy.show.net;

import com.person.newscopy.api.Api;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.show.net.bean.ArticleDetail;
import com.person.newscopy.show.net.bean.AttitudeBean;
import com.person.newscopy.show.net.bean.CommentResult;
import com.person.newscopy.user.net.bean.BaseResult;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ShowInterface {

    @GET(Api.SHOW.ADD_HISTORY)
    Observable<BaseResult> addReadHistory(@Query("userId") String userId, @Query("contentId") String contentId, @Query("contentType") int contentType,
                                          @Query("lookId") String lookId);

    @GET(Api.SHOW.CARE_OR_CANCEL_CARE)
    Observable<BaseResult> careOrNot(@Query("userId")String userId,@Query("careUserId")String careUserId,@Query("isCare")String isCare);

    @POST(Api.SHOW.ADD_MESSAGE)
    @FormUrlEncoded
    Observable<BaseResult> addMessage(@Field("userId") String userId, @Field("messageType") int messageType, @Field("fromUserId") String fromUserId, @Field("content") String content,
                                      @Field("image") String image, @Field("contentId") String contentId);

    @GET(Api.SHOW.FEED_DETAIL)
    Observable<ArticleDetail> queryArticleDetail(@Query("contentId") String contentId, @Query("userId")String userId, @Query("contentUserId")String contentUserId);

    @GET(Api.SHOW.RECOMMEND)
    Observable<ContentResult> feedNewsRecommend(@Query("contentType") int contentType,@Query("tag")String tag);

    @GET(Api.SHOW.RECOMMEND)
    Observable<ContentResult> feedVideoRecommend(@Query("contentType") int contentType);

    @GET(Api.SHOW.FEED_COMMENT)
    Observable<CommentResult> feedComment(@Query("contentId")String contentId);

    @POST(Api.SHOW.ADD_COMMENT)
    @FormUrlEncoded
    Observable<BaseResult> addComment(@Field("userId")String userId,@Field("contentId")String contentId,
                                      @Field("content")String content,@Field("contentType")int contentType,@Field("toId")String toId);

    @GET(Api.USER.LIKE)
    Observable<BaseResult> like(@Query("isLike")String isLike,@Query("userId")String userId,
                                @Query("contentId")String contentId,@Query("releaseUserId")String releaseUserId);

    @GET(Api.USER.SAVE)
    Observable<BaseResult> save(@Query("isSave")String isSave,@Query("userId")String userId,
                                @Query("contentId")String contentId);

    @GET(Api.SHOW.QUERY_CONTENT_ATTITUDE)
    Observable<AttitudeBean> queryContentAttitude(@Query("contentId")String contentId, @Query("userId")String userId, @Query("contentUserId")String contentUserId);

}
