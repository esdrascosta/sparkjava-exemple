package com.todoapp;

import com.mongodb.BasicDBObject;

import java.util.Date;

/**
 * Created by esdras on 27/06/16.
 */
public class Todo {
    private String id;
    private String title;
    private boolean done;
    private Date createdOn = new Date();

    public Todo(BasicDBObject dbObject) {
        this.id =  dbObject.get("_id").toString();
        this.title = dbObject.getString("title");
        this.done = dbObject.getBoolean("done");
        this.createdOn = dbObject.getDate("createdOn");
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    public Date getCreatedOn() {
        return createdOn;
    }
}
