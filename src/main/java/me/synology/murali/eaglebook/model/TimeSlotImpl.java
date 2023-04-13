package me.synology.murali.eaglebook.model;

import static me.synology.murali.eaglebook.Utils.formatLeft;
import static me.synology.murali.eaglebook.Utils.formatRight;

import org.springframework.jmx.export.annotation.ManagedOperationParameter;


public class TimeSlotImpl implements TimeSlot {
	
	private final String date;
	private final HourOnDay hourOnDay;
	private final AltSpecific altSpecific;
	
	private HourOrHalfSlot hourOrHalfSlot;
	
	
	public TimeSlotImpl(String date,
		HourOnDay hourOnDay,
		AltSpecific altSpecific) {
		this.date = date;
		this.hourOnDay = hourOnDay;
		this.altSpecific = altSpecific;
		
	}
	
	
	public HourOnDay getHourOnDay() {
		return this.hourOnDay;
	}
	
	
	public void setHourOrHalfSlot(HourOrHalfSlot hourOrHalfSlot) {
		this.hourOrHalfSlot = hourOrHalfSlot;
	}
	
	
	private HourOrHalfSlot getHourOrHalfSlot() {
		if (this.hourOrHalfSlot != null) {
			return this.hourOrHalfSlot;
		}
		return DayBasedSlotsfactory.getInstance().getHourOrHalfSlot(date, hourOnDay);
	}
	
	
	@Override
	public String getDate() {
		return this.date;
	}
	
	
	@Override
	public Duration getDuration() {
		return getHourOrHalfSlot().getDuration();
	}
	
	
	@Override
	public String getSlotTime() {
		return getHourOrHalfSlot().getSlotTime();
	}
	
	
	@Override
	public String getSpecificTime() {
		return getHourOrHalfSlot().getSpecificTime();
	}
	
	
	@Override
	public String getAltSpecificTime() {
		String altSpecificTime = null;
		switch (this.altSpecific) {
		case NONE:
			break;
		case PREVIOUS:
			altSpecificTime = subHourToSpecific(getHourOrHalfSlot().getSpecificTime());
			break;
		case NEXT:
			altSpecificTime = addHourToSpecific(getHourOrHalfSlot().getSpecificTime());
			break;
		}
		return altSpecificTime;
	}
	
	
	@Override
	public AltSpecific getAltSpecific() {
		return this.altSpecific;
	}
	
	
	@Override
	public int getRowMoves() {
		int rowMoves = 0;
		switch (this.altSpecific) {
		case NONE:
			break;
		case PREVIOUS:
			rowMoves = -2;
			break;
		case NEXT:
			rowMoves = 2;
			break;
		}
		return rowMoves;
	}
	
	
	private String addHourToSpecific(String specific) {
		int hour = Integer.valueOf(specific.substring(0, 2));
		int hourPlus = hour + 1;
		
		return hourPlus
			+ specific.substring(2);
			
	}
	
	
	private String subHourToSpecific(String specific) {
		int hour = Integer.valueOf(specific.substring(0, 2));
		int hourPlus = hour - 1;
		
		return hourPlus
			+ specific.substring(2);
			
	}
	
	
	public String toString() {
		return new StringBuilder().append("[")
			.append("date=").append(date).append(",")
			.append("hourOnDay=").append(formatLeft(6, hourOnDay.toString())).append(",")
			.append("hourOrHalfSlot=").append(getHourOrHalfSlot()).append(",")
			.append("altSpecific=").append(formatLeft(8, altSpecific.toString())).append(",")
			.append("altSpecificTime=").append(getAltSpecificTime()).append(",")
			.append("rowMoves=").append(formatRight(2, Integer.toString(getRowMoves())))
			.append("]")
			.toString();
	}
	
}
