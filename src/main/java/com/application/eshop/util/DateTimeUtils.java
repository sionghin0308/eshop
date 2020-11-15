package com.application.eshop.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	
	public static Date getCurrentDate() {
		return new Date();
	}
	
	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static String getFormatDate(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
}
