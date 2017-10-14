package com.bogdan.pojo;

import java.io.File;
import java.io.Serializable;

public class AttachedFile implements Serializable {
    public static String UPLOADPATH = System.getProperty("catalina.base") + File.separator + "webapps" + File.separator + "upload"
            + File.separator;
    public static String RELATIVEPATH =  File.separator + "upload" + File.separator;

    private int id;
    private String name;
    private String relativePath;
    private String realPath;
    private String description;
    private int contactId;
    private byte[] bytes;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
