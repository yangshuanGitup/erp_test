package com.singsong.erp.component.docment.excel;

import java.io.Serializable;

public class CellPosition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1121697267212002104L;
	private int sheetIndex;
	private int rowIndex;
	private int cellIndex;
	private String et;
	private String source;
	private String fieldtype;
	private String fieldname;
	
	public int getSheetIndex() {
		return sheetIndex;
	}
	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getCellIndex() {
		return cellIndex;
	}
	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}
	
	public String getEt() {
		return et;
	}
	public void setEt(String et) {
		this.et = et;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getFieldtype() {
		return fieldtype;
	}
	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	@Override
	public String toString() {
		return "CellPosition [sheetIndex=" + sheetIndex + ", rowIndex=" + rowIndex + ", cellIndex=" + cellIndex
				+ ", et=" + et + ", source=" + source + ", fieldtype=" + fieldtype + ", fieldname=" + fieldname + "]";
	}

}
