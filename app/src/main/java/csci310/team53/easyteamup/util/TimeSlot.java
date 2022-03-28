package csci310.team53.easyteamup.util;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class TimeSlot {

    @BsonProperty("_id")
    @BsonId
    private ObjectId id;
    private String start;
    private String end;
    private int votes;

    public TimeSlot() { }

    public TimeSlot(String start, String end) {
        this.start = start;
        this.end = end;
        this.votes = 0;
    }

    public TimeSlot(String start, String end, int votes) {
        this.start = start;
        this.end = end;
        this.votes = votes;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
