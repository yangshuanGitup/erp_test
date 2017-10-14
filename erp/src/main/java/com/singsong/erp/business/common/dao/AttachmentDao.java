package com.singsong.erp.business.common.dao;

import java.util.List;

import com.singsong.erp.base.dao.MybaticsBaseDao;
import com.singsong.erp.business.common.entity.Attachment;

/**
 * 附件dao
 * 
 * @author 杨树安
 * @version 创建时间：2017年10月13日 上午9:18:06
 */
public interface AttachmentDao extends MybaticsBaseDao {
	/**
	 * 根据业务ID新增附件
	 * @param businessId
	 * @return
	 */
	public int saveByBusinessId(String businessId,List<Attachment> attachmentList);

	/**
	 * 根据业务ID删除附件
	 * @param businessId
	 * @return
	 */
	public int deleteByBusinessId(String businessId);

	/**
	 * 根据业务ID查询所有附件
	 * @param businessId
	 * @return
	 */
	public List<Attachment> findByBusinessId(String businessId);
}
