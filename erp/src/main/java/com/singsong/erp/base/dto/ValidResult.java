package com.singsong.erp.base.dto;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class ValidResult implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -4643384756960345924L;
    private int status = 1;
    private int errorCode;
    private String errorMsg;
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
    public ValidResult(int errorCode, String errorMsg) {
        super();
        this.status = 0;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    public ValidResult() {
        super();
        // TODO Auto-generated constructor stub
    }  
    
}
