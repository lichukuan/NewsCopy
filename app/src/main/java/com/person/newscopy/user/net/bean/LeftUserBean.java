package com.person.newscopy.user.net.bean;

import java.util.List;

public class LeftUserBean {
    /**
     * leftId : d81984324f594c8bbc2ffd53a9f0f7af
     * leftName : 迪哥闯世界
     * leftIcon : https://p3.pstatp.com/large/9b1f000000722c5dd2ac
     * contents : [{"time":1571837232,"content":"什么时候出下一期","type":"right"},{"time":1571837270,"content":"快来，这周末","type":"left"}]
     */

    private String leftId;
    private String leftName;
    private String leftIcon;
    private List<ContentsBean> contents;

    public String getLeftId() {
        return leftId;
    }

    public void setLeftId(String leftId) {
        this.leftId = leftId;
    }

    public String getLeftName() {
        return leftName;
    }

    public void setLeftName(String leftName) {
        this.leftName = leftName;
    }

    public String getLeftIcon() {
        return leftIcon;
    }

    public void setLeftIcon(String leftIcon) {
        this.leftIcon = leftIcon;
    }

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }
}
