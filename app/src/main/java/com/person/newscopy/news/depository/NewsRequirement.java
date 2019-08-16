package com.person.newscopy.news.depository;

import com.person.newscopy.news.network.NewsType;

public class NewsRequirement {

    private boolean news;
    private NewsType type;
    private int time;
    private int widen;

    private boolean comment;
    private String groupId;
    private String itemId;
    private int offset;
    private int count;


    private boolean reply;
    private String commentId;
    private String dongtaiId;


    private boolean hotNews;

    public NewsType getType() {
        return type;
    }

    public void setType(NewsType type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getWiden() {
        return widen;
    }

    public void setWiden(int widen) {
        this.widen = widen;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getDongtaiId() {
        return dongtaiId;
    }

    public void setDongtaiId(String dongtaiId) {
        this.dongtaiId = dongtaiId;
    }

    public boolean isNews() {
        return news;
    }

    public void setNews(boolean news) {
        this.news = news;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public boolean isHotNews() {
        return hotNews;
    }

    public void setHotNews(boolean hotNews) {
        this.hotNews = hotNews;
    }
}
