package com.bogdan.pojo;

import java.io.Serializable;

public class Phone implements Serializable{
    private Integer id;
    private String stateCode;
    private String operatorCode;
    private String number;
    private String type;
    private String comment;
    private Integer contact_id;

    public Phone(){}

    public Phone(Integer contact_id){
        this.contact_id = contact_id;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getContactId() {
        return contact_id;
    }

    public void setContactId(int contactId) {
        this.contact_id = contactId;
    }

    @Override
    public String toString() {
        return getStateCode() + " " + getOperatorCode() + " " + getNumber() + " "+ getType().toUpperCase();
    }
}
