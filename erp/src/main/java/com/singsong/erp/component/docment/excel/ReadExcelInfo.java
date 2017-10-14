package com.singsong.erp.component.docment.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class ReadExcelInfo implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = -2677373956159857581L;
	private Sheet sheet;
	private List<CellPosition> masterCellPostList = new ArrayList<CellPosition>();
	private List<CellPosition> slaveCellPostList = new ArrayList<CellPosition>();
	private List<Map<String, Object>> slaveDataList = new ArrayList<Map<String, Object>>();
	private Map<String, Object> masterData = new HashMap<String, Object>();
	private String businessValue;
	private String sheetName;
	private String masterName;
	private String slaveName;
	private int startRowIndex;
	private int endRowIndex;
	private List<Picture> pictureList;
	private PrintSetup printSetup;
	private String printArea;
	private CellRangeAddress columnRangAddress;
	private CellRangeAddress rowRangAddress;

	public List<CellPosition> getMasterCellPostList() {
		return masterCellPostList;
	}

	public void setMasterCellPostList(List<CellPosition> masterCellPostList) {
		this.masterCellPostList = masterCellPostList;
	}

	public List<CellPosition> getSlaveCellPostList() {
		return slaveCellPostList;
	}

	public void setSlaveCellPostList(List<CellPosition> slaveCellPostList) {
		this.slaveCellPostList = slaveCellPostList;
	}

	public String getBusinessValue() {
		return businessValue;
	}

	public void setBusinessValue(String businessValue) {
		this.businessValue = businessValue;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getSlaveName() {
		return slaveName;
	}

	public void setSlaveName(String slaveName) {
		this.slaveName = slaveName;
	}

	public int getStartRowIndex() {
		return startRowIndex;
	}

	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	public int getEndRowIndex() {
		return endRowIndex;
	}

	public void setEndRowIndex(int endRowIndex) {
		this.endRowIndex = endRowIndex;
	}

	public List<Map<String, Object>> getSlaveDataList() {
		return slaveDataList;
	}

	public void setSlaveDataList(List<Map<String, Object>> slaveDataList) {
		this.slaveDataList = slaveDataList;
	}

	public Map<String, Object> getMasterData() {
		return masterData;
	}

	public void setMasterData(Map<String, Object> masterData) {
		this.masterData = masterData;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public List<Picture> getPictureList() {
		return pictureList;
	}

	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}

	public void setPrintArea(String printArea) {
		this.printArea = printArea;
	}
	public String getPrintArea() {
		return printArea;
	}
	public CellRangeAddress getColumnRangAddress() {
		return columnRangAddress;
	}

	public void setColumnRangAddress(CellRangeAddress columnRangAddress) {
		this.columnRangAddress = columnRangAddress;
	}

	public CellRangeAddress getRowRangAddress() {
		return rowRangAddress;
	}

	public void setRowRangAddress(CellRangeAddress rowRangAddress) {
		this.rowRangAddress = rowRangAddress;
	}

	public PrintSetup getPrintSetup() {
		return printSetup;
	}

	public void setPrintSetup(PrintSetup printSetup) {
		this.printSetup = printSetup;
	}

}
