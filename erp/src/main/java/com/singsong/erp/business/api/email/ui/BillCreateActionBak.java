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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import com.singsong.erp.business.system.config.template.dao.EmailTemplateDao;
import com.singsong.erp.business.system.config.template.entity.EmailTemplate;
import com.singsong.erp.component.docment.excel.BillExcel;
import com.singsong.erp.component.docment.excel.ReadExcelInfo;
import com.singsong.erp.component.message.email.EmailTransport;
import com.singsong.erp.framework.init.ReadConf;

@Controller
@RequestMapping("/bill")
public class BillCreateActionBak {
	private static final Logger logger = CommonLogger.getLogger(BillCreateActionBak.class); 
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EmailTemplateDao emailTemplateDao;	
	@RequestMapping("/send")
	@ResponseBody
	public String testJson(HttpServletRequest request, HttpServletResponse response,String callback) {
		String type=request.getParameter("type");
		String replaceValues=request.getParameter("replaceValues");
		String values[]=strToArray(replaceValues);
//		EmailTemplate template=emailTemplateDao.findByType(type);
		EmailTemplate template=new EmailTemplate();
		String content=template.getContent();
		String replaceArray[]=strToArray(template.getReplaceText());
		for(int i=0;i<replaceArray.length;i++){
			content=content.replaceAll("\\$(.*"+replaceArray[i]+")\\$",values[i]);
		}
		EmailTransport.sendMail(content,template.getSubject(), template.getFrom(),template.getTitle(),strToArray(template.getTo()),strToArray(template.getCc()), strToArray(template.getBcc()),null);
		String result=new AjaxResult("这个是测试接口").toJsonString();
	    //加上返回参数
	    result=callback+"("+result+")";
		return result;
	}	
	/**
	 * 单个主ID值,多个sheet模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/export")
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response) throws Exception{
/*			String templateNum=request.getParameter("templateNum");
			String businessValue=request.getParameter("businessValue");*/
			String templateNum="19";
			String businessValue="83232197-CCDD-4FCC-AC6E-88D9545D7AE8";
			String templateFile="d:/bill_invoice.xlsx";
			//根据模板ID查询模板的路径
/*			List<Map<String, Object>> templateList=jdbcTemplate.queryForList("select * from entity_print_new where autoinc='"+templateNum+"'");
			if(templateList.size()!=1){
				throw new Exception("单据Excel模板查询错误");
			}
			String baseDir=ReadConf.getCommonProperty("bill_excel_template_dir");
			String templateFile=baseDir+CommonUtils.getColumnValue(templateList.get(0), "templatefilename");*/
			InputStream inputStream=new FileInputStream(new File(templateFile));
			Workbook wb = WorkbookFactory.create(inputStream);
			List<ReadExcelInfo> infoList=singleTemplate(templateNum,wb,businessValue);
			//遍历生成数据到模板中,同时真正的开始创建相关cell
			BillExcel.initDataToExcel2(wb, infoList);
			//导出excel
/*			OutputStream out = response.getOutputStream();
			String name = new String("杨导出".getBytes("utf-8"), "ISO-8859-1");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=\"" + name + ".xlsx");*/
			//生成文件到本地
            FileOutputStream out=new FileOutputStream(new File("d:/report.xlsx"));
			wb.write(out);
			out.close();
			inputStream.close(); 
			wb.close();
	}
	/**
	 * 有多个子ID的情况,多个Excel模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportChildData")
	@ResponseBody
	public void exportChildData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info(new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
			String excelName="测试";
			String type="001";
			String businessValue="F6EA803A-3C64-4731-924F-9B3B35C7E6EC";
			List<ReadExcelInfo> infoList=new ArrayList<ReadExcelInfo>();
			//根据这个type判断是否需要查询子ID数据值
			if(StringUtils.isNotEmpty(type)){
				//查询父数据相关的模板信息
				List<Map<String, Object>> parentDataTmeplateList=jdbcTemplate.queryForList("select * from entity_print_data_template where type='"+type+"' order by sort");
				for(Map<String, Object> parentDataTmeplate:parentDataTmeplateList){
					String sheetCol=CommonUtils.getColumnValue(parentDataTmeplate,"sheetname");
					String filterCol=CommonUtils.getColumnValue(parentDataTmeplate,"keyName");
					String cidCol=CommonUtils.getColumnValue(parentDataTmeplate,"cidName");
					String sql=CommonUtils.getColumnValue(parentDataTmeplate,"sqlStr");
					
					//构建查询子数据值的SQL
					sql="select * from ("+sql+") t where "+filterCol+"='"+businessValue+"'";
					List<Map<String, Object>> cidMapList=jdbcTemplate.queryForList(sql);
					Map<String,String> cidSheetNameMap=new LinkedHashMap<String,String>();
					for(Map<String, Object> map:cidMapList){
						cidSheetNameMap.put(CommonUtils.getColumnValue(map,cidCol), CommonUtils.getColumnValue(map,sheetCol));
					}
					String templateNum=CommonUtils.getColumnValue(parentDataTmeplate,"cid");//模板ID
					List<ReadExcelInfo> tempList=singleTemplate(templateNum,cidSheetNameMap);
					infoList.addAll(tempList);
				}
			}
			
			Workbook wb = new XSSFWorkbook();
			//遍历生成数据到模板中
//			BillExcel.initDataToExcel(wb, infoList);
			//导出excel
/*			OutputStream out = response.getOutputStream();
			String name = new String(excelName.getBytes("utf-8"), "ISO-8859-1");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=\"" + name + ".xlsx");*/
			//生成文件到本地
            FileOutputStream out=new FileOutputStream(new File("d:/report.xlsx"));
			wb.write(out);
			out.close();
			wb.close();
			logger.info(new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
	}	
	/**
	 * 单个主ID值,多个sheet模板
	 * @param templateNum
	 * @param wb
	 * @param businessValue
	 * @return
	 * @throws Exception
	 */
	private List<ReadExcelInfo> singleTemplate(String templateNum,Workbook wb,String businessValue) throws Exception{
		//生成所有表格信息(没有实际生成),且给这些表格设置所有数据信息
		List<ReadExcelInfo> infoList=BillExcel.readBillTemp(wb);
		for(int i=0;i<infoList.size();i++){
			ReadExcelInfo readExcelInfo=infoList.get(i);
			String params="params:"+"sheetName="+readExcelInfo.getSheetName()+",businessValue"+readExcelInfo.getBusinessValue()+",";
			long startTime=System.currentTimeMillis();
			String tempSql="select * from entity_print_source s where commandid="+templateNum;
			List<Map<String, Object>> sourceList=jdbcTemplate.queryForList(tempSql);
			for(Map<String, Object> obj:sourceList){
				String dbsource=CommonUtils.getColumnValue(obj,"tablename");
				String sqlsource=CommonUtils.getColumnValue(obj,"sqlsource");
				String keyname= CommonUtils.getColumnValue(obj,"keyname");
				
				//构造好待查询的SQL语句与数据
				sqlsource="select * from ("+sqlsource+") t "; 
				String dataSql=sqlsource+" where "+keyname+"='"+businessValue+"'";
				String sortby=CommonUtils.getColumnValue(obj,"sortby");
				if(StringUtils.isNotEmpty(sortby)){
					dataSql=dataSql+" order by "+sortby;
				}
//				logger.info(dataSql);
				List<Map<String, Object>> dataList=jdbcTemplate.queryForList(dataSql);
				//设置数据到主表集合
				if(StringUtils.equals(dbsource, readExcelInfo.getMasterName())){
					readExcelInfo.setMasterData(dataList.get(0));//主表只会有一条数据
				}
				//设置数据到从表集合
				if(StringUtils.equals(dbsource, readExcelInfo.getSlaveName())){
					readExcelInfo.setSlaveDataList(dataList);
				}
			}
			logger.info(params+"查询单据数据,耗费时间:"+(System.currentTimeMillis()-startTime));
		}
		return infoList;
	}
	/**
	 * 有多个子ID的情况,多个Excel模板
	 * @param templateNum
	 * @param cidSheetNameMap 子ID与组成sheet名字的值  标签单_1001 eg:1001
	 * @return
	 * @throws Exception
	 */
	private List<ReadExcelInfo> singleTemplate(String templateNum,Map<String,String> cidSheetNameMap) throws Exception{
		//根据模板ID查询模板的路径
		List<Map<String, Object>> templateList=jdbcTemplate.queryForList("select * from entity_print_new where autoinc="+templateNum);
		if(templateList.size()!=1){
			throw new Exception("单据Excel模板查询错误");
		}
		String baseDir=ReadConf.getCommonProperty("bill_excel_template_dir");
		String templateFile=baseDir+CommonUtils.getColumnValue(templateList.get(0), "templatefilename");
		InputStream inputStream=new FileInputStream(new File(templateFile));
		Workbook wb = WorkbookFactory.create(inputStream);
		//生成所有表格信息(没有实际生成),且给这些表格设置所有数据信息
		List<ReadExcelInfo> infoList=BillExcel.readBillTemp(wb,cidSheetNameMap,true);
		for(int i=0;i<infoList.size();i++){
			ReadExcelInfo readExcelInfo=infoList.get(i);
			String params="params:"+"sheetName="+readExcelInfo.getSheetName()+",businessValue"+readExcelInfo.getBusinessValue()+",";
			long startTime=System.currentTimeMillis();
			String tempSql="select * from entity_print_source s where commandid="+templateNum;
			List<Map<String, Object>> sourceList=jdbcTemplate.queryForList(tempSql);
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
//				logger.info(dataSql);
				List<Map<String, Object>> dataList=jdbcTemplate.queryForList(dataSql);
				//设置数据到主表集合
				if(StringUtils.equals(dbsource, readExcelInfo.getMasterName())){
					readExcelInfo.setMasterData(dataList.get(0));//主表只会有一条数据
				}
				//设置数据到从表集合
				if(StringUtils.equals(dbsource, readExcelInfo.getSlaveName())){
					readExcelInfo.setSlaveDataList(dataList);
				}
			}
			logger.info(params+"查询单据数据,耗费时间:"+(System.currentTimeMillis()-startTime));
		}
		inputStream.close(); 
		wb.close();
		return infoList;
	}
	private String[]  strToArray(String src){
		if(StringUtils.isEmpty(src)){
			return null;
		}
		else{
			return src.split("\\,");
		}
	}
	public static void main(String[] args) {
//		System.out.println(StringEscapeUtils.escapeHtml("{a:1,b:2}"));
		System.out.println("c$name$,test$pwd$".replaceAll("[$"+"name"+"$]","11"));
		System.out.println("c$name$,test$pwd$".replaceAll("\\$(.*?)\\$","22"));
		System.out.println("c$name$,test$pwd$".replaceAll("\\$(.*name)\\$","22"));
	}
}
