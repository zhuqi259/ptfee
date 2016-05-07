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
<title>列出ID生成规则</title>
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
</style>

<script type="text/javascript">
	function fun(obj, id){	
		$.post("<%=basePath%>system/rule!updateUsed.action", { id: id }, function (data, status) {
			 if(status != "success"){
				alert("出错啦!");
				return;
			 }
      });	
	}
	
	function add(){
		window.location.href = "<%=basePath%>system/rule!addInput.action";
	}
</script>

</head>
<body>

	<div id="formwrapper">
		<table id="ruleList" class="display" width="100%" border="0"
			cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
			<thead>
				<tr>
					<th height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">Choose</div></th>
					<th height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">规则</span>
						</div></th>
					<th height="20" bgcolor="d3eaef" class="STYLE6"><div
							align="center">
							<span class="STYLE10">示例</span>
						</div></th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="allRules" var="rule" status="status">
					<s:if test="#status.odd">
						<tr bgcolor="ddffdd">
					</s:if>
					<s:else>
						<tr bgcolor="eeffee">
					</s:else>
					<td>
						<div align="center">
							<input type="radio" name="rule"
								<s:property value="hasChecked(id)" />
								onclick="fun(this, ${rule.id })">
						</div>
					</td>
					<td>
						<div align="center">${rule.irule }</div></td>
					<td>
						<div align="center">${rule.example }</div></td>

					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
	<div align="center">
		<input type="button" onclick="add()" value="新增规则"
			style="width: 125 ;height: 30; background-color: #FFC; font-size: larger; cursor: pointer;">
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

