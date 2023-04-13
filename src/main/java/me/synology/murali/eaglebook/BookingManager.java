package me.synology.murali.eaglebook;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import me.synology.murali.eaglebook.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BookingManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingManager.class);
	
	
	public static void main(String[] args) {
		new BookingManager().run();
		
	}
	
	
	private void run() {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/bookman-spring.xml");
		
		Map<String, Booking> bookings = context.getBeansOfType(Booking.class);
		
		for (Entry<String, Booking> entry : bookings.entrySet()) {
			LOGGER.info(entry.getKey() + " " + entry.getValue());
			String name = entry.getKey();
			Booking booking = entry.getValue();
			booking.setName(name);
			BookingHandler handler = new BookingHandler(booking);
			startTimers(name, handler);
		}
		
		context.close();
		
	}
	
	
	private void startTimers(String name, BookingHandler handler) {
		Timer prepTimer = new Timer("prepTimer-" + name);
		Timer performTimer = new Timer("performTimer-" + name);
		TimerTask prepTask = new BookingPrepareTask(handler);
		TimerTask performTask = new BookingPerformTask(handler);
		
		prepTimer.schedule(prepTask, handler.getBooking().getPrepareTime());
		performTimer.schedule(performTask, handler.getBooking().getPerformTime());
		
	}
}
