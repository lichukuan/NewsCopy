package com.person.newscopy.type;

import com.person.newscopy.api.Api;
import com.person.newscopy.type.bean.ContentType;
import com.person.newscopy.type.bean.MessageType;
import com.person.newscopy.type.bean.NewsType;
import com.person.newscopy.type.bean.ShortVideoType;
import com.person.newscopy.type.bean.VideoType;
import com.person.newscopy.user.net.bean.OtherUserInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface TypeInterface {

    @GET(Api.TYPE.NEWS_TYPE)
    Observable<NewsType> feedNewsType();

    @GET(Api.TYPE.VIDEO_TYPE)
    Observable<VideoType> feedVideoType();

    @GET(Api.TYPE.SHORT_VIDEO_TYPE)
    Observable<ShortVideoType> feedShortVideoType();

    @GET(Api.TYPE.MESSAGE_TYPE)
    Observable<MessageType> feedMessageType();

    @GET(Api.TYPE.CONTENT_TYPE)
    Observable<ContentType> feedContent();

    @POST(Api.USER.LOGIN)
    @FormUrlEncoded
    Observable<OtherUserInfo> login(@Field("name")String name, @Field("pas")String pas, @Field("salt")String salt);

}
