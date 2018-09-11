package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatConverter {

	private final static String DB_FORMAT = "yyyy-MM-dd HH:mm";
	private final static String NORMAL_FORMAT = "MM-dd-yyyy HH:mm";
	
	private DateFormatConverter() {
		//utility class
	}
	
	public static String convertFromDBToSearchingCalendarsView(Date date) {
		return new SimpleDateFormat("MM/dd/yyyy HH:mm").format(date);
	}
	
	public static String convertFromDBToListingOrders(Date date) throws ParseException {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
	}
	
	public static String convertFromPMTo24Hour(String date) throws ParseException {
		SimpleDateFormat date12Format  = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		SimpleDateFormat date24Format  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return date24Format.format(date12Format.parse(date));
	}
	
	public static Date convertFromCalendarViewToDate(String date) throws ParseException {
		SimpleDateFormat format  = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		SimpleDateFormat date24Format  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return 	date24Format.parse(date24Format.format(format.parse(date)));
	}
}
