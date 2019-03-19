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

    public static String sendMessage(String photoUrl, String text) {
        String newPicUrl =new String();
        JSONObject body = new JSONObject();
        body.put("url", photoUrl);
        body.put("text", text);
        String output = new String();
        //String url = backendUrl + ":" + backendPort + backendPath;
        String url = "http://18.219.76.113:9090/upload";
        try {
            HttpResponse<String> response = Unirest.post(url)
                    .body(body.toString())
                    .asString();
            output = response.getBody();

        }
        catch(Exception e){
            System.out.println(e);
        }

        return output;

    }
}
