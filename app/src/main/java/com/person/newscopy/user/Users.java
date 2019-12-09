package com.person.newscopy.user;

public final class Users {

    private Users(){

    }
    
    //标记用户是否登陆
    public static boolean LOGIN_FLAG = false;

    public static String userId = "";

    public static String userName = "";

    public static String userIcon = "";

    public static String userRecommend = "";

    public static int userCare = -1;

    public static int userWork = -1;

    public static int userFans = -1;

    public static String email = "";

    public static String key = "";//上传图片的key

    public static void reset(){
        LOGIN_FLAG = false;
        userId = "";
        userName = "";
        userIcon = "";
        userRecommend = "";
        userCare = -1;
        userWork = -1;
        userFans = -1;
        email = "";
    }

}
