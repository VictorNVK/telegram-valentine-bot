package com.example.ValentineBot;

import com.example.ValentineBot.Repository.UserRepository;
import com.example.ValentineBot.Repository.ValentineRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@SpringBootApplication
public class ValentineBotApplication {

	private final UserRepository userRepository;
	private final ValentineRepository valentineRepository;

	public ValentineBotApplication(UserRepository userRepository, ValentineRepository valentineRepository) {
		this.userRepository = userRepository;
        this.valentineRepository = valentineRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(ValentineBotApplication.class, args);
	}

	@PostConstruct
	public void init() {
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new ValentineBot(userRepository, valentineRepository));
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
