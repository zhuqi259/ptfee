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
<title>生成Excel下载界面</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>

<script type="text/javascript">
	function mysubmit(obj){
		$(obj).submit();
	}	
</script>

</head>
<body>
	<div align="center">
		<div align="center">
			<IMG height=211 src="common/success.gif" width=329>
		</div>
		<div align="center">
			<font size="20"> 生成成功~ </font>
		</div>
		<div align="center">
			<form action="fileLook.action" method="post" id="fileLook">
				<input type="hidden" name="type" value="3"> <input
					type="hidden" name="downloadPath" value="${path }"> <a
					href="javascript:void(0);" onclick="mysubmit('#fileLook');return false;"><font size="15">查看</font>
				</a>
			</form>
			<hr>
			<form action="fileDownFull.action" method="post" id="fileDownFull">
				<input type="hidden" name="type" value="3"> <input
					type="hidden" name="downloadPath" value="${path }"> <a
					href="javascript:void(0);" onclick="mysubmit('#fileDownFull');return false;"><font size="15">下载</font>
				</a>
			</form>
		</div>

	</div>
</body>
</html>