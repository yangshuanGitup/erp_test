package com.singsong.erp.business.system.config.template.ui;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.singsong.erp.base.constants.Errors;
import com.singsong.erp.base.pagin.GridConditionHandle;
import com.singsong.erp.base.pagin.GridSortHandle;
import com.singsong.erp.base.pagin.PaginatedResult;
import com.singsong.erp.base.pagin.Paginator;
import com.singsong.erp.base.vo.AjaxResult;
import com.singsong.erp.business.system.config.template.entity.EmailTemplate;
import com.singsong.erp.business.system.config.template.service.EmailTemplateService;
import com.singsong.erp.framework.init.ReadConf;

@Controller
@RequestMapping("/message/email")
public class EmailTemplateAction {
	@Autowired
	private EmailTemplateService emailTemplateService;
	
	/**
	 * 页面跳转
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/page")
	public String page(HttpServletRequest request, Model model) {
		return "/message/email/page";
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
			String convertFilters = GridConditionHandle.handle(EmailTemplate.class.getCanonicalName(), filters);
			pagin.setFilters(convertFilters);
		}
		if (StringUtils.isNotEmpty(sorts)) {
			String convertSorts = GridSortHandle.handle(EmailTemplate.class.getCanonicalName(), sorts);
			pagin.setSorts(convertSorts);
		}
		pagin.setStart(start);
		pagin.setPageSize(rows);
		PaginatedResult<EmailTemplate> result = emailTemplateService.findPageList(pagin);
		return new AjaxResult(result).toJsonString();
	}
    @RequestMapping("/add")
    @ResponseBody
    public String save(HttpServletRequest request,
    		EmailTemplate obj) {
        //EmailTemplate的type是唯一字段，不能为空
        String uniqueKey = obj.getType();
        if (StringUtils.isEmpty(uniqueKey)) {
            return new AjaxResult(Errors.ERR_UNIQUE_NULL.getCode(), Errors.ERR_UNIQUE_NULL.getMessage()).toJsonString();
        }
        EmailTemplate dbObjUnique = emailTemplateService.findByUnique(uniqueKey);
        if (StringUtils.isEmpty(obj.getId())) {
            if (dbObjUnique != null) {
                return new AjaxResult(Errors.ERR_UNIQUE_CONSTRAINT.getCode(), Errors.ERR_UNIQUE_CONSTRAINT.getMessage()).toJsonString();
            }
            obj.setCreateD(DateTime.now().toDate());
            obj.setUpdateD(obj.getCreateD());
            obj.setCreaterId("yangshuanid");
            obj.setFrom(ReadConf.getCommonProperty("email_auth_user"));
            emailTemplateService.save(obj);
        }
        //更新时候要检验ID是否存在,第二,如果修改了type，则要保证不能与已有记录存在相同type
        else {
            String id = obj.getId();
            EmailTemplate dbObj = emailTemplateService.findByPk(id);
            if (dbObj == null) {
                return new AjaxResult(Errors.ERR_ID_NOTEXIST.getCode(), Errors.ERR_ID_NOTEXIST.getMessage()).toJsonString();
            }
            //因为type是唯一的,所以是不允许修改的,只有在新增的时候,才会有值
            if (dbObjUnique != null && (!StringUtils.equals(id, dbObjUnique.getId()))) {
                return new AjaxResult(Errors.ERR_UNIQUE_CONSTRAINT.getCode(), Errors.ERR_UNIQUE_CONSTRAINT.getMessage()).toJsonString();
            }
            obj.setUpdateD(DateTime.now().toDate());
            emailTemplateService.update(obj);
        }
        return new AjaxResult().toJsonString();
    }
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(HttpServletRequest request,
                         String id) {
    	EmailTemplate dbObj = emailTemplateService.findByPk(id);
        if (dbObj == null) {
            return new AjaxResult(Errors.ERR_ID_NOTEXIST.getCode(), Errors.ERR_ID_NOTEXIST.getMessage()).toJsonString();
        }
        emailTemplateService.deleteByPk(id);
        return new AjaxResult().toJsonString();
    }
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public String deleteBatch(HttpServletRequest request,
                         Object ids[]) {
		List<Object> list = Arrays.asList(ids);
		emailTemplateService.deleteByPkBatch(list);
		return new AjaxResult().toJsonString();
    }    
    @RequestMapping("/findOne")
    @ResponseBody
    public String findOne(HttpServletRequest request,
                         String id) {
    	EmailTemplate dbObj = emailTemplateService.findByPk(id);
        if (dbObj == null) {
            return new AjaxResult(Errors.ERR_ID_NOTEXIST.getCode(), Errors.ERR_ID_NOTEXIST.getMessage()).toJsonString();
        }
        return new AjaxResult(dbObj).toJsonString();
    }    
}
