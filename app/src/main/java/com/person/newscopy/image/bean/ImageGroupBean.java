package com.person.newscopy.image.bean;

public class ImageGroupBean {

    private String groupId;
    private String groupName;
    private String path;

    public ImageGroupBean(String groupId, String groupName, String path) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.path = path;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getPath() {
        return path;
    }
}
