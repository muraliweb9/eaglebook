package me.synology.murali.eaglebook.model;

import static me.synology.murali.eaglebook.Utils.formatLeft;


public class HourOrHalfSlotImpl implements HourOrHalfSlot {
	
	private final Duration duration;
	private final String slotTime;
	private final String specificTime;
	
	
	public HourOrHalfSlotImpl(
		Duration duration,
		String slotTime,
		String specificTime) {
		this.duration = duration;
		this.slotTime = slotTime;
		this.specificTime = specificTime;
		
	}
	
	
	@Override
	public Duration getDuration() {
		return this.duration;
	}
	
	
	@Override
	public String getSlotTime() {
		return this.slotTime;
	}
	
	
	@Override
	public String getSpecificTime() {
		return this.specificTime;
	}
	
	
	public String toString() {
		return new StringBuilder().append("[")
			.append("duration=").append(formatLeft(6, duration.toString())).append(",")
			.append("slotTime=").append(slotTime).append(",")
			.append("specificTime=").append(specificTime)
			.append("]")
			.toString();
	}
	
}
