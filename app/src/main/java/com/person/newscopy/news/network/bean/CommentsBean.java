package com.person.newscopy.news.network.bean;

class CommentsBean {
    /**
     * text : [赞]站立时代潮头，不畏浮云遮望眼！中国智慧引领世界！全球期待！为英明领袖习主席点赞！👍👍👍
     * digg_count : 327
     * reply_data : {"reply_list":[]}
     * reply_count : 0
     * create_time : 1561900848
     * user : {"avatar_url":"https://p1.pstatp.com/thumb/dab50015f0d3857f615b","user_id":1490529335128525,"name":"阔老总许愿共圆幸福梦"}
     * dongtai_id : 6708313048371445764
     * user_digg : 0
     * id : 6708313048371445764
     */

    private String text;
    private int digg_count;
    private ReplyDataBean reply_data;
    private int reply_count;
    private int create_time;
    private UserBean user;
    private String dongtai_id;
    private int user_digg;
    private String id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDigg_count() {
        return digg_count;
    }

    public void setDigg_count(int digg_count) {
        this.digg_count = digg_count;
    }

    public ReplyDataBean getReply_data() {
        return reply_data;
    }

    public void setReply_data(ReplyDataBean reply_data) {
        this.reply_data = reply_data;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getDongtai_id() {
        return dongtai_id;
    }

    public void setDongtai_id(String dongtai_id) {
        this.dongtai_id = dongtai_id;
    }

    public int getUser_digg() {
        return user_digg;
    }

    public void setUser_digg(int user_digg) {
        this.user_digg = user_digg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
