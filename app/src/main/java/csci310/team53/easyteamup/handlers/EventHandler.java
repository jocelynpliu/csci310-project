package csci310.team53.easyteamup.handlers;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.data.Event;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.iterable.MongoCursor;
import io.realm.mongodb.mongo.result.DeleteResult;
import io.realm.mongodb.mongo.result.InsertOneResult;

/**
 * Handles CRUD for Event objects in Database.
 *
 * @author Thomas Peters
 */
public class EventHandler {

    private final EasyTeamUp app;

    /**
     * Constructor with reference to application class.
     *
     * @param app reference to main application class.
     */
    public EventHandler(EasyTeamUp app) {
        this.app = app;
    }

    /**
     * Creates a new Event object in the database.
     *
     * @param name Name of the event.
     * @param location Location of the event.
     * @param description Description of the event.
     * @param start Start time of the event.
     * @param end End time of the event.
     * @param isPrivate Whether or not the event is private.
     * @param invitees List of user IDs of invited guests.
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<InsertOneResult> createEvent(String name, String location, String description, LocalDateTime start, LocalDateTime end, boolean isPrivate, List<String> invitees) {
        ObjectId id = new ObjectId();
        User host = app.getRealm().currentUser();
        Event event = new Event(id, name, location, description, host.getId(), isPrivate, start, end, invitees);
        return app.getDatabase().events.insertOne(event);
    }

    /**
     * Adds an already created Event object to the database.
     * Works functionally the same as all over createEvent() methods.
     *
     * @param event the event object to be added to the database.
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<InsertOneResult> createEvent(Event event) {
        return app.getDatabase().events.insertOne(event);
    }

    public void createEvent(String hostID, String location, String description, LocalDateTime voteEnd, boolean isPrivate, List<String> invitees) {
        // TODO: Implement
    }

    /**
     * Deletes an event from the database given the event ID.
     *
     * @param eventID the ID of the event to be deleted.
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<DeleteResult> deleteEvent(String eventID) {
        return app.getDatabase().events.deleteOne(new Document("_id", eventID));
    }

    /**
     * Add user to the list of invitees in an event.
     *
     * @param eventID the ID of the event to add this user to.
     */
    public void joinEvent(String eventID) {
        // TODO: Implement
    }

    /**
     * Remove user from the list of invitees in an event.
     *
     * @param eventID the ID of the event to add this user to.
     */
    public void leaveEvent(String eventID) {
        // TODO: Implement
    }

    /**
     * Retrieves all events that this user is hosting from database.
     *
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<MongoCursor<Event>> retrieveHostedEvents() {
        String id = app.getRealm().currentUser().getId();
        return app.getDatabase().events.find(new Document("host", id)).iterator();
    }

    /**
     * Retrieves all events that this user is associated with from database.
     *
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<MongoCursor<Event>> retrieveEvents() {
        // TODO: Implement
        Document queryFilter = new Document().append("private", false);
        return app.getDatabase().events.find(queryFilter).iterator();
    }

    /**
     * Retrieve an event given a specified event ID.
     *
     * @param eventID The ID of the event to be retrieved.
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<MongoCursor<Event>> retrieveEvent(String eventID) {
        return app.getDatabase().events.find(new Document("_id", eventID)).iterator();
    }
}
