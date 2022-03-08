import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
public class EventHandler {

    private Map<String, List<Event>> events;
    private Map<String, List<HostedEvent>> hostedEvents;

    public void createEvent(String hostID, String location, String description, LocalDateTime start, LocalDateTime end, boolean isPrivate, List<String> invitees) {
        // implement
    }

    public void createEvent(String hostID, String location, String description, LocalDateTime voteEnd, boolean isPrivate, List<String> invitees) {
        // implement
    }

    public void deleteEvent(String eventID) {
        // implement
    }

    public void joinEvent(String username, String eventID) {
        // implement
    }

    public void leaveEvent(String eventID) {
        // implement
    }

    public List<HostedEvent> retrieveHostedEvents(String username) {
        // implement
        return hostedEvents.get(0); // just so it compiles
    }

    public List<Event> retrieveEvents(String username) {
        // implement
        return events.get(0); // just so it compiles
    }
}
