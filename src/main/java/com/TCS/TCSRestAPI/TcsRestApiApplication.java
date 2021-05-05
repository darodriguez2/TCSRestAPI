package com.TCS.TCSRestAPI;

import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.TCS.TCSRestAPI.listeners.MessageListener;

@SpringBootApplication
public class TcsRestApiApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(TcsRestApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Builder builder = new Configuration.Builder()
				.setAutoNickChange(false)
				.setOnJoinWhoEnabled(false)
				.setCapEnabled(true)
				.addServer("irc.twitch.tv")
				.setName("tcstatbot")
				.setServerPassword("oauth:7m9hfwr3htcrmc2yahg1dj21dg1geu")
				.addAutoJoinChannel("#mizkif")
				.addListener(new MessageListener());
		
		Configuration config = builder.buildConfiguration();
		
		PircBotX bot = new PircBotX(config);
		
		try {
			bot.startBot();	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
