package com.singsong.erp.framework.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.singsong.erp.base.constants.Errors;
import com.singsong.erp.base.util.CommonLogger;
import com.singsong.erp.base.vo.AjaxResult;

/**
 * spring mvc 全局异常处理类
 * @author 杨树安
 * @version 创建时间：2017年9月26日 下午9:21:31
 */
public class GraphHandlerExceptionResolver implements HandlerExceptionResolver {
	private static Logger logger = CommonLogger.getLogger(GraphHandlerExceptionResolver.class);
	public ModelAndView resolveException(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) {
		response.setContentType("text/html;charset=utf-8");//对于要用过response写出数据的,就这样预设置,对于特殊的response写出(导出xml,excel等文件)的时候,再重新设置下
        ModelAndView view=null;
        try {
        	logger.error("调用url="+request.getRequestURI()+" 异常", ex);
            String result=new AjaxResult(Errors.FAILURE.getCode(),Errors.FAILURE.getMessage()).toJsonString();
            if(StringUtils.isNotEmpty(request.getParameter("callback"))){
            	result=request.getParameter("callback")+"("+result+")";
            }
            response.getWriter().write(result);
            response.getWriter().flush();
        } catch (IOException e) {
            logger.error("resolveException 异常", e);
        }
        return view;
	}
}
