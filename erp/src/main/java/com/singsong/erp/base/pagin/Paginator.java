package com.singsong.erp.base.pagin;

import java.io.Serializable;

/**
 * 在进行单表过滤查询的时候,filters不为空,filtersMap为空
 * 分页参数封装
 * @author 杨树安
 * @version 创建时间：2017年9月26日 下午9:14:46
 * @param <T> 要传入的参数数据对象类型
 */
public class Paginator implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pageSize = 5;
    private int start;
    private String filters;
    private String sorts;//排序map,key是排序字段,value是排序内容(升充(asc),降序(desc))

    public Paginator() {

    }

	public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start; 
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getSorts() {
		return sorts;
	}

	public void setSorts(String sorts) {
		this.sorts = sorts;
	}

}
