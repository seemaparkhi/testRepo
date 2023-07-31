package com.gc.myproject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JavaUtility {
	public static Map<String,String> createHeaders(){
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        headers.put("X-amazon-author","Seema Parkhi");
        headers.put("X-amazon-apiVersion","v1");
        return  headers ;
    }

    public static String convertObjToString(Book book, Context context){
        String jsonBody = null;
        try {
            jsonBody =   new ObjectMapper().writeValueAsString(book);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }
    public static Book convertStringToObj(String jsonBody,Context context){
        Book book = null;
        try {
            book =   new ObjectMapper().readValue(jsonBody,Book.class);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting string to obj:::" + e.getMessage());
        }
        return book;
    }
    public static String convertListOfObjToString(List<Book> employees, Context context){
        String jsonBody = null;
        try {
            jsonBody =   new ObjectMapper().writeValueAsString(employees);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }
}
