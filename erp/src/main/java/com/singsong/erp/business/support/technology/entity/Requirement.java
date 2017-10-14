package com.singsong.erp.business.support.technology.entity;

import java.io.Serializable;
import java.util.Date;

public class Requirement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9088374652067304793L;
	private String id;
	private Date createDate; // 创建时间
	private Date updateDate; // 最后修改时间
	private String createrId;//创建人
	private String modifierId;//最后修改人
	private byte deleteFlag; // 删除标志,默认值是0; 0正常1删除
	
	private String moduleId;// 需求所属模块
	private String title;// 需求标题
	private String content;// 需求内容
	private String reqDeptId;// 需求提出部门,IT部门自己提出的需求属于IT部门
	private String reqUserId;// 需求的提出人
	private String reqDate;// 需求提出时间
	private Date planStartDate;// 计划开始时间
	private Date planEndDate;// 计划结束时间
	private Date startDate;//解决开始执行时间
	private Date finishDate;//解决完成时间
	private String solverId;//指定解决人
	private short status=0;//需求状态,是字典;1:初始,2:计划,3:开始,4:完成,5:作废,6取消;默认是1;
	private String imgBase64;//前端图片的64位编码
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
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReqDeptId() {
		return reqDeptId;
	}
	public void setReqDeptId(String reqDeptId) {
		this.reqDeptId = reqDeptId;
	}
	public String getReqUserId() {
		return reqUserId;
	}
	public void setReqUserId(String reqUserId) {
		this.reqUserId = reqUserId;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public Date getPlanStartDate() {
		return planStartDate;
	}
	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}
	public Date getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public String getSolverId() {
		return solverId;
	}
	public void setSolverId(String solverId) {
		this.solverId = solverId;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public String getImgBase64() {
		return imgBase64;
	}
	public void setImgBase64(String imgBase64) {
		this.imgBase64 = imgBase64;
	}
	
}
