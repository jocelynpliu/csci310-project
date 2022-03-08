import java.time.LocalDateTime;
import java.util.*;

public class HostedEvent extends Event {
    private List<LocalDateTime> votableTimeslots;
    private Timer endVoteTimer;
    private List<String> attendees;

    public void addAttendee(String userID) {
        // implement
    }

    public void changeName(String newName) {
        // implement
    }

    public void changeStartTime(LocalDateTime newTime) {
        // implement
    }

    public void changeEndTime(LocalDateTime newTime) {
        // implement
    }

    public void changeLocation(String newLoc) {
        // implement
    }

    public void changeDescription(String newDesc) {
        // implement
    }

    public void changeTime(LocalDateTime newTime) {
        // implement
    }

    public void startTimer(LocalDateTime end) {
        // implement
    }

    public void makePublic() {
        // implement
    }

    public void makePrivate(List<String> invitees) {
        // implement
    }
}
