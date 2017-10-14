package com.singsong.erp.business.support.technology.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.singsong.erp.base.constants.AttachmentBusinessTypeEnum;
import com.singsong.erp.base.constants.Errors;
import com.singsong.erp.base.pagin.GridConditionHandle;
import com.singsong.erp.base.pagin.GridSortHandle;
import com.singsong.erp.base.pagin.PaginatedResult;
import com.singsong.erp.base.pagin.Paginator;
import com.singsong.erp.base.util.AttachUtil;
import com.singsong.erp.base.util.DateUtil;
import com.singsong.erp.base.vo.AjaxResult;
import com.singsong.erp.business.common.dao.AttachmentDao;
import com.singsong.erp.business.common.entity.Attachment;
import com.singsong.erp.business.support.technology.entity.Requirement;
import com.singsong.erp.business.support.technology.service.RequirementService;
import com.singsong.erp.framework.util.WebUtil;

@Controller
@RequestMapping("/support/technology/requirement")
public class RequirementAction {
	@Autowired
	private RequirementService requirementService;
	@Autowired
	private AttachmentDao attachmentDao;
	
	/**
	 * 页面跳转
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/page")
	public String page(HttpServletRequest request, Model model) {
		return "/support/technology/requirements/page";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param page 当前页号
	 * @param rows 每页条数
	 * @param sorts eg:order by name asc or order by name desc,sex asc
	 * @param filters 过滤条件
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/pagedata")
	@ResponseBody
	public String pageData(HttpServletRequest request, HttpServletResponse response,
			int page, int rows, String sorts, String filters) throws Exception {
		int start = (page - 1) * rows;
		Paginator pagin = new Paginator();
		if(StringUtils.isNotEmpty(sorts)){
			
		}
		if (StringUtils.isNotEmpty(filters)) {
			String convertFilters = GridConditionHandle.handle(Requirement.class.getCanonicalName(), filters);
			pagin.setFilters(convertFilters);
		}
		if (StringUtils.isNotEmpty(sorts)) {
			String convertSorts = GridSortHandle.handle(Requirement.class.getCanonicalName(), sorts);
			pagin.setSorts(convertSorts);
		}
		pagin.setStart(start);
		pagin.setPageSize(rows);
		PaginatedResult<Requirement> result = requirementService.findPageList(pagin);
		return new AjaxResult(result).toJsonString();
	}
    @RequestMapping("/add")
    @ResponseBody
    public String save(HttpServletRequest request) {
//    	List<Attachment> attachmentList=WebUtil.handlerAttach(request,AttachmentBusinessTypeEnum.REQUIREMENT.getCode());
    	Requirement obj=WebUtil.formBeanToEntity(request,Requirement.class);
		String imgstr=obj.getImgBase64();
		System.out.println(imgstr);
		String imgBase64StrArray[]=imgstr.split("\\$\\$");
    	List<Attachment> attachmentList=WebUtil.handlerAttachBase64(AttachmentBusinessTypeEnum.REQUIREMENT.getCode(), imgBase64StrArray);
        //Requirements的title,moduleId不能为空
        String title = obj.getTitle();
        String moduleId = obj.getModuleId();
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(moduleId)) {
            return new AjaxResult(Errors.ERR_UNIQUE_NULL.getCode(), Errors.ERR_UNIQUE_NULL.getMessage()).toJsonString();
        }
        Map<String,Object> uniqueMap=new HashMap<String,Object>();
        uniqueMap.put("title", title);
        uniqueMap.put("moduleId", moduleId);
        Requirement dbObjUnique = requirementService.findByUnique(uniqueMap);
        if (StringUtils.isEmpty(obj.getId())) {
            if (dbObjUnique != null) {
                return new AjaxResult(Errors.ERR_UNIQUE_CONSTRAINT.getCode(), Errors.ERR_UNIQUE_CONSTRAINT.getMessage()).toJsonString();
            }
            obj.setCreateDate(DateUtil.getCurrentDate());
            obj.setUpdateDate(obj.getCreateDate());
            obj.setCreaterId(WebUtil.currentUserId());
            obj.setModifierId(obj.getCreaterId());
            requirementService.save(obj, attachmentList);
        }
        //更新时候要检验ID是否存在,第二,如果修改了type，则要保证不能与已有记录存在相同type
        else {
            String id = obj.getId();
            Requirement dbObj = requirementService.findByPk(id);
            if (dbObj == null) {
                return new AjaxResult(Errors.ERR_ID_NOTEXIST.getCode(), Errors.ERR_ID_NOTEXIST.getMessage()).toJsonString();
            }
            //因为type是唯一的,所以是不允许修改的,只有在新增的时候,才会有值
            if (dbObjUnique != null && (!StringUtils.equals(id, dbObjUnique.getId()))) {
                return new AjaxResult(Errors.ERR_UNIQUE_CONSTRAINT.getCode(), Errors.ERR_UNIQUE_CONSTRAINT.getMessage()).toJsonString();
            }
            obj.setUpdateDate(DateUtil.getCurrentDate());
            obj.setModifierId(WebUtil.currentUserId());
            requirementService.update(obj,attachmentList);
        }
        return new AjaxResult().toJsonString();
    }
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(HttpServletRequest request,
                         String id) {
    	Requirement dbObj = requirementService.findByPk(id);
        if (dbObj == null) {
            return new AjaxResult(Errors.ERR_ID_NOTEXIST.getCode(), Errors.ERR_ID_NOTEXIST.getMessage()).toJsonString();
        }
        requirementService.deleteByPkSpecial(dbObj);
        return new AjaxResult().toJsonString();
    }
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public String deleteBatch(HttpServletRequest request,
                         String ids) {
    	String array[]=ids.split("\\,");
		List<Object> list = Arrays.asList(array);
		requirementService.deleteByPkBatchSpecial(list);
		return new AjaxResult().toJsonString();
    }    
    @RequestMapping("/findOne")
    @ResponseBody
    public String findOne(HttpServletRequest request,
                         String id) {
    	Requirement dbObj = requirementService.findByPk(id);
        if (dbObj == null) {
            return new AjaxResult(Errors.ERR_ID_NOTEXIST.getCode(), Errors.ERR_ID_NOTEXIST.getMessage()).toJsonString();
        }
        List<Attachment> attachList=attachmentDao.findByBusinessId(id);
        dbObj.setImgBase64(AttachUtil.imgToBase64Str(attachList));
        return new AjaxResult(dbObj).toJsonString();
    }    
    public static void main(String[] args) {
		System.out.println("dddd$$aaa".split("\\$\\$")[1]);
		
	}
}
