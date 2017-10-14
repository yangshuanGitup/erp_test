package com.singsong.erp.base.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.singsong.erp.base.exception.ErpRunTimeException;
import com.singsong.erp.business.common.entity.Attachment;
import com.singsong.erp.framework.init.ReadConf;
import com.singsong.erp.framework.util.DECUtil;

/**
 * 附件的工具类,一些存储的处理
 * 
 * @author 杨树安
 * @version 创建时间：2017年10月13日 上午9:52:25
 */
public class AttachUtil {
	private static Logger logger = CommonLogger.getLogger(AttachUtil.class);
	public static String store(Attachment obj) throws IOException {
		File dirFile = new File(ReadConf.getCommonProperty("attach_base_dir")+obj.getPath());
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		String path=ReadConf.getCommonProperty("attach_base_dir")+obj.getPath() + "/" + obj.getRealName();
		if(StringUtils.isNotEmpty(obj.getImgBase64Str())){
			DECUtil.generateImage(obj.getImgBase64Str(),path);
		}
		else{
			obj.getFile().transferTo(new File(path));
		}
		return path;
	}
	public static String delete(Attachment obj) throws IOException {
		String path=ReadConf.getCommonProperty("attach_base_dir")+obj.getPath() + "/" + obj.getRealName();
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		return path;
	}
	public static void store(List<Attachment> attachmentList){
		List<String> list=new ArrayList<String>();
		try {
			for (Attachment obj : attachmentList) {
				String path=store(obj);
				logger.info("附件保存成功,附件路径="+path);
				list.add(path);
			}
		} catch (Exception e) {
			for(String path:list){
				File file=new File(path);
				file.delete();
			}
			String errMessage="附件保存失败,businessType="+attachmentList.get(0).getBusinessType()+",businessId="+attachmentList.get(0).getBusinessId();
			logger.error(errMessage,e);
			throw new ErpRunTimeException(errMessage,e);
		}
	}
	public static void delete(List<Attachment> attachmentList){
		try {
			for (Attachment obj : attachmentList) {
				String path=delete(obj);
				logger.info("附件删除成功,附件路径="+path);
			}
		} catch (Exception e) {
			String errMessage="附件删除失败,businessType="+attachmentList.get(0).getBusinessType()+",businessId="+attachmentList.get(0).getBusinessId();
			logger.error(errMessage,e);
			throw new ErpRunTimeException(errMessage,e);
		}
	}
	public static String imgToBase64Str(List<Attachment> attachList){
		String result=null;
		StringBuffer resultStr=new StringBuffer("");
		for(Attachment obj:attachList){
			String path=ReadConf.getCommonProperty("attach_base_dir")+obj.getPath() + "/" + obj.getRealName();
			String imgBase64Str=DECUtil.getImageStr(path);
			resultStr.append(imgBase64Str).append("$$");
		}
		if(attachList.size()>0){
			result=resultStr.substring(0, resultStr.length()-2).toString();
		}
		return result;
	}
}
