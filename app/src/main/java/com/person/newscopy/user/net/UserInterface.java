package com.person.newscopy.user.net;

import com.person.newscopy.api.Api;
import com.person.newscopy.news.network.bean.ContentResult;
import com.person.newscopy.user.net.bean.AllCareOrFans;
import com.person.newscopy.user.net.bean.AllPrivateTalkInfoBean;
import com.person.newscopy.user.net.bean.BaseResult;
import com.person.newscopy.user.net.bean.MessageBean;
import com.person.newscopy.user.net.bean.OneContentResult;
import com.person.newscopy.user.net.bean.OtherUserInfo;
import com.person.newscopy.user.net.bean.ReadBean;
import com.person.newscopy.user.net.bean.SimpleTalkBean;
import com.person.newscopy.user.net.bean.SimpleTalkData;
import com.person.newscopy.user.net.bean.UserBean;
import com.person.newscopy.user.net.bean.VersionBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

public interface UserInterface {

    @GET(Api.USER.DELETE_SAVE)
    Observable<BaseResult> deleteSave(@Query("userId")String userId,@Query("contentId")String contentId);

    @GET(Api.USER.QUERY_SAVE)
    Observable<ContentResult> querySave(@Query("userId")String userId);

    @POST(Api.USER.REGISTER)
    @FormUrlEncoded
    Observable<BaseResult> register(@Field("name") String name,@Field("email")String email ,@Field("pas") String pas);

    @GET(Api.USER.QUERY_CONTENT_INFO)
    Observable<OneContentResult> getContent(@Query("contentType")int contentType, @Query("contentId")String contentId);

    @POST(Api.SHOW.ADD_MESSAGE)
    @FormUrlEncoded
    Observable<BaseResult> addMessage(@Field("userId") String userId, @Field("messageType") int messageType, @Field("fromUserId") String fromUserId, @Field("content") String content,
                                      @Field("title") String title);

    @POST(Api.USER.LOGIN)
    @FormUrlEncoded
    Observable<UserBean> login(@Field("name")String name, @Field("pas")String pas, @Field("salt")String salt);

    @GET(Api.USER.QUERY_CARE)
    Observable<AllCareOrFans> queryCare(@Query("userId")String userId);

    @GET(Api.USER.QUERY_FANS)
    Observable<AllCareOrFans> queryFans(@Query("userId")String userId);

    @Multipart
    @POST(Api.USER.UPLOAD_USER_IMAGE)
    Observable<BaseResult> uploadUserIcon(@Part MultipartBody.Part file);

    @GET(Api.USER.UPDATE_USER_RECOMMEND)
    Observable<BaseResult> updateUserRecommend(@Query("userId")String userId,@Query("rec")String recommend);

    @GET(Api.USER.CHANGE_USER_NAME)
    Observable<BaseResult> changeUserName(@Query("id")String id,@Query("newName")String newName);

    @GET(Api.USER.CHANGE_USER_PAS)
    Observable<BaseResult> changeUserPas(@Query("oldPas")String oldPas,@Query("newPas")String newPas,@Query("id")String id,@Query("salt")String salt);

    @GET(Api.USER.CHANGE_EMAIL)
    Observable<BaseResult> changeUserEmail(@Query("userId")String userId,@Query("email")String email);

    @GET(Api.USER.GET_USER_PAS)
    Observable<BaseResult> getUserPas(@Query("email")String email);

    @GET(Api.USER.UPDATE_USER_ICON)
    Observable<BaseResult> changeUserIcon(@Query("userId")String userId,@Query("url")String url);

    @GET(Api.USER.CARE_OR_CANCEL_CARE)
    Observable<BaseResult> careOrNot(@Query("userId")String userId,@Query("careUserId")String careUserId,@Query("isCare")String isCare);


    @GET(Api.SHOW.QUERY_IS_CARE)
    Observable<BaseResult> queryIsCare(@Query("userId")String userId,@Query("searchId")String searchId);

    @GET(Api.USER.HISTORY)
    Observable<ReadBean> queryReadHistory(@Query("userId")String userId);

    @GET(Api.USER.DELETE_USER_READ)
    Observable<BaseResult> deleteReadHistory(@Query("contentIds") String contentIds);

    @GET(Api.USER.ADD_PRIVATE_TALK)
    Observable<BaseResult> addPrivateTalk(@Query("userId")String userId,@Query("to")String to,@Query("content")String content);

    @GET(Api.USER.QUERY_PRIVATE_TALK)
    Observable<AllPrivateTalkInfoBean> queryAllPrivateTalk(@Query("userId")String userId,@Query("toId")String toId);

    @GET(Api.USER.ALL_SIMPLE_TALK)
    Observable<SimpleTalkData> querySimpleTalkBean(@Query("userId")String userId);

    @GET(Api.USER.QUERY_OTHER_USER_INFO)
    Observable<OtherUserInfo> queryRequireUserInfo(@Query("userId")String userId);

    @GET(Api.USER.QUERY_MESSAGE)
    Observable<MessageBean> queryMessages(@Query("userId")String userId);

    @GET(Api.USER.NEW_VERSION)
    Observable<VersionBean> queryNewestVersion();

    @GET(Api.USER.LIKE)
    Observable<BaseResult> like(@Query("isLike")String isLike,@Query("userId")String userId,
                                @Query("contentId")String contentId,@Query("releaseUserId")String releaseUserId);

    @GET(Api.USER.SAVE)
    Observable<BaseResult> save(@Query("isSave")String isSave,@Query("userId")String userId,
                                @Query("contentId")String contentId);

    @GET(Api.USER.SEND)
    Observable<BaseResult> send(@Query("isSend")String isSend,@Query("userId")String userId,
                                @Query("contentId")String contentId);
    @POST(Api.USER.RELEASE_ARTICLE)
    @FormUrlEncoded
    Observable<BaseResult> releaseArticle(@Field("userId") String userId, @Field("content") String content,@Field("title")String title,
                                          @Field("image")String image,@Field("imageList")String imageList,@Field("tag")String tag,
                                          @Field("rec")String rec);

    @Multipart
    @POST(Api.CONTENT.UPLOAD_ARTICLE_IMAGE)
    Observable<BaseResult> uploadArticleImage(@PartMap Map<String, RequestBody> map);

}
