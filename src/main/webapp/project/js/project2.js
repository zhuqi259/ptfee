$(function() {
	// 创建AJAX表格
	oTable = $('#projectList').dataTable({
		"bProcessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : basePath + "person/approving!approvingList.action",
		"sPaginationType" : "full_numbers",
		"oLanguage" : {
			"sProcessing" : "正在处理...",
			"sLengthMenu" : "每页 _MENU_ 行",
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
	$("#projectList tbody").click(function(event) {
		$(oTable.fnSettings().aoData).each(function() {
			$(this.nTr).removeClass('row_selected');
		});
		$(event.target.parentNode).addClass('row_selected');
		openLink();
	});

	function openLink() {
		// 搜索表格中那一列被选中了，取出其ID值
		var projectId;
		$(oTable.fnSettings().aoData).each(function() {
			if ($(this.nTr).hasClass("row_selected")) {
				projectId = this._aData[0];
			}
		});
		if (projectId) {
			window.location = basePath
					+ "person/project!approveInput.action?id=" + projectId;
		}
	}
});

function viewProject() {
	var anSelected = fnGetSelected(oTable);

	if (anSelected.length == 0) {
		alert("请选中您要提交审核的工程！");
		return;
	}
	// 获得被选中记录的ID
	var projectId = anSelected[0].children[0].innerHTML;

	window.location = basePath + "person/project!approveInput.action?id="
			+ projectId;
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