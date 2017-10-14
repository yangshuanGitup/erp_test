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
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'east',title:'侧边分栏',split:true" style="width:400px;"></div>
    <div data-options="region:'center',title:'主数据分栏'" style="padding:5px;"></div>  
</div> 
