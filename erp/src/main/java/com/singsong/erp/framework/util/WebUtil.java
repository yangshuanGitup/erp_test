package com.singsong.erp.framework.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.singsong.erp.base.constants.AttachmentFileTypeEnum;
import com.singsong.erp.base.util.CommonUtils;
import com.singsong.erp.base.util.DateUtil;
import com.singsong.erp.business.common.entity.Attachment;

@Component
public class WebUtil {
	@Autowired  
	private static HttpSession session;  
	@Autowired  
	private static HttpServletRequest request;  
	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true);
	}
	public static String currentUserId(){
//		String userId=(String) session.getAttribute("userId");
//		if(StringUtils.isEmpty(userId)){
//			userId="test001";
//		}
		return "test001";
	}
	public static <T> T formBeanToEntity(HttpServletRequest request,Class<T> classOfT){
        Enumeration<String> param = request.getParameterNames();
        Map<String, Object> map = new HashMap<String, Object>();
        while (param.hasMoreElements()) {
            String key = param.nextElement();
            map.put(key, request.getParameter(key));
        }
       return CommonUtils.mapToObject(map, classOfT);
	}
	public static List<Attachment> handlerAttach(HttpServletRequest request,String businessType){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        List<Attachment> attachmentList=new ArrayList<Attachment>();
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                long pre =  System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String fileName = file.getOriginalFilename();  
                    if(fileName.trim() !=""){  
                        //重命名上传后的文件名  
                        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
                    	Attachment attachment=new Attachment();
                    	String fileType=AttachmentFileTypeEnum.getFileType(prefix,fileName);
                    	attachment.setBusinessType(businessType);
                    	attachment.setFileType(fileType);
                    	attachment.setFile(file);
                    	String realName=DateUtil.getCurrentTimestamp()+"_"+CommonUtils.getRandomString(8);
                    	attachment.setShortName(fileName);
                    	if(StringUtils.isEmpty(prefix)){
                    		attachment.setRealName(realName);
                    	}
                    	else{
                    		attachment.setRealName(realName+"."+prefix);
                    	}
                    	attachment.setCreateDate(DateUtil.getCurrentDate());
                    	attachment.setCreaterId(WebUtil.currentUserId());
                    	attachment.setUpdateDate(attachment.getCreateDate());
                    	attachment.setModifierId(attachment.getCreaterId());
                    	attachmentList.add(attachment);
                    }  
                }  
                //记录上传该文件后的时间  
                long finaltime = System.currentTimeMillis();  
                System.out.println(finaltime - pre);  
            }  
        } 
        return attachmentList;
	}
	public static List<Attachment> handlerAttachBase64(String businessType,String imgBase64StrArray[]){
		List<Attachment> attachmentList=new ArrayList<Attachment>();
		String prefix="jpg";
		for(String str:imgBase64StrArray){
			if(StringUtils.isNotEmpty(str)){
	        	Attachment attachment=new Attachment();
	        	attachment.setBusinessType(businessType);
	        	attachment.setFileType(AttachmentFileTypeEnum.IMG.getCode());
	        	attachment.setImgBase64Str(str);
	        	String realName=DateUtil.getCurrentTimestamp()+"_"+CommonUtils.getRandomString(8);
	        	attachment.setShortName(businessType+"_"+CommonUtils.getRandomString(8)+"."+prefix);
	        	attachment.setRealName(realName+"."+prefix);
	        	attachment.setCreateDate(DateUtil.getCurrentDate());
	        	attachment.setCreaterId(WebUtil.currentUserId());
	        	attachment.setUpdateDate(attachment.getCreateDate());
	        	attachment.setModifierId(attachment.getCreaterId());
	        	attachmentList.add(attachment);
			}
		}
		return attachmentList;
	}
}
