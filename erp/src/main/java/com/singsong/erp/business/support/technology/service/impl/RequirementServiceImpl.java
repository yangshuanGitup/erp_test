package com.singsong.erp.business.support.technology.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singsong.erp.base.dao.MybaticsBaseDao;
import com.singsong.erp.base.service.impl.BaseServiceImpl;
import com.singsong.erp.business.common.dao.AttachmentDao;
import com.singsong.erp.business.common.entity.Attachment;
import com.singsong.erp.business.support.technology.dao.RequirementDao;
import com.singsong.erp.business.support.technology.entity.Requirement;
import com.singsong.erp.business.support.technology.service.RequirementService;

@Service
public class RequirementServiceImpl extends BaseServiceImpl implements RequirementService {
	@Autowired
	private RequirementDao requirementsDao;
	@Autowired
	private AttachmentDao attachmentDao;

	@Override
	public MybaticsBaseDao getBusinessDao() {
		return requirementsDao;
	}

	public void save(Requirement requirement, List<Attachment> attachmentList) {
		requirementsDao.save(requirement);
		attachmentDao.saveByBusinessId(requirement.getId(), attachmentList);
	}

	@Override
	public void update(Requirement requirement, List<Attachment> attachmentList){
		attachmentDao.deleteByBusinessId(requirement.getId());
		attachmentDao.saveByBusinessId(requirement.getId(), attachmentList);
		requirementsDao.update(requirement);
	}
	public int deleteByPkSpecial(Requirement requirement){
		attachmentDao.deleteByBusinessId(requirement.getId());
		return requirementsDao.deleteByPk(requirement.getId());
	}

	public int deleteByPkBatchSpecial(List<Object> ids){
		int count=0;
		List<Requirement> reqList=requirementsDao.findByPkBatch(ids);
		for(Requirement req:reqList){
			count=deleteByPkSpecial(req)+count;
		}
		return count;
	}
}
