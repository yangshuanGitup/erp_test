package com.singsong.erp.base.constants;

/**
 * 附件的业务类型,与业务实体表的记录对应
 * 
 * @author 杨树安
 * @version 创建时间：2017年10月11日 下午3:07:29
 */
public enum AttachmentBusinessTypeEnum {
	REQUIREMENT("requirement", "业务需求的附件"),
	SALE_CONTRACT_("saleContract", "销售合同的附件");
	private String code;
	private String text;

	private AttachmentBusinessTypeEnum(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return code + ":" + text;
	}
}
