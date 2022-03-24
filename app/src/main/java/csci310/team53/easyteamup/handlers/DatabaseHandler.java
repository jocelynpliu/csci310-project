package csci310.team53.easyteamup.handlers;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import android.util.Log;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import csci310.team53.easyteamup.data.Event;
import csci310.team53.easyteamup.data.Message;
import io.realm.mongodb.AppConfiguration;
import csci310.team53.easyteamup.data.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class DatabaseHandler {

    public MongoCollection<Event> events;
    public MongoCollection<User> users;
    public MongoCollection<Message> messages;

    public DatabaseHandler(io.realm.mongodb.User user) {
        MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
        MongoDatabase database = mongoClient.getDatabase("EasyTeamUp");

        CodecRegistry pojoCodecRegistry = fromRegistries(AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        events = database.getCollection("events", Event.class).withCodecRegistry(pojoCodecRegistry);
        users = database.getCollection("users", User.class).withCodecRegistry(pojoCodecRegistry);
        messages = database.getCollection("messages", Message.class).withCodecRegistry(pojoCodecRegistry);
        Log.v("Database", "Successfully setup MongoDB Atlas database!");
    }
}
