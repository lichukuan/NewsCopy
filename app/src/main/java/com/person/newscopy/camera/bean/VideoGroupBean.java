package com.person.newscopy.camera.bean;

public class VideoGroupBean {

    private String groupId;
    private String groupName;
    private String path;

    public VideoGroupBean(String groupId, String groupName, String path) {
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
