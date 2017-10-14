package com.singsong.erp.business.api.email.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontScheme;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.singsong.erp.base.util.CommonLogger;
import com.singsong.erp.base.util.CommonUtils;
import com.singsong.erp.base.vo.AjaxResult;
import com.singsong.erp.component.docment.excel.BillExcel;
import com.singsong.erp.component.docment.excel.PoiUtils;
import com.singsong.erp.component.docment.excel.ReadExcelInfo;
import com.singsong.erp.framework.init.ReadConf;

@Controller
@RequestMapping("/export")
public class BillCreateAction {
	private static final Logger logger = CommonLogger.getLogger(BillCreateAction.class); 
    @Autowired
    private JdbcTemplate jdbcTemplateTest;
	/**
	 * 单个主ID值,多个sheet模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/singleTest")
	@ResponseBody
	public void singleTest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info(new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
	/*	String templateNum=request.getParameter("templateNum");
		String businessValue=request.getParameter("businessValue");*/
		String templateNum="19";
		String businessValue="83232197-CCDD-4FCC-AC6E-88D9545D7AE8";
		String excelName = "测试";
		
		//key businessValue,value 是sheetName
		Map<String,String> cidSheetNameMap=new LinkedHashMap<String,String>();
		cidSheetNameMap.put(businessValue,"单据");

		//根据模板ID查询模板的路径
		List<Map<String, Object>> templateList=jdbcTemplateTest.queryForList("select * from entity_print_new where autoinc="+templateNum);
		if(templateList.size()!=1){
			throw new Exception("单据Excel模板查询错误");
		}
		String baseDir=ReadConf.getCommonProperty("bill_excel_template_dir");
		String templateFile=baseDir+CommonUtils.getColumnValue(templateList.get(0), "templatefilename");
		InputStream inputStream=new FileInputStream(new File(templateFile));
		Workbook wb = WorkbookFactory.create(inputStream);
		
		List<ReadExcelInfo> infoList=singleTemplate(wb,templateNum,cidSheetNameMap);
		
		//遍历生成数据到模板中
//		BillExcel.initDataToExcel(wb, infoList);
		
