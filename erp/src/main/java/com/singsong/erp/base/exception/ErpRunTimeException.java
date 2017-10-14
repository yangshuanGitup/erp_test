package com.singsong.erp.base.exception;

/**
 * Service层的异常定义
 */
public class ErpRunTimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 */
	public ErpRunTimeException() {
		super();
	}

	/**
	 */
	public ErpRunTimeException(Throwable cause) {
		super(cause);
	}

	/**
	 */
	public ErpRunTimeException(String msg) {
		super(msg);
	}

	public ErpRunTimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
