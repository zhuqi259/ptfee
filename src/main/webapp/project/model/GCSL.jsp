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
<title>客户代表录入工程信息</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script type="text/javascript">	
	function fun(id){
		window.location.href="<%=basePath%>system/database!list2.action?id="+id;
	}
	function downfile(id){
		window.location.href="<%=basePath%>fileDownFull?type=1&id=" + id;
	}
	function look(id){
		window.location.href="<%=basePath%>fileLook?type=1&id=" + id;
	}
	
</script>
<style type="text/css">
<!--
body {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 16px;
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

input[type="button"] {
	background-color: #AAA;
	width:110px;
	height: 50px;
	font-size: 14px;
}
-->
</style>
</head>
<body>
	操作人员：${info.approver.person.name } &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	操作时间:${info.rTime }
	<hr>

	<div>
		<s:iterator value="databases">
			<input type="button" value="${name }" onclick="fun(${id })"
				<s:if test="IsRelatedGCSL(id)"> style="color: #F00; background-color: #FFC " </s:if> />
		</s:iterator>
	</div>

	<div id="formwrapper">
		<h3>录入工程信息界面</h3>
		<fieldset>
			<legend>工程信息</legend>
			<div>
				<TABLE cellSpacing=0 cellPadding=0 width="93%" align=center border=1>
					<tr>
						<td width="50%">工程名称：</td>
						<td><input name="proname" id="proname"
							value="${info.project.proname }" required="required"
							readonly="readonly" size=40 />
						</td>
					</tr>
					<tr>
						<td>开发单位：</td>
						<td><input name="area" id="area"
							value="${info.project.developer }" readonly="readonly" size=40 /></td>
					</tr>
					<tr>
						<td>受理建筑面积：</td>
						<td><input name="area" id="area"
							value="${info.project.area }" readonly="readonly" size=40 />平方米</td>
					</tr>
					<tr>
						<td>座落地点：</td>
						<td><input name="location" id="location"
							value="${info.project.location }" readonly="readonly" size=40 />
						</td>
					</tr>
					<tr>
						<td>收费标准：</td>
						<td><input name="fee" id="fee" value="${info.project.fee }"
							readonly="readonly" size=40 />元/平方米</td>
					</tr>
					<tr>
						<td>收取款项：</td>
						<td><input name="sfee" id="sfee"
							value="${info.project.sfee }" readonly="readonly" size=40 />元</td>
					</tr>
					<tr>
						<td colspan="2" align="center">上传扫描件列表:</td>
					</tr>
					<s:iterator value="myFilesByGCSL" status="st" var="files">
						<tr>
							<td><s:property value="#st.index+1" />.<s:property
									value="#files.name" /> <s:property value="#files.contentType" />
							</td>
							<td><s:if test="IsExisted(#projectId,id)">
									<a href="javascript:void(0);"
										onclick='look(<s:property value="getPFileByZFile(#projectId,id)"/>) ;return false;'>查看</a>
											|<a href="javascript:void(0);"
										onclick='downfile(<s:property value="getPFileByZFile(#projectId,id)"/>) ;return false;'>下载</a>
								</s:if> <s:else>
									<font color="red">文件暂缺 </font>
								</s:else>
							</td>
						</tr>

					</s:iterator>
				</TABLE>
			</div>
			<div>
				<label for="workflows">工作流</label> <select name="workflowId">
					<s:iterator value="workflows">
						<option value="<s:property value="id"/>">
							<s:property value="name" />
						</option>
					</s:iterator>
				</select> <br />
			</div>
		</fieldset>
	</div>

</body>
</html>

