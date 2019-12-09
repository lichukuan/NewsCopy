package com.person.newscopy.show.net;

public class DetailBean {
    /**
     * detail : <p>19日，军运会迎来开幕后的首个比赛日，共产生22枚金牌。中国代表团首日进账12枚金牌、4枚银牌和6枚铜牌，暂列奖牌榜榜首。</p><p><img class="" data-ratio="0.6109375" data-type="jpeg" data-w="640" title="1571531898289918.jpg?x-oss-process=style/w10" _width="640px"...
     * isLike : 1
     * isSave : 1
     */

    private String detail;
    private int isLike;
    private int isSave;
    private int isCare;

    public int getIsCare() {
        return isCare;
    }

    public void setIsCare(int isCare) {
        this.isCare = isCare;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getIsSave() {
        return isSave;
    }

    public void setIsSave(int isSave) {
        this.isSave = isSave;
    }
}
