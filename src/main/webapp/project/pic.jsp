<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script type="text/javascript">
	$(function() {

		// 兼容IE FF的ByName方法
		var getElementsByName = function(tag, name) {
			var returns = document.getElementsByName(name);
			if (returns.length > 0)
				return returns;
			returns = new Array();
			var e = document.getElementsByTagName(tag);
			for ( var i = 0; i < e.length; i++) {
				if (e[i].getAttribute("name") == name) {
					returns[returns.length] = e[i];
				}
			}
			return returns;
		}

		function flashit() {
			var myDivs = getElementsByName("div", "myDiv");
			
			for ( var i = 0; i < myDivs.length; i++) {
				var myDiv = myDivs[i];
				if (myDiv.style.borderColor == "red") {
					myDiv.style.borderColor = "yellow"
				} else {
					myDiv.style.borderColor = "red"
				}
			}
		}
		setInterval(flashit, 500);
	});
	
	function fun(activityId){
		var id = $("#id").val();
		window.location.href= "<%=basePath%>system/project!activity.action?activityId=" + activityId + "&id=" + id;
	}
	
</script>

</head>

<body>
	<input type="hidden" id="id" name="id" value="${id }">
	<img alt="工作流图" src="person/project!pic.action?id=${ id }"
		style="position:absolute;left:0px;top:0px;" name="project">
	<s:iterator value="acList" var="ac">
		<div name="myDiv"
			style="position:absolute;border:2px solid red;left:${ac.x }px;top:${ac.y }px;width:${ac.width }px;height:${ac.height}px;"></div>
	</s:iterator>
	<s:iterator value="allZActivity" status="st" var="ac">
		<div name="activityDiv" onclick="fun(${ac.id})"
			style="cursor:pointer; background:url('<%=basePath %>images/bg.png') repeat;position:absolute;left:${ac.x }px;top:${ac.y }px;width:${ac.width }px;height:${ac.height}px;">
			<font color="red" size="2" style="font-weight:bold;" >${id }
			</font>
			
			<font color="green" size="2"><br>&nbsp;&nbsp;&nbsp;<s:property
					value="getMyOwner(owner)" />
			</font>
		</div>
	</s:iterator>

</body>
</html>
