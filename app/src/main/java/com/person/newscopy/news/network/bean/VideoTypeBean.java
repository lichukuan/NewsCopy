package com.person.newscopy.news.network.bean;

public class VideoTypeBean {
    /**
     * channel_id : 61887739373
     * channel : subv_xg_movie
     * name : 影视
     * pinyin : yingshi
     * order : 1
     */

    private String channel_id;
    private String channel;
    private String name;
    private String pinyin;
    private int order;

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
