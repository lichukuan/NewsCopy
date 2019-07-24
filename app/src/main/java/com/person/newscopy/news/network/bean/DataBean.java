package com.person.newscopy.news.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataBean {
    /**
     * single_mode : false
     * abstract : 新华社记者鞠鹏/摄党的十九大决定，以县处级以上领导干部为重点，在全党开展“不忘初心、牢记使命”主题教育。
     * middle_mode : true
     * more_mode : false
     * tag : news_politics
     * comments_count : 155
     * tag_url : search/?keyword=%E6%97%B6%E6%94%BF
     * title : 习近平在“不忘初心、牢记使命”主题教育工作会议上的讲话
     * chinese_tag : 时政
     * source : 新华网客户端
     * group_source : 2
     * image_url : //p9.pstatp.com/list/241ef000803744b188084
     * media_url : /c/user/4377795668/
     * media_avatar_url : //p9.pstatp.com/large/ff1b0000332acf4a6c2e
     * source_url : /group/6708326699194384908/
     * article_genre : article
     * is_stick : true
     * item_id : 6708326699194384908
     * is_feed_ad : false
     * behot_time : 1561907542
     * has_gallery : false
     * group_id : 6708326699194384908
     * middle_image : http://p9.pstatp.com/list/241ef000803744b188084
     * image_list : [{"url":"//p1.pstatp.com/list/pgc-image/RUr3BqN7diM3QX"},{"url":"//p1.pstatp.com/list/pgc-image/RUr3BqmGZpm41Z"},{"url":"//p9.pstatp.com/list/pgc-image/RUr3Br8IwXpDQn"}]
     */

    private boolean single_mode;
    @SerializedName("abstract")
    private String abstractX;
    private boolean middle_mode;
    private boolean more_mode;
    private String tag;
    private int comments_count;
    private String tag_url;
    private String title;
    private String chinese_tag;
    private String source;
    private int group_source;
    private String image_url;
    private String media_url;
    private String media_avatar_url;
    private String source_url;
    private String article_genre;
    private boolean is_stick;
    private String item_id;
    private boolean is_feed_ad;
    private int behot_time;
    private boolean has_gallery;
    private String group_id;
    private String middle_image;
    private List<ImageListBean> image_list;

    public boolean isSingle_mode() {
        return single_mode;
    }

    public void setSingle_mode(boolean single_mode) {
        this.single_mode = single_mode;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public boolean isMiddle_mode() {
        return middle_mode;
    }

    public void setMiddle_mode(boolean middle_mode) {
        this.middle_mode = middle_mode;
    }

    public boolean isMore_mode() {
        return more_mode;
    }

    public void setMore_mode(boolean more_mode) {
        this.more_mode = more_mode;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public String getTag_url() {
        return tag_url;
    }

    public void setTag_url(String tag_url) {
        this.tag_url = tag_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChinese_tag() {
        return chinese_tag;
    }

    public void setChinese_tag(String chinese_tag) {
        this.chinese_tag = chinese_tag;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getGroup_source() {
        return group_source;
    }

    public void setGroup_source(int group_source) {
        this.group_source = group_source;
    }

    public String getImage_url() {
        return "https:"+image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getMedia_avatar_url() {
        return media_avatar_url;
    }

    public void setMedia_avatar_url(String media_avatar_url) {
        this.media_avatar_url = media_avatar_url;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getArticle_genre() {
        return article_genre;
    }

    public void setArticle_genre(String article_genre) {
        this.article_genre = article_genre;
    }

    public boolean isIs_stick() {
        return is_stick;
    }

    public void setIs_stick(boolean is_stick) {
        this.is_stick = is_stick;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public boolean isIs_feed_ad() {
        return is_feed_ad;
    }

    public void setIs_feed_ad(boolean is_feed_ad) {
        this.is_feed_ad = is_feed_ad;
    }

    public int getBehot_time() {
        return behot_time;
    }

    public void setBehot_time(int behot_time) {
        this.behot_time = behot_time;
    }

    public boolean isHas_gallery() {
        return has_gallery;
    }

    public void setHas_gallery(boolean has_gallery) {
        this.has_gallery = has_gallery;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getMiddle_image() {
        return middle_image;
    }

    public void setMiddle_image(String middle_image) {
        this.middle_image = middle_image;
    }

    public List<ImageListBean> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<ImageListBean> image_list) {
        this.image_list = image_list;
    }
}
