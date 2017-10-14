<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 禁止缓存 -->
<meta HTTP-EQUIV="pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<meta HTTP-EQUIV="expires" CONTENT="0">
<%
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/home/common/css/icon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/easyui-insdep/themes/insdep/easyui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/easyui-insdep/themes/insdep/easyui_animation.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/easyui-insdep/themes/insdep/easyui_plus.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/easyui-insdep/themes/insdep/insdep_theme_default.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/framework/easyui-insdep/themes/insdep/icon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/home/common/css/main.css">
<div class="header-left" style="position: absolute; z-index: 1; left: 15px; top: 0;">
	<img alt="" height="60px;" width="356px;" src="<%=request.getContextPath()%>/home/common/image/logo.png">
</div>
<script type="text/javascript">
	var baseUrl="<%=request.getContextPath()%>";
</script>
<script src="<%=request.getContextPath()%>/home/common/js/base-loading.js"></script>
<script src="<%=request.getContextPath()%>/home/common/js/combo-data.js"></script>