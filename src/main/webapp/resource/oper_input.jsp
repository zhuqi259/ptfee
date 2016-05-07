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
<style type="text/css">
<!--
body {
	font-family: Arial, Helvetica, sans-serif;
	font-size:12px;
	color:#666666;
	background:#fff;
	text-align:center;

}

* {
	margin:0;
	padding:0;
}

a {
	color:#1E7ACE;
	text-decoration:none;	
}

a:hover {
	color:#000;
	text-decoration:underline;
}
h3 {
	font-size:14px;
	font-weight:bold;
}

pre,p {
	color:#1E7ACE;
	margin:4px;
}
input, select,textarea {
	padding:1px;
	margin:2px;
	font-size:12px;
}
.buttom{
	padding:1px 10px;
	font-size:12px;
	border:1px #1E7ACE solid;
	background:#D0F0FF;
}
#formwrapper {
	width:95%;
	margin:15px auto;
	padding:20px;
	text-align:left;
}

fieldset {
	padding:10px;
	margin-top:5px;
	border:1px solid #1E7ACE;
	background:#fff;
}

fieldset legend {
	color:#1E7ACE;
	font-weight:bold;
	background:#fff;
}

fieldset label {
	float:left;
	width:60px;
	text-align:right;
	padding:4px;
	margin:1px;
}

fieldset div {
	clear:left;
	margin-bottom:2px;
}

.enter{ text-align:center;}
.clear {
	clear:both;
}

-->
</style>
</head>
<body>

<div id="formwrapper">
	<h3>增加操作的信息</h3>
	<form action="system/resource.action" method="post" enctype="multipart/form-data">
	<input type="hidden" name="method:addOper" value="">
	
	<!-- 资源ID -->
	<input type="hidden" name="id" value="<s:property value="id"/>">
	
	<fieldset>
		<legend>操作的基本信息
		</legend>
		<div>
			<label for="operName">操作名称</label>
			<input type="text" name="operName" id="operName" value="${operName }" size="30"  /> 
			<br />	
		</div>
		<div>
			<label for="operSn">操作标识</label>
			<input type="text" name="operSn" id="operSn" value="${operSn }" size="30"  /> 
			<br />	
		</div>
		<div>
			<label for="methodName">方法名</label>
			<input type="text" name="methodName" id="methodName" value="${methodName }" size="30"  /> 
			<br />	
		</div>
		<div>
			<label for="operIndex">操作索引</label>
			<input type="text" name="operIndex" id="operIndex" value="${operIndex }" size="5"  /> 
			(在同一个资源中，请保证操作索引不重复，且小于或等于31)<br />	
		</div>
		<div class="enter">
		    <input name="submit" type="submit" class="buttom" value="提交" />
		    <input name="reset" type="reset" class="buttom" value="重置" />
		</div>		
	</fieldset>
	</form>
</div>
</body>
</html>

