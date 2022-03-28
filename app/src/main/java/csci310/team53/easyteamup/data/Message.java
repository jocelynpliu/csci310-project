package csci310.team53.easyteamup.data;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Represents a Message in the MongoDB Atlas database.
 * Serializable POJO.
 *
 * @author Thomas Peters
 */
public class Message {

    @BsonProperty("_id")
    @BsonId
    private ObjectId id;
    private ObjectId sender;
    private List<ObjectId> receivers;
    private String content;
    private ObjectId event;

    /**
     * Empty constructor to be used by MongoDB POJO.
     */
    public Message() { }

    /**
     * Constructor for Message object.
     *
     * @param id the ObjectID to be used by MongoDB.
     * @param sender User ID of the user that sent this message.
     * @param receivers List of user IDs that have received this message.
     * @param content The content body of the message.
     */
    public Message(ObjectId id, String sender, List<ObjectId> receivers, String content) {
        this.id = id;
        this.sender = new ObjectId(sender);
        this.receivers = receivers;
        this.content = content;
    }

    /**
     * Constructor for Message object.
     *
     * @param id the ObjectID to be used by MongoDB.
     * @param sender User ID of the user that sent this message.
     * @param receivers List of user IDs that have received this message.
     * @param content The content body of the message.
     * @param event The event ID for this invite notification.
     */
    public Message(ObjectId id, String sender, List<ObjectId> receivers, String content, String event) {
        this.id = id;
        this.sender = new ObjectId(sender);
        this.receivers = receivers;
        this.content = content;
        this.event = new ObjectId(event);
    }

    /**
     * Constructor for Message object. Automatically generates a ObjectID.
     *
     * @param sender User ID of the user that sent this message.
     * @param receivers List of user IDs that have received this message.
     * @param content The content body of the message.
     */
    public Message(String sender, List<ObjectId> receivers, String content) {
        this.id = new ObjectId();
        this.sender = new ObjectId(sender);
        this.receivers = receivers;
        this.content = content;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getSender() {
        return sender;
    }

    public void setSender(ObjectId sender) {
        this.sender = sender;
    }

    public List<ObjectId> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<ObjectId> receivers) {
        this.receivers = receivers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ObjectId getEvent() {
        return event;
    }

    public void setEvent(ObjectId event) {
        this.event = event;
    }
}
