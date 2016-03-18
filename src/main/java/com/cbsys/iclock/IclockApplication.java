package com.cbsys.iclock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan
public class IclockApplication {

	private static final Log logger = LogFactory.getLog(IclockApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(IclockApplication.class, args);
		logger.info("=======iClock Service Start========");
	}
}
