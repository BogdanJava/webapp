package com.bogdan.pojo;

import com.bogdan.exceptions.DataNotValidException;
import com.bogdan.pojo.validation.IValidated;
import org.antlr.stringtemplate.StringTemplate;
import org.apache.commons.mail.Email;
import org.apache.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Set;

public class EmailData implements IValidated {

    @NotNull(message = "topic cannot be null")
    private String topic;
    @NotNull(message = "message cannot be null")
    private String message;
    @Size(min = 1, message = "Size cannot be less than 1")
    private ArrayList<String> subscribers;
    private StringTemplate template;

    public ArrayList<String> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(ArrayList<String> subscribers) {
        this.subscribers = subscribers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StringTemplate getTemplate() {
        return template;
    }

    public void setTemplate(StringTemplate template) {
        this.template = template;
    }
}