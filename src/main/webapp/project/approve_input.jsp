<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看并审批工程信息,包括签订协议链接</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>

<script type="text/javascript">
	function fun(url) {
		var id = $("#id").val();
		window.location.href = url + "id=" + id;
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
	font-size: 15px;
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
	font-size: 18px;
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

#zhuqi {
	width: 75%;
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
		<h3>查看工程信息界面</h3>

		<form action="person/project.action" method="post">
			<input type="hidden" id="id" name="id" value="${id }"> <input
				type="hidden" name="userId" value="${userId }">
			<fieldset>
				<legend>工程信息</legend>
				<div>
					<br> <a
						href="<%=basePath %>person/project!history.action?id=${id }"><font
						size="4px">查看工程审核记录</font> </a> &nbsp;&nbsp;|&nbsp;&nbsp; <a
						href="<%=basePath %>person/project!viewPic.action?id=${id }"><font
						size="4px">查看工程当前状态</font> </a>


					<s:iterator value="myTask" var="task">
						<div>
							<br /> <a
								onclick="fun('<%=basePath %>person/project!taskInput.action?tid=${id }&');return false;"
								href="javascript:void(0);"><font size="4px" color="red">
									<s:property value="#task.activityName" /> </font> </a>
						</div>
					</s:iterator>
					<br>
				</div>
				<div>
					<TABLE cellSpacing=0 cellPadding=0 width="93%" align=center
						border=1>
						<tr>
							<td width="40%">工程名称：</td>
							<td><input name="proname" id="proname" value="${proname }"
								readonly="readonly" size=40 /></td>
						</tr>
						<tr>
							<td>开发单位：</td>
							<td><input name="developer" id="developer"
								value="${developer }" size=40 readonly="readonly" />
							</td>
						</tr>
						<tr>
							<td>受理建筑面积：</td>
							<td><input name="area" id="area" value="${area }"
								readonly="readonly" size=40 />平方米</td>
						</tr>
						<tr>
							<td>座落地点：</td>
							<td><input name="location" id="location"
								value="${location }" readonly="readonly" size=40 /></td>
						</tr>
						<tr>
							<td>收费标准：</td>
							<td><input name="fee" id="fee" value="${fee }"
								readonly="readonly" size=40 />元/平方米</td>
						</tr>
						<tr>
							<td>收取款项：</td>
							<td><input name="sfee" id="sfee" readonly="readonly"
								value="${sfee }" size=40 />元</td>
						</tr>
					</TABLE>
				</div>
				<div id="zhuqi" align="center">
					<fieldset>
						<legend>操作信息</legend>
						<div>
							<s:iterator value="usedActivity" status="st" var="ua">
								<div>
									<fieldset>
										<legend>${ua.name }</legend>
										<div>
											<s:set value="getMyApprove(#projectId, #ua.name)" var="info">
											</s:set>
											<s:if test="#info.approver!=null">
												<div align="right">操作人：【${info.approver.person.parent.name
													}】${info.approver.person.name }</div>
											</s:if>
											<s:if
												test='IsShenHe(#ua.name)|| (#ua.name=="工程受理"&&#info.comment!=null)'>
												<div>
													<font color="green">审核意见： ${info.comment }</font>
												</div>
											</s:if>
											<table>
												<s:iterator value="getZfile(#ua.id)" status="sta" var="fs">
													<tr>
														<td><s:property value="#sta.index+1" /> . <s:property
																value="#fs.name" /> <s:property value="#fs.contentType" />
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
											</table>
										</div>
									</fieldset>
								</div>
							</s:iterator>
						</div>
					</fieldset>
				</div>
			</fieldset>
		</form>
	</div>

</body>
</html>

