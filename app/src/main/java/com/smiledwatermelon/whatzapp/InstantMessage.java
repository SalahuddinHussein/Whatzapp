package com.smiledwatermelon.whatzapp;
/**
 * Created by salahuddin on 3/4/18.
 */

public class InstantMessage {


    private String message;
    private String author;

    public InstantMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public InstantMessage() {

    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
