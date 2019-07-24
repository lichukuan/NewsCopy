package com.person.newscopy.news.network.bean;

public class VideoSearchBean {

    /**
     * code : 0
     * data : {"message":"success","skip_request_count":3,"default_search_word":"搜你想看的","data":[{"word":"大胃王","id":"6527573314687210759","type":"recom","icon_type":"default"},{"word":"侵华日军毒气弹报告","id":"6711144236340745475","type":"recom","icon_type":"default"},{"word":"不锈钢冰粒","id":"6581319818299839757","type":"recom","icon_type":"default"}]}
     */

    private int code;
    private DataBeanXXXXXXX data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanXXXXXXX getData() {
        return data;
    }

    public void setData(DataBeanXXXXXXX data) {
        this.data = data;
    }
}
