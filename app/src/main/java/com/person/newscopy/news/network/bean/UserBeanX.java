package com.person.newscopy.news.network.bean;

class UserBeanX {
    /**
     * user_id : 66229622248
     * screen_name : 王兵205827048
     * name : 王兵205827048
     * verified_reason :
     * avatar_url : http://p3.pstatp.com/thumb/173b9001bc7e6cece02de
     * user_verified : false
     * description :
     */

    private long user_id;
    private String screen_name;
    private String name;
    private String verified_reason;
    private String avatar_url;
    private boolean user_verified;
    private String description;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVerified_reason() {
        return verified_reason;
    }

    public void setVerified_reason(String verified_reason) {
        this.verified_reason = verified_reason;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public boolean isUser_verified() {
        return user_verified;
    }

    public void setUser_verified(boolean user_verified) {
        this.user_verified = user_verified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
