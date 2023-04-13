package me.synology.murali.eaglebook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class Utils {
	
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	
	private static final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yy");
	
	private static final DateTimeFormatter DAY_NO_YEAR_FORMAT = DateTimeFormatter.ofPattern("EEE dd MMM yyyy");
	
	
	public static final String formatLeft(int width, String value) {
		return String.format("%-" + width + "s", value);
	}
	
	
	public static final String formatRight(int width, String value) {
		return String.format("%" + width + "s", value);
	}
	
	
	public static Date parse(String str) {
		try {
			return FORMAT.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}
	
	
	public static LocalDate parseShortDate(String str) {
		LocalDate localDate = LocalDate.parse(str, SHORT_DATE_FORMAT);
		return localDate;
	}
	
	
	public static DayOfWeek getDayOfWeek(String shortDate) {
		LocalDate localDate = parseShortDate(shortDate);
		return localDate.getDayOfWeek();
	}
	
	
	public static String formatDate(Date date) {
		return FORMAT.format(date);
	}
	
	
	public static Date addSecondsToDate(Date date, int seconds) {
		return new Date(date.getTime() + (seconds * 1000));
	}
	
	
	public static String formatDayDate(String dayDateString) {
		return SHORT_DATE_FORMAT.format(
			DAY_NO_YEAR_FORMAT.parse(dayDateString + " " + Calendar.getInstance().get(Calendar.YEAR)));
	}
	
}
