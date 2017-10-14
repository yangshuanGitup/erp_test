<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/frame/jquery-easyui-1.5.3/themes/default/easyui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/frame/jquery-easyui-1.5.3/themes/icon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/home/common/css/icon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/home/common/css/main.css">
<div class="header-left" style="position: absolute; z-index: 1; left: 15px; top: 0;">
	<img alt="" height="60px;" width="356px;" src="/erp/home/logo.png">
</div>
<div class="header-right" style="position: absolute; z-index: 1; right: 5px; top: 0; color: #fff; text-align: right;">
	<p>
		<strong class="easyui-tooltip" title="2条未读消息">admin</strong>，欢迎您！
	</p>
	<p>
		<a href="#">网站首页</a>|<a href="#">支持论坛</a>|<a href="#">帮助中心</a>|<a href="#">安全退出</a>
	</p>
</div>
<script src="<%=request.getContextPath()%>/home/common/js/base-loading.js"></script> 