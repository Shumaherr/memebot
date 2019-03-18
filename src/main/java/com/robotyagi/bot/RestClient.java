package com.robotyagi.bot;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

public class RestClient {

    @Value("${backend.url}")
    private static String backendUrl;
    @Value("${backend.port}")
    private static String backendPort;
    @Value("${backend.path}")
    private static String backendPath;

    public static JSONObject sendMessage(String photoUrl, String text) {
        String newPicUrl =new String();
        JSONObject body = new JSONObject();
        body.put("url", photoUrl);
        body.put("text", text);
        String output = new String();
        String url = backendUrl + ":" + backendPort + backendPath;
        JSONObject responseBody = new JSONObject();
        try {
            HttpResponse<String> response = Unirest.post(url)
                    .body(body.toString())
                    .asString();
            output = response.getBody();
            JSONArray responseVector = new JSONArray(output);
            responseBody.put("results", responseVector);
        }
        catch(Exception e){
            System.out.println(e);
            responseBody.put("Error", "Error zero results");
        }

        return responseBody;

    }
}
