package com.singsong.erp.business.system.config.template.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件发送配置实体类
 * @author 杨树安
 * @version 创建时间：2017年9月26日 下午9:49:12
 */
public class EmailTemplate implements Serializable{

	private static final long serialVersionUID = 6501507541557824243L;
	private String id;
	private Date createD; // 创建时间
	private Date updateD; // 最后修改时间
	private String createrId;//创建人
	private String modifierId;//修改人
	private byte deleteFlag; // 删除标志,默认值是0; 0正常1删除
	private String from;
	private String to;
	private String cc;
	private String bcc;
	private String title;
	private String subject;
	private String content;
	private String replaceText;
	private String type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateD() {
		return createD;
	}
	public void setCreateD(Date createD) {
		this.createD = createD;
	}
	public Date getUpdateD() {
		return updateD;
	}
	public void setUpdateD(Date updateD) {
		this.updateD = updateD;
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
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getReplaceText() {
		return replaceText;
	}
	public void setReplaceText(String replaceText) {
		this.replaceText = replaceText;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "EmailTemplate [from=" + from + ", to=" + to + ", cc=" + cc + ", bcc=" + bcc + ", title=" + title
				+ ", subject=" + subject + ", content=" + content + ", replaceText=" + replaceText + ", type=" + type
				+ "]";
	}
}
