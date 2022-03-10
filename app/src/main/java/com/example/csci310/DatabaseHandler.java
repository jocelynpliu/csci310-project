package com.example.csci310;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import java.util.Arrays;

public class DatabaseHandler {

    // this method is just me messing around with MongoDB and trying to learn it
//    public static void main( String[] args ) {
//        // Replace the uri string with your MongoDB deployment's connection string
//        String uri = "mongodb+srv://jliu:CSCI310@easyteamup.vhkvv.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
////        try (MongoClient mongoClient = MongoClients.create(uri)) {
////            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
////            MongoCollection<Document> collection = database.getCollection("movies");
////            Document doc = collection.find(eq("title", "Back to the Future")).first();
////            System.out.println(doc.toJson());
////        }
//
//        try (MongoClient mongoClient = MongoClients.create(uri)) {
//            MongoDatabase database = mongoClient.getDatabase("easy_team_up");
//            MongoCollection<Document> collection = database.getCollection("users");
//            try {
//                InsertOneResult result = collection.insertOne(new Document()
//                        .append("_id", new ObjectId())
//                        .append("email", "jliu6011@usc.edu")
//                        .append("username", "jocelynliu")
//                        .append("password", "CSCI310"));
//                System.out.println("Success! Inserted document id: " + result.getInsertedId());
//            } catch (MongoException me) {
//                System.err.println("Unable to insert due to an error: " + me);
//            }
//        }
//    }
}
