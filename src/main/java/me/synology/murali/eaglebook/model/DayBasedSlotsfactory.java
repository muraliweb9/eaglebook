package me.synology.murali.eaglebook.model;

import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class DayBasedSlotsfactory {
	
	private static DayBasedSlots instance = null;
	
	
	public static synchronized DayBasedSlots getInstance() {
		if (instance == null) {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/bookman-spring.xml");
			instance = context.getBean(DayBasedSlots.class);
			context.close();
		}
		return instance;
	}
	
}
