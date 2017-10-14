<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="easyui-accordion" data-options="fit:true,border:false">
	<div title="供应链管理"	data-options="selected:true,iconCls:'icon-application-cascade'">
		<ul class="tree easyui-tree easyui-tree-menu">
			<li><span>工厂管理</span>
				<ul>
					<li data-options="iconCls:'icon-chart-organisation'"><span><a href="javascript:void(0)" data-icon="icon-users" data-link="temp/layout-3.html">工厂信息</a></span></li>
					<li data-options="iconCls:'icon-users'"><span><a href="javascript:void(0)" data-icon="icon-users" data-link="<%=request.getContextPath()%>/temp/layout-3.html">工厂价格</a></span></li>
				</ul>
			</li>
			<li><span>船务管理</span>
				<ul>
					<li data-options="iconCls:'icon-user-group'"><span><a href="javascript:void(0)" data-icon="icon-users" data-link="temp/layout-3.html">出口报关</a></span></li>
					<li data-options="iconCls:'icon-cog'"><span><a	href="javascript:void(0)" data-icon="icon-users" data-link="temp/layout-3.html">海外清关</a></span></li>
				</ul>
			</li>
		</ul>
	</div>
	<div title="营销管理" data-options="iconCls:'icon-cart'">
		<ul class="easyui-tree easyui-tree-menu">
			<li data-options="iconCls:'icon-user-group'"><span><a href="javascript:void(0)" data-icon="icon-users" data-link="temp/layout-3.html">客户拜访</a></span></li>
			<li data-options="iconCls:'icon-cog'"><span><a	href="javascript:void(0)" data-icon="icon-users" data-link="https://www.baidu.com">订单管理</a></span></li>
		</ul>
	</div>
	<div title="消息模板配置" data-options="iconCls:'icon-creditcards'">
		<ul class="easyui-tree easyui-tree-menu">
			<li data-options="iconCls:'icon-user-group'"><span><a href="javascript:void(0)" data-icon="icon-users" data-link="<%=request.getContextPath()%>/message/email/page">邮件配置</a></span></li>
		</ul>
	</div>	
</div>