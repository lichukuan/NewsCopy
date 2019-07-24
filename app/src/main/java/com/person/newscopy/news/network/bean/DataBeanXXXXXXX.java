package com.person.newscopy.news.network.bean;

import java.util.List;

class DataBeanXXXXXXX {
    /**
     * message : success
     * skip_request_count : 3
     * default_search_word : 搜你想看的
     * data : [{"word":"大胃王","id":"6527573314687210759","type":"recom","icon_type":"default"},{"word":"侵华日军毒气弹报告","id":"6711144236340745475","type":"recom","icon_type":"default"},{"word":"不锈钢冰粒","id":"6581319818299839757","type":"recom","icon_type":"default"}]
     */

    private String message;
    private int skip_request_count;
    private String default_search_word;
    private List<DataBeanXXXXXX> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSkip_request_count() {
        return skip_request_count;
    }

    public void setSkip_request_count(int skip_request_count) {
        this.skip_request_count = skip_request_count;
    }

    public String getDefault_search_word() {
        return default_search_word;
    }

    public void setDefault_search_word(String default_search_word) {
        this.default_search_word = default_search_word;
    }

    public List<DataBeanXXXXXX> getData() {
        return data;
    }

    public void setData(List<DataBeanXXXXXX> data) {
        this.data = data;
    }
}
