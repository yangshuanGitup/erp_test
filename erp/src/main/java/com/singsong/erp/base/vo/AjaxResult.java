package com.singsong.erp.base.vo;

import java.io.Serializable;

import com.singsong.erp.base.constants.Errors;
import com.singsong.erp.base.util.CommonUtils;

/**
 * ajax请求返回的json数据封装
 * @author 杨树安
 * @version 创建时间：2017年9月26日 下午9:22:45
 */
public class AjaxResult implements Serializable {

	private static final long serialVersionUID = 7013055435048080260L;
	private int status = Errors.SUCCESS.getCode();
	private int errorCode;
	private String errorMsg;
	private Object responseData;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getResponseData() {
		return responseData;
	}

	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	public AjaxResult(int errorCode, String errorMsg) {
		super();
		this.status = Errors.FAILURE.getCode();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public AjaxResult(Object responseData) {
		super();
		this.responseData = responseData;
	}

	public AjaxResult() {
		super();
	}
	
	public String toJsonString(){
		return CommonUtils.toJson(this);
	}
}
