package com.person.newscopy.type.bean;

import java.util.List;

public class ShortVideoType {

    /**
     * code : 1
     * result : ["TECH","ENTERTAINMENT","FINANCE","KNOWLEDGE"]
     */

    private int code;
    private List<String> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
