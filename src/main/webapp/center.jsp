<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CMS 后台管理工作平台</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	overflow: hidden;
}
-->
</style>
<script type="text/javascript">
	function refresh(){
		window.document.getElementById('leftFrame').contentWindow.refresh();
	}
</script>
</head>

<body>
	<table width="100%" height="100%" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<td width="8" bgcolor="#00706b">&nbsp;</td>
			<td width="207" id="leftMenu" valign="top"><iframe height="100%"
					width="100%" frameborder="0" frameborder="0" src="left.jsp"
					name="leftFrame" id="leftFrame" title="leftFrame"></iframe></td>
			<td width="10" bgcolor="#00706b">&nbsp;</td>
			<td valign="top"><iframe height="100%" width="100%"
					frameborder="0" frameborder="0" src="right.jsp" name="rightFrame"
					id="rightFrame" title="rightFrame"></iframe></td>
			<td width="8" bgcolor="#00706b">&nbsp;</td>
		</tr>
	</table>
</body>
</html>

