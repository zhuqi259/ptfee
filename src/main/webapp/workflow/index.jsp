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
<title>流程管理</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="workflow/js/workflow.js"></script>
<style type="text/css" title="currentStyle">
@import "js/datatable/css/demo_page.css";

@import "js/datatable/css/demo_table.css";
</style>
<link href="org/js/party.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	var oTable;
	var basePath = 	"<%=basePath%>";
</script>
</head>
<body>

	<div>
		<table id="workflowList" class="display" width="100%" border="0"
			cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
			<thead>
				<tr>
					<th width="10" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">工作流ID</span>
						</div>
					</th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">工作流名称</span>
						</div>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="2">这里是数据</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>

