package com.singsong.erp.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.singsong.erp.base.util.CommonUtils;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class Main {

	public static void main(String[] args) {
//		compreImage();
//		insertImage2();
		String s=UUID.randomUUID().toString();
		System.out.println(s);
		System.out.println(s.replaceAll("\\-", ""));
	}

	public static BufferedImage zoomImage(String src) {
		BufferedImage result = null;
		try {
			File srcfile = new File(src);
			if (!srcfile.exists()) {
				System.out.println("文件不存在");

			}
			BufferedImage im = ImageIO.read(srcfile);

			/* 原始图像的宽度和高度 */
			int width = im.getWidth();
			int height = im.getHeight();

			// 压缩计算
			float resizeTimes = 0.3f; /* 这个参数是要转化成的倍数,如果是1就是转化成1倍 */

			/* 调整后的图片的宽度和高度 */
			int toWidth = (int) (width * resizeTimes);
			int toHeight = (int) (height * resizeTimes);

			/* 新生成结果图片 */
			result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);

			result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0,
					null);

		} catch (Exception e) {
			System.out.println("创建缩略图发生异常" + e.getMessage());
		}
		return result;

	}

	public static boolean writeHighQuality(BufferedImage im, String fileFullPath) {
		try {
			/* 输出到文件流 */
			FileOutputStream newimage = new FileOutputStream(fileFullPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
			/* 压缩质量 */
			jep.setQuality(0.9f, true);
			encoder.encode(im, jep);
			/* 近JPEG编码 */
			newimage.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void compreImage() {
		 String inputFoler = "d:\\c.jpg" ;   
         /*这儿填写你存放要缩小图片的文件夹全地址*/  
        String outputFolder = "d:\\c_1.jpg";    
        /*这儿填写你转化后的图片存放的文件夹*/  
         Main.writeHighQuality(Main.zoomImage(inputFoler), outputFolder);  
	}
	public static void insertImage2(){
		try {
			   Workbook workbook = new XSSFWorkbook();
			   Sheet sheet = workbook.createSheet("MYSheet");
			   InputStream inputStream = new FileInputStream("d:\\c.jpg");
			   byte[] imageBytes = IOUtils.toByteArray(inputStream);
			   int pictureureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
			   

			   CreationHelper helper = workbook.getCreationHelper();

			   Drawing drawing = sheet.createDrawingPatriarch();

			   ClientAnchor anchor = helper.createClientAnchor();

			   anchor.setCol1(5);
			   anchor.setRow1(9);

			   drawing.createPicture(anchor, pictureureIdx);


			   FileOutputStream fileOut = null;
			   fileOut = new FileOutputStream("d://2.xlsx");
			   workbook.write(fileOut);
			   fileOut.close();
			   inputStream.close();
			}catch (Exception e) {
			   System.out.println(e);
			}
	}	
	
	public static void insertImage(){
		 FileOutputStream fileOut = null;     
	        BufferedImage bufferImg = null;     
	       //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray    
	       try {  
	           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();     
	           bufferImg = ImageIO.read(new File("d:/d.jpg"));     
	           ImageIO.write(bufferImg, "jpg", byteArrayOut);  
	           System.out.println(bufferImg.getHeight());
	           System.out.println(bufferImg.getWidth());  
	           XSSFWorkbook wb = new XSSFWorkbook();     
	           XSSFSheet sheet = wb.createSheet("Sheet1");
	           
	           Row row = sheet.createRow(10);
	           row.setHeight((short) 2500);
	           sheet.setColumnWidth(4, 100*256);
//	           sheet.setColumnWidth((short) 0, (short) 2500);
	           
	           //XSSFSheet sheet1 = wb.getSheet("Sheet1");
	           //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）  
	           XSSFDrawing patriarch = sheet.createDrawingPatriarch();     
	           //anchor主要用于设置图片的属性  
	           XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 255, 255,(short) 1, 1, (short) 2, 2);     
//	           anchor.setAnchorType(3);     
	           //插入图片    
	           patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));   
	           
	           
	           fileOut = new FileOutputStream("d://11111.xlsx");     
	           // 写入excel文件     
	           wb.write(fileOut);
	           System.out.println("----Excle文件已生成------");
	       } catch (Exception e) {  
	           e.printStackTrace();  
	       }finally{  
	           if(fileOut != null){  
	                try {  
	                   fileOut.close();  
	               } catch (IOException e) {  
	                   e.printStackTrace();  
	               }  
	           }  
	       }  
	}
}
