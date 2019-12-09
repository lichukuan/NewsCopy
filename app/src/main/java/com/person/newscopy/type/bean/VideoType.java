package com.person.newscopy.type.bean;

import java.util.List;

public class VideoType {

    /**
     * code : 1
     * result : [{"channelId":"61887739373","name":"影视"},{"channelId":"61887739376","name":"游戏"},{"channelId":"61887739345","name":"综艺"},{"channelId":"61887739390","name":"农人"},{"channelId":"6141508391","name":"美食"},{"channelId":"94349530916","name":"NBA"},{"channelId":"61887739368","name":"音乐"},{"channelId":"6141508406","name":"宠物"},{"channelId":"6141508395","name":"儿童"},{"channelId":"61887739369","name":"搞笑"},{"channelId":"6141508390","name":"时尚"},{"channelId":"7005980951","name":"懂车帝"},{"channelId":"5798809184","name":"体育"},{"channelId":"61887739374","name":"娱乐"},{"channelId":"6141508399","name":"文化"},{"channelId":"94349531488","name":"手工"},{"channelId":"6141508396","name":"科技"},{"channelId":"61887739388","name":"广场舞"},{"channelId":"61887739344","name":"亲子"},{"channelId":"94349533351","name":"VLOG"}]
     */

    private int code;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }
}
