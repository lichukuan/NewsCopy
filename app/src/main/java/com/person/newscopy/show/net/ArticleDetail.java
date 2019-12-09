package com.person.newscopy.show.net;

public class ArticleDetail {

    /**
     * code : 1
     * result : {"detail":"<p>19日，军运会迎来开幕后的首个比赛日，共产生22枚金牌。中国代表团首日进账12枚金牌、4枚银牌和6枚铜牌，暂列奖牌榜榜首。<\/p><p><img class=\"\" data-ratio=\"0.6109375\" data-type=\"jpeg\" data-w=\"640\" title=\"1571531898289918.jpg?x-oss-process=style/w10\" _width=\"640px\"...","isLike":1,"isSave":1}
     */

    private int code;
    private DetailBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DetailBean getResult() {
        return result;
    }

    public void setResult(DetailBean result) {
        this.result = result;
    }
}
