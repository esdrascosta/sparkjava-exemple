package com.todoapp;

import static spark.Spark.*;

/**
 * Created by esdras on 27/06/16.
 */
public class TodoResource {
    private static final String API_CONTEXT = "/api/v1";
    private static final String ACCEPT_TYPE = "application/json";
    private final TodoService todoService;

    public TodoResource(TodoService todoService) {
        this.todoService = todoService;
        setupEndPoints();
    }

    private void setupEndPoints() {
        post(API_CONTEXT+"/todos", ACCEPT_TYPE, (request, response) -> {
            todoService.createNewTodo(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());

        get(API_CONTEXT + "/todos/:id", "application/json", (request, response) -> {
            String id = request.params(":id");
            return todoService.find(id);
        }, new JsonTransformer());

        get(API_CONTEXT + "/todos", "application/json", (request, response) ->  todoService.findAll()
        , new JsonTransformer());

        put(API_CONTEXT + "/todos/:id", "application/json", (request, response) -> {
            String id = request.params(":id");
            return todoService.update(id, request.body());
        }, new JsonTransformer());
    }
}
