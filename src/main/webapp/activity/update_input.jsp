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
	var activityId = <s:property value="id"/>;
	$(function() {
		//创建AJAX表格
		oTable = $('#zfileList')
				.dataTable(
						{
							"bProcessing" : true,
							"bServerSide" : true,
							"sAjaxSource" : "system/activity!allFiles.action?id="
									+ activityId,
							"sPaginationType" : "full_numbers",
							"oLanguage" : {
								"sProcessing" : "正在处理...",
								"sLengthMenu" : "每页 _MENU_ 行<input type='button' value='删除文件' onclick='deleteZfile()'><input type='button' value='更新文件' onclick='updateZfile()'>",
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
		$("#zfileList tbody").click(function(event) {
			$(oTable.fnSettings().aoData).each(function() {
				$(this.nTr).removeClass('row_selected');
			});
			$(event.target.parentNode).addClass('row_selected');
		});
	});

	function updateZfile() {
		var anSelected = fnGetSelected(oTable);

		if (anSelected.length == 0) {
			alert("请选中您要更新的文件！");
			return;
		}
		//获得被选中记录的ID
		var zfileId = anSelected[0].children[0].innerHTML;

		window.location = "<%=basePath%>system/zfile!updateInput.action?id=" + zfileId;
	}

	function deleteZfile() {

		var anSelected = fnGetSelected(oTable);

		if (anSelected.length == 0) {
			alert("请选中您要删除的文件！");
			return;
		}

		if (!confirm("请确认是否要删除选中的文件！")) {
			return;
		}
		//获得被选中记录的ID
		var zfileId = anSelected[0].children[0].innerHTML;
		$.get("<%=basePath%>system/zfile!del.action?id=" + zfileId, function() {
			oTable.fnDeleteRow(anSelected[0]);
		});
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

	<div id="formwrapper">
		<h3>更新流程结点的文件</h3>
		<form action="system/activity.action" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="method:update" value=""> <input
				type="hidden" name="id" value="<s:property value="id"/>">
			<fieldset>
				<legend>
					流程结点基本信息 <input type="button" value="添加文件"
						onclick="window.location = '<%=basePath%>system/zfile!addInput.action?activity.id=${id}'" />
					<input type="button" value="删除本节点"
						onclick="window.location = '<%=basePath%>system/activity!del.action?id=${id}'" />
				</legend>
				<div>
					<label for="name">名称</label> <input type="text" name="name"
						id="name" value="${name }" size="30" /> <br />
				</div>
				<div class="enter">
					<input name="submit" type="submit" class="buttom" value="提交" /> <input
						name="reset" type="reset" class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>
	<!-- 显示activity下面的文件信息 -->
	<div>
		<table id="zfileList" class="display" width="100%" border="0"
			cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
			<thead>
				<tr>
					<th width="10" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">ID</span>
						</div>
					</th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">文件名称</span>
						</div>
					</th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">所属七库</span>
						</div>
					</th>

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

