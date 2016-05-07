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
<title>分配工程编号</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>

<style type="text/css">
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
	width: 95%;
	margin: 15px auto;
	padding: 20px;
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
	操作人员：${info.approver.person.name } &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	操作时间:${info.rTime }
	<hr>
	<div id="formwrapper">
		<h2 style="color: purple">工程名称:${info.project.proname }</h2>
		<h3>${currentStatus }</h3>
		<fieldset>
			<legend>工程信息</legend>
			<div>
				<TABLE cellSpacing=0 cellPadding=0 width="93%" align=center border=1>
					<tr>
						<td width="40%">工程名称：</td>
						<td><input name="proname" id="proname"
							value="${info.project.proname }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td>工程编号：</td>
						<td><input name="fid" id="fid" value="${info.project.fid }"
							readonly="readonly" />
							<button id="b01" type="button">分配工程编号</button></td>
					</tr>
				</TABLE>
				<div align="center">
					<input type="submit" class="buttom" value="提交" />
				</div>
		</fieldset>
	</div>
</body>
</html>

