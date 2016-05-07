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
<script type="text/javascript">
	var resourceId = <s:property value="id"/>;
	var basePath = "<%=basePath%>";
	function delResource(id){
		if(!confirm("请确认是否要删除选中的资源")){
			return;
		}	
		window.location = "<%=basePath%>system/resource!del.action?id=" + id;
	}

</script>
<style type="text/css" title="currentStyle">
@import "js/datatable/css/demo_page.css";

@import "js/datatable/css/demo_table.css";
</style>
<link href="resource/js/resource.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="resource/js/opers_table.js"></script>
</head>
<body>

	<div id="formwrapper">
		<h3>更新操作资源的信息</h3>
		<form action="system/resource.action" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="method:update" value=""> <input
				type="hidden" name="id" value="<s:property value="id"/>">
			<s:if test="parent != null">
				<input type="hidden" name="parent.id"
					value="<s:property value="parent.id"/>">
			</s:if>

			<fieldset>
				<legend>
					操作资源基本信息 <input type="button" value="添加下级资源"
						onclick="window.location = '<%=basePath %>system/resource!addInput.action?parent.id=${id}&parent.sn=${sn}'" />
					<input type="button" value="删除资源" onclick="delResource(${id})" />
				</legend>
				<div>
					<label for="name">资源名称</label> <input type="text" name="name"
						id="name" value="${name }" size="30" /> <br />
				</div>
				<div>
					<label for="sn">唯一标识</label> <input type="text" name="sn" id="sn"
						value="${sn }" size="30" /> (助记标识符)<br />
				</div>
				<div>
					<label for="className">类名</label> <input type="text"
						name="className" id="className" value="${className }" size="60" />
					<br />
				</div>
				<div>
					<label for="orderNumber">排序号</label> <input type="text"
						name="orderNumber" id="orderNumber" value="${orderNumber }"
						size="60" /> <br />
				</div>
				<div class="enter">
					<input name="submit" type="submit" class="buttom" value="提交" /> <input
						name="reset" type="reset" class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>
	<!-- 显示资源下面的操作信息 -->
	<div>
		<fieldset>
			<legend>
				资源对应的操作列表 <input type="button" value="添加操作" onclick="addOper()" />
				<input type="button" value="删除操作" onclick="deleteOper()" />
			</legend>
			<table id="opersList" class="display" width="100%" border="0"
				cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
				<thead>
					<tr>
						<th width="10" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">标识</span>
							</div>
						</th>
						<th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">操作名称</span>
							</div>
						</th>
						<th width="25" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">方法</span>
							</div>
						</th>
						<th width="50" height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">操作索引</span>
							</div>
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="opers">
						<tr>
							<td><s:property value="value.operSn" />
							</td>
							<td><s:property value="value.operName" />
							</td>
							<td><s:property value="value.methodName" />
							</td>
							<td><s:property value="value.operIndex" />
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</fieldset>
	</div>
</body>
</html>

