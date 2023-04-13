package com.dumpBot;

import com.dumpBot.bot.BotInitializer;
import com.dumpBot.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Main {
	public static void main(String[] args) {
		Config config = Config.init();
		new BotInitializer().init(config);
		SpringApplication.run(Main.class, args);
	}


}