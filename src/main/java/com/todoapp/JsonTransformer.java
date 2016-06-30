package com.todoapp;

import com.google.gson.Gson;
import spark.Response;
import spark.ResponseTransformer;

import java.util.HashMap;

/**
 * Created by esdras on 27/06/16.
 */
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) throws Exception {
        if (model instanceof Response) {
            return gson.toJson(new HashMap<>());
        }
        return gson.toJson(model);
    }
}
