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
<title>我的排序Excel属性</title>
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

</head>
<body>
	<DIV align="center" style="margin-top: 10px">
		<font color="red" size="4px">【注】排序使用Index从1开始，不能重复，请小心处理</font>
	</DIV>
	<div id="formwrapper">
		<form action="system/sequenceSort.action" method="post">
			<input type="hidden" name="method:sort" value="">
			<table id="sequenceList" class="display" width="100%" border="0"
				cellpadding="0" cellspacing="1" bgcolor="#a8c7ce">
				<thead>
					<tr>
						<th height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">属性标识</span>
							</div>
						</th>
						<th height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">属性名称</span>
							</div>
						</th>
						<th height="20" bgcolor="d3eaef" class="STYLE6"><div
								align="center">
								<span class="STYLE10">顺序Index</span>
							</div>
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="customSequences" var="sequence" status="status">
						<s:if test="#status.odd">
							<tr bgcolor="ddffdd">
						</s:if>
						<s:else>
							<tr bgcolor="eeffee">
						</s:else>
						<td><div align="center">${sequence.fieldMap.name}</div>
						</td>
						<td><div align="center">${sequence.fieldMap.displayName}</div>
						</td>
						<td><div align="center">
								<input type="text" name="myIndex" value="${sequence.index }">
							</div>
						</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<div class="enter">
				<input name="submit" type="submit" class="buttom" value="提交"
					style="cursor: pointer;" /> <input name="reset" type="reset"
					class="buttom" value="重置" />
			</div>
		</form>
	</div>
</body>
</html>

