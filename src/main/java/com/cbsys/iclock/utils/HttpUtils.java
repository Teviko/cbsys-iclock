package com.cbsys.iclock.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpUtils {
	public static HttpServletRequest getRequest() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		if(ra == null)
			return null;
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		return sra.getRequest();
	}

	public static HttpServletResponse getResponse() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		if (ra == null)
			return null;
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		return sra.getResponse();
	}

	public static void loggerRequest(Log log) {
		if (log == null)
			return;
		HttpServletRequest request = getRequest();
		if (request == null)
			return;
		log.info("------------HttpRequest--------------");
		log.info(request.getRequestURI());
		log.info(request.getServletPath());
		log.info(request.getProtocol());
		log.info(request.getContentLength());
		log.info("--------------------Request Header-----------------------");
		Enumeration<String> header = request.getHeaderNames();
		while (header.hasMoreElements()) {
			String name = header.nextElement();
			log.info(name + "::" + request.getHeader(name));
		}
		log.info("--------------------Request Parameters-----------------------");
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String name = params.nextElement();
			log.info(name + "::" + request.getParameter(name));
		}
	}
}
