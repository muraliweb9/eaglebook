package me.synology.murali.eaglebook.model;

public class BookingConfigWithProxy extends BookingConfigNoProxy{
	
	private final String proxyConfig;
	
	
	public BookingConfigWithProxy(String url,
		String proxyConfig,
		long sleepTime,
		Integer dateRetry) {
		super(url, sleepTime, dateRetry);
		this.proxyConfig = proxyConfig;
		
	}
	
	public final Boolean isUseProxy() {
		return Boolean.TRUE;
	}
	
	
	public final String getProxyConfig() {
		return this.proxyConfig;
	}
	
	public String toString() {
		return new StringBuilder().append("[")
			.append("url=").append(getUrl()).append(",")
			.append("useProxy=").append(isUseProxy()).append(",")
			.append("proxyConfig=").append(getProxyConfig()).append(",")
			.append("sleepTime=").append(getSleepTime()).append(",")
			.append("dateRetry=").append(getDateRetry())
			.append("]")
			.toString();
	}
	
}
