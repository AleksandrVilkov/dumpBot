package com.dumpBot;

import com.dumpBot.bot.Initializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		new Initializer().init();
	}


}