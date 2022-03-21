package csci310.team53.easyteamup.handlers;

import java.util.List;
import java.util.Map;

import csci310.team53.easyteamup.data.Message;

public class MessageHandler {
    private Map<String, Message> messages;
    private void sendMessage(String receiver, String content) {
        // IMPLEMENT
    }

    public void sendInvite(List<String> invitees, String content) {
        // IMPLEMENT
    }

    public void sendNotification(List<String> receivers, String content) {
        // IMPLEMENT
    }

    public void sendNotification(String receiver, String content) {
        // IMPLEMENT
    }

    // this needs a return type?
    public void retrieveMessage(String userID) {
        // IMPLEMENT
    }

    public void clearInbox() {
        // IMPLEMENT
    }
}
