package com.cbsys.iclock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ErrorParamsException extends RuntimeException {

	private static final long serialVersionUID = 3017703346661651884L;

}
