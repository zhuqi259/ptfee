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
<title>我的全部Excel属性</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>

<link href="org/js/party.css" rel="stylesheet" type="text/css">


<style type="text/css" title="currentStyle">
@import "js/datatable/css/demo_page.css";

@import "js/datatable/css/demo_table.css";

#formwrapper {
	width: 70%;
	margin: 15px auto;
	padding: 10px;
	text-align: left;
}
</style>
<script type="text/javascript">
	function fun(obj, id){
		if($(obj).is(':checked')){
		//	alert("选中了");
			$.post("<%=basePath%>system/sequence!updateZSequenceInUse.action", { id: id }, function (data, status) {
				 if(status != "success"){
					alert("出错啦!");
					return;
				 }				 
           });			
		}else{
		//	alert("no");
			$.post("<%=basePath%>system/sequence!updateZSequenceOffUse.action", { id: id }, function (data, status) {
				 if(status != "success"){
					alert("出错啦!");
					return;
				 }				 
          });		
		}
	}
	
	function edit(id){
		window.location.href = "<%=basePath%>system/fieldMap!updateInput.action?id="+id;
	}
	
	function copy(id){
		window.location.href = "<%=basePath%>system/fieldMap!copy.action?id="+id;
	}
	
	function del(id){
		if(!confirm("确定删除?")){
			return ;
		}
		window.location.href = "<%=basePath%>system/sequence!del.action?id="+id;
	}
	
	function add(){
		window.location.href = "<%=basePath%>system/fieldMap!addInput.action";
	}
	
	function sort(){
		window.location.href = "<%=basePath%>system/sequenceSort.action";
	}
</script>
</head>
<body>
	<div align="center">
		<input type="button" onclick="sort()" value="属性排序"
			style="width: 125 ;height: 30; background-color: #FFC; font-size: larger; cursor: pointer;">
		<input type="button" onclick="add()" value="新增属性"
			style="width: 125 ;height: 30; background-color: #FFC; font-size: larger; cursor: pointer;">
	</div>
	<div id="formwrapper">
		<table id="sequenceList" class="display" width="100%" border="0"
			cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
			<thead>
				<tr>
					<th height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">Choose</div></th>
					<th height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">ID</span>
						</div></th>
					<th height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">属性标识</span>
						</div></th>
					<th height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">属性名称</span>
						</div></th>
					<th height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">操作</span>
						</div></th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="allSequences" var="sequence" status="status">
					<s:if test="#status.odd">
						<tr bgcolor="ddffdd">
					</s:if>
					<s:else>
						<tr bgcolor="eeffee">
					</s:else>
					<td><div align="center">
							<input type="checkbox" name=""
								<s:property value="hasChecked(id)"/>
								onclick="fun(this, ${sequence.id })">
						</div></td>
					<td><div align="center">${sequence.id }</div></td>
					<td><div align="center">${sequence.fieldMap.name }</div></td>
					<td><div align="center">${sequence.fieldMap.displayName
							}</div></td>
					<td><div align="center">
							<s:if test="#sequence.editable=='Y'">
								<a href="javascript:void(0);"
									onclick="edit(${sequence.fieldMap.id });return false;">编辑</a>
							</s:if>
							<s:if test="#sequence.copyable=='Y'">
								|<a href="javascript:void(0);"
									onclick="copy(${sequence.fieldMap.id });return false;">复制</a>
							</s:if>
							<s:if test="#sequence.delable=='Y'">
								|<a href="javascript:void(0);" onclick="del(${sequence.id });return false;">删除</a>
							</s:if>
						</div></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<div align="center">
		<input type="button" onclick="sort()" value="属性排序"
			style="width: 125 ;height: 30; background-color: #FFC; font-size: larger; cursor: pointer;">

		<input type="button" onclick="add()" value="新增属性"
			style="width: 125 ;height: 30; background-color: #FFC; font-size: larger; cursor: pointer;">
	</div>
</body>
</html>

