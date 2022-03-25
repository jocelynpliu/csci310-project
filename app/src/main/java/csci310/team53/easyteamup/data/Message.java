package csci310.team53.easyteamup.data;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * Represents a Message in the MongoDB Atlas database.
 * Serializable POJO.
 *
 * @author Thomas Peters
 */
public class Message {

    private ObjectId id;
    private String sender;
    private List<String> receivers;
    private String content;

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
    public Message(ObjectId id, String sender, List<String> receivers, String content) {
        this.id = id;
        this.sender = sender;
        this.receivers = receivers;
        this.content = content;
    }

    /**
     * Constructor for Message object. Automatically generates a ObjectID.
     *
     * @param sender User ID of the user that sent this message.
     * @param receivers List of user IDs that have received this message.
     * @param content The content body of the message.
     */
    public Message(String sender, List<String> receivers, String content) {
        this.id = new ObjectId();
        this.sender = sender;
        this.receivers = receivers;
        this.content = content;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
