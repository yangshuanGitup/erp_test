package com.singsong.erp.base.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.singsong.erp.base.dao.MybaticsBaseDao;
import com.singsong.erp.base.dto.ValidResult;
import com.singsong.erp.base.pagin.PaginatedResult;
import com.singsong.erp.base.pagin.Paginator;
import com.singsong.erp.base.service.BaseService;

@Transactional
public abstract class BaseServiceImpl implements BaseService {

	public abstract MybaticsBaseDao getBusinessDao();

	@Override
	public int save(Object entity) {
		return getBusinessDao().save(entity);
	}

	@Override
	public int deleteByPk(Object pk) {
		return getBusinessDao().deleteByPk(pk);
	}
	@Override
	public int deleteByPkBatch(List<Object> pks){
		return getBusinessDao().deleteByPkBatch(pks);
	}
/*	public int deleteByPk(Object pk,Object userId){
		return getBusinessDao().deleteByPk(pk, userId);
	}
	
	public int deleteByPkBatch(List<Object> pks,Object userId){
		return getBusinessDao().deleteByPkBatch(pks, userId);
	}*/
	@Override
	public int update(Object entity) {
		return getBusinessDao().update(entity);
	}

	@Override
	public <T> T findByPk(Object pk) {
		return getBusinessDao().findByPk(pk);
	}
	@Override
	public <T> List<T> findByPkBatch(List<Object> pks){
		return getBusinessDao().findByPkBatch(pks);
	}
	public <T> T findByUnique(Object uniqueKey){
		return getBusinessDao().findByUnique(uniqueKey);
	}
	@Override
	public <T> List<T> findAll() {
		return getBusinessDao().findAll();
	}

	public <T> PaginatedResult<T> findPageList(Paginator paginator){
		return getBusinessDao().findPageList(paginator);
	}

	@Override
	public ValidResult validSave(Object entity) {
		// TODO Auto-generated method stub
		return new ValidResult();
	}

	@Override
	public ValidResult validUpdate(Object entity) {
		// TODO Auto-generated method stub
		return new ValidResult();
	}

	@Override
	public ValidResult validDelete(Object id) {
		// TODO Auto-generated method stub
		return new ValidResult();
	}
}
