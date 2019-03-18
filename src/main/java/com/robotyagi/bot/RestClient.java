package com.robotyagi.bot;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
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

        try {
            HttpResponse<String> response = Unirest.post(url)
                    .body(body.toString())
                    .asString();
            output = response.getBody();
        }
        catch(Exception e){System.out.println(e); }

        return (new JSONObject(output));

    }
}
