package com.singsong.erp.business.system.config.template.dao.impl;

import org.springframework.stereotype.Repository;

import com.singsong.erp.base.dao.impl.MybaticsBaseDaoImpl;
import com.singsong.erp.business.system.config.template.dao.EmailTemplateDao;

@Repository
public class EmailTemplateDaoImpl extends MybaticsBaseDaoImpl implements EmailTemplateDao {

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return "EmailTemplate";
	}

}
