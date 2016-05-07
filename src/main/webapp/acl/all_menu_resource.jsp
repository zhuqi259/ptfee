<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>    
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>OA办公自动化平台 - 所有菜单资源</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script type="text/javascript" src="js/jquery.jstree.aclcheckbox.js"></script>
<script type="text/javascript">
$(function(){

	//点击了某个节点的菜单：允许
	function permit(node){
		//this是树对象本身
		this.permit_node(node);
	}
	function deny(node){
		//alert("拒绝:"+node.attr("menuId"));
		this.deny_node(node);
	}
	function cancel(node){
		//alert("取消:"+node.attr("menuId"));
		this.cancel_node(node);
		
		//TODO 查询父亲的授权信息，并根据父亲的授权信息改变界面
	}
	function _extends(node){
		//alert("继承:"+node.attr("menuId"));
		this.extends_node(node);
		
		//TODO 查询父亲的授权信息，并根据父亲的授权信息改变界面
	}
	function permitAll(node){
		//alert("全部允许："+node.attr("menuId"));
		this.permit_all(node);
	}
	function denyAll(node){
		//alert("全部拒绝:"+node.attr("menuId"));
		this.deny_all(node);
	}
	function cancelAll(node){
		//alert("全部取消:"+node.attr("menuId"));
		this.cancel_all(node);
		
		//TODO 查询父亲的授权信息，并根据父亲的授权信息改变界面
	}
	function extendsAll(node){
		//alert("全部继承:"+node.attr("menuId"));
		this.extends_all(node);
		
		//TODO 查询父亲的授权信息，并根据父亲的授权信息改变界面
	}
	
	var contextmenu_items = function(node){
		return {
			"permit":{"label":"允许","action":permit},
			"deny":{"label":"拒绝","action":deny},
			"cancel":{"label":"取消","action":cancel},
			"extends":{"label":"继承","action":_extends},
			"permitAll":{"label":"全部允许","action":permitAll},
			"denyAll":{"label":"全部拒绝","action":denyAll},
			"cancelAll":{"label":"全部取消","action":cancelAll},
			"extendsAll":{"label":"全部继承","action":extendsAll}
		}
	};
	
	//初始化授权表格
	var maxMenus = <s:property value="#menuIds.size()"/>;
	var loadedMenus = 0;
	function initTables(){
		loadedMenus = loadedMenus + 1;
		if(loadedMenus == maxMenus){ //如果所有菜单都已经全部加载，发出请求，加载初始化的授权数据
			$.getJSON(
				"system/acl!findMenuAcls.action?principalType=${principalType}&principalId=${principalId}",
				function(data){
					for(var i=0; i<data.length; i++){
						var authvo = data[i];
						var resourceId = authvo.resourceId;
						var permit = authvo.permit;
						var ext = authvo.ext;
						var node = $("#"+resourceId);
						if(permit){//允许
							node.removeClass("jstree-deny jstree-normal jstree-extends").addClass("jstree-permit");
						}else{//拒绝
							node.removeClass("jstree-permit jstree-normal jstree-extends").addClass("jstree-deny");
						}
						if(ext){ //继承
							node.addClass("jstree-extends");
						}
					}
				}
			);
		}
	}

	<s:iterator value="menuIds">
	$("#menuTree_<s:property/>").bind(
		"loaded.jstree", //当菜单树加载完毕，触发本事件被执行
		function(event,data){
			//默认打开所有的节点
			$("#menuTree_<s:property/>").jstree("open_all",-1);
			
			//初始化授权表格
			initTables();
		}
	);
	/**
	* 提供右键菜单
	* 允许
	* 拒绝
	* 取消
	* 继承
	* 全部允许（本节点，及以下的所有子节点全部允许）
	* 全部拒绝（本节点，及以下的所有子节点全部拒绝）
	* 全部取消（本节点，及以下的所有子节点全部取消，然后根据父亲的授权情况，显示某些节点是继承拒绝或继承取消（显示为绿色））
	* 全部继承（本节点，及以下的所有子节点全部继承父亲（全部显示背景为绿色），然后根据父亲的授权情况，显示某些节点是继承拒绝或继承取消或未定义（不打勾/叉））
	* 点击的时候，只控制本节点，在允许/拒绝之间进行切换，如果要做其它操作，请点击右键
	*/
	$("#menuTree_<s:property/>").jstree({ 
		"json_data" : {
			"ajax" : {
				"url" : "system/acl!allMenuResourceTree.action?topMenuId=<s:property/>",
				"data": function(node){
					return {id: node.attr ? node.attr("id"):0};
				}
			}
		},
		"themes" : {
			"theme" : "classic"
		},
		"contextmenu" : {
			"show_at_node":false, //将菜单显示在鼠标所在的地方
			"items":contextmenu_items //右键点击显示的菜单项
		},
		"plugins" : [ "themes", "json_data","ui","aclcheckbox","contextmenu"]
	});
	</s:iterator>
});

