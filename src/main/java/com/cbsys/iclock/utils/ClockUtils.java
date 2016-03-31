package com.cbsys.iclock.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class ClockUtils {
	private static DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssZ");
	private static DateTimeFormatter FormatterToTMS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

	public static long getLastOnlineTime() {
		return LocalDateTime.now().withNano(0).toEpochSecond(ZoneOffset.ofTotalSeconds(TimeZone.getDefault().getRawOffset() / 1000)) * 1000;
	}

	public static long transToUTC(String datetime, int timeZoneOffset) {
		String tz = "";
		if (timeZoneOffset > 9)
			tz = "+" + timeZoneOffset;
		else if (timeZoneOffset < -9)
			tz = String.valueOf(timeZoneOffset);
		else if (timeZoneOffset <= 9 && timeZoneOffset >= 0)
			tz = "+0" + timeZoneOffset;
		else if (timeZoneOffset <= -1 && timeZoneOffset >= -9)
			tz = "-0" + Math.abs(timeZoneOffset);
		Instant utcDatetime = OffsetDateTime.parse(datetime + tz + "00", Formatter).toInstant();
		return utcDatetime.toEpochMilli();
	}

	public static String transToOffsetDatetime(long utcTime, int timeZoneOffset) {
		String tz = "";
		if (timeZoneOffset > 0)
			tz = "+" + timeZoneOffset;
		else
			tz = String.valueOf(timeZoneOffset);
		return OffsetDateTime.ofInstant(Instant.ofEpochMilli(utcTime), ZoneOffset.of(tz)).format(FormatterToTMS);
	}
	/*
		public static void main(String[] args) {
	
			System.out.println(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
			//Date time = format.parse("2016-03-02T15:56:57+1300");
			long t = 1458573700000l;//transToUTC("2016-03-02 15:56:57", 13);
			System.out.println(t);
			System.out.println(transToOffsetDatetime(t, 13));
		}*/
}
