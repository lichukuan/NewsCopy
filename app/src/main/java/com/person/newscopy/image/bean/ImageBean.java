package com.person.newscopy.image.bean;

public class ImageBean {

    private String id;
    private String thumbnailsPath;
    private String path;
    private String groupId;
    private String groupName;
    private String type;
    private String size;

    public ImageBean(String id, String thumbnailsPath, String path, String groupId, String groupName, String type, String size) {
        this.id = id;
        this.thumbnailsPath = thumbnailsPath;
        this.path = path;
        this.groupId = groupId;
        this.groupName = groupName;
        this.type = type;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public String getThumbnailsPath() {
        return thumbnailsPath;
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

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }
}
