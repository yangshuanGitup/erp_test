var datagrid;
var rowEditor = undefined;
$(function() {
	init();
	bindEvent();
	var selectIndex=0;
	datagrid = $("#grid-data").datagrid(
					{
						url : baseUrl+"/message/email/pagedata",// 加载的URL
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
								checkbox:true
							},							
							{
								field : 'id',
								title : 'id',
								width : 300,
								editor : {
									type : 'text',
									options : {
										required : true
									}
								}
							}
						]],
						loadFilter: function(data){
						        var grid_data = {};
						        grid_data.total = data.responseData.count;
						        grid_data.rows = data.responseData.data;
						        return grid_data;
						 },
						columns : [ [ // 每个列具体内容
						 {
							field : 'bcc',
							title : 'bcc',
							width : 400,
							editor : {
								type : 'datebox',
								options : {
									required : true
								}
							}
						}, {
							field : 'subject',
							title : 'subject',
							width : 400,
							editor : {
								type : 'combobox',
								options : {
									required : true,
									valueField:'value',
				                    textField:'text',
									 data : [ {
										value : '1',
										text : '初中'
									}, {
										value : '2',
										text : '高中'
									}, {
										value : '3',
										text : '大专'
									}, {
										value : '4',
										text : '本科'
									} ]										
								}
							}
						}, {
							field : 'content',
							title : 'content',
							width : 500,
							editor : {
								type : 'checkbox',
								options : {
									required : true,
									valueField:'value',
				                    textField:'text',
									 data : [ {
										value : '0',
										text : '正确'
									}, {
										value : '1',
										text : '错误'
									} ]	
								}
							}
						}, {
							field : 'replaceText',
							title : 'replaceText',
							width : 200,
							editor : {
								type : 'datebox',
								options : {
									required : true								
								}
							}
						} , {
							field : 'createD',
							title : 'createD',
							width : 200,
							editor : {
								type : 'text',
								options : {
									required : true
								}
							}
						}] ],
						toolbar : [ // 工具条
								{
									text : "增加",
									iconCls : "icon-add",
									handler : function() {// 回调函数
										//弹出窗口
										$("#form-dialog-email").window('open');
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
						                        url:baseUrl+'/message/email/delete?id='+selectedRow.id,    
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
					                        url:baseUrl+'/message/email/findOne?id='+selectedRow.id,
					                        dataType : 'json',
					                        success:function(result){
					            				if (typeof result =="string") {
					            					result = JSON.parse(result);
					            				}
					            	            if(result.status==1){
					            	            	$("#form-email").form('clear');
					            	            	$('#form-email').form('load',result.responseData);
					            	         	    $("#form-dialog-email").window('open');
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
										var a=$('#grid-data').datagrid('getSelections'); 
										var b=$('#grid-data').datagrid('getChecked'); 
										var c=$('#grid-data').datagrid('getSelected'); 
										return false;
										
										var rows = $('#grid-data').datagrid('getChecked'); //获取选中行
										if(selectedRow==null){
											$.messager.alert('提示',"你没选中任何一行",'error');
											return;
										}
										return;
										$.messager.confirm('确认','确认删除?',function(){  
						                $.ajax({  
						                        url:baseUrl+'/message/email/delete?id='+selectedRow.id,    
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
	$('#form-email-subject').combobox({
	    data:email_temp_excel_data,
	    valueField:'value',
	    textField:'text'
	});
	$("#txtContent").pasteUploadImage();
	tinymce.init({
		  selector: '#txtContent',
		  height: 200,
		  plugins: [
			    'paste',
		        "advlist autolink lists link image charmap print preview anchor",
		        "searchreplace visualblocks code fullscreen",
		        "insertdatetime media table contextmenu paste imagetools"
		   ],
		    toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image",
		  // imagetools_cors_hosts: ['www.tinymce.com', 'codepen.io'],
		  content_css: [
		    '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
		    '//www.tinymce.com/css/codepen.min.css'
		  ]
		}); 
//	$("#form-email").window('open');
	    document.querySelector('#editDiv').addEventListener('paste',function(e){  
	     var cbd = e.clipboardData;  
	        var ua = window.navigator.userAgent;  
	        // 如果是 Safari 直接 return  
	        if ( !(e.clipboardData && e.clipboardData.items) ) {  
	            return ;  
	        }  
	        // Mac平台下Chrome49版本以下 复制Finder中的文件的Bug Hack掉  
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
	                // blob 就是从剪切板获得的文件 可以进行上传或其他操作  
	                /*-----------------------与后台进行交互 start-----------------------*/  
	                /*var data = new FormData();  
	                data.append('discoverPics', blob);  
	                $.ajax({  
	                    url: '/discover/addDiscoverPicjson.htm',  
	                    type: 'POST',  
	                    cache: false,  
	                    data: data,  
	                    processData: false,  
	                    contentType: false,  
	                    success:function(res){  
	                        var obj = JSON.parse(res);  
	                        var wrap = $('#editDiv');  
	                        var file = obj.data.toString();  
	                        var img = document.createElement("img");  
	                        img.src = file;  
	                        wrap.appendChild(img);  
	                    },error:function(){  
	                          
	                    }  
	                })*/  
	                /*-----------------------与后台进行交互 end-----------------------*/  
	                /*-----------------------不与后台进行交互 直接预览start-----------------------*/  
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
	                /*-----------------------不与后台进行交互 直接预览end-----------------------*/  
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
   $("#form-email-save").bind("click",function() {
	   $("#form-email").form('submit', {  
	        url : baseUrl+'/message/email/add',  
	        dataType : 'json',  
	        success : function(result) { 
				if (typeof result =="string") {
					result = JSON.parse(result);
				}
	             if(result.status==1){
	            	 $.messager.alert('提示','操作成功','info',function(){
	            		 $("#form-dialog-email").window('close');
	            		 $("#form-email").form('clear');
	            		 $("#grid-data").datagrid('reload');
	            	 });
	             }
	             else{
	            	 $.messager.alert('提示',result.errorMsg,'error');
	             }
	            
	        }  
	    });  
	});
   $("#form-email-cancle").bind("click",function() {
	   $("#form-dialog-email").window('close');
	   $("#form-email").form('clear');
	});   
}