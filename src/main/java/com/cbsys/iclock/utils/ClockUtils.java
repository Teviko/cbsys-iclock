package com.cbsys.iclock.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRules;

import org.apache.commons.lang3.StringUtils;

public class ClockUtils {
	private static DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static DateTimeFormatter FormatterToTMS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

	public static long getLastOnlineTime() {
		//return LocalDateTime.now().withNano(0).toEpochSecond(ZoneOffset.ofTotalSeconds(TimeZone.getDefault().getRawOffset() / 1000)) * 1000;
		return System.currentTimeMillis();
	}

	public static int getClockTZOffset(String tzId) {
		if (StringUtils.isBlank(tzId))
			return 99;
		/*ZoneId systemTZ = ZoneId.systemDefault();
		if (systemTZ.getId().equals(tzId))
			return 0;*/
		ZoneRules zr = ZoneId.of(tzId).getRules();
		int offsetHours = zr.getOffset(Instant.now()).getTotalSeconds() / 3600;
		return offsetHours;
	}

	/**
	 * @param datetime
	 * @param tzId
	 * @return  array: [0] utcMilli, [1] timezoneOffsetHours
	 */
	public static long[] transToUTC(String datetime, String tzId) {
		ZoneId zone = null;
		if (StringUtils.isBlank(tzId))
			zone = ZoneId.systemDefault();
		else
			zone = ZoneId.of(tzId);
		LocalDateTime localDT = LocalDateTime.parse(datetime, Formatter);
		ZoneOffset offset = zone.getRules().getOffset(localDT);
		Instant utcDatetime = OffsetDateTime.of(localDT, offset).toInstant();
		long[] result = new long[2];
		result[0] = utcDatetime.toEpochMilli();
		result[1] = offset.getTotalSeconds() / 3600;
		return result;
	}

	public static String transToOffsetDatetime(long utcTime, int timeZoneOffset) {
		String tz = "";
		if (timeZoneOffset > 0)
			tz = "+" + timeZoneOffset;
		else
			tz = String.valueOf(timeZoneOffset);
		return OffsetDateTime.ofInstant(Instant.ofEpochMilli(utcTime), ZoneOffset.of(tz)).format(FormatterToTMS);
	}

	/*public static void main(String[] args) {
		System.out.println(getClockTZOffset("Pacific/Rarotonga"));
			//	String[] ids = TimeZone.getAvailableIDs();
			//	System.out.println(ids.length);
			//	System.out.println(TimeZone.getTimeZone("NZ").getOffset(System.currentTimeMillis()) / 1000 / 60 / 60);
			Set<String> zoneIds = ZoneId.getAvailableZoneIds();
			System.out.println(zoneIds.size());
			//System.out.println(ZoneId.of("NZ").getRules().getOffset(Instant.parse("2016-03-02T15:56:57z")).getTotalSeconds() / 60 / 60);
			for (String zoneId : zoneIds) {
				ZoneId z = ZoneId.of(zoneId);
				System.out.println(zoneId + "," + z.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + "," + z.getRules().toString().split("=")[1].replace("]", ""));
			}
			//for (String id : ids)
			//	System.out.println(id);
			//	System.out.println(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
			//Date time = format.parse("2016-03-02T15:56:57+1300");
			long t = 1458573700000l;
			long t2 = transToUTC("2016-04-06 15:56:57", "NZ")[0];
			//	System.out.println(t);
			//	System.out.println(t2);
			//	System.out.println(Instant.ofEpochMilli(t2).toString());
			//	System.out.println(transToOffsetDatetime(t, 13));
	}*/
}
