package me.synology.murali.eaglebook.model;

import static me.synology.murali.eaglebook.Utils.formatLeft;
import static me.synology.murali.eaglebook.Utils.parse;
import static me.synology.murali.eaglebook.Utils.formatDate;
import static me.synology.murali.eaglebook.Utils.addSecondsToDate;
import java.util.Date;


public class BookingImpl extends AbstractBookingImpl {
	
	private String name;
	private final BookingConfig config;
	private final User user;
	private final TimeSlot timeSlot;
	private final CourtPref courtPref;
	private final Date prepareTime;
	private final Date performTime;
	
	
	public BookingImpl(BookingConfig config, User user, TimeSlot timeSlot, CourtPref courtPref,
		String prepDate, String prepTime, int performDelay) {
		this.config = config;
		this.user = user;
		this.timeSlot = timeSlot;
		this.courtPref = courtPref;
		this.prepareTime = parse(prepDate + " " + prepTime);
		this.performTime = addSecondsToDate(prepareTime, performDelay);
	}
	
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String getName() {
		return name;
	}
	
	
	@Override
	public String getUserName() {
		return user.getName();
	}
	
	
	@Override
	public String getUserId() {
		return user.getId();
	}
	
	
	@Override
	public String getUserPin() {
		return user.getPin();
	}
	
	
	@Override
	public Date getPrepareTime() {
		return prepareTime;
	}
	
	
	@Override
	public Date getPerformTime() {
		return performTime;
	}
	
	
	@Override
	public String getUrl() {
		return config.getUrl();
	}
	
	
	@Override
	public Boolean isUseProxy() {
		return config.isUseProxy();
	}
	
	
	@Override
	public String getProxyConfig() {
		return config.getProxyConfig();
	}
	
	
	@Override
	public Long getSleepTime() {
		return config.getSleepTime();
	}
	
	
	@Override
	public Integer getDateRetry() {
		return config.getDateRetry();
	}
	
	
	@Override
	public String getDate() {
		return timeSlot.getDate();
	}
	
	
	@Override
	public Duration getDuration() {
		return timeSlot.getDuration();
	}
	
	
	@Override
	public String getSlotTime() {
		return timeSlot.getSlotTime();
	}
	
	
	@Override
	public String getSpecificTime() {
		return timeSlot.getSpecificTime();
	}
	
	
	@Override
	public String getAltSpecificTime() {
		return timeSlot.getAltSpecificTime();
	}
	
	
	@Override
	public AltSpecific getAltSpecific() {
		return timeSlot.getAltSpecific();
	}
	
	
	@Override
	public int getRowMoves() {
		return timeSlot.getRowMoves();
	}
	
	
	@Override
	public CourtPref getCourtPref() {
		return courtPref;
	}
	
	
	public String toString() {
		return new StringBuilder().append("[")
			.append("config=").append(config).append(",")
			.append("user=").append(user).append(",")
			.append("timeSlot=").append(timeSlot).append(",")
			.append("courtPref=").append(formatLeft(4, courtPref.toString())).append(",")
			.append("prepTime=").append(formatDate(prepareTime)).append(",")
			.append("performTime=").append(formatDate(performTime))
			.append("]")
			.toString();
	}
	
}
