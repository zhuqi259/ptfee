var oTable;

$(function() {
	// 创建AJAX表格
	oTable = $('#personList')
			.dataTable(
					{
						"bProcessing" : true,
						"bServerSide" : true,
						"sAjaxSource" : basePath
								+ "system/person!list.action?id=" + parentId,
						"sPaginationType" : "full_numbers",
						"oLanguage" : {
							"sProcessing" : "正在处理...",
							"sLengthMenu" : "每页 _MENU_ 行<input type='button' value='删除人员' onclick='deletePerson()'><input type='button' value='更新人员' onclick='updatePerson()'>",
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
	$("#personList tbody").click(function(event) {
		$(oTable.fnSettings().aoData).each(function() {
			$(this.nTr).removeClass('row_selected');
		});
		$(event.target.parentNode).addClass('row_selected');
	});
});

function updatePerson() {
	var anSelected = fnGetSelected(oTable);

	if (anSelected.length == 0) {
		alert("请选中您要更新的人员！");
		return;
	}
	// 获得被选中记录的ID
	var personId = anSelected[0].children[0].innerHTML;

	window.location = basePath + "system/person!updateInput.action?id="
			+ personId;
}

function deletePerson() {

	var anSelected = fnGetSelected(oTable);

	if (anSelected.length == 0) {
		alert("请选中您要删除的人员！");
		return;
	}

	if (!confirm("请确认是否要删除选中的人员，删除操作不可恢复，请小心操作！")) {
		return;
	}

	// 获得被选中记录的ID
	var personId = anSelected[0].children[0].innerHTML;
	$.get(basePath + "system/person!del.action?id=" + personId, function() {
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