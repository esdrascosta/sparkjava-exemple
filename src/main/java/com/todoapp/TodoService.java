package com.todoapp;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by esdras on 27/06/16.
 */
public class TodoService {

    private final MongoDatabase db;
    private final MongoCollection<BasicDBObject> collection;

    public TodoService(MongoDatabase db) {
        this.db = db;
        this.collection = db.getCollection("todos",BasicDBObject.class);
    }

    public List<Todo> findAll(){
        FindIterable<BasicDBObject> dbObjects = this.collection.find();
        ArrayList<Todo> todos = new ArrayList<>();
        for (BasicDBObject todo: dbObjects) {
            todos.add(new Todo(todo));
        }
        return todos;
    }

    public void createNewTodo(String responceBody){
        Todo todo = new Gson().fromJson(responceBody, Todo.class);
        collection.insertOne(
                new BasicDBObject("title",todo.getTitle())
                        .append("done",todo.isDone())
                        .append("createdOn", new Date()));
    }

    public Todo find(String id){
        BasicDBObject todo = (BasicDBObject) collection.find(new BasicDBObject("_id", new ObjectId(id)));
        return new Todo(todo);
    }


    public Todo update(String todoId, String body){
        Todo todo = new Gson().fromJson(body,Todo.class);
        collection.updateOne(
                new BasicDBObject("_id",new ObjectId(todoId)),
                new BasicDBObject("$set",
                        new BasicDBObject("done",todo.isDone())));
        return this.find(todoId);
    }
}