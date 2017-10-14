package com.singsong.erp.component.docment.excel;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;

import com.singsong.erp.base.util.CommonLogger;
import com.singsong.erp.base.util.CommonUtils;
import com.singsong.erp.framework.init.ReadConf;
import com.singsong.erp.test.ImageCompressUtil;

public class BillExcel {
	 private static final Logger logger = CommonLogger.getLogger(BillExcel.class); 
	 private static final Pattern PATTERN = Pattern.compile("\\##(.*?)\\##");
	 public static void main(String[] args) {
		 try {
/*			String templateFile="d:/dom_purchasecontract.xlsx";
			InputStream inputStream=new FileInputStream(new File(templateFile));
			Workbook wb = WorkbookFactory.create(inputStream);
			inputStream.close(); 
			for(ReadExcelInfo readExcelInfo:BillExcel.readBillTemp(wb)){
				List<CellPosition> list=readExcelInfo.getMasterCellPostList();
				for(CellPosition obj:list){
					System.out.println(obj.toString());
				}
			}*/
			 double cw=100;
			 double ch=40;
			 double iw=230;
			 double ih=200;
			 double array[]=BillExcel.calculatePicResize(cw, ch, iw, ih);
			 System.out.println(array[0]);
			 System.out.println(array[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 /**
	  * 
	  * @param wb
	  * @param cidSheetNameMap
	  * @param groupFlag 是否是组模板
	  * @return
	  * @throws Exception
	  */
	// 读取单据模板中的数据信息与单元格信息
	public static List<ReadExcelInfo> readBillTemp(Workbook wb,Map<String,String> cidSheetNameMap,boolean groupFlag) throws Exception{
		// 先计算excel模板上面的sheet模板数
		ReadExcelInfo readExcelInfo = new ReadExcelInfo();
		int sheetIndex = 0;
		Sheet sheet = wb.getSheetAt(sheetIndex);
		String templateSheetName = sheet.getSheetName();
		int lastRowNum = sheet.getLastRowNum();
		for (int rowIndex = 0; rowIndex <= lastRowNum; rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				int lastCellNum = row.getLastCellNum();
				for (int cellIndex = 0; cellIndex <= lastCellNum; cellIndex++) {
					Cell cell = row.getCell(cellIndex);
					if (cell != null) {
						String value = getCellValue(cell);
						CellPosition cellPosition = matcherDataInfo(value);
						if (cellPosition != null) {
							cellPosition.setSheetIndex(sheetIndex);
							cellPosition.setRowIndex(rowIndex);
							cellPosition.setCellIndex(cellIndex);
							// 判断与设置主表
							if (readExcelInfo.getMasterName() == null
									&& StringUtils.equals(cellPosition.getEt(), "sv")) {
								readExcelInfo.setMasterName(cellPosition.getSource());
							}
							// 判断与设置从表
							if (readExcelInfo.getSlaveName() == null
									&& StringUtils.equals(cellPosition.getEt(), "mv")) {
								readExcelInfo.setSlaveName(cellPosition.getSource());
							}
							String key = cellPosition.getEt();
							switch (key) {
							case "sv":
								readExcelInfo.getMasterCellPostList().add(cellPosition);
								break;
							case "mv":
								readExcelInfo.getSlaveCellPostList().add(cellPosition);
								// 设置模板中从表的startRowIndex,endRowIndex
								// 这里的0是int类型的默认值,当不为0时候就说明已经取到第一行
								if (readExcelInfo.getStartRowIndex() == 0) {
									readExcelInfo.setStartRowIndex(rowIndex);
								}
								// 每次都将mv条件下面的rowIndex设置成最后一行,当真正的最后一行没有的时候,这个设置的值就是最后一行了
								readExcelInfo.setEndRowIndex(rowIndex);
								break;
							default:
								break;
							}
						}
					}
				}
			}
		}
		//设置打印
		if(wb.getPrintArea(0)!=null){
			String printArea=wb.getPrintArea(0).split("\\!")[1];
			readExcelInfo.setPrintArea(printArea);
		}
		PrintSetup srcPrintSetup =sheet.getPrintSetup();
		readExcelInfo.setPrintSetup(srcPrintSetup);
		List<? extends Name> nameList=wb.getAllNames();
		for(Name nameArea:nameList){
			String name =nameArea.getNameName();
			if(name.endsWith("Print_Titles")){
				String array[]=printTitles(nameArea);
				if(array[0]!=null){
					CellRangeAddress cra = CellRangeAddress.valueOf(array[0]);
					readExcelInfo.setRowRangAddress(cra);
				}
				if(array[1]!=null){
					CellRangeAddress cra = CellRangeAddress.valueOf(array[1]);
					readExcelInfo.setColumnRangAddress(cra);
				}
			}
			if(name.endsWith("Print_Titles")){
				String[] formula = nameArea.getRefersToFormula().split(",");
			}
		}
		// 存储sheet到内存中
		readExcelInfo.setSheet(sheet);
		//设置图片区域
		List<Picture> pictureList=findAnchorList(readExcelInfo.getSheet());
		readExcelInfo.setPictureList(pictureList);
		List<ReadExcelInfo> returnList = new ArrayList<ReadExcelInfo>();
		if(groupFlag==false){
			//这里设置业务参数
			Iterator<String> it=cidSheetNameMap.keySet().iterator();
			String key=it.next();
			readExcelInfo.setBusinessValue(key);
			readExcelInfo.setSheetName(cidSheetNameMap.get(key));
			returnList.add(readExcelInfo);
		}
		else{
			boolean firstSheet = false;
			Iterator<Entry<String, String>> entries = cidSheetNameMap.entrySet().iterator();
			// 在当前模板中根据子ID数据参数克隆sheet
			while (entries.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) entries.next();
				String cid = entry.getKey();
				String sheetName = entry.getValue();
				if (firstSheet == false) {
					readExcelInfo.setSheetName(templateSheetName + "_" + sheetName);
					wb.setSheetName(0, templateSheetName + "_" + sheetName);
					readExcelInfo.setBusinessValue(cid);
					firstSheet = true;
					returnList.add(readExcelInfo);
				} else {
					Sheet cloneSheet = wb.cloneSheet(0);
					int index=wb.getSheetIndex(cloneSheet);
//					wb.setPrintArea(index,readExcelInfo.getPrintArea());
					//一定要先设置打印缩放,再设置打印标题行,不然打印缩放出不来
					cloneSheet.getPrintSetup().setPaperSize(readExcelInfo.getPrintSetup().getPaperSize());
					cloneSheet.getPrintSetup().setScale(readExcelInfo.getPrintSetup().getScale());
					cloneSheet.setRepeatingColumns(readExcelInfo.getColumnRangAddress());
					cloneSheet.setRepeatingRows(readExcelInfo.getRowRangAddress());
					
					wb.setSheetName(wb.getSheetIndex(cloneSheet), templateSheetName + "_" + sheetName);
					ReadExcelInfo readExcelInfoClone = (ReadExcelInfo) BeanUtils.cloneBean(readExcelInfo);
					readExcelInfoClone.setSheet(cloneSheet);
					readExcelInfoClone.setBusinessValue(cid);
					readExcelInfoClone.setSheetName(templateSheetName + "_" + sheetName);
					readExcelInfoClone.setPictureList(pictureList);
					returnList.add(readExcelInfoClone);
				}
			}
		}
		return returnList;
	}
	/**
	 * 有多个子ID的情况,多个Excel模板
	 * @param wb
	 * @param infoList
	 * @throws Exception
	 */
	// 根据模板生成相应单据
	public static void initDataToExcel(Workbook wb,CreationHelper helper,List<ReadExcelInfo> infoList) throws Exception {
		for (ReadExcelInfo readExcelInfo : infoList) {
			String emptyText="";
			String params = "params:" + "sheetName=" + readExcelInfo.getSheetName() + ",businessValue="+ readExcelInfo.getBusinessValue() + ",";
			long startTime = System.currentTimeMillis();
			
			//重新生成新的
			Sheet sheet = wb.createSheet(readExcelInfo.getSheetName());
			PoiUtils.copySheet(wb, readExcelInfo.getSheet(), sheet, true);
			
			//这里就用模板上面的那个sheet,是在模板上面生成
//			Sheet sheet=readExcelInfo.getSheet();
			// 设置主表数据到模板中
			List<CellPosition> masterCellPostList = readExcelInfo.getMasterCellPostList();
			Map<String, Object> masterDataMap = readExcelInfo.getMasterData();
			for (CellPosition cellPosition : masterCellPostList) {
				//在读取模板的时候就把主表的CellPosition确定了,但是现在新增了中间的从表的数据行,如果CellPosition的行在从表开始行前面的,则不做处理
				//如果在从表开始行后面的主表CellPosition,那这个CellPosition的行坐标需要再加上中间数据表的总行数
				int rowIndex=cellPosition.getRowIndex();
				if(rowIndex>readExcelInfo.getStartRowIndex()){
					rowIndex=rowIndex+(readExcelInfo.getEndRowIndex()-readExcelInfo.getStartRowIndex()+1)*(readExcelInfo.getSlaveDataList().size()-1);
				}
				Cell cell = sheet.getRow(rowIndex).getCell(cellPosition.getCellIndex());
				String fielname = cellPosition.getFieldname();
				String value = CommonUtils.getColumnValue(masterDataMap, fielname);
				if (StringUtils.isEmpty(value)) {
					 cell.setCellValue(emptyText);
					 logger.info("这个字段没有数据,fieldname="+fielname);
				} else {
					SetCellValue(cell,value);
				}
			}

			Drawing<?> drawing = sheet.createDrawingPatriarch();
			// 设置从表的数据值
			List<CellPosition> slaveCellPostList = readExcelInfo.getSlaveCellPostList();
			List<Map<String, Object>> slaveDataList = readExcelInfo.getSlaveDataList();
			int n1 = readExcelInfo.getStartRowIndex();
			int n2 = readExcelInfo.getEndRowIndex();
			// 计算出要遍历的行数
			int count = n2 - n1 + 1;
			for (int m = 0; m < slaveDataList.size(); m++) {
				Map<String, Object> slaveDataMap = slaveDataList.get(m);
				// 设置单元格值
				for (CellPosition cellPosition : slaveCellPostList) {
					int rowIndex = cellPosition.getRowIndex();
					rowIndex = rowIndex + m * count;// 算出距离,同时有几个大的循环
					Cell cell = sheet.getRow(rowIndex).getCell(cellPosition.getCellIndex());
					String fielname = cellPosition.getFieldname();
					String value = CommonUtils.getColumnValue(slaveDataMap, fielname);
					if (StringUtils.isEmpty(value)) {
						cell.setCellValue(emptyText);
						 logger.info("这个字段没有数据,fieldname="+fielname);
					} else {
						if(cell.getColumnIndex()==3 && rowIndex==n2){
							value="d.jpg";
						}
						if(value.endsWith("jpeg") || value.endsWith("jpg")){
							insertPic(wb,sheet,helper,drawing,cell,value);
						}
						else{
							SetCellValue(cell,value);
						}
					}
				}
			}
			//把以前的图片插入到新的sheet中
			int shiftRowCount=count*(slaveDataList.size()-1);
			List<Picture> pictureList=readExcelInfo.getPictureList();
			for(Picture pic:pictureList){
					byte[] bytes = pic.getPictureData().getData();
					int pictureIndex = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
					ClientAnchor srcAnchor=pic.getClientAnchor();
					ClientAnchor anchor = helper.createClientAnchor();
					anchor.setDx1(srcAnchor.getDx1());
					anchor.setDx2(srcAnchor.getDx2());
					anchor.setDy1(srcAnchor.getDy1());
					anchor.setDy2(srcAnchor.getDy2());
					anchor.setCol1(srcAnchor.getCol1());
					anchor.setCol2(srcAnchor.getCol2());
					int row1=srcAnchor.getRow1();
					int row2=srcAnchor.getRow2();
					if(row1>readExcelInfo.getStartRowIndex()){
						anchor.setRow1(row1+shiftRowCount);
						anchor.setRow2(row2+shiftRowCount);
					}
					//下面这两个方法就是计算单元格的长高(单位:像素)
					drawing.createPicture( anchor, pictureIndex);
			}
			//设置页面、打印信息
			pageConfig(wb,sheet,readExcelInfo);
			logger.info(params+"加载数据到模板中,耗费时间:"+(System.currentTimeMillis()-startTime));
		}
	}
	/**
	 * 单个主ID值,单个sheet模板
	 * @param wb
	 * @return
	 * @throws Exception
	 */
	// 读取单据模板中的数据信息与单元格信息
	public static List<ReadExcelInfo> readBillTemp(Workbook wb) throws Exception{
		List<ReadExcelInfo> list = new ArrayList<ReadExcelInfo>();
		ReadExcelInfo readExcelInfo = new ReadExcelInfo();
		int sheetIndex = 0;
		Sheet sheet = wb.getSheetAt(sheetIndex);
		int lastRowNum = sheet.getLastRowNum();
		for (int rowIndex = 0; rowIndex <= lastRowNum; rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				int lastCellNum = row.getLastCellNum();
				for (int cellIndex = 0; cellIndex <= lastCellNum; cellIndex++) {
					Cell cell = row.getCell(cellIndex);
					if (cell != null) {
						String value = getCellValue(cell);
						CellPosition cellPosition = matcherDataInfo(value);
						if (cellPosition != null) {
							cellPosition.setSheetIndex(sheetIndex);
							cellPosition.setRowIndex(rowIndex);
							cellPosition.setCellIndex(cellIndex);
							// 判断与设置主表
							if (readExcelInfo.getMasterName() == null
									&& StringUtils.equals(cellPosition.getEt(), "sv")) {
								readExcelInfo.setMasterName(cellPosition.getSource());
							}
							// 判断与设置从表
							if (readExcelInfo.getSlaveName() == null
									&& StringUtils.equals(cellPosition.getEt(), "mv")) {
								readExcelInfo.setSlaveName(cellPosition.getSource());
							}
							String key = cellPosition.getEt();
							switch (key) {
							case "sv":
								readExcelInfo.getMasterCellPostList().add(cellPosition);
								break;
							case "mv":
								readExcelInfo.getSlaveCellPostList().add(cellPosition);
								// 设置模板中从表的startRowIndex,endRowIndex
								// 这里的0是int类型的默认值,当不为0时候就说明已经取到第一行
								if (readExcelInfo.getStartRowIndex() == 0) {
									readExcelInfo.setStartRowIndex(rowIndex);
								}
								// 每次都将mv条件下面的rowIndex设置成最后一行,当真正的最后一行没有的时候,这个设置的值就是最后一行了
								readExcelInfo.setEndRowIndex(rowIndex);
								break;
							default:
								break;
							}
						}
					}
				}
			}
		}
		readExcelInfo.setSheetName(sheet.getSheetName());
		list.add(readExcelInfo);
		return list;
	}
	// 根据模板生成相应单据
	public static void initDataToExcel2(Workbook wb,List<ReadExcelInfo> infoList) throws Exception {
		for (ReadExcelInfo readExcelInfo : infoList) {
			String params = "params:" + "sheetName=" + readExcelInfo.getSheetName() + ",businessValue="+ readExcelInfo.getBusinessValue() + ",";
			long startTime = System.currentTimeMillis();
			// 判断sheet是否存在
			Sheet sheet = wb.getSheet(readExcelInfo.getSheetName());
			if (sheet == null) {
				sheet = wb.createSheet(readExcelInfo.getSheetName());
			}
			if (readExcelInfo.getSheet() != null) {
				PoiUtils.copySheet(wb, readExcelInfo.getSheet(), sheet, true);
			}
			// 设置主表数据到模板中
			List<CellPosition> masterCellPostList = readExcelInfo.getMasterCellPostList();
			Map<String, Object> masterDataMap = readExcelInfo.getMasterData();
			for (CellPosition cellPosition : masterCellPostList) {

				if (sheet.getRow(cellPosition.getRowIndex()) == null) {
					sheet.createRow(cellPosition.getRowIndex());
				}
				if (sheet.getRow(cellPosition.getRowIndex()).getCell(cellPosition.getCellIndex()) == null) {
					sheet.getRow(cellPosition.getRowIndex()).createCell(cellPosition.getCellIndex());
				}

				Cell cell = sheet.getRow(cellPosition.getRowIndex()).getCell(cellPosition.getCellIndex());
				String fielname = cellPosition.getFieldname();
				String value = CommonUtils.getColumnValue(masterDataMap, fielname);
				if (StringUtils.isEmpty(value)) {
					cell.setCellValue("");
					logger.info("这个字段没有数据,fieldname=" + fielname);
				} else {
					cell.setCellValue(value);
				}
			}
			// 先新增模板中的从表的表格
			int n1 = readExcelInfo.getStartRowIndex();
			int n2 = readExcelInfo.getEndRowIndex();
			// 计算出要遍历的行数
			int count = n2 - n1 + 1;
			List<Map<String, Object>> slaveDataList = readExcelInfo.getSlaveDataList();
			// 开始与结束是同一行,slaveDataList.size()-1这里的减去1是因为第1轮数据表格不用创建
			if (count == 1) {
				for (int m = 0; m < slaveDataList.size() - 1; m++) {
					Row srcRow = sheet.getRow(n1);
					Row row = PoiUtils.createRow(sheet, n1 + 1);
					PoiUtils.copyRow(wb, srcRow, row, true);
					n1 = n1 + 1;
				}
			} else {
				for (int m = 0; m < slaveDataList.size() - 1; m++) {
					for (int i = 1; i <= count; i++) {
						Row srcRow = sheet.getRow(n1 + i - 1);
						Row row = PoiUtils.createRow(sheet, n2 + i);
						PoiUtils.copyRow(wb, srcRow, row, true);
					}
					n2 = n2 + count;
				}
			}
			// 设置从表的数据值
			List<CellPosition> slaveCellPostList = readExcelInfo.getSlaveCellPostList();
			for (int m = 0; m < slaveDataList.size(); m++) {
				Map<String, Object> slaveDataMap = slaveDataList.get(m);
				// 设置单元格值
				for (CellPosition cellPosition : slaveCellPostList) {
					int rowIndex = cellPosition.getRowIndex();
					rowIndex = rowIndex + m * count;// 算出距离,同时有几个大的循环
					if (sheet.getRow(rowIndex) == null) {
						sheet.createRow(rowIndex);
					}
					if (sheet.getRow(rowIndex).getCell(cellPosition.getCellIndex()) == null) {
						sheet.getRow(rowIndex).createCell(cellPosition.getCellIndex());
					}
					Cell cell = sheet.getRow(rowIndex).getCell(cellPosition.getCellIndex());
					String fielname = cellPosition.getFieldname();
					String value = CommonUtils.getColumnValue(slaveDataMap, fielname);
					if (StringUtils.isEmpty(value)) {
						cell.setCellValue("");
						logger.info("这个字段没有数据,fieldname=" + fielname);
					} else {
						cell.setCellValue(value);
					}
				}

			}
			logger.info(params + "加载数据到模板中,耗费时间:" + (System.currentTimeMillis() - startTime));
		}
	}
	private static CellPosition matcherDataInfo(String source) throws Exception{
		CellPosition cellPosition=null;
		if(StringUtils.isNotEmpty(source)){
			source=source.trim(); 
			Matcher matcher = PATTERN.matcher(source);
	            String patStr="";
	            while(matcher.find()){
	            	cellPosition=new CellPosition();
	            	 patStr=matcher.group(1);
	                 String elementArray[]=patStr.split("\\,",4);
	                 for(String ele:elementArray){
	                     String mapArray[]=ele.split("\\=",2);
	                     String key=mapArray[0];
	                     String value=mapArray[1];
	                     switch (key) {
						case "et":
							cellPosition.setEt(value);
							break;
						case "source":
							cellPosition.setSource(value);
							break;
						case "fieldname":
							cellPosition.setFieldname(value);
							break;
						case "fieldtype":
							cellPosition.setFieldtype(value);
							break;							
						default:
							String errMsg="模板中的数据设置格式不对,cellValue="+source;
							logger.error(errMsg);
							throw new Exception(errMsg);
						}
	                 }
	            }
		}
		return cellPosition;
	}
	private static String getCellValue(Cell cell) {
		if (cell.getCellTypeEnum() == CellType.NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else if (cell.getCellTypeEnum() == CellType.STRING) {
			return String.valueOf(cell.getStringCellValue());
		} else if (cell.getCellTypeEnum() == CellType.FORMULA) {
			return String.valueOf(cell.getCellFormula());
		} else if (cell.getCellTypeEnum() == CellType.BLANK) {
			return null;
		} else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}
	private static void SetCellValue(Cell cell,String value) {
		//为空不要设置值
		if(StringUtils.isNotEmpty(value)){
			CellStyle style=cell.getCellStyle();
			short formatType=style.getDataFormat();
			String formatStr=style.getDataFormatString();
			if(formatType==31 && formatStr.equals("reserved-0x1f")){
				cell.setCellValue(DateTime.parse(value.substring(0,value.length()-2), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate());
			}
			else if (formatStr.contains("mmmm\\ d\\,\\ yyyy")){
				cell.setCellValue(DateTime.parse(value.substring(0,value.length()-2), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate());
			}
			else if(formatStr.startsWith("\\¥#") || formatStr.startsWith("\\$#") || formatStr.startsWith("\\US$#")){
				BigDecimal b = new BigDecimal(value);
				double d = b.intValue();
				cell.setCellValue(d);
			}
			else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
				BigDecimal b = new BigDecimal(value);
				double d = b.intValue();
				cell.setCellValue(d);
			} else {
				cell.setCellValue(value);
			}
		}
	}
	private static List<Picture> findAnchorList(Sheet sheet){
		List<Picture> list=new ArrayList<Picture>();
		// 处理sheet中的图形
		Drawing<?> hssfPatriarch = sheet.getDrawingPatriarch();
		if(hssfPatriarch!=null){
			// 获取所有的形状图
			Iterator<?> shapesIt = hssfPatriarch.iterator();
			while(shapesIt.hasNext()){
				Object shape=shapesIt.next();
				if(shape instanceof Picture){
					Picture picture = (Picture) shape;
					picture.getPictureData().getData();
					list.add(picture);
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param cw 单元格长
	 * @param ch 单元格高
	 * @param iw 图片长
	 * @param ih 图片高
	 * @return
	 */
	private static double[] calculatePicResize(double cw, double ch, double iw, double ih) {
		// 因为这里的图片的长、高一定是都大于单元格长、高的,其他情况下面的计算不适用
		double wr = 0.0;
		double hr = 0.0;
		if (cw >= ch) {
			hr = 1.0;// 求cr
			double temp = (ch * hr * iw) / (cw * ih);
			BigDecimal bd = new BigDecimal(temp);
			wr = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		} else {
			wr = 1.0;// 求hr
			double temp = (cw * wr * ih) / (ch * iw);
			BigDecimal bd = new BigDecimal(temp);
			hr = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		double[] anArray = { wr, hr };
		return anArray;
	}
	/**
	 * 插入图片
	 * @param wb
	 * @param sheet
	 * @param helper
	 * @param drawing
	 * @param cell
	 * @param picPath
	 */
	private static void insertPic(Workbook wb,Sheet sheet,CreationHelper helper,Drawing<?> drawing,Cell cell,String picPath)throws Exception{
		String baseDir=ReadConf.getCommonProperty("bill_excel_image_dir");
		String fileName =baseDir+picPath;
		
        Image src = ImageIO.read(new File(fileName));
        int srcHeight = src.getHeight(null);
        int srcWidth = src.getWidth(null);
		
		double cellWidth =sheet.getColumnWidthInPixels(cell.getColumnIndex());
		double cellHeight=(cell.getRow().getHeightInPoints()/72)*96;
		double srcScale = (double) srcHeight / srcWidth;		
		double comBase;
		if(cellWidth>=cellHeight){
			comBase=cellHeight;
		}
		else{
			comBase=srcScale*cellWidth;
		}
		String ext="_small";
		String midName = baseDir + picPath.substring(0, picPath.indexOf(".")) + ext + picPath.substring(picPath.indexOf("."));
		ImageCompressUtil.saveMinPhoto(fileName,midName, comBase,srcScale);
		
		InputStream is = new FileInputStream(midName);
		byte[] bytes = IOUtils.toByteArray(is);
		int pictureIndex = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setCol1(cell.getColumnIndex());
		anchor.setCol2(cell.getColumnIndex());
		anchor.setRow1(cell.getRowIndex());
		anchor.setRow2(cell.getRowIndex());
		Picture pict = drawing.createPicture( anchor, pictureIndex);
		//下面这两个方法就是计算单元格的长高(单位:像素)
//		double cellWidth =sheet.getColumnWidthInPixels(cell.getColumnIndex());
//		double cellHeight=(cell.getRow().getHeightInPoints()/72)*96;
		double imgWidth=pict.getImageDimension().getWidth();
		double imgHeight=pict.getImageDimension().getHeight();
		//图片的长高都大于单元格的长高
		double array[]=BillExcel.calculatePicResize(cellWidth, cellHeight, imgWidth, imgHeight);
//		pict.resize(array[0],array[1]);
		pict.resize();
	}
	
	private static void pageConfig(Workbook wb,Sheet disSheet,ReadExcelInfo readExcelInfo){
		//设置页眉
		Header srcHeader=readExcelInfo.getSheet().getHeader();
		Header header=disSheet.getHeader();
		header.setCenter(srcHeader.getCenter());
		header.setLeft(srcHeader.getLeft());
		header.setRight(srcHeader.getRight());
		//设置页脚
		Footer srcFooter=readExcelInfo.getSheet().getFooter();
		Footer footer=disSheet.getFooter();
		footer.setCenter(srcFooter.getCenter());
		footer.setLeft(srcFooter.getLeft());
		footer.setRight(srcFooter.getRight());
		//设置打印
		PrintSetup srcPrintSetup=readExcelInfo.getSheet().getPrintSetup();
		PrintSetup printSetup=disSheet.getPrintSetup();
//		printSetup.setCopies(srcPrintSetup.getCopies());
//		printSetup.setDraft(srcPrintSetup.getDraft());
//		printSetup.setFitHeight(srcPrintSetup.getFitHeight());
//		printSetup.setFitWidth(srcPrintSetup.getFitWidth());
//		printSetup.setFooterMargin(srcPrintSetup.getFooterMargin());
//		printSetup.setHeaderMargin(srcPrintSetup.getHeaderMargin());
//		printSetup.setHResolution(srcPrintSetup.getHResolution());
		printSetup.setLandscape(srcPrintSetup.getLandscape());// 打印方向，true：横向，false：纵向 
//		printSetup.setLeftToRight(srcPrintSetup.getLeftToRight());
//		printSetup.setNoColor(srcPrintSetup.getNoColor());
//		printSetup.setNoOrientation(srcPrintSetup.getNoOrientation());
//		printSetup.setNotes(srcPrintSetup.getNotes());
//		printSetup.setPageStart(srcPrintSetup.getPageStart());
//		printSetup.setUsePage(srcPrintSetup.getUsePage());
//		printSetup.setValidSettings(srcPrintSetup.getValidSettings());
//		printSetup.setVResolution(srcPrintSetup.getVResolution());
		
		printSetup.setPaperSize(srcPrintSetup.getPaperSize());//打印纸张
		printSetup.setScale(srcPrintSetup.getScale());//打印缩放比例
		//设置页边距
		disSheet.setMargin(Sheet.LeftMargin, readExcelInfo.getSheet().getMargin(Sheet.LeftMargin));
		disSheet.setMargin(Sheet.RightMargin, readExcelInfo.getSheet().getMargin(Sheet.RightMargin));
		disSheet.setMargin(Sheet.TopMargin, readExcelInfo.getSheet().getMargin(Sheet.TopMargin));
		disSheet.setMargin(Sheet.BottomMargin, readExcelInfo.getSheet().getMargin(Sheet.BottomMargin));
		disSheet.setHorizontallyCenter(readExcelInfo.getSheet().getHorizontallyCenter());//设置打印页面为水平居中  
		disSheet.setVerticallyCenter(readExcelInfo.getSheet().getVerticallyCenter());//设置打印页面为垂直居中 
		
		int index=wb.getSheetIndex(disSheet);
		if(readExcelInfo.getPrintArea()!=null){
			wb.setPrintArea(index,readExcelInfo.getPrintArea());//设置打印区域
		}
		//一定要先设置打印缩放,再设置打印标题行,不然打印缩放出不来
		disSheet.setRepeatingColumns(readExcelInfo.getColumnRangAddress());//设置打印标题行
		disSheet.setRepeatingRows(readExcelInfo.getRowRangAddress());//设置打印标题列
	}
	
	private static String[] printTitles(Name nameArea) {
		String array[] = new String[2];
		String[] formula = nameArea.getRefersToFormula().split(",");
		for (int i = 0; i < formula.length; i++) {
			AreaReference[] arefs = AreaReference.generateContiguous(SpreadsheetVersion.EXCEL2007, formula[i]);
			// 顶端标题行
			if (i == 0) {
				int firstTitleRow = arefs[0].getFirstCell().getRow() + 1;
				int lastTitleRow = arefs[0].getLastCell().getRow() + 1;
				array[i] = "$" + firstTitleRow + ":" + "$" + lastTitleRow;
			}
			// 左端标题列
			if (i == 1) {
				String firstTitleCol = arefs[0].getFirstCell().getCellRefParts()[2];
				String lastTitleCol = arefs[0].getLastCell().getCellRefParts()[2];
				array[i] = "$" + firstTitleCol + ":" + "$" + lastTitleCol;
			}
		}
		return array;
	}
}
