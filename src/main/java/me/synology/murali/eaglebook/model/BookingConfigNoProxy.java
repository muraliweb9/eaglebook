package me.synology.murali.eaglebook.model;

public class BookingConfigNoProxy implements BookingConfig {
	
	private final String url;
	private final long sleepTime;
	private final Integer dateRetry;
	
	
	public BookingConfigNoProxy(String url,
		long sleepTime,
		Integer dateRetry) {
		this.url = url;
		this.sleepTime = sleepTime;
		this.dateRetry = dateRetry;
		
	}
	
	@Override
	public final String getUrl() {
		return this.url;
	}
	
	@Override
	public final Long getSleepTime() {
		return this.sleepTime;
	}
	
	@Override
	public final Integer getDateRetry() {
		return this.dateRetry;
	}
	
	@Override
	public Boolean isUseProxy() {
		return Boolean.FALSE;
	}
	
	@Override
	public String getProxyConfig() {
		throw new UnsupportedOperationException("Non proxy config");
	}
	
	public String toString() {
		return new StringBuilder().append("[")
			.append("url=").append(url).append(",")
			.append("useProxy=").append(isUseProxy()).append(",")
			.append("sleepTime=").append(sleepTime).append(",")
			.append("dateRetry=").append(dateRetry)
			.append("]")
			.toString();
	}
	
}
