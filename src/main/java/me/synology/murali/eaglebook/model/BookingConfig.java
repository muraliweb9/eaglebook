package me.synology.murali.eaglebook.model;

public interface BookingConfig {
	
	String getUrl();
	
	
	Boolean isUseProxy();
	
	
	String getProxyConfig();
	
	
	Long getSleepTime();
	
	
	Integer getDateRetry();


}
