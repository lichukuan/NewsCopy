package com.person.newscopy.user.net.bean;

public class VersionBean {

    /**
     * code : 1
     * result : {"version":"1.1","info":"这是测试版"}
     */

    private int code;
    private ResultBeanXX result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResultBeanXX getResult() {
        return result;
    }

    public void setResult(ResultBeanXX result) {
        this.result = result;
    }
}
