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
<title>列出5次正式用电付款比例</title>
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
	
</script>

</head>
<body>

	<div id="formwrapper">
		<div align="center">
			<font color="purple" size="6">5次正式用电付款比例设置</font>
		</div>
		<div align="center">
			<form action="system/zpercentum.action" method="post">
				<input type="hidden" name="method:update" value="">
				<table cellSpacing=0 cellPadding=0 width="63%" align=center border=1>
					<thead>
						<tr>
							<td width="50%">流程节点名称</td>
							<td>付款比例</td>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="allZFKActivities" var="activity">
							<tr>
								<td>${activity.name }</td>
								<td><input type="text" name="percentum"
									value='<s:property value="getPercentum(#activity.id)" />'>%
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<div class="enter">
					<input name="submit" type="submit" class="buttom" value="提交" /> <input
						name="reset" type="reset" class="buttom" value="重置" />
				</div>
			</form>
		</div>
	</div>

</body>
</html>

