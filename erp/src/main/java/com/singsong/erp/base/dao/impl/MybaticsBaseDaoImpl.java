package com.singsong.erp.base.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.singsong.erp.base.dao.MybaticsBaseDao;
import com.singsong.erp.base.pagin.PaginatedResult;
import com.singsong.erp.base.pagin.Paginator;
import com.singsong.erp.base.util.CommonLogger;
import com.singsong.erp.base.util.DateUtil;
import com.singsong.erp.framework.util.WebUtil;

@Component
public abstract class MybaticsBaseDaoImpl implements MybaticsBaseDao {
	private Logger logger = CommonLogger.getLogger(MybaticsBaseDaoImpl.class);
	public  final String SEPARATOR = ".";
	private final String SQLID_SAVE = "save";
	private final String SQLID_UPDATE = "update";
	private final String SQLID_DELETE_PK = "deleteByPk";
	private final String SQLID_DELETE_PK_BATCH = "deleteByPkBatch";
	private final String SQLID_FIND_ALL = "findAll";
	private final String SQLID_FIND_PK = "findByPk";
	private final String SQLID_FIND_PK_BATCH = "findByPkBatch";
	private final String SQLID_FIND_UNIQUE = "findByUnique";
	private final String SQLID_FIND_PAGE_LIST = "findPageList";
	private final String SQLID_FIND_PAGE_COUNT = "findPageCount";
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private SqlSession sqlSession;

	public abstract String getNamespace();

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public SqlSession getSqlSession() {
		if (this.sqlSession == null) {
			this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
		}
		return this.sqlSession;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public int save(Object entity) {
		return getSqlSession().insert(getNamespace() +SEPARATOR + SQLID_SAVE, entity);
	}

	public int deleteByPk(Object pk) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", WebUtil.currentUserId());
		params.put("updateDate", DateUtil.getCurrentDate());
		params.put("id",pk);
		return getSqlSession().delete(getNamespace() +SEPARATOR+ SQLID_DELETE_PK, params);
	}
	public int deleteByPkBatch(List<Object> pks){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", WebUtil.currentUserId());
		params.put("updateDate",DateUtil.getCurrentDate());
		params.put("ids", pks);
		int count=getSqlSession().delete(getNamespace() +SEPARATOR+ SQLID_DELETE_PK_BATCH, params);
		if(count<pks.size()){
			logger.error("批量删除错误,传入ID记录数:"+pks.size()+"条,删除成功记录数:"+count+"条.");
		}
		return count;
	}
/*	*//**
	 * 根据主键删除对象
	 * 
	 * @param pk,userId
	 * @return
	 *//*
	public int deleteByPk(Object pk,Object userId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("updateDate", DateUtil.getCurrentDate());
		params.put("id",pk);
		return getSqlSession().delete(getNamespace() +SEPARATOR+ SQLID_DELETE_PK, params);
	}
	
	*//**
	 * 根据主键删除对象(批量删除)
	 * 
	 * @param pks,userId
	 * @return
	 *//*
	public int deleteByPkBatch(List<Object> pks,Object userId){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("updateDate", DateUtil.getCurrentDate());
		params.put("ids", pks);
		int count=getSqlSession().delete(getNamespace() +SEPARATOR+ SQLID_DELETE_PK_BATCH, params);
		if(count<pks.size()){
			logger.error("批量删除错误,传入ID记录数:"+pks.size()+"条,删除成功记录数:"+count+"条.");
		}
		return count;
	}*/
	public int update(Object entity) {
		return getSqlSession().update(getNamespace() +SEPARATOR+ SQLID_UPDATE, entity);
	}

	public <T> T findByPk(Object pk) {
		return getSqlSession().selectOne(getNamespace() +SEPARATOR+ SQLID_FIND_PK, pk);
	}
	
	public <T> List<T> findByPkBatch(List<Object> pks) {
		return getSqlSession().selectList(getNamespace() +SEPARATOR+ SQLID_FIND_PK_BATCH, pks);
	}
	
	public <T> T findByUnique(Object uniqueKey){
		return getSqlSession().selectOne(getNamespace() +SEPARATOR+ SQLID_FIND_UNIQUE, uniqueKey);
	}

	public <T> List<T> findAll() {
		return getSqlSession().selectList(getNamespace() +SEPARATOR+ SQLID_FIND_ALL);
	}

	public <T> PaginatedResult<T> findPageList(Paginator paginator) {
		int count = getSqlSession().selectOne(getNamespace() +SEPARATOR+ SQLID_FIND_PAGE_COUNT, paginator);
		PaginatedResult<T> pageResult = new PaginatedResult<T>();
		List<T> list = null;
		if (count > 0) {
			list = getSqlSession().selectList(getNamespace() +SEPARATOR+ SQLID_FIND_PAGE_LIST, paginator);
		} else {
			list = new ArrayList<T>();
		}
		pageResult.setData(list);;
		pageResult.setCount(count);
		return pageResult;
	}

	public List<Map<String, Object>> selectListByJdbc(String sql, Object... params) {
		return this.getJdbcTemplate().queryForList(sql, params);
	}

	public <T> List<T> findList(String sqlName, Object params) {
		return getSqlSession().selectList(sqlName, params);
	}

	public int findCount(String sqlName, Object params) {
		int count = getSqlSession().selectOne(sqlName, params);
		return count;
	}
	
	public int deleteByParams(String sqlName, Object params){
		return getSqlSession().delete(sqlName, params);
	}

}
