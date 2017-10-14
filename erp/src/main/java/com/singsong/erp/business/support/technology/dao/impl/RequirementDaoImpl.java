package com.singsong.erp.business.support.technology.dao.impl;

import org.springframework.stereotype.Repository;

import com.singsong.erp.base.dao.impl.MybaticsBaseDaoImpl;
import com.singsong.erp.business.support.technology.dao.RequirementDao;

@Repository
public class RequirementDaoImpl extends MybaticsBaseDaoImpl implements RequirementDao {

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return "Requirement";
	}

}
