package com.mertalptasdelen.quick;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class App {
	
    public static void main( String[] args )
    {
    	ApiContextInitializer.init();
    	
    	//This is a new message about my development branch
        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            botsApi.registerBot(new Kaan());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("LoggingTestBot successfully started!");
    }
}
