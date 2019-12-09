package com.person.newscopy.user.net.bean;

import java.util.List;

public class SimpleTalkData {

    private int code;
    private List<SimpleTalkBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SimpleTalkBean> getResult() {
        return result;
    }

    public void setResult(List<SimpleTalkBean> result) {
        this.result = result;
    }
}
