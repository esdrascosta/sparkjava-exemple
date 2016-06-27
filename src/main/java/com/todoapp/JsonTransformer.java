package com.todoapp;

import com.google.gson.Gson;
import spark.ResponseTransformer;
import spark.TemplateEngine;

/**
 * Created by esdras on 27/06/16.
 */
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) throws Exception {
        return gson.toJson(model);
    }
}
