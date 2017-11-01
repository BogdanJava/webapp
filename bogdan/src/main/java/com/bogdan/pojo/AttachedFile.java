package com.bogdan.pojo;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class AttachedFile implements Serializable {

    public static final String DEFAULTPHOTOURL = "https://pp.userapi.com/c837624/v837624857/5794b/LLJfnj6Xnos.jpg";
    public static String UPLOADPATH = System.getProperty("catalina.base") + File.separator + "webapps" + File.separator + "upload"
            + File.separator;
    public static String RELATIVEPATH =  File.separator + "upload" + File.separator;

    private Integer id;
    private String name;
    private String relative_path;
    private String real_path;
    private String description;
    private Integer contact_id;
    private Byte[] bytes;
    private String file_type;
    private Date add_date;

    public AttachedFile(){}

    public Date getDate() {
        return add_date;
    }

    public void setDate(Date add_date) {
        this.add_date = add_date;
    }

    public AttachedFile(Integer contact_id){
        this.contact_id = contact_id;
    }

    public String getType() {
        return file_type;
    }

    public void setType(String type) {
        this.file_type = type;
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
        return relative_path;
    }

    public void setRelativePath(String relativePath) {
        this.relative_path = relativePath;
    }

    public String getRealPath() {
        return real_path;
    }

    public void setRealPath(String realPath) {
        this.real_path = realPath;
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
        return name + file_type;
    }

    @Override
    public boolean equals(Object obj) {
        AttachedFile file = (AttachedFile) obj;
        return file != null && obj.getClass() == this.getClass() && Arrays.equals(this.bytes, file.bytes);
    }
}
