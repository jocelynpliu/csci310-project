package csci310.team53.easyteamup.data;

import java.time.LocalDateTime;
import java.util.List;

import io.realm.annotations.PrimaryKey;

public class Event {

    @PrimaryKey
    private String id;
    private String name;
    private String location;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private String host;
    private boolean isPrivate;
    private List<String> invitees;

    public Event() {

    }
}
