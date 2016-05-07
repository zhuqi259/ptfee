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
		oTable = $('#zfundList')
				.dataTable(
						{
							"bProcessing" : true,
							"bServerSide" : true,
							"sAjaxSource" : "system/activityZfund!allFunds.action?id="
									+ activityId,
							"sPaginationType" : "full_numbers",
							"bFilter": false,
							"oLanguage" : {
								"sProcessing" : "正在处理...",
								"sLengthMenu" : "每页 _MENU_ 行<input type='button' value='删除缴费' onclick='deleteZfund()'><input type='button' value='更新缴费' onclick='updateZfund()'>",
								"sZeroRecords" : "没有找到记录",
								"sEmptyTable" : "没有找到记录",
								"sInfo" : "当前显示从 _START_ 到 _END_ ,总记录数： _TOTAL_",
								"sInfoEmpty" : "",
							//	"sSearch" : "搜索:",
								"oPaginate" : {
									"sFirst" : "首页",
									"sPrevious" : "前页",
									"sNext" : "下页",
									"sLast" : "尾页"
								}
							}
						});

		//点击的时候，显示被点击的记录处于选中状态
		$("#zfundList tbody").click(function(event) {
			$(oTable.fnSettings().aoData).each(function() {
				$(this.nTr).removeClass('row_selected');
			});
			$(event.target.parentNode).addClass('row_selected');
		});
	});

	function updateZfund() {
		var anSelected = fnGetSelected(oTable);

		if (anSelected.length == 0) {
			alert("请选中您要更新的缴费信息！");
			return;
		}
		//获得被选中记录的ID
		var zfundId = anSelected[0].children[0].innerHTML;

		window.location = "<%=basePath%>system/zfund!updateInput.action?id=" + zfundId;
	}

	function deleteZfund() {

		var anSelected = fnGetSelected(oTable);

		if (anSelected.length == 0) {
			alert("请选中您要删除的缴费信息！");
			return;
		}

		if (!confirm("请确认是否要删除选中的缴费信息！")) {
			return;
		}
		//获得被选中记录的ID
		var zfundId = anSelected[0].children[0].innerHTML;
		$.get("<%=basePath%>system/zfund!del.action?id=" + zfundId, function() {
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
		<h3>更新节点缴费信息</h3>
		<form action="system/activity.action" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="method:update" value=""> <input
				type="hidden" name="id" value="<s:property value="id"/>">
			<fieldset>
				<legend>
					流程结点缴费信息<input type="button" value="添加缴费信息"
						onclick="window.location = '<%=basePath%>system/zfund!addInput.action?activity.id=${id}'" />
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
	<!-- 显示activity下面的缴费信息 -->
	<div>
		<table id="zfundList" class="display" width="100%" border="0"
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
							<span class="STYLE10">金额</span>
						</div>
					</th>
					<th width="30" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">金额是否使用</span>
						</div>
					</th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">时间</span>
						</div>
					</th>
					<th width="30" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">时间是否使用</span>
						</div>
					</th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">单位</span>
						</div>
					</th>
					<th width="30" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">单位是否使用</span>
						</div>
					</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="7">这里是数据</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>

