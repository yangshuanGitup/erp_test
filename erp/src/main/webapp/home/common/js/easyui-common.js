$(function(){
	$('.easyui-tree-menu .tree-node').bind("click",function(){
		var a=$(this).find("a");
		var url=$(a).attr("data-link");
		var title = $(a).text();
		var iconCls = $(a).attr('data-icon');
		var iframe = $(a).attr('iframe')==1?true:false;
		addTab(title,url,iconCls,false);
	});
	//添加首页
	var tabPanel = $('#center-main-tabs');
	tabPanel.tabs('add',{
		title:"首页",
		href:baseUrl+'/support/technology/requirement/page',
		iconCls:'icon-tip',
		cls:'pd3',
		closable:false,
		tools: [{
            iconCls: 'icon-mini-refresh',
            handler: function () {
                refreshTab('首页');//刷新当前指定tab
            }
        }]
	});	
});
/**
* Name 添加菜单选项
* Param title 名称
* Param href 链接
* Param iconCls 图标样式
* Param iframe 链接跳转方式（true为iframe，false为href）
*/	
function addTab(title, href, iconCls, iframe){
	var tabPanel = $('#center-main-tabs');
	if(!tabPanel.tabs('exists',title)){
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:100%;"></iframe>';
		if(iframe){
			tabPanel.tabs('add',{
				title:title,
				content:content,
				iconCls:iconCls,
				fit:true,
				cls:'pd3',
				closable:true
			});
		}
		else{
			tabPanel.tabs('add',{
				title:title,
				href:href,
				iconCls:iconCls,
				fit:true,
				cls:'pd3',
				closable:true,
				tools: [{
	                iconCls: 'icon-mini-refresh',
	                handler: function () {
	                    refreshTab(title);//刷新当前指定tab
	                }
	            }]
			});
		}
	}
	else
	{
		tabPanel.tabs('select',title);
	}
}
//刷新当前标签Tabs
function refreshTab(title) {
	var timestamp = (new Date()).valueOf();
	var tt=$('#center-main-tabs');
	tt.tabs('select', title);
	var currentTab =tt.tabs('getTab', title);
    var url = $(currentTab.panel('options')).attr('href');
    $('#center-main-tabs').tabs('update', {
        tab: currentTab,
        options: {
            href: url+"?time="+timestamp
        }
    });
    currentTab.panel('refresh');
}
/**
* Name 移除菜单选项
*/
function removeTab(){
	var tabPanel = $('#center-main-tabs');
	var tab = tabPanel.tabs('getSelected');
	if (tab){
		var index = tabPanel.tabs('getTabIndex', tab);
		tabPanel.tabs('close', index);
	}
}