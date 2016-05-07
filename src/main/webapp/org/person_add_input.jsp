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
	<h3>添加人员的信息</h3>
	<form action="system/person.action" method="post" enctype="multipart/form-data">
	<input type="hidden" name="method:add" value="">
	<input type="hidden" name="parent.id" value="<s:property value="parent.id"/>">
	<fieldset>
		<legend>人员基本信息</legend>
		<div>
			<label for="name">姓名</label>
			<input type="text" name="name" id="name" value="${name }" size="30"  /> 
			<br />	
		</div>
		<div>
			<label for="snumber">员工编号</label>
			<input type="text" name="snumber" id="snumber" value="${snumber }" size="30"  /> 
			<br />	
		</div>
		<div>
			<label for="sex">性别</label>
			<input type="text" name="sex" id="sex" value="${sex }" size="30"  /> 
			<br />	
		</div>
		<div>
			<label for="phone">手机</label>
			<input type="text" name="phone" id="phone" value="${phone }" size="30"  /> 
			<br />	
		</div>
		<div>
			<label for="qq">QQ号</label>
			<input type="text" name="qq" id="qq" value="${qq }" size="30"  /> 
			<br />	
		</div>
		<div>
			<label for="msn">MSN</label>
			<input type="text" name="msn" id="msn" value="${msn }" size="30"  /> 
			<br />	
		</div>
		<div>
			<label for="email">电子邮件</label>
			<input type="text" name="email" id="email" value="${email }" size="30"  /> 
			<br />	
		</div>
		<div>
			<label for="duty">主要负责</label>
			<input type="text" name="duty" id="duty" value="${duty }" size="60"  /> 
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

</body>
</html>