function refresh(){
	//调用refresh方法，刷新整棵组织机构树
	//$("#menuTree").jstree("refresh","#roleTree");
}

function auth(){
	try{
		//得到被选中的节点
		var nodes = getAllCheckedMenuId();
		// alert(nodes.length);
		
		//拼装需要传递到后台的参数！
		var param = "principalType=${principalType}&principalId=${principalId}";
		for(var i=0; i<nodes.length; i++){
			var resourceId = nodes[i].attr("menuId");
			var operIndex = 0;
			var permit; //
			var ext;
			if(nodes[i].hasClass("jstree-permit")){
				permit = true; //允许
			}else if(nodes[i].hasClass("jstree-deny")){
				permit = false; //拒绝
			}
			if(nodes[i].hasClass("jstree-extends")){
				ext = true;
			}else{
				ext = false;
			}
			param = param + "&authvos["+i+"].resourceId="+resourceId;
			param = param + "&authvos["+i+"].operIndex="+operIndex;
			param = param + "&authvos["+i+"].permit="+permit;
			param = param + "&authvos["+i+"].ext="+ext;
		}
		//var url = "system/acl!authMenu.action?principalType=${principalType}&principalId=${principalId}"+menuIdsParam;
		var url = "system/acl!authMenu.action";
		// alert(param);
		/**
		* 向后台提交的数据格式如下：
		* authvos[0].resourceId=xx
		* authvos[0].operIndex=xx
		* authvos[0].permission=1/0/-1
		* authvos[1].xxx=xxxx
		*/
		$.post(
			url,
			param,
			function(){
				alert("已授权");
			}
		);
	}catch(e){
		alert(e);
	}

}

//获得被选中的菜单的ID列表
function getAllCheckedMenuId(){
	var menuIds = new Array();
	<s:iterator value="menuIds">
		//首先，得到被选中的节点
		var allChecked<s:property/> = $("#menuTree_<s:property/>").jstree("get_all_auths_node");
		for(var i=0; i<allChecked<s:property/>.length; i++){
			// var menuId = $(allChecked<s:property/>[i]).attr("menuId");
			menuIds.push($(allChecked<s:property/>[i]));
			// alert($(allChecked<s:property/>[i]).attr("menuId"));
		}
	</s:iterator>
	return menuIds;
}

function permit_all(){
	<s:iterator value="menuIds">
		$("#menuTree_<s:property/>").jstree("permit_all");
	</s:iterator>
}

function deny_all(){
	<s:iterator value="menuIds">
		$("#menuTree_<s:property/>").jstree("deny_all");
	</s:iterator>
}

function cancel_all(){
	<s:iterator value="menuIds">
		$("#menuTree_<s:property/>").jstree("cancel_all");
	</s:iterator>
}

function extends_all(){
	<s:iterator value="menuIds">
		$("#menuTree_<s:property/>").jstree("extends_all");
	</s:iterator>
}

</script>
</head>

<body>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  	<td colspan="<s:property value="#menuIds.size()"/>">
  	<a href="system/acl!allMenuResource.action?principalId=${principalId}&principalType=${principalType}" style="color:red">菜单资源授权</a>
  	<a href="system/acl!allActionResource.action?principalId=${principalId}&principalType=${principalType}">操作资源授权</a>
  	<a href="javascript:auth();">保存授权</a> | 
  	<a href="javascript:permit_all();">全部允许</a>
  	<a href="javascript:deny_all();">全部拒绝</a>
  	<a href="javascript:cancel_all();">全部取消</a>
  	<a href="javascript:extends_all();">全部继承</a>
  	<hr/>
  	</td>
  </tr>
  <tr>
  	<s:iterator value="#menuIds">
    <td width="147" id="leftMenu" valign="top">
    	<div id="menuTree_<s:property/>">
    	</div>
    </td>
	</s:iterator>
  </tr>
</table>
</body>
</html>

