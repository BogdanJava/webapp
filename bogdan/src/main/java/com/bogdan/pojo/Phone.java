package com.bogdan.pojo;

import com.bogdan.pojo.validation.IValidated;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Phone implements Serializable, IValidated {
    private Integer id;
    @Pattern(regexp = "^[0-9]+$", message = "Only digits expected")
    private String state_code;
    @Pattern(regexp = "^[0-9]+$", message = "Only digits expected")
    private String operator_code;
    @Pattern(regexp = "^[0-9]+$", message = "Only digits expected")
    @Size(min = 7, max = 7)
    private String number;
    private String phone_type;
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
        return state_code;
    }

    public void setStateCode(String stateCode) {
        this.state_code = stateCode;
    }

    public String getOperatorCode() {
        return operator_code;
    }

    public void setOperatorCode(String operatorCode) {
        this.operator_code = operatorCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return phone_type;
    }

    public void setType(String type) {
        this.phone_type = type;
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
