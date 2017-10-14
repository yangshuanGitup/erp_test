package com.singsong.erp.component.docment.excel;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class PoiUtils {
	/**
	 * Sheet复制
	 * 
	 * @param fromSheet
	 * @param toSheet
	 * @param copyValueFlag
	 */
	public static void copySheet(Workbook wb, Sheet fromSheet, Sheet toSheet, boolean copyValueFlag) {
		// 合并区域处理
		mergerRegion(fromSheet, toSheet);
		//先要设置特殊列的宽度、特殊行的高度
		Iterator<Cell> itCell=fromSheet.getRow(0).cellIterator();
		while(itCell.hasNext()){
			Cell cell=itCell.next();
			int columnIndex=cell.getColumnIndex();
			int width=fromSheet.getColumnWidth(columnIndex);
			toSheet.setColumnWidth(columnIndex, width);
		}
/*		Iterator<Row> itRow=fromSheet.rowIterator();
		while(itRow.hasNext()){
			Row row=itRow.next();
			int rowNum=row.getRowNum();
			short height=row.getHeight();
			if(toSheet.getRow(rowNum)==null){
				toSheet.createRow(rowNum);
			}
			toSheet.getRow(rowNum).setHeight(height);
		}*/
		for (Iterator rowIt = fromSheet.rowIterator(); rowIt.hasNext();) {
			Row tmpRow = (Row) rowIt.next();
			Row newRow = toSheet.getRow(tmpRow.getRowNum());
			if(newRow==null){
				newRow=toSheet.createRow(tmpRow.getRowNum());
			}
			newRow.setHeight(tmpRow.getHeight());
			// 行复制
			copyRow(wb, tmpRow, newRow, copyValueFlag);
		}
		
	}
	/**
	 * 行复制功能
	 * 
	 * @param fromRow
	 * @param toRow
	 */
	public static void copyRow(Workbook wb, Row fromRow, Row toRow, boolean copyValueFlag) {
		for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext();) {
			Cell tmpCell = (Cell) cellIt.next();
			Cell newCell = toRow.createCell(tmpCell.getColumnIndex());
			copyCell(wb, tmpCell, newCell, copyValueFlag);
		}
	}

	/**
	 * 复制原有sheet的合并单元格到新创建的sheet
	 * 
	 * @param sheetCreat
	 *            新创建sheet
	 * @param sheet
	 *            原有的sheet
	 */
	public static void mergerRegion(Sheet fromSheet, Sheet toSheet) {
		int sheetMergerCount = fromSheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergerCount; i++) {
			CellRangeAddress mergedRegionAt = fromSheet.getMergedRegion(i);
			toSheet.addMergedRegion(mergedRegionAt);
		}
	}

	/**
	 * 复制单元格
	 * 
	 * @param srcCell
	 * @param distCell
	 * @param copyValueFlag
	 *            true则连同cell的内容一起复制
	 */
	public static void copyCell(Workbook wb, Cell srcCell, Cell distCell, boolean copyValueFlag) {
		CellStyle newstyle = wb.createCellStyle();
		newstyle.cloneStyleFrom(srcCell.getCellStyle());
		distCell.setCellStyle(newstyle);
		// 不同数据类型处理
		CellType srcCellType = srcCell.getCellTypeEnum();
		if (copyValueFlag) {
			if (srcCellType == CellType.NUMERIC) {
				if (DateUtil.isCellDateFormatted(srcCell)) {
					distCell.setCellValue(srcCell.getDateCellValue());
				} else {
					distCell.setCellValue(srcCell.getNumericCellValue());
				}
			} else if (srcCellType == CellType.STRING) {
				distCell.setCellValue(srcCell.getRichStringCellValue());
			} else if (srcCellType == CellType.BLANK) {
				// nothing21
			} else if (srcCellType == CellType.BOOLEAN) {
				distCell.setCellValue(srcCell.getBooleanCellValue());
			} else if (srcCellType == CellType.ERROR) {
				distCell.setCellErrorValue(srcCell.getErrorCellValue());
			} else if (srcCellType == CellType.FORMULA) {
				distCell.setCellFormula(srcCell.getCellFormula());
			} else { // nothing29
			}
		}
	}

	/**
	 * 找到需要插入的行数，并新建一个POI的row对象
	 * 
	 * @param sheet
	 * @param rowIndex
	 * @return
	 */
	public static Row createRow(Sheet sheet, int rowIndex) {
		Row row = null;
		if (sheet.getRow(rowIndex) != null) {
			int lastRowNo = sheet.getLastRowNum();
			//将rowIndex到lastRowNo之间的所有行全部向下移动1行
			//copyRowHeight表示是否将待移动的行的行高一起移动
			//resetOriginalRowHeight 原来的行移动后,原来行所占的行高是否恢复默认行高
			sheet.shiftRows(rowIndex, lastRowNo, 1, true, true);
		}
		row = sheet.createRow(rowIndex);
		return row;
	}
	public static void main(String[] args) {
		System.out.println("合同单据_389A!$A$1:$XFD$8".split("\\!\\$A\\$")[1]);
	}
}
