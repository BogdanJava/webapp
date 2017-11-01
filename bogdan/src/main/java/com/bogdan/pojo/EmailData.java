package com.bogdan.pojo;

import org.antlr.stringtemplate.StringTemplate;

import java.util.ArrayList;

public class EmailData {

    private String topic;
    private String message;
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