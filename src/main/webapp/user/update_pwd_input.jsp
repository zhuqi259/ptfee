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
<title>修改密码</title>
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

#passwordText {
	border: 1px solid green;
	width: 100px;
	height: 27px;
	text-align: center;
	padding: 6px;
	background-color: #FFC;
	position: absolute;
	z-index: 99;
	display: none; /*使div初始化时隐藏*/
}
-->
</style>

<script language="javascript">

	$(document).ready(function() {
		$("#password").blur(function() {
			var id = $("#id").val();
			var password = $("#password").val();
			var input = $(this);            
	        var offset = input.offset();
			htmlobj = $.ajax({
				url : "<%=basePath%>system/userA!check.action?id="+id+"&password="+password,
				async : false
			});
			$("#passwordText").html(htmlobj.responseText)
				.css('left',offset.left + input.width() + 2 + 'px')
            	.css('top',offset.top + 'px')
            	.fadeIn();
			$("#passwordText").fadeOut(3000);
		});
	});
	
	function checkForm(form) {
		if (form.password.value == "") {
			alert("请输入的原密码!");
			form.password.focus();
			return false;
		}
		
		if (form.newPassword.value == "") {
			alert("请输入的新密码!");
			form.newPassword.focus();
			return false;
		}
		if (form.newPassword.value.length > 25) {
			alert("新密码长度不能太长了!");
			form.newPassword.focus();
			return false;
		}
		if (form.rePassword.value == "") {
			alert("请确认新密码!");
			form.rePassword.focus();
			return false;
		}
		if (form.newPassword.value != form.rePassword.value) {
			alert("您两次输入的新密码不一致，请重新输入!");
			form.newPassword.value = "";
			form.rePassword.value = "";
			form.newPassword.focus();
			return false;
		}
	}
	
</script>
</head>
<body>
	<div id="formwrapper">
		<h3>设置登录帐号</h3>
		<form name="pwd_change_form" action="system/userA.action"
			method="post">
			<input type="hidden" name="method:updatePwd" value=""> <input
				type="hidden" id="id" name="id" value="${login.id }">
			<fieldset>
				<legend>修改密码 </legend>
				<div>
					<label for="username" style="width: 95px">登录帐号</label> <input
						type="text" name="username" id="username" value="${username }"
						size="30" readonly="readonly" />
				</div>
				<div>
					<label for="password" style="width: 95px">请输入原密码</label> <input
						type="password" name="password" id="password" size="30" />
					<div id="passwordText"></div>
				</div>

				<div>
					<label for="newPassword" style="width: 95px">请输入新密码</label> <input
						type="password" name="newPassword" id="newPassword" size="30" />
				</div>

				<div>
					<label for="rePassword" style="width: 95px">请确认新密码</label> <input
						type="password" name="rePassword" id="rePassword" size="30" />
				</div>

				<div class="enter">
					<input name="submit" type="submit" class="buttom" value="确认修改"
						onclick="return checkForm(pwd_change_form)" /> <input
						name="reset" type="reset" class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>

</body>
</html>

