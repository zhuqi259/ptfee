<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎使用配套费管控系统</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script type="text/javascript">
	function Fproject(id) {
		window.location.href = "person/project!approveInput.action?id=" + id;
	}
	
	function TaskInput(tid, id){
		window.location.href = "person/project!taskInput.action?tid=" + tid +"&id=" +id;
	}
	
	var info1 = '<font color="purple" size="6">【任务提醒】</font>&nbsp;&nbsp;&nbsp;&nbsp;您有【待办工程】&nbsp;';
	var info2 = '&nbsp;项<br><hr><font color="black" size="5">工程名称</font><br><font color="purple" size="4">--------&gt;任务1</font><br><font color="purple" size="4">--------&gt;任务...</font><br>';
	$(function() {
		$.getJSON("system/index!count.action?t=" + new Date(),
			function(data) {
				var count = data["count"];
				var datas = data["datas"];
				var text = "";
				for ( var i = 0; i < datas.length; i++) {
					var projId = datas[i].project.id;
					var append = "<a href='javascript:void(0);' onclick='Fproject("
							+ projId
							+ ")'>"
							+ (i + 1)
							+ ".【" + datas[i].project.proname + "】</a><br>";
					text += append;
					var appTaskText = "";
					jQuery.each(datas[i].data, function(j, item) {
						appTaskText += '<font color="purple" size="4">--------&gt;任务名称:&nbsp;&nbsp;'
									+	'<a href="javascript:void(0);" onclick="TaskInput('
									+ 	item.id + ','+ projId
									+ 	')"><font color="green" size="4">'
									+ 	item.taskname
									+ '</font></a><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;任务发起人:&nbsp;&nbsp;'
									+ item.username
									+ '<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;任务发起时间:&nbsp;&nbsp;'
									+ item.time
									+ '</font><br>';
						});
					text += appTaskText;							
				}
				if (count > 0) {
					$("#tipText").html(info1 + count + info2 + text)
								 .css('left', '250px').css('top', '50px').fadeIn();
				}
			});
	});
</script>
<style type="text/css">
<!--
#tipText {
	border: 1px solid green;
	width: 600px;
	text-align: left;
	padding: 6px;
	background-color: #FFC;
	position: absolute;
	z-index: 99;
	display: none; /*使div初始化时隐藏*/
}
-->
</style>
</head>
<body>
	欢迎使用新建小区配套费管控系统
	<div id="tipText" style="display: none; color: red"></div>
</body>
</html>

