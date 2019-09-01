package com.person.newscopy.news.network.bean;

public class CommentBean {

    /**
     * my_message : success
     * data : {"has_more":false,"total":16,"comments":[{"text":"[赞]站立时代潮头，不畏浮云遮望眼！中国智慧引领世界！全球期待！为英明领袖习主席点赞！👍👍👍","digg_count":327,"reply_data":{"reply_list":[]},"reply_count":0,"create_time":1561900848,"user":{"avatar_url":"https://p1.pstatp.com/thumb/dab50015f0d3857f615b","user_id":1490529335128525,"name":"阔老总许愿共圆幸福梦"},"dongtai_id":"6708313048371445764","user_digg":0,"id":"6708313048371445764"},{"text":"大国领袖！大国风范！中国智慧！中国方案！引领世界！主席英明！[赞][赞][赞][玫瑰][玫瑰][玫瑰]","digg_count":279,"reply_data":{"reply_list":[]},"reply_count":0,"create_time":1561901857,"user":{"avatar_url":"https://p3.pstatp.com/thumb/da8c00086bde66e23bfd","user_id":4367709610,"name":"雨柔39368574"},"dongtai_id":"6708317387524931595","user_digg":0,"id":"6708317387524931595"}]}
     */

    private String message;
    private DataBeanXX data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBeanXX getData() {
        return data;
    }

    public void setData(DataBeanXX data) {
        this.data = data;
    }
}
