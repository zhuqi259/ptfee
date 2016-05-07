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
<title>列出ID以及今年已经使用的ID号</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>

<link href="org/js/party.css" rel="stylesheet" type="text/css">

<style type="text/css" title="currentStyle">
@import "js/datatable/css/demo_page.css";

@import "js/datatable/css/demo_table.css";

<!--
body {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #666666;
	background: #fff;
	text-align: center;
}

* {
	margin: 0;
	padding: 0;
}

a {
	color: #1E7ACE;
	text-decoration: none;
}

a:hover {
	color: #000;
	text-decoration: underline;
}

h3 {
	font-size: 14px;
	font-weight: bold;
}

pre,p {
	color: #1E7ACE;
	margin: 4px;
}

input,select,textarea {
	padding: 1px;
	margin: 2px;
	font-size: 12px;
}

.buttom {
	padding: 1px 10px;
	font-size: 12px;
	border: 1px #1E7ACE solid;
	background: #D0F0FF;
}

#formwrapper {
	width: 70%;
	margin: 15px auto;
	padding: 10px;
	text-align: left;
}

fieldset {
	padding: 10px;
	margin-top: 5px;
	border: 1px solid #1E7ACE;
	background: #fff;
}

fieldset legend {
	color: #1E7ACE;
	font-weight: bold;
	background: #fff;
}

fieldset label {
	float: left;
	width: 60px;
	text-align: right;
	padding: 4px;
	margin: 1px;
}

fieldset div {
	clear: left;
	margin-bottom: 2px;
}

.enter {
	text-align: center;
}

.clear {
	clear: both;
}
-->
</style>

</head>
<body>
	<div align="center" style="margin-top: 10px">
		<font color="red" size="4px">【注】ID修改必须非常小心,其注意查看已使用ID号！</font>
	</div>
	<div id="formwrapper">
		<form action="system/idtable.action">
			<input type="hidden" name="method:update" value=""> <input
				type="hidden" name="id" value="${id }">
			<fieldset>
				<legend>请设置ID表</legend>
				<div>
					<label for="myYear">年限</label><input type="text" name="myYear"
						value="${myYear }" readonly="readonly">年
				</div>
				<div>
					<label for="myId">序号ID</label><input type="text" name="myId"
						value="${myId }">
				</div>
				<div class="enter">
					<input name="submit" type="submit" class="buttom" value="提交" /> <input
						name="reset" type="reset" class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>
	<hr>
	<br>
	<div align="center">
		<table border="1" cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>项目名称</td>
				<td>工程编号</td>
			</tr>

			<s:iterator value="allProjectByToday" var="project">
				<tr align="center">
					<td>${proname }</td>
					<td>${fid }</td>
				</tr>
			</s:iterator>
		</table>

	</div>
	<br>

</body>
</html>

