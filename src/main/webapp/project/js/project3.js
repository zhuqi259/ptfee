$(function() {
	// 创建AJAX表格
	oTable = $('#approvedHistory').dataTable(
			{
				"bProcessing" : true,
				"bServerSide" : true,
				"sAjaxSource" : basePath
						+ "person/project!approvedHistory.action?id="
						+ projectId,
				"sPaginationType" : "full_numbers",
				"oLanguage" : {
					"sProcessing" : "正在处理...",
					"sLengthMenu" : "每页 _MENU_ 行   工程名称 " + proname,
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
});