package com.person.newscopy.news.network.bean;

class DataBeanXXX {
    /**
     * text : 那就够了，这就是实力啊。
     * digg_count : 209
     * create_time : 1562493735
     * user : {"user_id":66229622248,"screen_name":"王兵205827048","name":"王兵205827048","verified_reason":"","avatar_url":"http://p3.pstatp.com/thumb/173b9001bc7e6cece02de","user_verified":false,"description":""}
     * dongtai_id : 6710856670861393931
     * user_digg : 0
     * id : 6710859481653231628
     */

    private String text;
    private int digg_count;
    private int create_time;
    private UserBeanX user;
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

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public UserBeanX getUser() {
        return user;
    }

    public void setUser(UserBeanX user) {
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
