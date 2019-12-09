package com.person.newscopy.user.net.bean;

public class ResultBean {
    /**
     * name : 时代不变
     *
     * id : 46665d7383d84e78b827ef9424f73cd0
     * icon : www.fingertips.vip/image/u1569476841.jpg
     * email : 123456789@qq.com
     * likeCount : 0
     * releaseCount : 0
     * careCount : 0
     * fansCount : 0
     */

    private String name;
    private String pas;
    private String id;
    private String icon;
    private String email;
    private int likeCount;
    private int releaseCount;
    private int careCount;
    private int fansCount;
    private String recommend;

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getReleaseCount() {
        return releaseCount;
    }

    public void setReleaseCount(int releaseCount) {
        this.releaseCount = releaseCount;
    }

    public int getCareCount() {
        return careCount;
    }

    public void setCareCount(int careCount) {
        this.careCount = careCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }
}
