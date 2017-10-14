package com.singsong.erp.base.pagin;

import java.io.Serializable;

/**
 * grid过滤组件条件封装
 * @author 杨树安
 * @version 创建时间：2017年9月28日 下午1:33:10
 */
public class GridCondition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2955207190673735451L;
	private String field;
	private String op;
	private String data;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "JqgridParamFilterRule [field=" + field + ", op=" + op + ", data=" + data + "]";
	}
	
}
