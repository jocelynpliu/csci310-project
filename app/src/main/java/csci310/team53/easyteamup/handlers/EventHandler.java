package csci310.team53.easyteamup.handlers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import csci310.team53.easyteamup.data.Event;
import csci310.team53.easyteamup.data.HostedEvent;

public class EventHandler {

    private Map<String, List<Event>> events;
    private Map<String, List<HostedEvent>> hostedEvents;

    public void createEvent(String hostID, String location, String description, LocalDateTime start, LocalDateTime end, boolean isPrivate, List<String> invitees) {
        // IMPLEMENT
    }

    public void createEvent(String hostID, String location, String description, LocalDateTime voteEnd, boolean isPrivate, List<String> invitees) {
        // IMPLEMENT
    }

    public void deleteEvent(String eventID) {
        // IMPLEMENT
    }

    public void joinEvent(String username, String eventID) {
        // IMPLEMENT
    }

    public void leaveEvent(String eventID) {
        // IMPLEMENT
    }

    public List<HostedEvent> retrieveHostedEvents(String username) {
        // IMPLEMENT
        return hostedEvents.get(0); // just so it compiles
    }

    public List<Event> retrieveEvents(String username) {
        // IMPLEMENT
        return events.get(0); // just so it compiles
    }
}
