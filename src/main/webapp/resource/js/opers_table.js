var oTable;

$(function() {
	// 创建AJAX表格
	oTable = $('#opersList').dataTable({
		"bPaginate" : false,
		"bLengthChange" : false,
		"bFilter" : false,
		"bSort" : false,
		"bInfo" : false,
		"bAutoWidth" : false
	});

	// 点击的时候，显示被点击的记录处于选中状态
	$("#opersList tbody").click(function(event) {
		$(oTable.fnSettings().aoData).each(function() {
			$(this.nTr).removeClass('row_selected');
		});
		$(event.target.parentNode).addClass('row_selected');
	});
});

function addOper() {
	window.location = basePath + "system/resource!operInput.action?id="
			+ resourceId;
}

function deleteOper() {

	var anSelected = fnGetSelected(oTable);

	if (anSelected.length == 0) {
		alert("请选中您要删除的操作！");
		return;
	}

	if (!confirm("请确认是否要删除选中的操作，删除操作不可恢复，请小心！")) {
		return;
	}

	// 获得被选中记录的ID
	var operSn = anSelected[0].children[0].innerHTML;
	$.get(basePath + "system/resource!delOper.action?id=" + resourceId
			+ "&operSn=" + operSn, function() {
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