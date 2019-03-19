package com.robotyagi.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
        RestClient.sendMessage("http://worker-images.ws.pho.to/i1/1DDC063A-49D2-11E9-B4A7-0E691775E776.jpg", "jgfhgfgf");
    }

}
