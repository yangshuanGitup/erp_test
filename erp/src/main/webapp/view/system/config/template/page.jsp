<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<style>.label-top{ margin:0 0 10px; display:block;}
	         #editDiv{  
                width:600px;  
                display: inline-block;  
                height: 300px;  
                background:#fff;  
                border-radius:10px;  
                line-height: 20px;  
                padding:10px;  
                font-size: 14px;  
                color:#666;  
                resize: none;  
                outline: none;  
                overflow-y: scroll;  
            }  
            #editDiv{  
                border:1px solid #333;  
                border-color:rgba(169,169,169,1);  
                text-align: left;  
            } 
	</style>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',title:'列表信息'">
		<div id="grid-data">
		</div>
		<div id="form-dialog-email" class="easyui-window" title="邮件发送模板" data-options="modal:true,closed:true,iconCls:'icon-add',maximizable:false,minimizable:false,collapsible:false" 
				style="padding:10px;">
			<form id="form-email" action="" method="post">
				<input type="hidden" name="id">
				<div style="margin-bottom:20px">	
				 	<input class="easyui-textbox" name="subject" data-options="required:true,label:'邮件主题'" style="width:560px;">
				 </div>
				<div style="margin-bottom:20px">	
				 	<input class="easyui-textbox" name="to" data-options="required:true,multiline:true,label:'邮件接收'" style="height:80px;width:560px;">
				 </div>	
				<div style="margin-bottom:20px">	
				 	<input class="easyui-textbox" name="cc" data-options="multiline:true,label:'邮件抄送'" style="height:80px;width:560px;">
				 </div>	
				<div style="margin-bottom:20px">	
				 	<input class="easyui-textbox" name="bcc" data-options="multiline:true,label:'邮件密送'" style="height:80px;width:560px;">
				 </div>	
				<div style="margin-bottom:20px">	
				 	<input class="easyui-textbox" name="content" data-options="multiline:true,required:true,label:'邮件内容'" style="height:80px;width:560px;">
				 </div>
				<div style="margin-bottom:20px">	
				 	<div id="editDiv" contenteditable="true"></div>
				 </div>
				<div style="margin-bottom:20px">				 			 			 			 		 
				 	<input class="easyui-combobox" id="form-email-subject" name="type" data-options="required:true,label:'模板类型'" style="width:200px;">
				 </div>
		        <div style="text-align:right;padding:10px">
		            <a  href="javascript:void(0)" class="easyui-linkbutton" id="form-email-save" data-options="iconCls:'icon-ok'" style="width:100px;height:32px">保存</a>
		            <a  href="javascript:void(0)" class="easyui-linkbutton" id="form-email-cancle" data-options="iconCls:'icon-cancel'" style="width:100px;height:32px">取消</a>
		        </div>
	        </form>			 
		</div>
	</div>
	<div data-options="region:'east',split:true,title:'详细信息'" style="width: 400px;">
			<div class="easyui-tabs" style="width:100%;" data-options="fit:true">
				<div title="基础信息" style="padding: 10px" >
					<p>The tabs has a width of 100%.</p>
				</div>
				<div title="附件信息" style="padding: 10px;">
					<div class="easyui-accordion" data-options="multiple:true">
							<div title="Language" data-options="iconCls:'icon-ok',collapsed:false" style="padding:10px;">
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>								
							</div>
							<div title="Java" data-options="iconCls:'icon-ok',collapsed:false" style="padding:10px;">
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>	
							</div>
							<div title="C#" data-options="iconCls:'icon-ok',collapsed:false" style="padding:10px;">
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming langua杨夺在lgorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>	
							</div>
							<div title="Ruby" data-options="iconCls:'icon-ok',collapsed:false" style="padding:10px;">
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming langua杨夺在lgorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
							</div>
							<div title="Fortran" data-options="iconCls:'icon-ok',collapsed:false" style="padding:10px;">
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming langua杨夺在lgorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming langua杨夺在lgorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
								<p>A programming language is a formal language designedess algorithms precisely.</p>
							</div>
					</div>				
				</div>
		</div>
	</div>	
</div>
<script	src="<%=request.getContextPath()%>/home/js/message/email/page.js"></script>
<script type="text/javascript">
function setHeight(){
	var c = $('#cc');
	var p = c.layout('panel','center');	// get the center panel
	var oldHeight = p.panel('panel').outerHeight();
	p.panel('resize', {height:'auto'});
	var newHeight = p.panel('panel').outerHeight();
	c.layout('resize',{
		height: (c.height() + newHeight - oldHeight)
	});
}
</script>
