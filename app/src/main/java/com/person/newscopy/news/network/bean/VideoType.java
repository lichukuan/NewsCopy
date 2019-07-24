package com.person.newscopy.news.network.bean;

import java.util.List;

public class VideoType {
    /**
     * code : 0
     * data : [{"channel_id":"61887739373","channel":"subv_xg_movie","name":"影视","pinyin":"yingshi","order":1}]
     */

    private int code;
    private List<VideoTypeBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<VideoTypeBean> getData() {
        return data;
    }

    public void setData(List<VideoTypeBean> data) {
        this.data = data;
    }
}
