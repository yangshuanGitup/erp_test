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
<link rel="stylesheet" href="<%=request.getContextPath()%>/frame/easyui-insdep/themes/insdep/easyui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/frame/easyui-insdep/themes/insdep/easyui_animation.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/frame/easyui-insdep/themes/insdep/easyui_plus.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/frame/easyui-insdep/themes/insdep/insdep_theme_default.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/frame/easyui-insdep/themes/insdep/icon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/home/common/css/icon.css">
<script type="text/javascript">
	var baseUrl="<%=request.getContextPath()%>";
</script>
<script src="<%=request.getContextPath()%>/frame/easyui-insdep/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/frame/easyui-insdep/jquery.easyui.min.js"></script>
<script src="<%=request.getContextPath()%>/frame/easyui-insdep/themes/insdep/jquery.insdep-extend.min.js"></script>
<script src="<%=request.getContextPath()%>/frame/easyui-insdep/locale/easyui-lang-zh_CN.js"></script>