package com.TCS.TCSRestAPI.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TCS.TCSRestAPI.listeners.MessageListener;

@RestController
public class MainController {

	@GetMapping("/chatters")
	Map<String, Integer> chatters() {
		return MessageListener.count;
	}
	
	@GetMapping("/topchatters")
	Map<String, Integer> topChatters() {
		Map<String, Integer> topChatters = new HashMap<>();
		Map<String, Integer> chatters = MessageListener.count;
		for(int i = 0; i < 10; i++) {
			String user = Collections.max(chatters.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
			topChatters.put(user, chatters.get(user));
			chatters.remove(user);		
		}
		return topChatters;
	}
	
	@GetMapping("/numUniqueChatters")
	int uniqueChatters() {
		return MessageListener.count.keySet().size();
	}
}
