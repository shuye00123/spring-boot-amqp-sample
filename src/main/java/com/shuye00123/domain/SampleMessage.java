package com.shuye00123.domain;

/**
 * Created by shuye on 2017/6/16.
 */
public class SampleMessage {
    private int id;
    private String message;

    public SampleMessage() {
    }

    public SampleMessage(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
