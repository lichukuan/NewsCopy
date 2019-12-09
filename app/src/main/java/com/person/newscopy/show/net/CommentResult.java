package com.person.newscopy.show.net;

import java.util.List;

public class CommentResult {

    /**
     * code : 1
     * result : [{"id":"f564f3c876c8488092dc4b3fa261cb09","contentId":"eee5a31db06341d285448a88bea9dea0","content":"...........","releaseTime":1571853013,"likeNum":0,"replyNum":0,"icon":"https://sf6-ttcdn-tos.pstatp.com/img/mosaic-legacy/2dd3b0002ddb188f08d77~120x256.image","name":"央视新闻","time":"2019-10-24 01:50:13","toType":3,"userId":"ffe4f595050f438bb2c2ea313b0bba3f"}]
     */

    private int code;
    private List<CommentBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CommentBean> getResult() {
        return result;
    }

    public void setResult(List<CommentBean> result) {
        this.result = result;
    }
}
