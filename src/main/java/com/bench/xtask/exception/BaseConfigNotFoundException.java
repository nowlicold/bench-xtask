/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.xtask.exception;


import com.bench.common.enums.error.ErrorEnum;
import com.bench.common.error.ErrorCode;
import com.bench.common.exception.BenchException;
import com.bench.common.exception.BenchRuntimeException;

/**
 * 配置无法找到的异常
 * 
 * @author cold
 *
 * @version $Id: ConfigNotFoundException.java, v 0.1 2018年8月1日 上午11:14:03
 *          cold Exp $
 */
public class BaseConfigNotFoundException extends BenchRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3983270804365390914L;

	/**
	 * 
	 */
	public BaseConfigNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 */
	public BaseConfigNotFoundException(ErrorCode errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param exception
	 */
	public <E extends BenchException> BaseConfigNotFoundException(E exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param exception
	 */
	public <E extends BenchRuntimeException> BaseConfigNotFoundException(E exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorEnum
	 */
	public BaseConfigNotFoundException(ErrorEnum errorEnum) {
		super(errorEnum);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 * @param message
	 */
	public BaseConfigNotFoundException(ErrorCode errorCode, String message) {
		super(errorCode, message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 * @param cause
	 */
	public BaseConfigNotFoundException(ErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param exception
	 */
	public <E extends BenchException> BaseConfigNotFoundException(String message, E exception) {
		super(message, exception);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param exception
	 */
	public <E extends BenchRuntimeException> BaseConfigNotFoundException(String message, E exception) {
		super(message, exception);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorEnum
	 * @param message
	 */
	public BaseConfigNotFoundException(ErrorEnum errorEnum, String message) {
		super(errorEnum, message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorEnum
	 * @param cause
	 */
	public BaseConfigNotFoundException(ErrorEnum errorEnum, Throwable cause) {
		super(errorEnum, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param throwable
	 */
	public BaseConfigNotFoundException(ErrorCode errorCode, String message, Throwable throwable) {
		super(errorCode, message, throwable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorEnum
	 * @param message
	 * @param throwable
	 */
	public BaseConfigNotFoundException(ErrorEnum errorEnum, String message, Throwable throwable) {
		super(errorEnum, message, throwable);
		// TODO Auto-generated constructor stub
	}

}
