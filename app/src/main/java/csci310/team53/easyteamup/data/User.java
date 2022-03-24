package csci310.team53.easyteamup.data;

import org.bson.types.ObjectId;

/**
 * Represents a User in the MongoDB Atlas database.
 * Serializable POJO.
 *
 * @author Thomas Peters
 */
public class User {

    private ObjectId id;
    private String username;
    private String password;
    //private BufferedImage avatar;

    /**
     * Blank constructor to be utilized by MongoDB POJO.
     */
    public User() { }

    /**
     * Constructor to initialize a User object.
     *
     * @param id the ObjectID of this object in the MongoDB database.
     * @param username the username associated with this user.
     * @param password the password associated with this user,
     */
    public User(ObjectId id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor to initialize a User object.
     * Generates a new ObjectID for you.
     *
     * @param username the username associated with this user.
     * @param password the password associated with this user,
     */
    public User(String username, String password) {
        this.id = new ObjectId();
        this.username = username;
        this.password = password;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

