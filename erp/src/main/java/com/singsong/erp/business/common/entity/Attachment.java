package com.singsong.erp.business.common.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 附件表
 * @author 杨树安
 * @version 创建时间：2017年10月11日 下午2:53:54
 */
public class Attachment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3903527798399785807L;
	private String id;
	private Date createDate; // 创建时间
	private Date updateDate; // 最后修改时间
	private String createrId;//创建人
	private String modifierId;//最后修改人
	private byte deleteFlag; // 删除标志,默认值是0; 0正常1删除
	
	private MultipartFile file;
	private String imgBase64Str;
	private String fileType;//附件文件类型
	private String businessType;//附件所属业务类型,这个字段主要用于最后的一些统计,统计我们的附件多少业务类型的附件,业务流程中基本不用
	private String businessId;//附件所属业务ID
	private String path;//附件存放路径
	private String shortName;//附件名字
	private String realName;//序列号名字
	private String desc;//附件描述
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	public String getModifierId() {
		return modifierId;
	}
	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}
	public byte getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(byte deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImgBase64Str() {
		return imgBase64Str;
	}
	public void setImgBase64Str(String imgBase64Str) {
		this.imgBase64Str = imgBase64Str;
	}
	
}
