package com.singsong.erp.base.pagin;

import java.io.Serializable;

/**
 * grid过滤组件排序封装
 * @author 杨树安
 * @version 创建时间：2017年9月28日 下午1:32:37
 */
public class GridSort implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8216948927677704756L;
	public String field;
	public String order;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "GridSort [field=" + field + ", order=" + order + "]";
	}
	
}
