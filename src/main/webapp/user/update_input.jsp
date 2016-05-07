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
<title>更新登录帐号</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
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
	function fun(obj){
		if($(obj).is(':checked')){
			$("#used").attr("value", "1");
		}else{
			$("#used").attr("value", "0");
		}
	}
</script>
</head>
<body>

	<div id="formwrapper">
		<h3>请设置人员的登录帐号</h3>
		<form action="system/user.action" method="post">
			<input type="hidden" name="method:update" value=""> <input
				type="hidden" name="id" value="<s:property value="id"/>"> <input
				type="hidden" name="person.id"
				value="<s:property value="person.id"/>">
			<fieldset>
				<legend>请设置人员的登录帐号 </legend>
				<div>
					<label for="username">登录帐号</label> <input type="text"
						name="username" id="username" value="${username }" size="30" /> <br />
				</div>
				<div>
					<label for="password">登录密码</label> <input type="password"
						name="password" id="password" size="30" /> <input type="hidden"
						name="used" id="used" value="0" /> <input type="checkbox"
						onclick="fun(this)" /><font color="red">修改密码?(默认不修改，要修改请勾上)</font><br />
				</div>
				<div>
					<label for="roles">分配角色</label> <select name="roleIds"
						multiple="multiple">
						<s:iterator value="roles">
							<option value="<s:property value="id"/>"
								<s:property value="hasSelected(id,#selectedRoleIds)"/>>
								<s:property value="name" />
							</option>
						</s:iterator>
					</select> <br />
				</div>
				<div class="enter">
					<input name="submit" type="submit" class="buttom" value="保存登录帐号" />
					<input name="reset" type="reset" class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>

</body>
</html>

