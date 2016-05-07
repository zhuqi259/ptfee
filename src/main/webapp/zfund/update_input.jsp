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
<title>更新缴费信息</title>
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
</head>
<body>

	<div id="formwrapper">
		<h3>请设置缴费信息</h3>
		<form action="system/zfund.action" method="post">
			<input type="hidden" name="method:update" value=""> <input
				type="hidden" name="id" value="${id }"> <input type="hidden"
				name="activity.id" value="<s:property value="activity.id"/>">
			<fieldset>
				<legend>请设置缴费信息 </legend>
				<div>
					<label for="fund_name">金额</label> <input type="text"
						name="fund_name" size="30" value="${ fund_name}" /> <input
						type="hidden" value="${fn_use }" id="fn_use_id" name="fn_use">
					<input type="checkbox" id="fn_id"><font color="red">选中则使用该属性</font>
					<br />
				</div>

				<div>
					<label for="fund_time">时间</label> <input type="text"
						name="fund_time" size="30" value="${ fund_time}" /> <input
						type="hidden" value="${ft_use }" id="ft_use_id" name="ft_use"><input
						type="checkbox" id="ft_id"><font color="red">选中则使用该属性</font>
					<br />
				</div>

				<div>
					<label for="fund_oper">单位</label> <input type="text"
						name="fund_oper" size="30" value="${fund_oper }" /> <input
						type="hidden" value="${fo_use }" id="fo_use_id" name="fo_use">
					<input type="checkbox" id="fo_id"><font color="red">选中则使用该属性</font>
					<br />
				</div>

				<div class="enter">
					<input name="submit" type="submit" class="buttom" value="提交" /> <input
						name="reset" type="reset" class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>

	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
		jQuery(function() {
			if (jQuery("#fn_use_id").val() == "1") {
				jQuery("#fn_id").attr('checked', true);
			}
			if (jQuery("#ft_use_id").val() == "1") {
				jQuery("#ft_id").attr('checked', true);
			}
			if (jQuery("#fo_use_id").val() == "1") {
				jQuery("#fo_id").attr('checked', true);
			}

			jQuery("#fn_id").click(
					function() {
						jQuery("#fn_use_id").attr("value",
								jQuery("#fn_id").is(':checked') ? 1 : 0);
					});

			jQuery("#ft_id").click(
					function() {
						jQuery("#ft_use_id").attr("value",
								jQuery("#ft_id").is(':checked') ? 1 : 0);
					});

			jQuery("#fo_id").click(
					function() {
						jQuery("#fo_use_id").attr("value",
								jQuery("#fo_id").is(':checked') ? 1 : 0);
					});
		});
	</script>
</body>
</html>

