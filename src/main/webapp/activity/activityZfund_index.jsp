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
<title>OA办公自动化平台 - 缴费fund信息管理主界面</title>

<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<style type="text/css" title="currentStyle">
@import "js/datatable/css/demo_page.css";

@import "js/datatable/css/demo_table.css";
</style>
<style type="text/css">
<!--
.dataTables_filter {
	width: 100%;
	float: left;
	text-align: left;
}

.dataTables_filter input {
	width: 100px;
	float: right;
}
-->
</style>
<script type="text/javascript">
	var oTable;
	$(function() {
		//创建AJAX表格
		oTable = $("#activityList")
				.dataTable(
						{
							"bProcessing" : true,
							"bInfo" : false, //不显示当前显示第几条记录等信息
							"bSort" : false, //不需要排序特性
							"aoColumnDefs" : [ {
								"bVisible" : false,
								"aTargets" : [ 0 ]
							} //隐藏ID列，只显name列
							],
							"sPaginationType" : "two_button",
							"bServerSide" : true,
							"sAjaxSource" : "system/activity!activityPage.action", //本查询返回两列数据：ID、name列
							"oLanguage" : {
								"sProcessing" : "正在处理...",
								"sLengthMenu" : "每页_MENU_行",
								"sZeroRecords" : "没有找到记录",
								"sEmptyTable" : "没有找到记录",
								//	"sInfo":"",
								"sInfoEmpty" : "",
								"sSearch" : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;搜索",
								"oPaginate" : {
									"sPrevious" : "前页",
									"sNext" : "下页"
								}
							}
						});

		//点击的时候，显示被点击的记录处于选中状态
		$("#activityList tbody").click(function(event) {
			$(oTable.fnSettings().aoData).each(function() {
				$(this.nTr).removeClass('row_selected');
			});
			$(event.target.parentNode).addClass('row_selected');

			openAuth();

		});
	});

	function openAuth() {
		//搜索表格中那一列被选中了，取出其ID值
		var activityId;
		$(oTable.fnSettings().aoData).each(function() {
			if ($(this.nTr).hasClass("row_selected")) {
				activityId = this._aData[0];
			}
		});
		if (activityId) {
			$("#rightFrame").attr(
					"src",
					"system/activityZfund!updateZfundInput.action?id="
							+ activityId);
		}
	}

	function refresh() {
		//	$("#activityList").dataTable("refresh", "#activityList");
	}
</script>
</head>

<body>
	<table width="100%" height="100%" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<td width="247" valign="top" align="right">
				<table id="activityList" class="display" width="100%" border="0"
					cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
					<thead>
						<tr>
							<th width="10" height="20" bgcolor="d3eaef" class="STYLE6"><div
									align="center">
									<span class="STYLE10">ID</span>
								</div>
							</th>
							<th height="20" bgcolor="d3eaef" class="STYLE6"><div
									align="center">
									<span class="STYLE10">选择节点进行缴费管理</span>
								</div>
							</th>
						</tr>
					</thead>

					<tbody>
						<tr>
							<td>这里是ID</td>
							<td>这里是name</td>
						</tr>
					</tbody>
				</table></td>
			<td width="5" bgcolor="#00706b">&nbsp;</td>
			<td valign="top"><iframe height="100%" width="100%"
					frameborder="0" frameborder="0" src="right.jsp" name="rightFrame"
					id="rightFrame" title="rightFrame"></iframe>
			</td>
		</tr>
	</table>
</body>
</html>

