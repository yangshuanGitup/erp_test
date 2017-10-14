package com.singsong.erp.base.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.singsong.erp.base.pagin.PaginatedResult;
import com.singsong.erp.base.pagin.Paginator;

/**
 * 数据库调用层封装(dao层)
 * 
 * @author 杨树安
 * @version 创建时间：2017年9月26日 下午9:08:39
 */
public interface MybaticsBaseDao {

	public SqlSession getSqlSession();

	/**
	 * 实体对象的保存
	 * 
	 * @param entity
	 * @return
	 */
	public int save(Object entity);

	/**
	 * 根据主键删除对象
	 * 
	 * @param pk
	 * @return
	 */
	public int deleteByPk(Object pk);
	
	/**
	 * 根据主键删除对象(批量删除)
	 * 
	 * @param pks
	 * @return
	 */
	public int deleteByPkBatch(List<Object> pks);
/*	*//**
	 * 根据主键删除对象
	 * 
	 * @param pk,userId
	 * @return
	 *//*
	public int deleteByPk(Object pk,Object userId);
	
	*//**
	 * 根据主键删除对象(批量删除)
	 * 
	 * @param pks,userId
	 * @return
	 *//*
	public int deleteByPkBatch(List<Object> pks,Object userId);	*/

	/**
	 * 修改实体对象
	 * 
	 * @param entity
	 * @return
	 */
	public int update(Object entity);

	/**
	 * 根据主键获取实体对象
	 * 
	 * @param pk
	 * @return
	 */
	public <T> T findByPk(Object pk);
	
	/**
	 * 根据主键获取实体对象
	 * @param pks 主键数组
	 * @return
	 */
	public <T> List<T> findByPkBatch(List<Object> pks);

	/**
	 * 根据唯一属性获取实体对象
	 * 
	 * @param pk
	 * @return
	 */
	public <T> T findByUnique(Object uniqueKey);

	/**
	 * 返回所有的实体对象
	 * 
	 * @return
	 */
	public <T> List<T> findAll();

	/**
	 * 分页查询
	 * 支持单表或是视图查询
	 * 过滤条件主要是实体表字段
	 * 根据过滤条件返回分页后的实体对象集合,并且返回分页相关数据
	 * 
	 * @param paginator
	 * @return
	 */
	public <T> PaginatedResult<T> findPageList(Paginator paginator);

	/**
	 * 返回条件执行jdbc查询,返回map类型的数据集合
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectListByJdbc(String sql, Object... params);
	
	/**
	 * 根据特殊的sql返回特殊的数据集合
	 * 
	 * @param sqlName
	 * @param params
	 * @return
	 */
	public <T> List<T> findList(String sqlName, Object params);

	/**
	 * 获取总记录数
	 * 
	 * @param sqlName
	 * @param params
	 * @return
	 */
	public int findCount(String sqlName, Object params);
	
	/**
	 * 根据特殊的sql删除记录
	 * @param sqlName
	 * @param params
	 * @return
	 */
	public int deleteByParams(String sqlName, Object params);

}
