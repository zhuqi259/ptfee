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
<title>七库文件下载</title>

<script type="text/javascript">
	function downfile(id){
		window.location.href="<%=basePath%>fileDownFull?type=2&id=" + id;
	}
	function look(id){
		window.location.href="<%=basePath%>fileLook?type=2&id=" + id;
	}
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
		<h3>七库文件下载</h3>

		<fieldset>
			<legend>${name } </legend>

			<div>
				<table cellSpacing=0 cellPadding=0 width="93%" align=center border=1>
					<s:iterator value="allZfiles" status="st" var="fs">
						<tr>
							<td><s:property value="#fs.name" /><s:property value="#fs.contentType" /></td>
							<td><s:if test="!IsLost(#fs.path)">
									<a href="javascript:void(0);" onclick="look(${id});return false;">查看</a>| <a
										href="javascript:void(0);" onclick="downfile(${id});return false;">下载</a>
								</s:if> <s:else>
									<font color="red">文件暂缺 </font>
								</s:else>
							</td>
						</tr>

					</s:iterator>
				</table>
			</div>


			<DIV align=center>
				<FONT class=p6> <A href="javascript:history.go(-1)">返回上一页</A>
				</FONT>
			</DIV>
		</fieldset>
		</form>

	</div>
</body>
</html>

