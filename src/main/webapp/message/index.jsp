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
<title>我的未读消息</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<link href="org/js/party.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	var oTable;
	$(function() {
		//创建AJAX表格
		oTable = $("#messageList")
				.dataTable(
						{
							"bProcessing" : true,
							"bServerSide" : true,
							"sAjaxSource" : "<%=basePath%>person/message!Readinglist.action",
							"aoColumnDefs" : [ {
								"bVisible" : false,
								"aTargets" : [ 0 ]
							} //隐藏ID列，只显name列
							],
							"sPaginationType" : "full_numbers",
							"oLanguage" : {
								"sProcessing" : "正在处理...",
								"sLengthMenu" : "每页 _MENU_ 行<input type='button' value='查看消息' onclick='viewMessage()'><input type='button' value='删除消息' onclick='delMessage()'>",
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
		$("#messageList tbody").click(function(event) {
			$(oTable.fnSettings().aoData).each(function() {
				$(this.nTr).removeClass('row_selected');
			});
			$(event.target.parentNode).addClass('row_selected');
		});
	});

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

	function viewMessage() {
		var anSelected = fnGetSelected(oTable);

		if (anSelected.length == 0) {
			alert("请选中您要查看的消息！");
			return;
		}
		// 获得被选中记录的ID
		//	var messageId = anSelected[0].children[0].innerHTML;
		var messageId;
		$(oTable.fnSettings().aoData).each(function() {
			if ($(this.nTr).hasClass("row_selected")) {
				messageId = this._aData[0];
			}
		});
		//	alert(messageId);
		window.location = "<%=basePath%>person/message!view.action?id=" + messageId;
	}

	function delMessage() {
		var anSelected = fnGetSelected(oTable);

		if (anSelected.length == 0) {
			alert("请选中您要删除的消息！");
			return;
		}
		// 获得被选中记录的ID
		//	var messageId = anSelected[0].children[0].innerHTML;
		var messageId;
		$(oTable.fnSettings().aoData).each(function() {
			if ($(this.nTr).hasClass("row_selected")) {
				messageId = this._aData[0];
			}
		});
		//	alert(messageId);
		$.get("<%=basePath%>person/message!del.action?type=2&id=" + messageId, function() {
			oTable.fnDeleteRow(anSelected[0]);
		});
	}
</script>

<style type="text/css" title="currentStyle">
@import "js/datatable/css/demo_page.css";

@import "js/datatable/css/demo_table.css";
</style>


</head>
<body>

	<div>
		<table id="messageList" class="display" width="100%" border="0"
			cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
			<thead>
				<tr>
					<th width="10" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">ID</span>
						</div></th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">发件人</span>
						</div></th>
					<th width="70" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">主题</span>
						</div></th>
					<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">时间</span>
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

