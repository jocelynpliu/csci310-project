package com.example.csci310;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private String name;
    private String location;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private String host;
    private String id;
    private boolean isPrivate;
    private List<String> invitees;

    public void inviteUser(String username) {
        invitees.add(username);
    }

    public void uninviteUser(String username) {
        // IMPLEMENT
    }
}
