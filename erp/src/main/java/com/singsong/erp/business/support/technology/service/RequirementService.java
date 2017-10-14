package com.singsong.erp.business.support.technology.service;

import java.util.List;

import com.singsong.erp.base.service.BaseService;
import com.singsong.erp.business.common.entity.Attachment;
import com.singsong.erp.business.support.technology.entity.Requirement;

/**
 * 邮件发送配置业务处理
 * 
 * @author 杨树安
 * @version 创建时间：2017年9月26日 下午9:54:00
 */
public interface RequirementService extends BaseService {
	public void save(Requirement requirement, List<Attachment> attachmentList);

	public void update(Requirement requirement, List<Attachment> attachmentList);

	public int deleteByPkSpecial(Requirement requirement);

	public int deleteByPkBatchSpecial(List<Object> ids);

}
