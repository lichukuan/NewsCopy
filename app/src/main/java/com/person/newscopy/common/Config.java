package com.person.newscopy.common;

public final class Config {

    public static final int SUCCESS = 1;

    public static final int FAIL = 2;

    public static final String DEFACULT_IMAGE_BASE_URL = "http://q0g9t9jc8.bkt.clouddn.com/";

    public static final String USER_INFO_STORE_KEY = "user_info_store_key";

    public static final String[] TYPE = {
            "游戏","学习","编程","健身","大学","影视","工作","饮食","股票","软件","生活",
            "旅行","运动","娱乐","电子产品","家居","亲子","英语","面试","备考"
    };

    public static final String[] MESSAGE_TYPE = {"system","like","care","send","comment","save"};

    public static class MESSAGE{

        public static final int SYSTEM_MESSAGE_TYPE = 0;

        public static final int LIKE_TYPE = 1;

        public static final int CARE_TYPE = 2;

        public static final int SEND_TYPE = 3;

        public static final int COMMENT_TYPE = 4;

        public static final int SAVE_TYPE = 5;

        public static final int PRIVATE_TALK_TYPE = 6;
    }

    public static class CONTENT{

        public static final int NEWS_TYPE = 0;

        public static final int VIDEO_TYPE = 1;

        public static final int COMMENT_TYPE = 3;

        public static final int SYSTEM_TYPE = 4;

        public static final int SEND_TYPE = 5;
    }

}