		FileOutputStream out = new FileOutputStream(new File("d:/"+excelName+".xlsx"));
		wb.write(out);
		out.close();
		wb.close();
		logger.info(new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
	}
	@RequestMapping("/groupTest")
	@ResponseBody
	public void groupTest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info(new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
		String excelName = "测试";
		String type = "001";
		String businessValue = "F6EA803A-3C64-4731-924F-9B3B35C7E6EC";
		List<ReadExcelInfo> infoList = new ArrayList<ReadExcelInfo>();
		// 根据这个type判断是否需要查询子ID数据值
		if (StringUtils.isNotEmpty(type)) {
			// 查询父数据相关的模板信息
			List<Map<String, Object>> parentDataTmeplateList = jdbcTemplateTest
					.queryForList("select * from entity_print_data_template where type='" + type + "' order by sort");
			for (Map<String, Object> parentDataTmeplate : parentDataTmeplateList) {
				String sheetCol = CommonUtils.getColumnValue(parentDataTmeplate, "sheetname");
				String filterCol = CommonUtils.getColumnValue(parentDataTmeplate, "keyName");
				String cidCol = CommonUtils.getColumnValue(parentDataTmeplate, "cidName");
				String sql = CommonUtils.getColumnValue(parentDataTmeplate, "sqlStr");

				// 构建查询子数据值的SQL
				sql = "select * from (" + sql + ") t where " + filterCol + "='" + businessValue + "'";
				List<Map<String, Object>> cidMapList = jdbcTemplateTest.queryForList(sql);
				Map<String, String> cidSheetNameMap = new LinkedHashMap<String, String>();
				for (Map<String, Object> map : cidMapList) {
					cidSheetNameMap.put(CommonUtils.getColumnValue(map, cidCol),CommonUtils.getColumnValue(map, sheetCol));
				}
				String templateNum = CommonUtils.getColumnValue(parentDataTmeplate, "cid");// 模板ID
				List<ReadExcelInfo> tempList = singleTemplate(templateNum, cidSheetNameMap,true);
				infoList.addAll(tempList);
			}
		}
		
		Workbook wb = new XSSFWorkbook();
		// 遍历生成数据到模板中
//		BillExcel.initDataToExcel(wb, infoList);
		
		//导出excel
/*		OutputStream out = response.getOutputStream();
		String name = new String(excelName.getBytes("utf-8"), "ISO-8859-1");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.addHeader("Content-Disposition", "attachment;filename=\"" + name + ".xlsx");*/
		
		FileOutputStream out = new FileOutputStream(new File("d:/"+excelName+".xlsx"));
		wb.write(out);
		out.close();
		wb.close();
		logger.info(new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
	}	
	@RequestMapping("/single")
	@ResponseBody
	public void single(HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info(new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
	/*	String templateNum=request.getParameter("templateNum");
		String businessValue=request.getParameter("businessValue");*/
		String templateNum="19";
		String businessValue="83232197-CCDD-4FCC-AC6E-88D9545D7AE8";
		String excelName = "测试";
		
		//key businessValue,value 是sheetName
		Map<String,String> cidSheetNameMap=new LinkedHashMap<String,String>();
		cidSheetNameMap.put(businessValue,"单据");
		List<ReadExcelInfo> infoList=singleTemplate(templateNum,cidSheetNameMap,false);
		//遍历生成数据到模板中,同时真正的开始创建相关cell
		Workbook wb = new XSSFWorkbook();
		//遍历生成数据到模板中
		CreationHelper helper=wb.getCreationHelper();
		BillExcel.initDataToExcel(wb,helper, infoList);
		
		//导出excel
/*			OutputStream out = response.getOutputStream();
			String name = new String(excelName.getBytes("utf-8"), "ISO-8859-1");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=\"" + name + ".xlsx");*/
		
		FileOutputStream out = new FileOutputStream(new File("d:/"+excelName+".xlsx"));
		wb.write(out);
		out.close();
		wb.close();
		logger.info(new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
	}
	/**
	 * 有多个子ID的情况,多个Excel模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/group")
	@ResponseBody
	public String group(HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info(new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
//		Thread thread=new Thread(){
//			@Override
//			public void run(){
	            	try {
						Thread.sleep(4000);
						String excelName = "测试";
						String type = "001";
						String businessValue = "F6EA803A-3C64-4731-924F-9B3B35C7E6EC";
						List<ReadExcelInfo> infoList = new ArrayList<ReadExcelInfo>();
						// 根据这个type判断是否需要查询子ID数据值
						if (StringUtils.isNotEmpty(type)) {
							// 查询父数据相关的模板信息
							List<Map<String, Object>> parentDataTmeplateList = jdbcTemplateTest
									.queryForList("select * from entity_print_data_template where type='" + type + "' order by sort");
							for (Map<String, Object> parentDataTmeplate : parentDataTmeplateList) {
								String sheetCol = CommonUtils.getColumnValue(parentDataTmeplate, "sheetname");
								String filterCol = CommonUtils.getColumnValue(parentDataTmeplate, "keyName");
								String cidCol = CommonUtils.getColumnValue(parentDataTmeplate, "cidName");
								String sql = CommonUtils.getColumnValue(parentDataTmeplate, "sqlStr");

								// 构建查询子数据值的SQL
								sql = "select * from (" + sql + ") t where " + filterCol + "='" + businessValue + "'";
								List<Map<String, Object>> cidMapList = jdbcTemplateTest.queryForList(sql);
								Map<String, String> cidSheetNameMap = new LinkedHashMap<String, String>();
								for (Map<String, Object> map : cidMapList) {
									cidSheetNameMap.put(CommonUtils.getColumnValue(map, cidCol),CommonUtils.getColumnValue(map, sheetCol));
								}
								String templateNum = CommonUtils.getColumnValue(parentDataTmeplate, "cid");// 模板ID
								List<ReadExcelInfo> tempList = singleTemplate(templateNum, cidSheetNameMap,true);
								infoList.addAll(tempList);
							}
						}
						
						Workbook wb = new XSSFWorkbook();
						
  						//下面的这段代码是用于修改常规样式的
					    Font font = wb.getFontAt((short)0);
					    font.setFontHeightInPoints((short)12);
					    font.setFontName("宋体");
					    ((XSSFFont)font).setScheme(FontScheme.NONE);
					    CellStyle style = wb.getCellStyleAt(0);
					    style.setWrapText(true);
					    ((XSSFCellStyle)style).getStyleXf().addNewAlignment().setVertical(org.openxmlformats.schemas.spreadsheetml.x2006.main.STVerticalAlignment.CENTER);
					    ((XSSFCellStyle)style).getStyleXf().getAlignment().setWrapText(true);
						
						// 遍历生成数据到模板中
						CreationHelper helper = wb.getCreationHelper();
						BillExcel.initDataToExcel(wb,helper,infoList.subList(0, 4));
						
						//导出excel
						/*		OutputStream out = response.getOutputStream();
						String name = new String(excelName.getBytes("utf-8"), "ISO-8859-1");
						response.setContentType("application/vnd.ms-excel;charset=utf-8");
						response.addHeader("Content-Disposition", "attachment;filename=\"" + name + ".xlsx");*/
						File file=new File("d:/"+excelName+".xlsx");
						FileOutputStream out = new FileOutputStream(file);
						wb.write(out);
						out.close();
						wb.close();
					}  catch (Exception e) {
						logger.error("调用邮件发送任务出现异常",e);
					}
//	            }
//		};
//		thread.start();
        logger.info(new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
        return new AjaxResult().toJsonString();
	}	
	private List<ReadExcelInfo> singleTemplate(String templateNum,Map<String,String> cidSheetNameMap,boolean groupFlag) throws Exception{
		//根据模板ID查询模板的路径
		List<Map<String, Object>> templateList=jdbcTemplateTest.queryForList("select * from entity_print_new where autoinc="+templateNum);
		if(templateList.size()!=1){
			throw new Exception("单据Excel模板查询错误");
		}
		String baseDir=ReadConf.getCommonProperty("bill_excel_template_dir");
		String templateFile=baseDir+CommonUtils.getColumnValue(templateList.get(0), "templatefilename");
		InputStream inputStream=new FileInputStream(new File(templateFile));
		Workbook wb = WorkbookFactory.create(inputStream);
		//生成所有表格信息(没有实际生成),且给这些表格设置所有数据信息
		List<ReadExcelInfo> infoList=BillExcel.readBillTemp(wb,cidSheetNameMap,groupFlag);
		for(int i=0;i<infoList.size();i++){
			ReadExcelInfo readExcelInfo=infoList.get(i);
			String params="params:"+"sheetName="+readExcelInfo.getSheetName()+",businessValue"+readExcelInfo.getBusinessValue()+",";
			long startTime=System.currentTimeMillis();
			String tempSql="select * from entity_print_source s where commandid="+templateNum;
			List<Map<String, Object>> sourceList=jdbcTemplateTest.queryForList(tempSql);
			for(Map<String, Object> obj:sourceList){
				String dbsource=CommonUtils.getColumnValue(obj,"tablename");
				String sqlsource=CommonUtils.getColumnValue(obj,"sqlsource");
				String keyname= CommonUtils.getColumnValue(obj,"keyname");
				//构造好待查询的SQL语句与数据
				sqlsource="select * from ("+sqlsource+") t "; 
				String dataSql=sqlsource+" where "+keyname+"='"+readExcelInfo.getBusinessValue()+"'";
				String sortby=CommonUtils.getColumnValue(obj,"sortby");
				if(StringUtils.isNotEmpty(sortby)){
					dataSql=dataSql+" order by "+sortby;
				}
				List<Map<String, Object>> dataList=jdbcTemplateTest.queryForList(dataSql);
				//设置数据到主表集合
				if(StringUtils.equals(dbsource, readExcelInfo.getMasterName())){
					readExcelInfo.setMasterData(dataList.get(0));//主表只会有一条数据
				}
				//设置数据到从表集合
				if(StringUtils.equals(dbsource, readExcelInfo.getSlaveName())){
					readExcelInfo.setSlaveDataList(dataList);
					//遍历从表数据生成行表格,必须要先从表格模板中获取到相应的查询信息才能这样做
					Sheet sheet=readExcelInfo.getSheet();
					//先新增模板中的从表的表格
					int n1=readExcelInfo.getStartRowIndex();
					int n2=readExcelInfo.getEndRowIndex();
					//计算出要遍历的行数
					int count=n2-n1+1;
					List<Map<String, Object>> slaveDataList=readExcelInfo.getSlaveDataList();
					//开始与结束是同一行,slaveDataList.size()-1这里的减去1是因为第1轮数据表格不用创建
					int shiftRowCount=count*(slaveDataList.size()-1);
					//这里判断是否需新增表格
					if(shiftRowCount>0){
						//移动行
						int lastRowNo = sheet.getLastRowNum();
						List<CellRangeAddress> originMerged = sheet.getMergedRegions();
						sheet.shiftRows(n1+count, lastRowNo, shiftRowCount, true, true);
						for (CellRangeAddress cellRangeAddress : originMerged) {
							if (cellRangeAddress.getFirstRow() > readExcelInfo.getStartRowIndex()) {
								try {
									int firstRow = cellRangeAddress.getFirstRow() + shiftRowCount;
									CellRangeAddress newCellRangeAddress = new CellRangeAddress(firstRow,(firstRow + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
									sheet.addMergedRegion(newCellRangeAddress);
								} catch (Exception e) {
//									System.err.println(e.getMessage());
								}
							}
						}
						//生成中间插入的表格
						if(count==1){
							for(int m=1;m<slaveDataList.size();m++){
								Row srcRow = sheet.getRow(n1);
								Row row = sheet.createRow(n1+m);
								//这里一定要设置高度,在从这个中间sheet汇到总的sheet的时候,这个高度是默认的单元格高度
								row.setHeight(srcRow.getHeight());
								PoiUtils.copyRow(wb, srcRow, row, true);
							}
						}
						else{
							for(int m=1;m<slaveDataList.size();m++){
								for(int r=1;r<=count;r++){
									Row srcRow = sheet.getRow(n1+r-1);
									Row row = sheet.createRow(n2+r);
									PoiUtils.copyRow(wb, srcRow, row, true);
								}
								n2=n2+count;
							}
						}
					}
				}
			}
			logger.info(params+"查询单据数据,耗费时间:"+(System.currentTimeMillis()-startTime));
		}
        FileOutputStream out=new FileOutputStream(new File("d:/report"+templateNum+".xlsx"));
		wb.write(out);
		out.close();
		
		inputStream.close(); 
		wb.close();
		return infoList;
	}
	private List<ReadExcelInfo> singleTemplate(Workbook wb,String templateNum, Map<String,String> cidSheetNameMap) throws Exception{
		//生成所有表格信息(没有实际生成),且给这些表格设置所有数据信息
		List<ReadExcelInfo> infoList=BillExcel.readBillTemp(wb,cidSheetNameMap,false);
		for(int i=0;i<infoList.size();i++){
			ReadExcelInfo readExcelInfo=infoList.get(i);
			String params="params:"+"sheetName="+readExcelInfo.getSheetName()+",businessValue"+readExcelInfo.getBusinessValue()+",";
			long startTime=System.currentTimeMillis();
			String tempSql="select * from entity_print_source s where commandid="+templateNum;
			List<Map<String, Object>> sourceList=jdbcTemplateTest.queryForList(tempSql);
			for(Map<String, Object> obj:sourceList){
				String dbsource=CommonUtils.getColumnValue(obj,"tablename");
				String sqlsource=CommonUtils.getColumnValue(obj,"sqlsource");
				String keyname= CommonUtils.getColumnValue(obj,"keyname");
				//构造好待查询的SQL语句与数据
				sqlsource="select * from ("+sqlsource+") t "; 
				String dataSql=sqlsource+" where "+keyname+"='"+readExcelInfo.getBusinessValue()+"'";
				String sortby=CommonUtils.getColumnValue(obj,"sortby");
				if(StringUtils.isNotEmpty(sortby)){
					dataSql=dataSql+" order by "+sortby;
				}
				List<Map<String, Object>> dataList=jdbcTemplateTest.queryForList(dataSql);
				//设置数据到主表集合
				if(StringUtils.equals(dbsource, readExcelInfo.getMasterName())){
					readExcelInfo.setMasterData(dataList.get(0));//主表只会有一条数据
				}
				//设置数据到从表集合
				if(StringUtils.equals(dbsource, readExcelInfo.getSlaveName())){
					readExcelInfo.setSlaveDataList(dataList);
					//遍历从表数据生成行表格,必须要先从表格模板中获取到相应的查询信息才能这样做
					Sheet sheet=readExcelInfo.getSheet();
					//先新增模板中的从表的表格
					int n1=readExcelInfo.getStartRowIndex();
					int n2=readExcelInfo.getEndRowIndex();
					//计算出要遍历的行数
					int count=n2-n1+1;
					List<Map<String, Object>> slaveDataList=readExcelInfo.getSlaveDataList();
					//开始与结束是同一行,slaveDataList.size()-1这里的减去1是因为第1轮数据表格不用创建
					int shiftRowCount=count*(slaveDataList.size()-1);
					//这里判断是否需新增表格
					if(shiftRowCount>0){
						int lastRowNo = sheet.getLastRowNum();
						List<CellRangeAddress> originMerged = sheet.getMergedRegions();
						sheet.shiftRows(n1+count, lastRowNo, shiftRowCount, true, true);
						for (CellRangeAddress cellRangeAddress : originMerged) {
							if (cellRangeAddress.getFirstRow() > readExcelInfo.getStartRowIndex()) {
								try {
									int firstRow = cellRangeAddress.getFirstRow() + shiftRowCount;
									CellRangeAddress newCellRangeAddress = new CellRangeAddress(firstRow,(firstRow + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
									sheet.addMergedRegion(newCellRangeAddress);
								} catch (Exception e) {
									System.err.println(e.getMessage());
								}
							}
						}
						//生成中间插入的表格
						if(count==1){
							for(int m=1;m<slaveDataList.size();m++){
								Row srcRow = sheet.getRow(n1);
								Row row = sheet.createRow(n1+m);
								//这里一定要设置高度,在从这个中间sheet汇到总的sheet的时候,这个高度是默认的单元格高度
								row.setHeight(srcRow.getHeight());
								PoiUtils.copyRow(wb, srcRow, row, true);
							}
						}
						else{
							for(int m=1;m<slaveDataList.size();m++){
								for(int r=1;r<=count;r++){
									Row srcRow = sheet.getRow(n1+r-1);
									Row row = sheet.createRow(n2+r);
									PoiUtils.copyRow(wb, srcRow, row, true);
								}
								n2=n2+count;
							}
						}
					}
				}
			}
			logger.info(params+"查询单据数据,耗费时间:"+(System.currentTimeMillis()-startTime));
		}
//        FileOutputStream out=new FileOutputStream(new File("d:/report"+templateNum+".xlsx"));
//		wb.write(out);
//		out.close();
		return infoList;
	}
}
