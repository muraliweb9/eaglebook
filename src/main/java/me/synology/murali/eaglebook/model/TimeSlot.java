package me.synology.murali.eaglebook.model;

public interface TimeSlot {
	
	String getDate();
	
	
	Duration getDuration();
	
	
	String getSlotTime();
	
	
	HourOnDay getHourOnDay();
	
	
	String getSpecificTime();
	
	
	String getAltSpecificTime();
	
	
	AltSpecific getAltSpecific();
	
	
	int getRowMoves();
	
}
