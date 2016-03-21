package com.cbsys.iclock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ErrorDataFormatException extends RuntimeException {

	private static final long serialVersionUID = -5632617411109835174L;

}
