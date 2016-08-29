package com.randomsilo.mystash.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateHelper {
	private static Long SECONDS_PER_DAY = 86400L;

	public static Long getDaysInSeconds(int days) {
		Long seconds = 0L;

		seconds = SECONDS_PER_DAY * days;
		
		return seconds;
	}
	
	public static Long getStrDateInSeconds(String date) {
		Long d = 0L;

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date dateObj = sdf.parse(date);
			d = dateObj.getTime();
			
		} catch (ParseException e) {
			Log.e("MyStash", "getStrDateInSeconds", e);
		}
		
		return d;
	}
}
