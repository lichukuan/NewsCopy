package com.person.newscopy.type.bean;

import java.util.List;

public class ContentType {

    /**
     * code : 1
     * result : [{"tag":"news","value":0},{"tag":"video","value":1},{"tag":"short","value":2},{"tag":"comment","value":3},{"tag":"user_article","value":4},{"tag":"user_short","value":5},{"tag":"other","value":6}]
     */

    private int code;
    private List<ResultBeanX> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResultBeanX> getResult() {
        return result;
    }

    public void setResult(List<ResultBeanX> result) {
        this.result = result;
    }
}
