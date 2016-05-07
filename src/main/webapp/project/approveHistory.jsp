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
<title>工程审核历史</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="project/js/project3.js"></script>

<link href="org/js/party.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	var oTable;
	var basePath = 	"<%=basePath%>";
	var projectId = ${id};
	var proname = "${proname}";
</script>

<style type="text/css" title="currentStyle">
@import "js/datatable/css/demo_page.css";

@import "js/datatable/css/demo_table.css";
</style>
</head>
<body>
<div align="center">
<font color="purple" size="6">工程处理历史</font>
</div>
<hr>

	<div>
		<table id="approvedHistory" class="display" width="100%" border="0"
			cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
			<thead>
				<tr>
					<th width="70" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">审核人</span>
						</div></th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">审核内容</span>
						</div></th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">审核时间</span>
						</div></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="3">这里是数据</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>

