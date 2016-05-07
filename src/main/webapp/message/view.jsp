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
<title>查看短消息</title>
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
	width: 65%;
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

input[type="button"] {
	background-color: #0C0;
	height: 25px;
	font-size: 15px;
}
-->
</style>
</head>
<body>

	<div id="formwrapper">
		<fieldset>
			<form action="person/message.action" method="post">
				<input type="hidden" name="method:replyInput" value=""> <input
					type="hidden" name="uto.id" value="${ufr.id }"> <input
					type="hidden" name="title" value="${title }">
				<legend>[主题]${title }</legend>
				<div>
					<input name="button" type="button"
						onclick="javascript:history.go(-1)" value="&lt;&lt;返回" />
					<s:if test="#session.login.id!=ufr.id">
						<input type="button" value="回复" onclick="javascript:submit();" />
					</s:if>
				</div>
				<div>发件人: ${ufr.person.name }</div>
				<div>时&nbsp;&nbsp;&nbsp;&nbsp;间: ${rtime }</div>
				<div>收件人: ${uto.person.name }</div>
				<hr>
				<div>
					正文:<br> ${content }
				</div>
			</form>
		</fieldset>
	</div>

</body>
</html>

