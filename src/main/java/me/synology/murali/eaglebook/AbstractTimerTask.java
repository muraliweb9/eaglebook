package me.synology.murali.eaglebook;

import java.util.TimerTask;

import me.synology.murali.eaglebook.model.Booking;


public abstract class AbstractTimerTask extends TimerTask {
	
	protected void sleep(Booking booking) {
		try {
			Thread.sleep(booking.getSleepTime());
		} catch (InterruptedException e) {
		
		}
	}
	
}
