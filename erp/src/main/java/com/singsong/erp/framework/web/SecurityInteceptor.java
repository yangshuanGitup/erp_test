package com.singsong.erp.framework.web;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.singsong.erp.base.util.CommonLogger;
/**
 * 拦截器
 * @author 杨树安
 * @version 创建时间：2017年9月28日 下午2:40:58
 */
public class SecurityInteceptor implements HandlerInterceptor {
	private static Logger logger = CommonLogger.getLogger(SecurityInteceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.setContentType("text/html;charset=utf-8");// 对于要用过response写出数据的,就这样预设置,对于特殊的response写出(导出xml,excel等文件)的时候,再重新设置下
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		if (handler instanceof HandlerMethod) {
			StringBuilder sb = new StringBuilder(1000);
			HandlerMethod h = (HandlerMethod) handler;
			sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
			sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
			sb.append("Params    : ").append(getParamString(request.getParameterMap())).append("\n");
			sb.append("URI       : ").append(request.getRequestURI()).append("\n");
			logger.info(sb.toString());
		}
		return true;
	}

	private String getParamString(Map<String, String[]> map) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String[]> e : map.entrySet()) {
			sb.append(e.getKey()).append("=");
			String[] value = e.getValue();
			if (value != null && value.length == 1) {
				sb.append(value[0]).append("\t");
			} else {
				sb.append(Arrays.toString(value)).append("\t");
			}
		}
		return sb.toString();
	}
	/**
	 * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，如性能监控中我们可以在此记录结束时间并输出消耗时间，还可以进行一些资源清理，类似于try-catch-finally中的finally，但仅调用处理器执行链中preHandle返回true的拦截器的afterCompletion。
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {

	}

	/**
	 * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view) {	
		long startTime = (Long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTime;
		if (handler instanceof HandlerMethod) {
			StringBuilder sb = new StringBuilder(1000);
			sb.append("CostTime  : ").append(executeTime).append("ms").append("\n");
			sb.append("-------------------------------------------------------------------------------");
			logger.info(sb.toString());
		}
	}
}
