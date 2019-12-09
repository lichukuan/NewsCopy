package com.person.newscopy.user.net.bean;

import java.util.List;

public class ReadBean {

    /**
     * code : 1
     * result : [{"name":"经济日报","icon":"https://p1.pstatp.com/thumb/bc2000374269918957f","id":"b6eb5fa2825541f4a577421ae37a217a","title":"多游了200米后破纪录夺冠，全网都在心疼他！","time":1571831445,"image":"https://p1.pstatp.com/list/190x124/pgc-image/RfQFwJP7mMBgnx","isSend":0,"isLike":0,"isSave":0,"readId":"2000183930dd466381a273acc0589e59","commentCount":0,"likeCount":0,"saveCount":0,"sendCount":0,"contentType":0,"contentId":"136f69c100d4490e8d56c5f2896634b3"},{"name":"迪哥闯世界","icon":"https://p3.pstatp.com/large/9b1f000000722c5dd2ac","id":"d81984324f594c8bbc2ffd53a9f0f7af","title":"中国将节省天价电费！3颗人造月亮即将升空，代替路灯照明","time":1571827340,"image":"https://p9-xg.byteimg.com/img/tos-cn-i-0004/f4bbda4ade844be492d9805eb457dd30~c5_q75_576x324.jpeg","isSend":0,"isLike":0,"isSave":0,"readId":"c338e19644004cb88a7dee8e22891860","commentCount":0,"likeCount":0,"saveCount":0,"sendCount":0,"contentType":1,"contentId":"e1fafc06391b403c998be85e1ebea22e"}]
     */

    private int code;
    private List<ReadContent> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ReadContent> getResult() {
        return result;
    }

    public void setResult(List<ReadContent> result) {
        this.result = result;
    }
}
