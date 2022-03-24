package csci310.team53.easyteamup.data;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an Event in the MongoDB Atlas database.
 * Serializable POJO.
 *
 * @author Thomas Peters
 */
public class Event {

    private ObjectId id;
    private String name;
    private String location;
    private String description;
    private String host;
    private boolean isPrivate;
    private LocalDateTime start;
    private LocalDateTime end;
    private List<String> invitees;

    /**
     * Blank constructor to be utilized by MongoDB POJO.
     */
    public Event() { }

    /**
     * Constructor for the Event object.
     *
     * @param id the ObjectID to be used by MongoDB.
     * @param name the name of the event.
     * @param location the location of the event.
     * @param description the description of the event.
     * @param host the user ID of the host.
     * @param isPrivate whether or not this event is private,
     * @param start the start time of the event.
     * @param end the end time of the event.
     * @param invitees the list of invitees (list of user IDs)
     */
    public Event(ObjectId id, String name, String location, String description, String host, boolean isPrivate, LocalDateTime start, LocalDateTime end, List<String> invitees) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.host = host;
        this.isPrivate = isPrivate;
        this.start = start;
        this.end = end;
        this.invitees = invitees;
    }

    /** Getters and Setters */

    public ObjectId getId() { return id; }

    public void setId(ObjectId id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public LocalDateTime getStart() { return start; }

    public void setStart(LocalDateTime start) { this.start = start; }

    public LocalDateTime getEnd() { return end; }

    public void setEnd(LocalDateTime end) { this.end = end; }

    public List<String> getInvitees() { return invitees; }

    public void setInvitees(List<String> invitees) { this.invitees = invitees; }
}
