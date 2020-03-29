package com.person.newscopy.api;

public interface Api {

    String BASE_URL = "http://www.fingertips.vip/";

    //String BASE_URL = "http://192.168.43.200:8081/Gradle___gitDesktop___gitDesktop_1_0_SNAPSHOT_war/";

    String DOWNLOAD_NEWEST_APP = "http://www.fingertips.vip/download/app";

    interface SEARCH{

        String SEARCH_REQUIREED_CONTENT = "phone/query/search";

        String QUERY_HOT_CONTENT = "phone/query/hot";
    }

    interface USER{

        String ADD_VIDEO = "phone/add/video";

        String ADD_MESSAGE = "phone/add/message";

        String QUERY_SAVE = "phone/query/save";

        String DELETE_SAVE = "phone/delete/save";

        String HISTORY = "phone/query/read";

        String REGISTER = "phone/register";

        String LOGIN = "phone/login";

        String UPLOAD_USER_IMAGE = "phone/update/icon";

        String UPDATE_USER_ICON = "phone/add/icon";

        String CHANGE_USER_NAME = "phone/change/name";

        String CHANGE_USER_PAS = "phone/change/password";

        String UPDATE_USER_RECOMMEND = "phone/add/recommend";

        String CARE_OR_CANCEL_CARE = "phone/add/care";

        String LIKE = "phone/like";

        String SAVE = "phone/save";

        String SEND = "phone/send";

        String QUERY_CARE = "phone/query/care";

        String QUERY_FANS = "phone/query/fans";

        String DELETE_USER_READ = "phone/delete/read";

        String ADD_PRIVATE_TALK = "phone/add/talk";

        String QUERY_PRIVATE_TALK = "phone/query/talk";

        String QUERY_OTHER_USER_INFO = "phone/query/user";

        String QUERY_CONTENT_INFO = "phone/query/content";

        String RELEASE_ARTICLE = "phone/add/article";

        String QUERY_MESSAGE = "phone/query/message";

        String NEW_VERSION = "phone/version";

        String CHANGE_EMAIL = "phone/change/email";

        String GET_USER_PAS = "phone/get/pas";

        String ALL_SIMPLE_TALK = "phone/query/simpleTalk";

    }

    interface CONTENT{

        String NEWS = "phone/query/news";

        String VIDEO = "phone/query/video";

        String SHORT_VIDEO = "phone/query/short";

        String ADD_ARTICLE = "phone/add/article";

        String ADD_SHORT_VIDEO = "phone/add/short";

        String UPLOAD_ARTICLE_IMAGE = "phone/upload/image";

        String UPLOAD_SHORT_VIDEO = "phone/upload/short";

        String FEED_NEWS_RECOMMEND = "phone/recommend?contentType=0";

        String FEED_VIDEO_RECOMMEND = "phone/recommend?contentType=1";

        String FEED_CARE_USER_ARTICLE_AND_VIDEO = "phone/query/careArticleAndVideo";
    }

    interface SHOW{

        String CARE_OR_CANCEL_CARE = "phone/add/care";

        String ADD_HISTORY = "phone/add/read";

        String ADD_MESSAGE = "phone/add/message";

        String RECOMMEND = "phone/recommend";

        String ADD_COMMENT = "phone/add/comment";

        String FEED_COMMENT = "phone/query/comment";

        String FEED_DETAIL = "phone/query/detail";

        String QUERY_CONTENT_ATTITUDE = "phone/query/attitude";

        String QUERY_IS_CARE = "phone/query/isCare";

        String QUERY_ATTITUDE = "phone/query/allAttitude";
    }

    interface TYPE{

        String NEWS_TYPE = "phone/type/news";

        String SHORT_VIDEO_TYPE  = "phone/type/short";

        String VIDEO_TYPE = "phone/type/video";

        String MESSAGE_TYPE = "phone/type/message";

        String CONTENT_TYPE = "phone/type/content";

    }
}
