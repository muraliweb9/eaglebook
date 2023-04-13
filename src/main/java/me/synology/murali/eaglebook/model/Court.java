package me.synology.murali.eaglebook.model;

public enum Court {
	COURT_PREBOOKED, COURT_2, COURT_3, COURT_4;
	
	public static Court ordinal(int ordinal) {
		for (Court c : values()) {
			if (c.ordinal() == ordinal) {
				return c;
			}
		}
		return null;
	}
	
}
