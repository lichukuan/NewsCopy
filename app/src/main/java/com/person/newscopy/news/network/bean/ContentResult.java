package com.person.newscopy.news.network.bean;

import java.util.List;

public class ContentResult {


    /**
     * code : 1
     * result : [{"id":"fa3101f79bc04c18b872355f9ca46ca1","userId":"46665d7383d84e78b827ef9424f73cd0","userName":"时代不变","releaseTime":2,"likeCount":0,"saveCount":0,"sendCount":0,"commentCount":0,"tag":"游戏","title":"这是测试标题","image":"这是测试图片","recommend":"测试","readCount":0,"playCount":0,"dislikeCount":0,"time":"02:11","videoUrl":"视频地址","secondTime":0,"type":1},{"id":"da0ed9199b7b49b0a602cb00a6d9b8bb","userId":"d81984324f594c8bbc2ffd53a9f0f7af","userName":"迪哥闯世界","userRecommend":"我的世界游戏视频制作，以讲故事方式记录成","userIcon":"https://p3.pstatp.com/large/9b1f000000722c5dd2ac","releaseTime":5,"likeCount":0,"saveCount":0,"sendCount":0,"commentCount":0,"tag":"游戏","title":"我的世界联机第七季37：我做出高效率的钴镐，2处钻石矿挖到35颗","image":"https://p3-xg.byteimg.com/img/tos-cn-i-0004/aaafd94fe0da462f96148b661b2dde9e~c5_q75_576x324.jpeg","recommend":"我的世界联机第七季37：我做出高效率的钴镐，2处钻石矿挖到35颗；我的世界联机第七季37：我用钴镐挖到35颗钻石，再次变成高富帅！","readCount":0,"playCount":1,"dislikeCount":0,"time":"05:03","videoUrl":"https://v6.pstatp.com/f895428b58f4976e1a6cc4cc7ccf106f/5dac7020/video/tos/cn/tos-cn-ve-4/c30c58bc701346f7bc022cfe689b5de8/?a=1768&br=817&cr=0&cs=0&dr=0&ds=3&er=&l=20191020212741010014051094036E496E&lr=&rc=anBzdnE6eGV5cDMzOzczM0ApZDNkODQzO2Q1N2RnOGllZ2czcWZmaWouNGlfLS02LS9zcy5hMmEtMi8uNC8tYl80Mi86Yw%3D%3D","secondTime":303,"type":1}]
     */

    private int code;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }
}
