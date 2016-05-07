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
<title></title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<style type="text/css" title="currentStyle">
@import "js/datatable/css/demo_page.css";

@import "js/datatable/css/demo_table.css";
</style>
<link href="org/js/party.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	var oTable;

	$(function() {
		//创建AJAX表格
		oTable = $('#personList')
				.dataTable(
						{
							"bProcessing" : true,
							"bServerSide" : true,
							"sAjaxSource" : "system/person!list.action?id="
									+ <s:property value="id"/>,
							"sPaginationType" : "full_numbers",
							"oLanguage" : {
								"sProcessing" : "正在处理...",
								"sLengthMenu" : "每页 _MENU_ 行<input type='button' value='添加人员' onclick='addPerson()'><input type='button' value='删除人员' onclick='deletePerson()'><input type='button' value='更新人员' onclick='updatePerson()'>",
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

		//点击的时候，显示被点击的记录处于选中状态
		$("#personList tbody").click(function(event) {
			$(oTable.fnSettings().aoData).each(function() {
				$(this.nTr).removeClass('row_selected');
			});
			$(event.target.parentNode).addClass('row_selected');
		});
	});

	function addPerson() {
	//	alert("添加人员请至【部门/岗位设置】菜单下进行添加！");
		window.location = "<%=basePath%>system/party.action";
	}

	function updatePerson() {
	//	alert("更新人员请至【部门/岗位设置】菜单下进行更新！");
		var anSelected = fnGetSelected(oTable);
		if (anSelected.length == 0) {
			alert("请选中您要更新的人员！");
			return;
		}
		// 获得被选中记录的ID
		var personId = anSelected[0].children[0].innerHTML;
		window.location = "<%=basePath %>system/person!updateInput.action?id=" + personId;
	}

	function deletePerson() {
		alert("删除人员请至【部门/岗位设置】菜单下进行删除！");
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
</head>
<body>
	<div>
		<table id="personList" class="display" width="100%" border="0"
			cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
			<thead>
				<tr>
					<th width="10" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">ID</span>
						</div></th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">姓名</span>
						</div></th>
					<th width="25" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">性别</span>
						</div></th>
					<th width="50" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">电话</span>
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

