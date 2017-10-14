package com.singsong.erp.base.service;

import java.util.List;

import com.singsong.erp.base.dto.ValidResult;
import com.singsong.erp.base.pagin.PaginatedResult;
import com.singsong.erp.base.pagin.Paginator;

/**
 * 业务处理层的封装(service层)
 * @author 杨树安
 * @version 创建时间：2017年9月26日 下午9:11:27
 */
public interface BaseService {

	/**
	 * 实体对象的保存
	 * @param entity
	 * @return
	 */
	public int save(Object entity);

	/**
	 * 根据主键删除对象
	 * @param pk
	 * @return
	 */
	public int deleteByPk(Object pk);
	/**
	 * 根据主键删除对象
	 * @param pks 主键数组
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
	 * @param entity
	 * @return
	 */
	public int update(Object entity);

	/**
	 * 根据主键获取实体对象
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
	 * @return
	 */
	public <T> List<T> findAll();
	
	/**
	 * 分页查询
	 * 支持单表或是视图查询
	 * 根据过滤条件返回分页后的实体对象集合,并且返回分页相关数据
	 * @param paginator
	 * @return
	 */
	public <T> PaginatedResult<T> findPageList(Paginator paginator);

	/**
	 * 新增的基础校验,这里只为实现接口,在action需要,则调用
	 */
	public ValidResult validSave(Object entity);

	/**
	 * 修改的基础校验,这里只为实现接口,在action需要,则调用
	 */
	public ValidResult validUpdate(Object entity);

	/**
	 * 删除的基本校验,在action需要,则调用
	 */
	public ValidResult validDelete(Object id);
}
