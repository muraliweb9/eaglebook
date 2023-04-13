package me.synology.murali.eaglebook.model;

import static me.synology.murali.eaglebook.Utils.formatLeft;


public class UserImpl implements User {
	
	private final String name;
	private final String id;
	private final String pin;
	
	
	public UserImpl(String name, String id, String pin) {
		this.name = name;
		this.id = id;
		this.pin = pin;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	@Override
	public String getId() {
		return id;
	}
	
	
	@Override
	public String getPin() {
		return pin;
	}
	
	
	public String toString() {
		return new StringBuilder().append("[")
			.append("name=").append(formatLeft(10, name)).append(",")
			.append("id=").append(id).append(",")
			.append("pin=").append(pin)
			.append("]")
			.toString();
	}
	
}
