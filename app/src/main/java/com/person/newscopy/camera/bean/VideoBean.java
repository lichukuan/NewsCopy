package com.person.newscopy.camera.bean;

public class VideoBean {
    private String id;
    private String path;
    private String groupId;
    private String groupName;
    private String duration;
    private String type;
    private String size;

    public VideoBean(String id, String path, String groupId, String groupName, String duration, String type, String size) {
        this.id = id;
        this.path = path;
        this.groupId = groupId;
        this.groupName = groupName;
        this.duration = duration;
        this.type = type;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }
}
