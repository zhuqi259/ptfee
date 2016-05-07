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
<title>我的全部工程</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<link href="org/js/party.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	var oTable;
	$(function() {
		// 创建AJAX表格
		oTable = $('#projectList')
				.dataTable(
						{
							"bProcessing" : true,
							"bServerSide" : true,
							"sAjaxSource" : "<%=basePath%>person/project!allProjects.action",
							"sPaginationType" : "full_numbers",
							"oLanguage" : {
								"sProcessing" : "正在处理...",
								"sLengthMenu" : "每页 _MENU_ 行<input type='button' value='列表查看工程' onclick='viewProject()'><input type='button' value='图形查看工程' onclick='viewProject2()'><input type='button' value='生成工程财务报表' onclick='generateExcel()'>",
								"sZeroRecords" : "没有找到记录",
								"sEmptyTable" : "没有找到记录",
								"sInfo" : "当前显示从 _START_ 到 _END_ ,总记录数： _TOTAL_",
								"sInfoEmpty" : "",
								"sSearch" : "搜索:",
								"oPaginate" : {
									"sFirst" : "首页",
									"sPrevious" : "前页",
									"sNext" : "下页",
									"sLast" : "尾页"
								}
							}
						});

		// 点击的时候，显示被点击的记录处于选中状态
	//	$("#projectList tbody").click(function(event) {
		$('#projectList').delegate('tbody', 'click', function (event) {
			if(event.ctrlKey) {
				//Ctrl+Click
				var node = $(event.target.parentNode);
				if (node.hasClass('row_selected')) {
					node.removeClass('row_selected');
				}else{
					node.addClass('row_selected');
				}
				return;
			}
			$(oTable.fnSettings().aoData).each(function() {
				$(this.nTr).removeClass('row_selected');
			});
			$(event.target.parentNode).addClass('row_selected');					
		});
	});
	
	function viewProject() {
		var anSelected = fnGetSelected(oTable);

		if (anSelected.length == 0) {
			alert("请选中您要查看的工程！");
			return;
		}
		// 获得被选中记录的ID
		var projectId = anSelected[0].children[0].innerHTML;

		window.location = "<%=basePath%>system/project!viewActivity.action?id=" + projectId;
		//	window.location = "person/project!view.action?id=" + projectId;
	}
	
	function viewProject2() {
		var anSelected = fnGetSelected(oTable);

		if (anSelected.length == 0) {
			alert("请选中您要查看的工程！");
			return;
		}
		// 获得被选中记录的ID
		var projectId = anSelected[0].children[0].innerHTML;

		window.location = "<%=basePath%>person/project!viewPic.action?id="+ projectId;
		//	window.location = "person/project!view.action?id=" + projectId;
	}
	
	function generateExcel(){
		var anSelected = fnGetSelected(oTable);		
	
		if (anSelected.length == 0) {
			alert("请选中您要生成报表的工程！");
			return;
		}
		// 获得被选中记录的ID
		var ids = new Array();
		for ( var i = 0; i < anSelected.length; i++) {
			var projectId = anSelected[i].children[0].innerHTML;
			ids.push(projectId);
		}				
		window.location = "<%=basePath%>system/project!generateExcel.action?ids=" + ids.join(',');
	}

	function fnGetSelected(oTableLocal) {
		var aReturn = new Array();
		var aTrs = oTableLocal.fnGetNodes();

		for ( var i = 0; i < aTrs.length; i++) {
			if ($(aTrs[i]).hasClass('row_selected')) {
				aReturn.push(aTrs[i]);
			}
		}
		return aReturn;
	}
</script>

<style type="text/css" title="currentStyle">
@import "js/datatable/css/demo_page.css";

@import "js/datatable/css/demo_table.css";
</style>


</head>
<body>

	<div>
		<table id="projectList" class="display" width="100%" border="0"
			cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
			<thead>
				<tr>
					<th width="10" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">工程ID</span>
						</div></th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">工程编号</span>
						</div></th>
					<th width="70" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">工程名称</span>
						</div></th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">工程状态</span>
						</div></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="4">这里是数据</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>

