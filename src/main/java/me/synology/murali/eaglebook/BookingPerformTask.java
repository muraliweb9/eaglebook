package me.synology.murali.eaglebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.synology.murali.eaglebook.model.Booking;


public class BookingPerformTask extends AbstractTimerTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingPerformTask.class);
	
	private final BookingHandler handler;
	
	
	public BookingPerformTask(BookingHandler handler) {
		this.handler = handler;
		
	}
	
	
	@Override
	public void run() {
		LOGGER.info("Booking perform task - start");
		
		int tries = 1;
		selectDuration();
		LOGGER.info("Sleeping for " + handler.getBooking().getSleepTime() + " after the first select duration");
		sleep(handler.getBooking());
		SlotAvailable slotAvailable = null;
		do {
			if (tries > 1) {
				LOGGER.info("Sleeping for " + handler.getBooking().getSleepTime() + " as date unavailable");
				sleep(handler.getBooking());
				selectDuration();
				refresh();
				sleep(handler.getBooking());
				
			}
			slotAvailable = checkDateAvailable(handler.getBooking());
			if (slotAvailable != SlotAvailable.DATE_AVAILABLE) {
				
				LOGGER.error("Booking perform " + slotAvailable + " after " + tries + " tries");
				tries++;
				back();
			} else {
				LOGGER.info("Booking perform DATE_AVAILABLE after " + tries + " tries");
			}
		} while (slotAvailable != SlotAvailable.DATE_AVAILABLE && tries <= handler.getBooking().getDateRetry());
		if (slotAvailable != SlotAvailable.DATE_AVAILABLE) {
			LOGGER.error("Abandoning booking as " + slotAvailable + " for " + handler.getBooking().getUserName());
		} else {
			LOGGER.info("Sleeping for " + handler.getBooking().getSleepTime() + " (ms) before select slot");
			sleep(handler.getBooking());
			slotAvailable = selectSlot();
			if (slotAvailable != SlotAvailable.SLOT_AVAILABLE) {
				LOGGER.error("Booking perform " + slotAvailable + " and abandoning for user "
					+ handler.getBooking().getUserName());
			} else {
				LOGGER.info("Sleeping for " + handler.getBooking().getSleepTime() + " (ms) before select specific");
				sleep(handler.getBooking());
				slotAvailable = selectSpecific();
				if (slotAvailable == SlotAvailable.COURT_AVAILABLE) {
					book();
					LOGGER.info("SUCCESS - booked for user " + handler.getBooking().getUserName());
				} else {
					LOGGER.error("Booking perform court not available " + slotAvailable + " and abandoning for user "
						+ handler.getBooking().getUserName());
				}
			}
		}
		LOGGER.info("Booking perform task - end");
		
	}
	
	
	private final void selectDuration() {
		handler.selectDuration();
		
	}
	
	
	private final void refresh() {
		handler.refresh();
		
	}
	
	
	private final SlotAvailable checkDateAvailable(Booking booking) {
		handler.checkDateAvailable1();
		sleep(booking);
		return handler.checkDateAvailable2();
		
	}
	
	
	private final SlotAvailable selectSlot() {
		return handler.selectSlot();
		
	}
	
	
	private final SlotAvailable selectSpecific() {
		return handler.selectSpecific();
		
	}
	
	
	private final void book() {
		handler.book();
		
	}
	
	
	private final void back() {
		handler.back();
		
	}
	
}
