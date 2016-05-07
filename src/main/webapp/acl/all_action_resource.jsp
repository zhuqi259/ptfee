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
<title>OA办公自动化平台 - 操作资源授权主界面</title>
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
<link href="acl/treetable/stylesheets/master.css" rel="stylesheet" type="text/css" />
<link href="acl/treetable/stylesheets/jquery.treeTable.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="acl/treetable/javascripts/jquery.treeTable.js"></script>
<script type="text/javascript">
$(function(){
	$(".treeTable").treeTable({
		initialState: "expanded"
	});
	
	$("a.oper,ins").toggle(
		function(){ //允许
			if($(this).is("ins")){
				$(this).parent().removeClass("jstree-extends");
				$(this).removeClass("jstree-normal jstree-permit jstree-deny").addClass("jstree-permit");
			}else{
				$(this).removeClass("jstree-extends");
				$(this.children[0]).removeClass("jstree-normal jstree-permit jstree-deny").addClass("jstree-permit");
			}
		},
		function(){ //拒绝
			if($(this).is("ins")){
				$(this).parent().removeClass("jstree-extends");
				$(this).removeClass("jstree-normal jstree-permit jstree-deny").addClass("jstree-deny");
			}else{
				$(this).removeClass("jstree-extends");
				$(this.children[0]).removeClass("jstree-normal jstree-permit jstree-deny").addClass("jstree-deny");
			}
		},
		function(){ //取消
			if($(this).is("ins")){
				$(this).parent().removeClass("jstree-extends");
				$(this).removeClass("jstree-normal jstree-permit jstree-deny").addClass("jstree-normal");
			}else{
				$(this).removeClass("jstree-extends");
				$(this.children[0]).removeClass("jstree-normal jstree-permit jstree-deny").addClass("jstree-normal");
			}
		},
		function(){ //继承
			if($(this).is("ins")){
				$(this).parent().addClass("jstree-extends");
				//$(this).removeClass("jstree-normal jstree-permit jstree-deny").addClass("jstree-deny");
			}else{
				$(this).addClass("jstree-extends");
				//$(this.children[0]).removeClass("jstree-normal jstree-permit jstree-deny").addClass("jstree-deny");
			}
		}
	);
});
function permit_all(){
	$("a.oper").removeClass("jstree-extends");
	$(".jstree-checkbox").removeClass("jstree-normal jstree-permit jstree-deny").addClass("jstree-permit");
}

function deny_all(){
	$("a.oper").removeClass("jstree-extends");
	$(".jstree-checkbox").removeClass("jstree-normal jstree-permit jstree-deny").addClass("jstree-deny");
}

function cancel_all(){
	$("a.oper").removeClass("jstree-extends");
	$(".jstree-checkbox").removeClass("jstree-normal jstree-permit jstree-deny").addClass("jstree-normal");
}

function extends_all(){
	$("a.oper").addClass("jstree-extends");
}

//保存授权信息
function auth(){
	//查找拥有jstree-deny的ins元素，或拥有jstree-permit的ins元素，
	//或拥有jstree-extends的a下面的ins元素
	//查询出来的全部是ins类型的元素
	var allNodes = $("ins.jstree-deny,ins.jstree-permit,a.jstree-extends ins");
	var param = "principalType=${principalType}&principalId=${principalId}";
	for(var i=0; i<allNodes.length; i++){
		var n = $(allNodes[i]);
		var resourceId = n.attr("resourceId");
		var operIndex = n.attr("operIndex");
		var permit;
		var ext;
		if(n.hasClass("jstree-permit")){ //允许
			permit = true;
		}else{ //拒绝
			permit = false;
		}
		
		if(n.parent().hasClass("jstree-extends")){ //继承
			ext = true;
		}else{
			ext = false;
		}
		param = param + "&authvos["+i+"].resourceId="+resourceId;
		param = param + "&authvos["+i+"].operIndex="+operIndex;
		param = param + "&authvos["+i+"].permit="+permit;
		param = param + "&authvos["+i+"].ext="+ext;
	}
	var url = "system/acl!authActionResource.action";
	$.post(
		url,
		param,
		function(){
			alert("已授权");
		}
	);
}

//初始化授权表格
$(function(){
	$.getJSON(
		"system/acl!findActionResourceAcls.action?principalType=${principalType}&principalId=${principalId}",
		function(data){
			for(var i=0; i<data.length; i++){
				var authvo = data[i];
				var resourceId = authvo.resourceId;
				var operIndex = authvo.operIndex;
				var permit = authvo.permit;
				var ext = authvo.ext;
				var node = $("ins[resourceId="+resourceId+"][operIndex="+operIndex+"]");
				if(permit){//允许
					node.removeClass("jstree-deny jstree-normal").addClass("jstree-permit");
				}else{//拒绝
					node.removeClass("jstree-permit jstree-normal").addClass("jstree-deny");
				}
				if(ext){ //继承
					node.parent().addClass("jstree-extends");
				}
			}
		}
	);
});

</script>
</head>

<body>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  	<td colspan="<s:property value="#ress.size()"/>">
  	<a href="system/acl!allMenuResource.action?principalId=${principalId}&principalType=${principalType}">菜单资源授权</a>
  	<a href="system/acl!allActionResource.action?principalId=${principalId}&principalType=${principalType}" style="color:red">操作资源授权</a>
  	<a href="javascript:auth();">保存授权</a> | 
  	<a href="javascript:permit_all();">全部允许</a>
  	<a href="javascript:deny_all();">全部拒绝</a>
  	<a href="javascript:cancel_all();">全部取消</a>
  	<a href="javascript:extends_all();">全部继承</a>
  	<hr/>
  	</td>
  </tr>
  <tr>
  	<td valign="top">
<table class="treeTable" >
  <thead> 
    <tr> 
      <th>操作资源名称</th> 
      <th>操作</th> 
    </tr> 
  </thead> 
  <tbody> 
  	 <s:iterator value="#ress" var="res">
     <tr id="res-<s:property value="#res.id"/>" class="<s:if test="#res.parent != null">child-of-res-<s:property value="#res.parent.id"/></s:if>">
     	<td><s:property value="#res.name"/></td>
     	<td>
     	<s:iterator value="opers">
     	<a class="oper"><ins class="jstree-checkbox jstree-normal" resourceId="<s:property value="#res.id"/>" operIndex="<s:property value="value.operIndex"/>">&nbsp;&nbsp;&nbsp;&nbsp;</ins><s:property value="value.operName"/>
     	</a>&nbsp;
     	</s:iterator>
     	</td>
     </tr>
     </s:iterator>
  </tbody> 
</table>   	
  	</td>
  </tr>
</table>

</body>
</html>

