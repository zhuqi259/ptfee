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
<title>添加文件</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>

<script type="text/javascript">
	var temp;
	$(document).ready(function() {
		temp = $(".iptdiv:first");
		$("a[name=rmlink]").click(function() {
			$(this).parent().remove();
		})
		$("#addfile").click(function() {
			temp.clone(true).appendTo($("#fileDiv"));
		})
	})
</script>

<style type="text/css">
<!--
body {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #666666;
	background: #fff;
	text-align: center;
}

* {
	margin: 0;
	padding: 0;
}

a {
	color: #1E7ACE;
	text-decoration: none;
}

a:hover {
	color: #000;
	text-decoration: underline;
}

h3 {
	font-size: 14px;
	font-weight: bold;
}

pre,p {
	color: #1E7ACE;
	margin: 4px;
}

input,select,textarea {
	padding: 1px;
	margin: 2px;
	font-size: 12px;
}

.buttom {
	padding: 1px 10px;
	font-size: 12px;
	border: 1px #1E7ACE solid;
	background: #D0F0FF;
}

#formwrapper {
	width: 70%;
	margin: 15px auto;
	padding: 20px;
	text-align: left;
}

fieldset {
	padding: 10px;
	margin-top: 5px;
	border: 1px solid #1E7ACE;
	background: #fff;
}

fieldset legend {
	color: #1E7ACE;
	font-weight: bold;
	background: #fff;
}

fieldset label {
	float: left;
	width: 60px;
	text-align: right;
	padding: 4px;
	margin: 1px;
}

fieldset div {
	clear: left;
	margin-bottom: 2px;
}

.enter {
	text-align: center;
}

.clear {
	clear: both;
}
-->
</style>
</head>
<body>
	<div style="display:none;">
		<!--clone div start-->
		<div class="iptdiv">
			<label for='zname'>文件名称</label><input type='text' name='zname'
				id='zname' required="required" />|<input type="file"
				name="uploadFiles" required="required"/><a href="javascript:void(0);" name="rmlink">X</a>
		</div>
	</div>
	<div id="formwrapper">
		<h3>${name }</h3>
		<form action="system/database.action" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="method:add" value=""> <input
				type="hidden" name="id" value="${id }">
			<fieldset>
				<legend>设置文件名称&上传文件</legend>
				<a href="javascript:void(0);" id="addfile">增加文件</a>
				<div id="fileDiv">
					<div>
						<label for="zname">文件名称</label> <input type="text" name="zname"
							id="zname" required="required" />|<input type="file"
							name="uploadFiles" required="required"/>
					</div>
				</div>

				<div class="enter">
					<input name="submit" type="submit" class="buttom" value="提交" /> <input
						name="reset" type="reset" class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>

</body>
</html>

