package com.person.newscopy.news.network.bean;

import java.util.List;

public class NewsBean {
    /**
     * has_more : true
     * my_message : success
     * data : [{"single_mode":false,"abstract":"新华社记者鞠鹏/摄党的十九大决定，以县处级以上领导干部为重点，在全党开展\u201c不忘初心、牢记使命\u201d主题教育。","middle_mode":true,"more_mode":false,"tag":"news_politics","comments_count":155,"tag_url":"search/?keyword=%E6%97%B6%E6%94%BF","title":"习近平在\u201c不忘初心、牢记使命\u201d主题教育工作会议上的讲话","chinese_tag":"时政","source":"新华网客户端","group_source":2,"image_url":"//p9.pstatp.com/list/241ef000803744b188084","media_url":"/c/user/4377795668/","media_avatar_url":"//p9.pstatp.com/large/ff1b0000332acf4a6c2e","source_url":"/group/6708326699194384908/","article_genre":"article","is_stick":true,"item_id":"6708326699194384908","is_feed_ad":false,"behot_time":1561907542,"has_gallery":false,"group_id":"6708326699194384908","middle_image":"http://p9.pstatp.com/list/241ef000803744b188084"},{"single_mode":true,"abstract":"NBA球星哈登道歉了此前因骑电动车违规被交警\u201c抓了个正着\u201d\u201c道路千万条，安全第一条，行车不规范，球迷两行泪\u201d。","middle_mode":true,"more_mode":true,"tag":"news_sports","comments_count":189,"tag_url":"news_sports","title":"首个被中国交警拦截的NBA球星！哈登道歉后，上海交警回应","chinese_tag":"体育","source":"环球网","group_source":2,"has_gallery":false,"media_url":"/c/user/5954781019/","media_avatar_url":"//p1.pstatp.com/large/4d00054b126ceaf920","image_list":[{"url":"//p1.pstatp.com/list/pgc-image/RUr3BqN7diM3QX"},{"url":"//p1.pstatp.com/list/pgc-image/RUr3BqmGZpm41Z"},{"url":"//p9.pstatp.com/list/pgc-image/RUr3Br8IwXpDQn"}],"source_url":"/group/6708198858960142859/","article_genre":"article","item_id":"6708198858960142859","is_feed_ad":false,"behot_time":1561904842,"image_url":"//p1.pstatp.com/list/190x124/pgc-image/RUr3BqN7diM3QX","group_id":"6708198858960142859","middle_image":"http://p1.pstatp.com/list/pgc-image/RUr3BqN7diM3QX"}]
     * next : {"max_behot_time":1561903492}
     */
    private boolean has_more;
    private String message;
    private NextBean next;
    private List<DataBean> data;

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NextBean getNext() {
        return next;
    }

    public void setNext(NextBean next) {
        this.next = next;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }
}
