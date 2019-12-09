package com.person.newscopy.news.network.bean;

import java.util.List;

public class ResultBean {
    /**
     * id : fa3101f79bc04c18b872355f9ca46ca1
     * userId : 46665d7383d84e78b827ef9424f73cd0
     * userName : 时代不变
     * releaseTime : 2
     * likeCount : 0
     * saveCount : 0
     * sendCount : 0
     * commentCount : 0
     * tag : 游戏
     * title : 这是测试标题
     * image : 这是测试图片
     * recommend : 测试
     * readCount : 0
     * playCount : 0
     * dislikeCount : 0
     * time : 02:11
     * videoUrl : 视频地址
     * secondTime : 0
     * type : 1
     * userRecommend : 我的世界游戏视频制作，以讲故事方式记录成
     * userIcon : https://p3.pstatp.com/large/9b1f000000722c5dd2ac
     */

    private String id;
    private String userId;
    private String userName;
    private int releaseTime;
    private int likeCount;
    private int saveCount;
    private int sendCount;
    private int commentCount;
    private String tag;
    private String title;
    private String image;
    private String recommend;
    private int readCount;
    private int playCount;
    private int dislikeCount;
    private String time;
    private String videoUrl;
    private int secondTime;
    private int type;
    private String userRecommend;
    private String userIcon;
    private List<String> images;
    private String imageList;

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(int releaseTime) {
        this.releaseTime = releaseTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getSaveCount() {
        return saveCount;
    }

    public void setSaveCount(int saveCount) {
        this.saveCount = saveCount;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getSecondTime() {
        return secondTime;
    }

    public void setSecondTime(int secondTime) {
        this.secondTime = secondTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserRecommend() {
        return userRecommend;
    }

    public void setUserRecommend(String userRecommend) {
        this.userRecommend = userRecommend;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }
}
