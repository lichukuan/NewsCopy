package com.person.newscopy.news.network.bean;

public class DataBeanXXXXXXXXXXXX {
    /**
     * channelInfo : {"channel":"recommend","channel_id":"6797027941","pinyin":"tuijian","name":"推荐"}
     * channelFeed : {"Header":{"Status":1,"Message":"Success","Extra":""},"Data":[{"videoId":"6711243488777732621","videoTitle":"用整座山做锚的大桥有多猛？重量堪比11艘辽宁舰，小国根本搞不动","tag":"video_military","videoBigImage":"http://p3-xg.byteimg.com/img/tos-cn-i-0000/7669145ca16e11e9b1d67cd30a545d72~c5_q75_864x486.jpeg","videoImage":"http://p3-xg.byteimg.com/img/tos-cn-i-0000/7669145ca16e11e9b1d67cd30a545d72~c5_q75_576x324.jpeg","playNum":"26.9万","commentNum":"195","timeText":"1天前","blackText":"02:48","authorName":"零度军武","uid":106328178005,"maxTime":1559109730},{"videoId":"6697948474366755336","videoTitle":"小矮人二人转《钱是妈》，台下观众掌声不断","tag":"video_funny","videoBigImage":"http://p3-xg.byteimg.com/img/tos-cn-i-0000/386dff8e854711e990970cc47af43c90~c5_q75_864x486.jpeg","videoImage":"http://p3-xg.byteimg.com/img/tos-cn-i-0000/386dff8e854711e990970cc47af43c90~c5_q75_576x324.jpeg","playNum":"46.9万","commentNum":"9","timeText":"1个月前","blackText":"13:50","authorName":"香香舞蹈","uid":95129286000,"maxTime":1559109130}],"HasMore":true,"HasMoreToRefresh":true,"BaseResp":{"StatusMessage":"success","StatusCode":0}}
     */

    private ChannelInfoBean channelInfo;
    private ChannelFeedBean channelFeed;

    public ChannelInfoBean getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(ChannelInfoBean channelInfo) {
        this.channelInfo = channelInfo;
    }

    public ChannelFeedBean getChannelFeed() {
        return channelFeed;
    }

    public void setChannelFeed(ChannelFeedBean channelFeed) {
        this.channelFeed = channelFeed;
    }
}
