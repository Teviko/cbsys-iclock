package com.cbsys.iclock.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {

	public static String stackTraceToString(Throwable e) {
		StringWriter l_oSw = new StringWriter();
		e.printStackTrace(new PrintWriter(l_oSw));
		return l_oSw.toString();
	}
}