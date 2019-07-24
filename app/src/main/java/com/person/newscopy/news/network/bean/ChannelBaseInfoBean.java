package com.person.newscopy.news.network.bean;

public class ChannelBaseInfoBean {
    /**
     * videoId : 6711243488777732621
     * videoTitle : 用整座山做锚的大桥有多猛？重量堪比11艘辽宁舰，小国根本搞不动
     * tag : video_military
     * videoBigImage : http://p3-xg.byteimg.com/img/tos-cn-i-0000/7669145ca16e11e9b1d67cd30a545d72~c5_q75_864x486.jpeg
     * videoImage : http://p3-xg.byteimg.com/img/tos-cn-i-0000/7669145ca16e11e9b1d67cd30a545d72~c5_q75_576x324.jpeg
     * playNum : 26.9万
     * commentNum : 195
     * timeText : 1天前
     * blackText : 02:48
     * authorName : 零度军武
     * uid : 106328178005
     * maxTime : 1559109730
     */

    private String videoId;
    private String videoTitle;
    private String tag;
    private String videoBigImage;
    private String videoImage;
    private String playNum;
    private String commentNum;
    private String timeText;
    private String blackText;
    private String authorName;
    private long uid;
    private int maxTime;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getVideoBigImage() {
        return videoBigImage;
    }

    public void setVideoBigImage(String videoBigImage) {
        this.videoBigImage = videoBigImage;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public String getPlayNum() {
        return playNum;
    }

    public void setPlayNum(String playNum) {
        this.playNum = playNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public String getBlackText() {
        return blackText;
    }

    public void setBlackText(String blackText) {
        this.blackText = blackText;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }
}
