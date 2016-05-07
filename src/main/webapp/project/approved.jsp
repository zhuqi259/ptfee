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
<title>我的已审核工程</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="project/js/project1.js"></script>
<link href="org/js/party.css" rel="stylesheet" type="text/css">
<link href="js/datatable/css/demo_page.css" rel="stylesheet" type="text/css">
<link href="js/datatable/css/demo_table.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	var oTable;
	var basePath = 	"<%=basePath%>";
</script>

</head>
<body>
<div align="center">
<font color="purple" size="6">我的已办任务</font>
</div>
<hr>
	<div>
		<table id="projectList" class="display" width="100%" border="0"
			cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
			<thead>
				<tr>
					<th width="10" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">工程ID</span>
						</div>
					</th>
					<th width="10" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">工程编号</span>
						</div>
					</th>
					<th width="30" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">工程名称</span>
						</div>
					</th>
					<th width="30" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">工程当前状态</span>
						</div>
					</th>
					<th width="30" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">历史工程执行状态</span>
						</div>
					</th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">提交或审核时间</span>
						</div>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="6">这里是数据</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>

