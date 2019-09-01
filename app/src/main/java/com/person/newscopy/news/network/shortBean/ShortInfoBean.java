package com.person.newscopy.news.network.shortBean;

import java.util.List;

public class ShortInfoBean {
    /**
     * title : 反差萌？李靖配音和海绵宝宝同一人
     * duration : 01:08
     * iconUrl : https://image.pearvideo.com/node/18-10027897-logo.jpg
     * videoUrl : https://video.pearvideo.com/mp4/adshort/20190731/cont-1584735-14203569_adpkg-ad_hd.mp4
     * my_like : 328
     * image : https://image2.pearvideo.com/cont/20190731/cont-1584735-12065633.jpg
     * comments : [{"name":"橘子汽水菠萝西瓜芒果","content":"厉害呀","icon":"https://image.pearvideo.com/const/user_default_120_120.png","my_like":"0","reply":"0","time":"08-01 01:28"},{"name":"勤劳的小蜜蜂","content":"多样性的声音","icon":"https://imageugc.pearvideo.com/user/20190719/10541557-192327.jpg","my_like":"0","reply":"0","time":"07-31 21:42"},{"name":"天马行空","content":"很厉害呦","icon":"https://imageugc.pearvideo.com/user/20190718/10157630-150742.jpg","my_like":"0","reply":"0","time":"07-31 20:17"},{"name":"一个半仙说","content":"@海绵宝宝海绵陈浩 指路","icon":"https://imageugc.pearvideo.com/user/20190718/10969423-150717-869839038519469.jpg","my_like":"0","reply":"0","time":"07-31 18:42"},{"name":"偶是凡人啊","content":"想看真人","icon":"https://imageugc.pearvideo.com/user/20171208/11510940-091512.jpg","my_like":"0","reply":"0","time":"07-31 18:42"}]
     */

    private String title;
    private String duration;
    private String iconUrl;
    private String videoUrl;
    private String like;
    private String image;
    private String author;
    private List<CommentsBean> comments;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }
}
