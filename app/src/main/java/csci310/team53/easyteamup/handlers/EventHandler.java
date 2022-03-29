package csci310.team53.easyteamup.handlers;

import android.util.Log;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.data.Event;
import csci310.team53.easyteamup.util.TimeSlot;
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

    /**\
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
    public RealmResultTask<InsertOneResult> createEvent(String name, String location, String description, String date, String start, String end, boolean isPrivate, List<ObjectId> invitees, List<TimeSlot> timeSlots) {
        ObjectId id = new ObjectId();
        User host = app.getRealm().currentUser();
        Event event = new Event(id, name, location, description, host.getId(), isPrivate, date, start, end, invitees, timeSlots);
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
     * Updates the details of an event object in the database.
     * Pass in null for anything you don't want to update
     *
     * @param eventID ID of the event object you are updating.
     * @param name Event name to be updated
     * @param desc Event description to be updated
     * @param location Event location to be updated
     * @param date Event date to be updated
     * @param start Event start time to be updated
     * @param end Event end time to be updated
     * @param isPrivate Event private status to be updated
     * @param invitees Event invite list to be updated
     */
    public void updateEvent(String eventID, String name, String desc, String location, String date, String start, String end, Boolean isPrivate, List<ObjectId> invitees) {
        Document findQuery = new Document("_id", new ObjectId(eventID));
        app.getDatabase().events.findOne(findQuery).getAsync(task -> {
            if (task.isSuccess()) {
                Document update = new Document("_id", task.get().getId());
                if (name != null) { update.append("name", name); }
                if (desc != null) { update.append("description", desc); }
                if (location != null) { update.append("location", location); }
                if (date != null) { update.append("date", date); }
                if (start != null) { update.append("start", start); }
                if (end != null) { update.append("end", end); }
                if (isPrivate != null) { update.append("private", isPrivate); }
                if (invitees != null) { update.append("invitees", invitees); }

                app.getDatabase().events.updateOne(findQuery, new Document("$set", update)).getAsync(task2 -> {
                    if (task2.isSuccess()) {
                        Log.v("Event", "Successfully updated event!");
                    } else {
                        Log.v("Event", "ERROR: " + task2.getError().getErrorMessage());
                    }
                });
            } else {
                Log.v("Event", "ERROR: " + task.getError().getErrorMessage());
            }
        });
    }

    /**
     * Deletes an event from the database given the event ID.
     *
     * @param eventID the ID of the event to be deleted.
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<DeleteResult> deleteEvent(String eventID) {
        return app.getDatabase().events.deleteOne(new Document("_id", new ObjectId(eventID)));
    }

    /**
     * Add user to the list of attendees in an event.
     *
     * @param eventID the ID of the event to add this user to.
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<Event> attendEvent(String eventID) {
        User user = app.getRealm().currentUser();
        Document findQuery = new Document("_id", new ObjectId(eventID));
        Document updateQuery = new Document("$push", new Document("attendees", new ObjectId(user.getId())));
        return app.getDatabase().events.findOneAndUpdate(findQuery, updateQuery);
    }

    /**
     * Remove user from the list of attendees in an event.
     *
     * @param eventID the ID of the event to add this user to.
     */
    public RealmResultTask<Event> denyEvent(String eventID) {
        User user = app.getRealm().currentUser();
        Document findQuery = new Document("_id", new ObjectId(eventID));
        Document updateQuery = new Document("$pull", new Document("attendees", new ObjectId(user.getId())));
        return app.getDatabase().events.findOneAndUpdate(findQuery, updateQuery);
    }

    /**
     * Retrieves all events that this user is hosting from database.
     *
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<MongoCursor<Event>> retrieveHostedEvents() {
        String id = app.getRealm().currentUser().getId();
        return app.getDatabase().events.find(new Document("host", new ObjectId(id))).sort(new Document("_id", -1)).iterator();
    }

    /**
     * Retrieves all events that this user is associated with from database.
     *
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<MongoCursor<Event>> retrievePublicEvents() {
        Document queryFilter = new Document().append("private", false);
        return app.getDatabase().events.find(queryFilter).sort(new Document("_id", -1)).iterator();
    }

    /**
     * Retrieves all events that this user is attending with from database.
     *
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<MongoCursor<Event>> retrieveAttendingEvents() {
        User user = app.getRealm().currentUser();
        Document queryFilter = new Document("attendees", new ObjectId(user.getId()));
        return app.getDatabase().events.find(queryFilter).iterator();
    }

    /**
     * Retrieve an event given a specified event ID.
     *
     * @param eventID The ID of the event to be retrieved.
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<MongoCursor<Event>> retrieveEvent(String eventID) {
        return app.getDatabase().events.find(new Document("_id", new ObjectId(eventID))).iterator();
    }


}
