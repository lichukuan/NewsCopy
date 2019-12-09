package com.person.newscopy.user.net.bean;

public class AllPrivateTalkInfoBean {


    /**
     * code : 1
     * result : {"leftId":"d81984324f594c8bbc2ffd53a9f0f7af","leftName":"迪哥闯世界","leftIcon":"https://p3.pstatp.com/large/9b1f000000722c5dd2ac","contents":[{"time":1571837232,"content":"什么时候出下一期","type":"right"},{"time":1571837270,"content":"快来，这周末","type":"left"}]}
     */

    private int code;
    private LeftUserBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LeftUserBean getResult() {
        return result;
    }

    public void setResult(LeftUserBean result) {
        this.result = result;
    }
}
