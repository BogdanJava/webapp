package com.bogdan.pojo;

import java.io.File;
import java.io.Serializable;

public class AttachedFile implements Serializable {

    public static final String DEFAULTPHOTOURL = "https://pp.userapi.com/c837624/v837624857/5794b/LLJfnj6Xnos.jpg";
    public static String UPLOADPATH = System.getProperty("catalina.base") + File.separator + "webapps" + File.separator + "upload"
            + File.separator;
    public static String RELATIVEPATH =  File.separator + "upload" + File.separator;

    private Integer id;
    private String name;
    private String relativePath;
    private String realPath;
    private String description;
    private Integer contact_id;
    private Byte[] bytes;
    private String type;

    public AttachedFile(){}

    public AttachedFile(Integer contact_id){
        this.contact_id = contact_id;
    }

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

    public Byte[] getBytes() {
        return bytes;
    }

    public void setBytes(Byte[] bytes) {
        this.bytes = bytes;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return contact_id;
    }

    public void setContactId(int contactId) {
        this.contact_id = contactId;
    }

    @Override
    public String toString() {
        return name + type;
    }
}
