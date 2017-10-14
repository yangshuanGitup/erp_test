package com.singsong.erp.business.api.report.ui;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.singsong.erp.base.vo.AjaxResult;
import com.singsong.erp.business.system.config.template.entity.EmailTemplate;

@Controller
@RequestMapping("/api/report")
public class TestAction {
	private final String filePath="/home/ysaln/file/bill_invoice.xlsx";
	@RequestMapping("/export")
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response) {
		try {
			XSSFWorkbook wb = new XSSFWorkbook(filePath);
//			PoiUtils.insertRows(wb);
			OutputStream out = response.getOutputStream();
			String name = new String("杨导出".getBytes("utf-8"), "ISO-8859-1");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=\"" + name + ".xlsx");
			wb.write(out);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/downfile1")
	@ResponseBody
	public void downfile1(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			OutputStream out = response.getOutputStream();
			String name = new String("杨地".getBytes("utf-8"), "ISO-8859-1");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=\"" + name + ".xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(filePath);
			wb.write(out);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/downfile2")
	@ResponseBody
	public ResponseEntity<byte[]> downfile2(HttpServletRequest request, HttpServletResponse response) {
		try {
			File file = new File(filePath);
			// 下载文件路径
			HttpHeaders headers = new HttpHeaders();
			// 下载显示的文件名，解决中文名称乱码问题
			String downloadFielName = new String("杨1麻花.xlsx".getBytes("UTF-8"), "iso-8859-1");
			// 通知浏览器以attachment（下载方式）打开图片
			headers.setContentDispositionFormData("attachment", downloadFielName);
			// headers.setContentLength(file.length());
			// application/octet-stream ： 二进制流数据（最常见的文件下载）。
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/downfile3")
	@ResponseBody
	public void downfile3(HttpServletRequest request, HttpServletResponse response) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				response.setContentType("multipart/form-data,charset=utf-8");
				String name = new String("杨地2".getBytes("utf-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment;fileName=" + name + ".xlsx");
				InputStream inputStream = FileUtils.openInputStream(file);
				byte[] data = IOUtils.toByteArray(inputStream);
				IOUtils.write(data, response.getOutputStream());
				IOUtils.closeQuietly(inputStream);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/testJson")
	@ResponseBody
	public String testJson(HttpServletRequest request, HttpServletResponse response,String callback) {
		String result=new AjaxResult("这个是测试接口").toJsonString();
	    //加上返回参数
	    result=callback+"("+result+")";
		return result;
	}
	@RequestMapping("/testData")
	@ResponseBody
	public String testData(HttpServletRequest request, HttpServletResponse response) {
		EmailTemplate emailTemplate=new EmailTemplate();
		EmailTemplate emailTemplate2=new EmailTemplate();
		List<EmailTemplate> list=new ArrayList<EmailTemplate>();
		String result=new AjaxResult("这个是测试接口").toJsonString();
	    //加上返回参数
		return result;
	}
	public static void main(String[] args) {
		System.out.println(StringEscapeUtils.escapeHtml("{a:1,b:2}"));
	}
}
