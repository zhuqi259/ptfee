<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
var parentId = <s:property value="id"/>;
var basePath = "<%=basePath%>";
</script>
<script type="text/javascript" src="org/js/person_table.js"></script>
</head>
<body>

<div id="formwrapper">
	<h3>更新岗位的信息</h3>
	<form action="system/position.action" method="post" enctype="multipart/form-data">
	<input type="hidden" name="method:update" value="">
	<input type="hidden" name="id" value="<s:property value="id"/>">
	<input type="hidden" name="parent.id" value="<s:property value="parent.id"/>">
	<fieldset>
		<legend>岗位基本信息
		<input type="button" value="添加人员" onclick="window.location = '<%=basePath%>system/person!addInput.action?parent.id=${id}'"/>
		<input type="button" value="删除本岗位" onclick="window.location = '<%=basePath%>system/position!del.action?id=${id}'"/>
		</legend>
		<div>
			<label for="name">名称</label>
			<input type="text" name="name" id="name" value="${name }" size="30"  /> 
			<br />	
		</div>
		<div>
			<label for="description">描述</label>
			<input type="text" name="description" id="description" value="${description }" size="60" /> 
			<br />	
		</div>
		<div class="enter">
		    <input name="submit" type="submit" class="buttom" value="提交" />
		    <input name="reset" type="reset" class="buttom" value="重置" />
		</div>
	</fieldset>
	</form>
</div>
<!-- 显示party下面的人员信息 -->
<div>
<table id="personList" class="display" width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
	<thead>
      <tr>
        <th width="10" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">ID</span></div></th>
        <th width="60" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">姓名</span></div></th>
        <th width="25" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">性别</span></div></th>
        <th width="50" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">电话</span></div></th>
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

