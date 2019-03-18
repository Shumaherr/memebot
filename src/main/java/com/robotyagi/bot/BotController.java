package com.robotyagi.bot;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * This example bot is an echo bot that just repeats the messages sent to him
 *
 */
@Component
public class BotController extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(BotController.class);

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(chatId);
            String text = message.getText();
            response.setText(text);
            SendPhoto sendPhotoRequest = new SendPhoto();
            sendPhotoRequest.setChatId(chatId);
            try {
                JSONObject responseJSON = new JSONObject(RestClient.sendMessage(getPhotoFromMessage(getBotToken(), message.getPhoto().get(message.getPhoto().size() - 1).getFileId()),message.getCaption()));
                JSONArray jsonArray = responseJSON.getJSONArray("results");
                sendPhotoRequest.setPhoto(jsonArray.get(0).toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                execute(sendPhotoRequest);
                logger.info("Sent message \"{}\" to {}", text, chatId);
            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
    }

    private String getPhotoFromMessage(String token, String fileId) throws MalformedURLException {
        URL url = new URL("https://api.telegram.org/bot"+token+"/getFile?file_id="+fileId);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String res = in.readLine();
            JSONObject jresult = new JSONObject(res);
            String full_path = "https://api.telegram.org/file/bot" + token + "/" + jresult.getJSONObject("result").getString("file_path");
            return full_path;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
