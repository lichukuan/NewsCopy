package com.person.newscopy.news.network.bean;

public class ReplyBean {

    /**
     * my_message : success
     * data : {"has_more":true,"data":[{"text":"那就够了，这就是实力啊。","digg_count":209,"create_time":1562493735,"user":{"user_id":66229622248,"screen_name":"王兵205827048","name":"王兵205827048","verified_reason":"","avatar_url":"http://p3.pstatp.com/thumb/173b9001bc7e6cece02de","user_verified":false,"description":""},"dongtai_id":"6710856670861393931","user_digg":0,"id":"6710859481653231628"},{"text":"[泪奔]","digg_count":10,"create_time":1562496110,"user":{"user_id":2414168672054979,"screen_name":"用户2810348401720","name":"用户2810348401720","verified_reason":"","avatar_url":"http://p0.pstatp.com/origin/3793/3131589739","user_verified":false,"description":""},"dongtai_id":"6710856670861393931","user_digg":0,"id":"6710869677104660484"}],"dongtai_id":"6710856670861393931","offset":20}
     */

    private String message;
    private DataBeanXXXX data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBeanXXXX getData() {
        return data;
    }

    public void setData(DataBeanXXXX data) {
        this.data = data;
    }
}
