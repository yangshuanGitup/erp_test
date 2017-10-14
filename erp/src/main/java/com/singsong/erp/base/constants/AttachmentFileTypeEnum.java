package com.singsong.erp.base.constants;

import org.apache.commons.lang.StringUtils;

/**
 * 附件文件类型;eg:doc,img,excel....
 * 
 * @author 杨树安
 * @version 创建时间：2017年10月11日 下午2:50:26
 */
public enum AttachmentFileTypeEnum {
	// 对于不同的附件类型,前端需要不同的展示控件支持
	DOC("doc", "文档"), IMG("img", "图片"), AUDIO("audio", "音频"), VIDEO("video", "视频");
	private String code;
	private String text;

	private AttachmentFileTypeEnum(String code, String text) {
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

	public static String getFileType(String prefix, String fileName) {
		String fileType = "other";
		if (StringUtils.isNotEmpty(prefix)) {
			switch (prefix) {
			case "jpg":
				fileType = IMG.getCode();
				break;
			case "jpeg":
				fileType = IMG.getCode();
				break;
			case "gif":
				fileType = IMG.getCode();
				break;
			case "png":
				fileType = IMG.getCode();
				break;
			case "doc":
				fileType = DOC.getCode();
				break;
			case "xls":
				fileType = DOC.getCode();
				break;
			case "ppt":
				fileType = DOC.getCode();
				break;
			case "docx":
				fileType = DOC.getCode();
				break;
			case "xlsx":
				fileType = DOC.getCode();
				break;
			case "pptx":
				fileType = DOC.getCode();
				break;
			case "zip":
				fileType = DOC.getCode();
				break;
			case "rar":
				fileType = DOC.getCode();
				break;
			case "txt":
				fileType = DOC.getCode();
				break;
			default:
				break;
			}
		}
		return fileType;
	}
}
