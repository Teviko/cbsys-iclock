package com.cbsys.iclock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ErrorDeviceException extends RuntimeException {

	private static final long serialVersionUID = 1217647667376294386L;

}
