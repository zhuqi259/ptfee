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
<title>新增规则信息</title>
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

#ruleText {
	border: 1px solid green;
	width: 225px;
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

<script type="text/javascript">
$(document).ready(function() {
	$("#irule").blur(function() {
		var irule = $("#irule").val();
		var input = $(this);            
        var offset = input.offset();
		htmlobj = $.ajax({
			url : "<%=basePath%>system/rule!check.action?irule=" + irule,
			async : false
		});
		$("#ruleText").html(htmlobj.responseText)
				.css('left', offset.left + input.width() + 2 + 'px')
				.css('top', offset.top + 'px')
				.fadeIn();
		$("#ruleText").fadeOut(3000);
	});
});

function checkForm(form) {
	if (form.irule.value == "") {
		alert("请输入规则!");
		form.irule.focus();
		return false;
	}
}
</script>

</head>
<body>

	<div id="formwrapper">
		<h3>设置规则</h3>
		<form name="add_rule" action="system/rule.action" method="post"
			onsubmit="submit.disabled=1">
			<input type="hidden" name="method:add" value=""> <input
				type="hidden" name="inUse" value="N">
			<fieldset>
				<legend>请设置ID生成规则 </legend>
				<div id="ruleText"></div>
				<div>
					<label for="irule">输入规则</label> <input type="text" name="irule"
						id="irule" required="required" size="30" /> <br />
				</div>

				<div>
					<input name="submit" type="submit" class="buttom" value="提交"
						onclick="return checkForm(add_rule)" style="cursor: pointer;" />
					<input name="reset" type="reset" class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>
	<hr>
	<br>

	<DIV align="center">
		<table>
			<tr>
				<td align="center"><font color="red" size="4px">【注】ID规则使用手册:</font>
				</td>
			</tr>
			<tr>
				<td>
					<DIV>1、规则之间用","隔开(注意是英文的,)。</DIV></td>
			</tr>
			<tr>
				<td><DIV>2、支持自定义字符，例如"JL2013001"中的"JL"，使用规则为"JL";</DIV></td>
			</tr>
			<tr>
				<td><DIV>
						3、支持日期格式，例如"JL2013001"中的"2013"，使用规则为"yyyy";
						<div align="center">
							日期格式遵循规则如下:<br>
							<table border="1" cellpadding="0" cellspacing="0">
								<tr align="center">
									<td>序号</td>
									<td>规则</td>
									<td>描述</td>
									<td>示例</td>
								</tr>
								<tr align="center">
									<td>①</td>
									<td>yyyy</td>
									<td>年</td>
									<td>2013</td>
								</tr>
								<tr align="center">
									<td>②</td>
									<td>yy</td>
									<td>年的尾数</td>
									<td>13</td>
								</tr>
								<tr align="center">
									<td>③</td>
									<td>MM</td>
									<td>月份</td>
									<td>08</td>
								</tr>
								<tr align="center">
									<td>④</td>
									<td>dd</td>
									<td>月份的天数</td>
									<td>03</td>
								</tr>
								<tr align="center">
									<td>⑤</td>
									<td>HH</td>
									<td>一天中的小时数（0-23）</td>
									<td>16</td>
								</tr>
								<tr align="center">
									<td>⑥</td>
									<td>hh</td>
									<td>am/pm 中的小时数（1-12）</td>
									<td>04</td>
								</tr>
								<tr align="center">
									<td>⑦</td>
									<td>mm</td>
									<td>小时中的分钟数</td>
									<td>04</td>
								</tr>
								<tr align="center">
									<td>⑧</td>
									<td>ss</td>
									<td>分钟中的秒数</td>
									<td>04</td>
								</tr>
							</table>
						</div>
					</DIV></td>
			</tr>
			<tr>
				<td><DIV>
						4、支持自定义长度的自增编号(从1开始)，例如"JL2013001"中的"001"，使用规则为"$3",该规则以$打头，之后的数字代表该自增编号的长度;
					</DIV></td>
			</tr>
			<tr>
				<td>
					<DIV>5、不支持字符%,&;</DIV></td>
			</tr>
			<tr>
				<td><DIV>
						6、假设1中的自定义字符需要使用$这个符号，规则书写为"$$"，例如要生成"JL$CC20130001"，使用规则为"JL$$CC,yyyy,$4".
					</DIV></td>
			</tr>
		</table>
	</DIV>
</body>
</html>

