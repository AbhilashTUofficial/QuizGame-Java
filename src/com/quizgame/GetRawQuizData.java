package com.quizgame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Iterator;

public class GetRawQuizData {
    public static void main(String[] args){

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("quiz_data.json"));

            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject jsonObject = (JSONObject) obj;

            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray list = (JSONArray) jsonObject.get("results");

            // An iterator over a collection. Iterator takes the place of Enumeration in the Java Collections Framework.
            // Iterators differ from enumerations in two ways:
            // 1. Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
            // 2. Method names have been improved.
            Iterator<JSONObject> iterator = list.iterator();
            while (iterator.hasNext()) {
                JSONObject map=iterator.next();
                System.out.println(map.get("category"));
                System.out.println(map.get("difficulty"));
                System.out.println(map.get("question"));
                System.out.println(map.get("correct_answer"));
                System.out.println(map.get("incorrect_answers"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
