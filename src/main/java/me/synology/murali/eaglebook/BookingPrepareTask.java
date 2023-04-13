package me.synology.murali.eaglebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BookingPrepareTask extends AbstractTimerTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingPrepareTask.class);
	private final BookingHandler handler;
	
	
	public BookingPrepareTask(BookingHandler handler) {
		this.handler = handler;
		
	}
	
	
	@Override
	public void run() {
		LOGGER.info("Booking prepare task - start");
		openBrowser();
		login();
		getToSelectSlot();
		sleep(handler.getBooking());
		sleep(handler.getBooking());
		getCentreAndHall();
		LOGGER.info("Booking prepare task - end");
	}
	
	
	private final void openBrowser() {
		handler.openBrowser();
		
	}
	
	
	private final void login() {
		handler.login();
		
	}
	
	
	private final void getToSelectSlot() {
		handler.getToSelectSlot();
		
	}
	
	
	private final void getCentreAndHall() {
		handler.getCentreAndHall();
		
	}
	
}
