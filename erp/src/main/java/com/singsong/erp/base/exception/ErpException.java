package com.singsong.erp.base.exception;

/**
 * Service层的异常定义
 */
public class ErpException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 */
	public ErpException() {
		super();
	}

	/**
	 */
	public ErpException(Throwable cause) {
		super(cause);
	}

	/**
	 */
	public ErpException(String msg) {
		super(msg);
	}

	public ErpException(String message, Throwable cause) {
		super(message, cause);
	}

}
