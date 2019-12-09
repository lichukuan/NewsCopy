package com.person.newscopy.user.net.bean;

import java.util.List;

public class MessageBean {

    /**
     * code : 1
     * result : [{"type":1,"name":"经济日报","icon":"https://p1.pstatp.com/thumb/bc2000374269918957f","timeSecond":1571880523,"time":"2019-10-24 09:28:43","content":"出错了","id":"b6eb5fa2825541f4a577421ae37a217a","fromId":"46665d7383d84e78b827ef9424f73cd0","contentId":"136f69c100d4490e8d56c5f2896634b3","contentImage":"www.jjkxjjkdk.xjxjx.jpg"},{"type":1,"name":"经济日报","icon":"https://p1.pstatp.com/thumb/bc2000374269918957f","timeSecond":1571880557,"time":"2019-10-24 09:28:43","content":"null","id":"b6eb5fa2825541f4a577421ae37a217a","fromId":"46665d7383d84e78b827ef9424f73cd0","contentId":"136f69c100d4490e8d56c5f2896634b3","contentImage":"www.jjkxjjkdk.xjxjx.jpg"}]
     */

    private int code;
    private List<MessageContentBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<MessageContentBean> getResult() {
        return result;
    }

    public void setResult(List<MessageContentBean> result) {
        this.result = result;
    }
}
