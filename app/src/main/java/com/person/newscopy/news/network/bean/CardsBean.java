package com.person.newscopy.news.network.bean;

public class CardsBean {
    /**
     * videoId : 83940182312
     * videoTitle : 和平精英：王牌2星路人局再上战神
     * videoImage : http://sf6-xgcdn-tos.pstatp.com/img/post-meta/push-rtmp-l6-xg.bytecdn.cn/game/stream-6711553646360234759/xigua1/20190709133007794.jpg~c5_580x327.jpeg
     * userId : 83940182312
     * short_id : 8787
     * hotNum : 177.0万
     * blackText : #和平精英
     * authorName : Dae丶阿布
     * avatar_url : http://p3.pstatp.com/large/ff7700005d4fdc15080c
     * audience_count : 1770267
     * isLive : true
     */

    private long videoId;
    private String videoTitle;
    private String videoImage;
    private long userId;
    private int short_id;
    private String hotNum;
    private String blackText;
    private String authorName;
    private String avatar_url;
    private int audience_count;
    private boolean isLive;

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getShort_id() {
        return short_id;
    }

    public void setShort_id(int short_id) {
        this.short_id = short_id;
    }

    public String getHotNum() {
        return hotNum;
    }

    public void setHotNum(String hotNum) {
        this.hotNum = hotNum;
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

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getAudience_count() {
        return audience_count;
    }

    public void setAudience_count(int audience_count) {
        this.audience_count = audience_count;
    }

    public boolean isIsLive() {
        return isLive;
    }

    public void setIsLive(boolean isLive) {
        this.isLive = isLive;
    }
}
