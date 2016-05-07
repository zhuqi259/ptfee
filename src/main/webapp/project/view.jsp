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
<title>查看工程信息</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>

<script type="text/javascript">
	function fun(id){
		window.location.href="<%=basePath%>system/database!list2.action?id="+ id;
	}
	
	function view(id){
		window.location.href="<%=basePath%>person/project!viewPic.action?id="+ id;
	}
	
	function downfile(id){
		window.location.href="<%=basePath%>fileDownFull?type=1&id=" + id;
	}
	
	function look(id){
		window.location.href="<%=basePath%>fileLook?type=1&id=" + id;
	}
	
	function fun2(url) {
		var id = $("#id").val();
		window.location.href = url + "id=" + id;
	}
	function del(id){
		if (!confirm("请确认是否要删除选中的工程！")) {
			return;
		}

		if (!confirm("请再次确认是否要删除选中的工程，删除之后，该工程的所有信息都将被删除，请确认！")) {
			return;
		}
		window.location.href="<%=basePath%>person/project!del.action?id="+ id;
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
	font-size: 12px;
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
	background-color: #FFC;
	width: 110px;
	height: 50px;
	font-size: 14px;
}
-->
</style>
</head>
<body>
	<div>
		<s:iterator value="databases">
			<input type="button" value="${name }" onclick="fun(${id })" />
		</s:iterator>
	</div>
	<div id="formwrapper">
		<input type="hidden" id="id" name="id" value="${id }">
		<h3>查看工程信息界面</h3>
		<fieldset>
			<legend>工程信息</legend>
			<s:if test="IsXiaohuable()">
				<div align="right">
					<a href="javascript:void(0);" onclick="del(${id});return false;"><font
						size="4px" color="red">销户</font> </a>
				</div>
			</s:if>
			<div>
				<br> <a href="person/project!history.action?id=${id }"><font
					size="4px">查看工程审核记录</font> </a> &nbsp;&nbsp;|&nbsp;&nbsp; <a
					onclick="view(${id});return false;" href="javascript:void(0);"><font
					size="4px">查看工程当前状态</font> </a> <br>
			</div>
			<div>

				<TABLE cellSpacing=0 cellPadding=0 width="93%" align=center border=1>
					<tr>
						<td width="24%">工程名称：</td>
						<td>
							<div>${proname }</div></td>
					</tr>
					<tr>
						<td>开发单位：</td>
						<td><div>${developer }</div>
						</td>
					</tr>
					<s:if test='fid!=null'>
						<tr>
							<td>工程编号：</td>
							<td>
								<div>${fid }</div>
							</td>
						</tr>

					</s:if>
					<tr>
						<td>受理建筑面积：</td>
						<td><div>${area }平方米</div>
						</td>
					</tr>
					<tr>
						<td>座落地点：</td>
						<td><div>${location }</div>
						</td>
					</tr>
					<tr>
						<td>收费标准：</td>
						<td><div>${fee }元/平方米</div>
						</td>
					</tr>
					<tr>
						<td>收取款项：</td>
						<td><div>${sfee }元</div>
						</td>
					</tr>

				</TABLE>
			</div>

			<div id="zhuqi">
				<fieldset>
					<legend>操作信息</legend>
					<div>
						<s:iterator value="usedActivity" status="st" var="ua">
							<div>
								<fieldset>
									<legend>${ua.name }</legend>
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

									<s:if
										test="IsCUser(userId, #projectId, #ua.name)&&!IsShenHe(#ua.name)">
										<div align="right">
											<s:if test='#ua.name=="工程受理"'>
												<a
													onclick="fun2('<%=basePath%>person/project!updateInput.action?activityId=${ua.id }&');return false;"
													href="javascript:void(0);"><font size="4px" color="red">修改工程信息</font>
												</a>
											</s:if>

											<s:elseif test='#ua.name=="分配工程编号"'>
												<a
													onclick="fun2('<%=basePath%>person/project!updateIdInput.action?activityId=${ua.id }&');return false;"
													href="javascript:void(0);"><font size="4px" color="red">修改工程编号</font>
												</a>
											</s:elseif>
											<s:else>
												<a
													onclick="fun2('<%=basePath%>person/project!updateOtherInput.action?activityId=${ua.id }&');return false;"
													href="javascript:void(0);"><font size="4px" color="red">上传文件、财务信息、附加信息修改</font>
												</a>

											</s:else>

										</div>
									</s:if>
									<div>
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
			<div align="center">
				<TABLE cellSpacing=0 cellPadding=0 width=480 border=0>
					<TBODY>
						<TR>
							<TD width=6><IMG height=26 src="common/left.gif" width=7>
							</TD>
							<TD background="common/bg.gif">
								<DIV align=center>
									<FONT class=p6> <A href="javascript:history.go(-1)">返回上一页</A>
									</FONT>
								</DIV></TD>
							<TD width=7><IMG src="common/right.gif"></TD>
						</TR>
					</TBODY>
				</TABLE>

			</div>

		</fieldset>
	</div>

</body>
</html>

