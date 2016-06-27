package com.todoapp;
import static spark.Spark.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        get("/", (request, response) -> "Hello World!");
    }
}
