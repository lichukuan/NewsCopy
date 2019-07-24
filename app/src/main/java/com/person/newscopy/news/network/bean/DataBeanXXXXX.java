package com.person.newscopy.news.network.bean;

public class DataBeanXXXXX {
    /**
     * livingFeed : {"channelId":"109","channelName":"正在直播","cards":[{"videoId":83940182312,"videoTitle":"和平精英：王牌2星路人局再上战神","videoImage":"http://sf6-xgcdn-tos.pstatp.com/img/post-meta/push-rtmp-l6-xg.bytecdn.cn/game/stream-6711553646360234759/xigua1/20190709133007794.jpg~c5_580x327.jpeg","userId":83940182312,"short_id":8787,"hotNum":"177.0万","blackText":"#和平精英","authorName":"Dae丶阿布","avatar_url":"http://p3.pstatp.com/large/ff7700005d4fdc15080c","audience_count":1770267,"isLive":true},{"videoId":50953987045,"videoTitle":"和平精英：战神局撩妹，我是最嗨的！","videoImage":"http://sf6-xgcdn-tos.pstatp.com/img/post-meta/push-rtmp-l6-xg.bytecdn.cn/game/stream-6711559869952183043/xigua1/20190709133009215.jpg~c5_580x327.jpeg","userId":50953987045,"short_id":666,"hotNum":"166.9万","blackText":"#和平精英","authorName":"陈大白游戏解说","avatar_url":"http://p1.pstatp.com/large/dc1100017fbf75d1be1f","audience_count":1668572,"isLive":true}],"hasMore":true}
     */

    private LivingFeedBean livingFeed;

    public LivingFeedBean getLivingFeed() {
        return livingFeed;
    }

    public void setLivingFeed(LivingFeedBean livingFeed) {
        this.livingFeed = livingFeed;
    }
}
