<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<meta charset="UTF-8">
	<title>欢迎使用三松erp系统</title>
	</head>
	<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">
		<%@ include file="/basePage/head.jsp" %>	
	</div>
	<div data-options="region:'west',split:true,title:'导航菜单'" style="width:200px;">
		<%@ include file="/basePage/menu.jsp" %>
	</div>
	<div class="center-main" data-options="region:'center'">
        <div id="center-main-tabs" class="easyui-tabs" data-options="border:false,fit:true">  
        </div>
	</div>
	<div data-options="region:'south',border:false"	style="height: 50px; padding: 10px;">
		<%@ include file="/basePage/foot.jsp" %>
	</div>
</body>
</html>