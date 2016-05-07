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
<title>提交/审核工程</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#b01").click(function() {
			htmlobj = $.ajax({
				url : "<%=basePath%>person/project!getId.action",
				async : false
			});
			$("#fid").attr("value", htmlobj.responseText);
		});

		$("input[name='allcomment']").change(function() {
			if ($(this).attr("checked")) {
				var comment = $(this).val();
				$("#comment").html("【" + $(this).val() + "】");
				if (comment == "驳回" || comment == "不同意") {
					$("#yes").hide();
					$("#no").show();
				} else {
					$("#yes").show();
					$("#no").hide();
				}
			}
		});
	});
	$(function() {
		if ($.browser.msie) {
			$('input:radio').click(function() {
				this.blur();
				this.focus();
			});
		}
	});
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
		<h2 style="color: purple">工程名称:${proname }</h2>
		<h3>${status }</h3>
		<form action="person/project.action" method="post">
			<input type="hidden" name="method:approve" value=""> <input
				type="hidden" name="id" value="${id }"> <input type="hidden"
				name="userId" value="${userId }"><input type="hidden"
				name="currentStatus" value="${currentStatus }"> <input
				type="hidden" name="tid" value="${tid }">
			<fieldset>
				<legend>审核意见</legend>

				<s:if test="!IsCSR(userId)">
					<div align="center">
						<font color="#00706b">请选择-&gt;</font> <input type="radio"
							name="allcomment" value="同意" />同意 <input type="radio"
							name="allcomment" value="批准" />批准 <input type="radio"
							name="allcomment" value="通过" />通过 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
							type="radio" name="allcomment" value="驳回" /><font color="red">驳回</font><input
							type="radio" name="allcomment" value="不同意" /><font color="red">不同意</font>
					</div>
				</s:if>

				<div align="center">
					<textarea id="comment" name="comment" cols="80" rows="10"></textarea>
				</div>
				<div class="enter">

					<s:if test="IsCSR(userId)">
						<input type="submit" class="buttom" value="提交" />
					</s:if>
					<s:else>

						<input name="result" id="yes" type="submit" class="buttom"
							value="审核通过" style="display:none" />&nbsp;&nbsp;<input
							name="result" id="no" type="submit" class="buttom" value="驳回"
							style="display:none" />
					</s:else>
				</div>
		</form>

		</fieldset>
	</div>

</body>
</html>

