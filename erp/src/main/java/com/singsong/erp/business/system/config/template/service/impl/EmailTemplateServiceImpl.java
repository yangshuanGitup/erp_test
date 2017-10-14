package com.singsong.erp.business.system.config.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singsong.erp.base.dao.MybaticsBaseDao;
import com.singsong.erp.base.service.impl.BaseServiceImpl;
import com.singsong.erp.business.system.config.template.dao.EmailTemplateDao;
import com.singsong.erp.business.system.config.template.service.EmailTemplateService;

@Service
public class EmailTemplateServiceImpl extends BaseServiceImpl implements EmailTemplateService {
	@Autowired
	private EmailTemplateDao emailTemplateDao;
	@Override
	public MybaticsBaseDao getBusinessDao() {
		// TODO Auto-generated method stub
		return emailTemplateDao;
	}

}
