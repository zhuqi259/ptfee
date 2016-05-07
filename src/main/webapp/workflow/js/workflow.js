$(function() {
	// 创建AJAX表格
	oTable = $('#workflowList')
			.dataTable(
					{
						"bProcessing" : true,
						"bServerSide" : true,
						"sAjaxSource" : basePath
								+ "system/workflow!list.action",
						"sPaginationType" : "full_numbers",
						"oLanguage" : {
							"sProcessing" : "正在处理...",
							"sLengthMenu" : "每页 _MENU_ 行<input type='button' value='添加流程' onclick='addWorkflow()'><input type='button' value='删除流程' onclick='deleteWorkflow()'><input type='button' value='查看流程' onclick='viewWorkflow()'>",
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
	$("#workflowList tbody").click(function(event) {
		$(oTable.fnSettings().aoData).each(function() {
			$(this.nTr).removeClass('row_selected');
		});
		$(event.target.parentNode).addClass('row_selected');
	});
});

function addWorkflow() {
	window.location = basePath + "system/workflow!addInput.action";
}

function viewWorkflow() {
	var anSelected = fnGetSelected(oTable);

	if (anSelected.length == 0) {
		alert("请选中您要查看的流程！");
		return;
	}
	// 获得被选中记录的ID
	var workflowId = anSelected[0].children[0].innerHTML;

	window.location = basePath + "system/workflow!view.action?id=" + workflowId;
}
function deleteWorkflow() {

	var anSelected = fnGetSelected(oTable);

	if (anSelected.length == 0) {
		alert("请选中您要删除的流程定义！");
		return;
	}

	if (!confirm("请确认是否要删除选中的流程，删除之后，此流程将无法使用！")) {
		return;
	}

	// 获得被选中记录的ID
	var workflowId = anSelected[0].children[0].innerHTML;
	$.get(basePath + "system/workflow!del.action?id=" + workflowId, function() {
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