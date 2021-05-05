package com.TCS.TCSRestAPI.listeners;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class MessageListener extends ListenerAdapter {

	public static Map<String, Integer> count;
	private int totalMessagesProcessed;
	private long start;
	private double elapsedTime;
	
	public MessageListener() {
		MessageListener.count = new HashMap<>();
		this.totalMessagesProcessed = 0;
		this.start = System.nanoTime();	
	}
	
	@Override
	public void onGenericMessage(GenericMessageEvent event) throws Exception {	
		this.totalMessagesProcessed++;
		processMessage(event);
		this.elapsedTime = (double) (System.nanoTime() - this.start) / 1_000_000_000;
		
		//runs for 1800 seconds = 30 minutes
		if(this.elapsedTime > 1800) {
			event.getBot().sendIRC().quitServer();
			System.out.println("Total number of messages processed: " + this.totalMessagesProcessed + " in " + this.elapsedTime + " seconds");
			System.out.println("Total number of unique chatters: " + this.count.keySet().size());
			System.out.println("Top chatters:");
			for(int i = 0; i < 10; i++) {
				String user = Collections.max(count.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
				System.out.println((i+1) + ") " + user + ", number of times chatted: " + count.get(user));
				count.remove(user);		
			}
			
		}
	}	
	
	private void processMessage(GenericMessageEvent event) {
		if(MessageListener.count.get(event.getUser().getNick()) == null) {
			MessageListener.count.put(event.getUser().getNick(), 1);
		} else {
			MessageListener.count.replace(event.getUser().getNick(), this.count.get(event.getUser().getNick()) + 1);
		}
	}
}
