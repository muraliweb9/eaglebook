package me.synology.murali.eaglebook.model;

import java.util.Date;


public interface Booking {
	
	void setName(String name);
	
	
	String getName();
	
	
	String getUserName();
	
	
	String getUserId();
	
	
	String getUserPin();
	
	
	Date getPrepareTime();
	
	
	Date getPerformTime();
	
	
	String getUrl();
	
	
	Boolean isUseProxy();
	
	
	String getProxyConfig();
	
	
	Long getSleepTime();
	
	
	Integer getDateRetry();
	
	
	String getDate();
	
	
	Duration getDuration();
	
	
	String getSlotTime();
	
	
	String getSpecificTime();
	
	
	String getAltSpecificTime();
	
	
	int getRowMoves();
	
	
	AltSpecific getAltSpecific();
	
	
	CourtPref getCourtPref();
	
}
