package com.person.newscopy.news.network.bean;

import java.util.List;

public class HotNewsBean {
    /**
     * message : success
     * data : [{"open_url":"/group/6710830814667473411/","group_id":"6710830814667473411","image_url":"//p1.pstatp.com/list/240x240/pgc-image/1da86651499f4db292ce4a9ea50de9c6","title":"上映3天票房破3.5亿，刘德华赚翻了，港片终于开始复苏崛起了"},{"open_url":"/group/6710889878202106372/","group_id":"6710889878202106372","image_url":"//p1.pstatp.com/list/240x240/pgc-image/RVXS9Cu3vlpXJg","title":"女友小腹凸起\u2026黄立行爆当爸！　徐静蕾「老子OO」亲回喜讯"}]
     */

    private String message;
    private List<DataBeanX> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }
}
