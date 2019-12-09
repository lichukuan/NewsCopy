package com.person.newscopy.show.net;

public class CommentBean {
    /**
     * id : f564f3c876c8488092dc4b3fa261cb09
     * contentId : eee5a31db06341d285448a88bea9dea0
     * content : ...........
     * releaseTime : 1571853013
     * likeNum : 0
     * replyNum : 0
     * icon : https://sf6-ttcdn-tos.pstatp.com/img/mosaic-legacy/2dd3b0002ddb188f08d77~120x256.image
     * name : 央视新闻
     * time : 2019-10-24 01:50:13
     * toType : 3
     * userId : ffe4f595050f438bb2c2ea313b0bba3f
     */

    private String id;
    private String contentId;
    private String content;
    private int releaseTime;
    private int likeNum;
    private int replyNum;
    private String icon;
    private String name;
    private String time;
    private int toType;
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(int releaseTime) {
        this.releaseTime = releaseTime;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getToType() {
        return toType;
    }

    public void setToType(int toType) {
        this.toType = toType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
