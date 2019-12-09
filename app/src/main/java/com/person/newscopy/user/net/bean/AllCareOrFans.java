package com.person.newscopy.user.net.bean;

import java.util.List;

public class AllCareOrFans {

    /**
     * code : 1
     * result : [{"id":"d81984324f594c8bbc2ffd53a9f0f7af","name":"迪哥闯世界","icon":"https://p3.pstatp.com/large/9b1f000000722c5dd2ac","recommend":"我的世界游戏视频制作，以讲故事方式记录成","time":1572351245,"isCareEach":0}]
     */

    private int code;
    private List<CareOrFansBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CareOrFansBean> getResult() {
        return result;
    }

    public void setResult(List<CareOrFansBean> result) {
        this.result = result;
    }
}
