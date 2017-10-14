package com.singsong.erp.base.pagin;

import java.util.List;

/**
 * 分页返回数据集合封装
 * 
 * @author 杨树安
 * @version 创建时间：2017年9月26日 下午9:13:29
 * @param <T>要返回的数据对象类型
 */
public class PaginatedResult<T> {

	private int count;
	private List<T> data;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}

}
