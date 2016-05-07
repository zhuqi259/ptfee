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
	width: 95%;
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

<script type="text/javascript">
	function delMenu(id){
		if(!confirm("请确认是否要删除选中的菜单")){
			return;
		}	
		window.location = "<%=basePath%>system/menu!del.action?id=" + id;
	}
</script>
</head>
<body>

	<div id="formwrapper">
		<h3>更新菜单的信息</h3>
		<form action="system/menu.action" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="method:update" value=""> <input
				type="hidden" name="id" value="<s:property value="id"/>">
			<s:if test="parent != null">
				<input type="hidden" name="parent.id"
					value="<s:property value="parent.id"/>">
			</s:if>

			<fieldset>
				<legend>
					菜单基本信息 <input type="button" value="添加下级菜单"
						onclick="window.location = '<%=basePath%>system/menu!addInput.action?parent.id=${id}'" />
					<input type="button" value="删除菜单信息" onclick="delMenu(${id})" />
				</legend>
				<div>
					<label for="name">名称</label> <input type="text" name="name"
						id="name" value="${name }" size="30" /> <br />
				</div>
				<div>
					<label for="href">链接地址</label> <input type="text" name="href"
						id="href" value="${href }" size="60" /> <br />
				</div>
				<div>
					<label for="orderNumber">排序号</label> <input type="text"
						name="orderNumber" id="orderNumber" value="${orderNumber }"
						size="15" /> <br />
				</div>
				<div>
					<label for="sn">编号</label> <input type="text" name="sn" id="sn"
						value="${sn }" size="30" /> <br />
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

