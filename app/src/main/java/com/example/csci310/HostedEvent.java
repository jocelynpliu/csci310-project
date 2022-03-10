package com.example.csci310;

import java.time.LocalDateTime;
import java.util.*;

public class HostedEvent extends Event {
    private List<LocalDateTime> votableTimeslots;
    private Timer endVoteTimer;
    private List<String> attendees;

    public void addAttendee(String userID) {
        // IMPLEMENT
    }

    public void changeName(String newName) {
        // IMPLEMENT
    }

    public void changeStartTime(LocalDateTime newTime) {
        // IMPLEMENT
    }

    public void changeEndTime(LocalDateTime newTime) {
        // IMPLEMENT
    }

    public void changeLocation(String newLoc) {
        // IMPLEMENT
    }

    public void changeDescription(String newDesc) {
        // IMPLEMENT
    }

    public void changeTime(LocalDateTime newTime) {
        // IMPLEMENT
    }

    public void startTimer(LocalDateTime end) {
        // IMPLEMENT
    }

    public void makePublic() {
        // IMPLEMENT
    }

    public void makePrivate(List<String> invitees) {
        // IMPLEMENT
    }
}
