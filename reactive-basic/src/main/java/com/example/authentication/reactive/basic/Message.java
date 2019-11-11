package com.example.authentication.reactive.basic;

/**
 * @author Gary Cheng
 */
public class Message {
    public Message(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
