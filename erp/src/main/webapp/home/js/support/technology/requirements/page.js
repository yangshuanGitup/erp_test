var module_data={
	'1':'营销',	
	'11':'设计',	
	'12':'样品管理',	
	'13':'营销组',	
	'14':'订单',	
	'15':'市场',
	'2':'供应链',	
	'21':'仓库',	
	'22':'船务',	
	'23':'质检',	
	'24':'交付',	
	'3':'职能',	
	'31':'IT',	
	'32':'财务',	
	'33':'行政'
};
var status_data={
	'1':'初始',	
	'2':'计划',	
	'3':'开始',	
	'4':'完成',	
	'5':'作废',	
	'6':'取消'
};
var datagrid;
var rowEditor = undefined;
$(function() {
	init();
	bindEvent();
	var selectIndex=0;
	datagrid = $("#grid-data").datagrid(
					{
						url : baseUrl+"/support/technology/requirement/pagedata",// 加载的URL
						isField : "id",
						pagination : true,// 显示分页
						pageSize : 5,// 分页大小
						fit:true,
						pageList : [ 5, 10, 15, 20 ],// 每页的个数
						iconCls : "icon-save",// 图标
//						title : "列表信息",
						singleSelect:true,
						checkOnSelect:false,
						selectOnCheck:false,
						onLoadSuccess:function(data){
							console.log(data);
							$(datagrid).datagrid('selectRow',selectIndex);
						},
						onSelect:function(rowIndex,rowData){
							selectIndex=rowIndex;
						},
						frozenColumns:[[
 							{
							field : 'ck',
							checkbox : true
						}, {
							field : 'id',
							title : 'id',
							hidden : 'true'
						}, {
							field : 'status',
							title : '需求状态',
							width : 50,
							formatter:function(value){
			                     return status_data[value];
			                }							
						}, {
							field : 'moduleId',
							title : '模块名称',
							width : 100,
							formatter:function(value){
			                     return module_data[value];
			                }
						}, {
							field : 'title',
							title : '标题',
							width : 100
						},							
						]],
						loadFilter: function(data){
						        var grid_data = {};
						        grid_data.total = data.responseData.count;
						        grid_data.rows = data.responseData.data;
						        return grid_data;
						 },
						columns : [ [ // 每个列具体内容
							{
							field : 'content',
							title : '描述',
							width : 200
						}, {
							field : 'reqDeptId',
							title : '需求提出部门',
							hidden : 'true',
							width : 200
						}, {
							field : 'reqUserId',
							title : '需求提出人',
							width : 100
						}, {
							field : 'reqDate',
							title : '需求提出时间',
							hidden : 'true',
							width : 180
						}, {
							field : 'planStartDate',
							title : '计划开始时间',
							hidden : 'true',
							width : 180
						}, {
							field : 'planEndDate',
							title : '计划完成时间',
							hidden : 'true',
							width : 180
						}, {
							field : 'startDate',
							title : '开始时间',
							hidden : 'true',
							width : 180
						}, {
							field : 'finishDate',
							title : '完成时间',
							hidden : 'true',
							width : 180
						}, {
							field : 'solverId',
							title : '需求解决人',
							hidden : 'true',
							width : 180
						}] ],
						toolbar : [ // 工具条
								{
									text : "增加",
									iconCls : "icon-add",
									handler : function() {// 回调函数
										//弹出窗口
										$("#form-dialog-req").window('open');
									}
								},
								{
									text : "删除",
									iconCls : "icon-remove",
									handler : function() {
										var selectedRow = $('#grid-data').datagrid('getSelected'); //获取选中行
										if(selectedRow==null){
											$.messager.alert('提示',"你没选中任何一行",'error');
											return;
										}
										$.messager.confirm('确认','确认删除?',function(){  
						                $.ajax({  
						                        url:baseUrl+'/support/technology/requirement/delete?id='+selectedRow.id,    
						                        success:function(result){
						            				if (typeof result =="string") {
						            					result = JSON.parse(result);
						            				}
						            	             if(result.status==1){
						            	            	 $.messager.alert('提示','操作成功','info',function(){
						            	            		 selectIndex=0;//删除后,要将选中的行设置为第一行
						            	            		 $("#grid-data").datagrid('reload');
						            	            	 });
						            	             }
						            	             else{
						            	            	 $.messager.alert('提示',result.errorMsg,'error');
						            	             }						                        	
						                        }  
						                    });  
								        }); 
									}
								},
								{
									text : "修改",
									iconCls : "icon-edit",
									handler : function() {
										var selectedRow = $('#grid-data').datagrid('getSelected');
										if(selectedRow==null){
											$.messager.alert('提示',"你没选中任何一行",'error');
											return;
										}
					                    $.ajax({  
					                        url:baseUrl+'/support/technology/requirement/findOne?id='+selectedRow.id,
					                        dataType : 'json',
					                        success:function(result){
					            				if (typeof result =="string") {
					            					result = JSON.parse(result);
					            				}
					            	            if(result.status==1){
					            	            	$("#form-req").form('clear');
					            	            	$('#form-req').form('load',result.responseData);
					            	            	var imgBase64Str=result.responseData.imgBase64;
					            	            	renderImg(imgBase64Str);
					            	         	    $("#form-dialog-req").window('open');
					            	            }
					            	            else{
					            	            	$.messager.alert('提示',result.errorMsg,'error');
					            	            }						                        	
					                        }  
					                    });  										
									}
								},{
									text : "批量删除",
									iconCls : "icon-remove",
									handler : function() {
										var rows = $('#grid-data').datagrid('getChecked'); //获取选中行
										if(rows.length==0){
											$.messager.alert('提示',"你没选中任何一行",'error');
											return;
										}
										var ids="";
										for(var i=0;i<rows.length;i++){
											if(i==0){
												ids=rows[i].id;
											}
											else{
												ids=rows[i].id+","+ids;
											}
										}
										$.messager.confirm('确认','确认删除?',function(){  
						                $.ajax({  
						                        url:baseUrl+'/support/technology/requirement/deleteBatch',   
						                        data:{'ids':ids},
						                        success:function(result){
						            				if (typeof result =="string") {
						            					result = JSON.parse(result);
						            				}
						            	             if(result.status==1){
						            	            	 $.messager.alert('提示','操作成功','info',function(){
						            	            		 selectIndex=0;//删除后,要将选中的行设置为第一行
						            	            		 $("#grid-data").datagrid('reload');
						            	            	 });
						            	             }
						            	             else{
						            	            	 $.messager.alert('提示',result.errorMsg,'error');
						            	             }						                        	
						                        }  
						                    });  
								        }); 
									}
								},]
					});
});
function init(){
    document.querySelector('#editDiv').addEventListener('paste',function(e){  
     var cbd = e.clipboardData;  
        var ua = window.navigator.userAgent;  
        if ( !(e.clipboardData && e.clipboardData.items) ) {  
            return ;  
        }  
        if(cbd.items && cbd.items.length === 2 && cbd.items[0].kind === "string" && cbd.items[1].kind === "file" &&  
            cbd.types && cbd.types.length === 2 && cbd.types[0] === "text/plain" && cbd.types[1] === "Files" &&  
            ua.match(/Macintosh/i) && Number(ua.match(/Chrome\/(\d{2})/i)[1]) < 49){  
            return;  
        }  
        for(var i = 0; i < cbd.items.length; i++) {  
            var item = cbd.items[i];  
            if(item.kind == "file"){  
                var blob = item.getAsFile();  
                if (blob.size === 0) {  
                    return;  
                }  
                var reader = new FileReader();  
                var imgs = new Image();   
                imgs.file = blob;  
                reader.onload = (function(aImg) {  
                  return function(e) {  
                    aImg.src = e.target.result;  
                  };  
                })(imgs);  
                reader.readAsDataURL(blob);  
                document.querySelector('#editDiv').appendChild(imgs);  
            }  
        }  
    }, false);  
}
function bindEvent(){
   $("#search").click(function() {
		datagrid.datagrid('load', {
			text : $("#text").val()
		});
	});
   $("#form-req-save").bind("click",function() {
	   var imgBase64Value="";
	   $("#editDiv img").each(function(index,data){
		   var str=$(this).attr("src");
		   var src=str.replace('data:image/png;base64,','');
		   src=src.replace('data:image/gif;base64,','');
		   src=src.replace('data:image/jpeg;base64,','');
		   src=src.replace('data:image/jpg;base64,','');
		   src=src.replace('data:image/x-icon;base64,','');
//		   var baseSrc=encode64(tmp);
		   imgBase64Value=imgBase64Value+src+"$$";
	   });
	   $("#attachment-img").val(imgBase64Value);
	   $("#form-req").form('submit', {  
	        url : baseUrl+'/support/technology/requirement/add',  
	        dataType : 'json',
	        success : function(result) { 
				if (typeof result =="string") {
					result = JSON.parse(result);
				}
	             if(result.status==1){
	            	 $.messager.alert('提示','操作成功','info',function(){
	            		 closeForm();
	            		 $("#grid-data").datagrid('load',{});
	            	 });
	             }
	             else{
	            	 $.messager.alert('提示',result.errorMsg,'error');
	             }
	            
	        }  
	    });  
	});
   $("#form-req-cancle").bind("click",function() {
	   closeForm();
	});   
}
function closeForm(){
	   $("#form-dialog-req").window('close');
	   $("#form-req").form('clear');
	   $("#editDiv").empty();
}
function renderImg(imgBase64Str){
	if(imgBase64Str!=undefined && imgBase64Str!=null){
		var array=imgBase64Str.split("$$");
		for(var i=0;i<array.length;i++){
			var tmp=array[i];
			$("#editDiv").append("<img src='data:image/jpg;base64,"+tmp+"'>");
		}
	}
}