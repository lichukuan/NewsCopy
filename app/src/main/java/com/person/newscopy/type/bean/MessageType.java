package com.person.newscopy.type.bean;

import java.util.List;

public class MessageType {

    /**
     * code : 1
     * result : [{"tag":"system","value":0},{"tag":"like","value":1},{"tag":"care","value":2},{"tag":"send","value":3},{"tag":"comment","value":4},{"tag":"save","value":5}]
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
