package com.todoapp;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

/**
 * Created by esdras on 27/06/16.
 */
public class Bootstrap {

    private static final String IP_ADDRESS =
            System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "localhost";
    private static final int PORT =
            System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 8080;

    public static void main(String[] args) {

        ipAddress(IP_ADDRESS);
        port(PORT);
        staticFileLocation("/public");
        new TodoResource(new TodoService(mongo()));
    }

    private static MongoDatabase mongo() {

        String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        if (host == null) {
            MongoClient mongoClient = new MongoClient("localhost");
            return mongoClient.getDatabase("todoapp");
        }

        try {

            int port = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
            String dbname = System.getenv("OPENSHIFT_APP_NAME");

        // TODO       String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
        // TODO       String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");


            MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
            MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
            mongoClient.setWriteConcern(WriteConcern.ACKNOWLEDGED);
            MongoDatabase db =  mongoClient.getDatabase(dbname);

            return db;

        }catch (Exception e) {
            throw new RuntimeException("Not able to connect to MongoDB");
        }
    }
}
