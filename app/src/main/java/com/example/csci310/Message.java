package com.example.csci310;

import java.util.List;

public class Message {
    private String sender;
    private List<String> receivers;
    private String content;
    private String id;
    private boolean isViewed;

    public void markViewed() {
        isViewed = true;
    }
}
