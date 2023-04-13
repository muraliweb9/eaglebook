package me.synology.murali.eaglebook.model;

import java.time.DayOfWeek;
import java.util.Map;

import me.synology.murali.eaglebook.Utils;


public class DayBasedSlots {
	
	private boolean inUse = false;
	
	private final Map<DayOfWeek, HourOrHalfSlot> firstDayBasedSlots;
	private final Map<DayOfWeek, HourOrHalfSlot> secondDayBasedSlots;
	
	
	public DayBasedSlots(
		boolean inUse, Map<DayOfWeek, HourOrHalfSlot> firstDayBasedSlots,
		Map<DayOfWeek, HourOrHalfSlot> secondDayBasedSlots) {
		this.inUse = inUse;
		this.firstDayBasedSlots = firstDayBasedSlots;
		this.secondDayBasedSlots = secondDayBasedSlots;
	}
	
	
	public void IsInUse(boolean inUse) {
		this.inUse = inUse;
	}
	
	
	public HourOrHalfSlot getHourOrHalfSlot(String date, HourOnDay hourOnDay) {
		DayOfWeek dayOfWeek = Utils.getDayOfWeek(date);
		switch (hourOnDay) {
		case FIRST:
			return firstDayBasedSlots.get(dayOfWeek);
		case SECOND:
			return secondDayBasedSlots.get(dayOfWeek);
		default:
			return null;
			
		}
	}
	
}
