<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.label-top {
	margin: 0 0 10px;
	display: block;
}

#editDiv {
	width: 830px;
	display: inline-block;
	height: 200px;
	background: #fff;
	border-radius: 10px;
	line-height: 20px;
	padding: 10px;
	font-size: 14px;
	color: #666;
	resize: none;
	outline: none;
	overflow-y: scroll;
}

#editDiv {
	border: 1px solid #333;
	border-color: rgba(169, 169, 169, 1);
	text-align: left;
}
</style>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center'">
		<div id="grid-data">
		</div>
		<div id="form-dialog-req" class="easyui-window" title="系统需求" data-options="modal:true,closed:true,iconCls:'icon-add',maximizable:false,minimizable:false,collapsible:false" 
				style="padding:10px;">
			<form id="form-req" action="" method="post" enctype="multipart/form-data">
				<div class="easyui-layout" style="width:900px;height:520px;">
					<div data-options="region:'center',border:false">
						<input type="hidden" name="id">
						<input type="hidden" id="attachment-img" name="imgBase64">
						<div style="margin-bottom:20px">	
						 	<input class="easyui-textbox" name="title" data-options="required:true,multiline:true,label:'标题'" style="width:400px;">
						 </div>
						<div style="margin-bottom:20px">	
						 	<input class="easyui-combotree" name="moduleId" data-options="url:'./home/js/support/technology/requirements/module_data.json',required:true,label:'需求模块'" style="width:400px;">
						 </div>						 
					</div>
					<div data-options="region:'east',border:false" style="width:450px;">
						<div style="margin-bottom:20px">	
						 	<input class="easyui-textbox" name="reqUserId" data-options="multiline:true,label:'提出人'" style="width:400px;">
						 </div>	
						<div style="margin-bottom:20px">				 			 			 			 		 
						 	<input class="easyui-combobox" id="form-req-status" name="status" data-options="url:'./home/js/support/technology/requirements/status_data.json',required:true,valueField:'value',textField:'text',label:'状态'" style="width:400px;" >
						 </div>
					</div>
					<div data-options="region:'south',border:false" style="height:400px;">
<!-- 				        <div style="margin-bottom:20px">
				            <label class="label-top" style="width:100%; display:block;">选择文件:</label>
				           <input class="easyui-filebox" style="width:300px" name="file[0]" data-options="buttonIcon:'icon-folder-search',buttonText:'',label:'文件'">  
				           <input class="easyui-filebox" style="width:300px" name="file[1]" data-options="buttonIcon:'icon-folder-search',buttonText:'',label:'文件'">
				        </div>	 -->				
						<div style="margin-bottom:20px">	
						 	<input class="easyui-textbox" name="content" data-options="multiline:true,label:'描述'" style="height:80px;width:850px;">
						</div>	
						<div style="margin-bottom:20px">	
						 	<div id="editDiv" contenteditable="true"></div>
						 </div>
				        <div style="text-align:right;padding:10px">
				            <a  href="javascript:void(0)" class="easyui-linkbutton" id="form-req-save" data-options="iconCls:'icon-ok'" style="width:100px;height:32px">保存</a>
				            <a  href="javascript:void(0)" class="easyui-linkbutton" id="form-req-cancle" data-options="iconCls:'icon-cancel'" style="width:100px;height:32px">取消</a>
				        </div>						 
					</div>					
				</div>
	        </form>			 
		</div>
	</div>
	<div data-options="region:'east',split:true,title:'附件信息'" style="width: 400px;">
			<div class="easyui-tabs" style="width:100%;" data-options="fit:true">
				<div title="基础信息" style="padding: 10px" >
					<p>The tabs has a width of 100%.</p>
				</div>
		</div>
	</div>		
</div>
<script	src="<%=request.getContextPath()%>/home/js/support/technology/requirements/page.js"></script>