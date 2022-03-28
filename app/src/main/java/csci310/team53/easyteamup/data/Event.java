package csci310.team53.easyteamup.data;

import android.util.Pair;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents an Event in the MongoDB Atlas database.
 * Serializable POJO.
 *
 * @author Thomas Peters
 */
public class Event {

    @BsonProperty("_id")
    @BsonId
    private ObjectId id;
    private String name;
    private String location;
    private String description;
    private ObjectId host;
    private boolean isPrivate;
    private String date;
    private String start;
    private String end;
    private List<ObjectId> invitees;
    private List<ObjectId> attendees;
    private Map<Pair<String, String>, Integer> timeSlots;

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
     * @param isPrivate whether or not this event is private.
     * @param date the day of the event.
     * @param start the start time of the event.
     * @param end the end time of the event.
     * @param invitees the list of invitees (list of user IDs).
     */
    public Event(ObjectId id, String name, String location, String description, String host, boolean isPrivate, String date, String start, String end, List<ObjectId> invitees, Map<Pair<String, String>, Integer> timeSlots) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.host = new ObjectId(host);
        this.isPrivate = isPrivate;
        this.date = date;
        this.start = start;
        this.end = end;
        this.invitees = invitees;
        this.attendees = new ArrayList<>();
        this.timeSlots = timeSlots;
    }

    /**
     * Checks if this event's variables are all filled out.
     * @return true if complete, otherwise false.
     */
    public boolean isValid() {
        if (name == null || name.isEmpty()) return false;
        if (location == null || location.isEmpty()) return false;
        if (description == null || description.isEmpty()) return false;
        if (host == null) return false;
        if (date == null || date.isEmpty()) return false;
        if (start == null || start.isEmpty()) return false;
        if (end == null || end.isEmpty()) return false;
        return true;
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

    public ObjectId getHost() {
        return host;
    }

    public void setHost(ObjectId host) {
        this.host = host;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<ObjectId> getInvitees() { return invitees; }

    public void setInvitees(List<ObjectId> invitees) { this.invitees = invitees; }

    public List<ObjectId> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<ObjectId> attendees) {
        this.attendees = attendees;
    }

    public Map<Pair<String, String>, Integer> getTimeSlots() { return timeSlots; }

    public void setTimeSlots(Map<Pair<String, String>, Integer> timeSlots) { this.timeSlots = timeSlots; }
}
