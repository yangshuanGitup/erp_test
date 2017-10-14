package com.singsong.erp.business.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.singsong.erp.base.dao.impl.MybaticsBaseDaoImpl;
import com.singsong.erp.base.util.AttachUtil;
import com.singsong.erp.base.util.DateUtil;
import com.singsong.erp.business.common.dao.AttachmentDao;
import com.singsong.erp.business.common.entity.Attachment;
import com.singsong.erp.framework.util.WebUtil;

@Repository
public class AttachmentDaoImpl extends MybaticsBaseDaoImpl implements AttachmentDao {

	private final String SQLID_FIND_BUSINESS_ID="findByBusinessId";
	private final String SQLID_DETELE_BUSINESS_ID="deleteByBusinessId";
	@Override
	public String getNamespace() {
		return "Attachment";
	}
	public int saveByBusinessId(String businessId,List<Attachment> attachmentList){
		int count=0;
		if (attachmentList != null) {
			for (Attachment obj : attachmentList) {
				dealPro(businessId,obj);
				count=count+this.save(obj);
			}
			AttachUtil.store(attachmentList);
		}
		return count;
	}

	@Override
	public int deleteByBusinessId(String businessId) {
		//删除附件,这里最好不要做文件的物理删除
		List<Attachment> attachmentList=this.findList(getNamespace()+SEPARATOR+SQLID_FIND_BUSINESS_ID, businessId);
		AttachUtil.delete(attachmentList);
		//删除附件表数据
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", WebUtil.currentUserId());
		params.put("updateDate", DateUtil.getCurrentDate());
		params.put("businessId", businessId);
		return this.deleteByParams(getNamespace()+SEPARATOR+SQLID_DETELE_BUSINESS_ID, params);
	}

	@Override
	public List<Attachment> findByBusinessId(String businessId) {
		return this.findList(getNamespace()+SEPARATOR+SQLID_FIND_BUSINESS_ID, businessId);
	}
	private void dealPro(String businessId,Attachment obj){
		obj.setBusinessId(businessId);
		String relaPath="/"+DateUtil.getCurrentDay()+"/"+obj.getBusinessType()+"/"+obj.getBusinessId();
		obj.setPath(relaPath);
	}
}
