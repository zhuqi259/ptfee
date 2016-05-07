<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CMS 后台管理工作平台</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	font-size: 12px;
	color: #ffffff;
}

.STYLE5 {
	font-size: 12
}

.STYLE7 {
	font-size: 12px;
	color: #FFFFFF;
}

.STYLE7 a {
	font-size: 12px;
	color: #FFFFFF;
	text-decoration: none;
}

a img {
	border: none;
}
-->
</style>
<script type="text/javascript">
	var open = true;
	function openClose() {
		if (open) {
			window.parent.mainFrame.document.getElementById("leftMenu").width = 1;
			open = false;
		} else {
			window.parent.mainFrame.document.getElementById("leftMenu").width = 207;
			open = true;
		}
	}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"
		bgcolor="#00706b">
		<tr>
			<td height="62"></td>
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="512" height="62" background="images/main1_1.gif"
							style="background-repeat: no-repeat;">&nbsp;</td>
						<td colspan="2"></td>

						<td width="248" align="right">
							<table width="238" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td height="17"><div align="right">
											<a href="system/user!updatePwdInput.action"
												target="rightFrame"><img src="images/pass.gif"
												width="69" height="17" /> </a>
										</div></td>
									<td><div align="right">
											<a href="system/person!updateInput2.action"
												target="rightFrame"><img src="images/user.gif"
												width="69" height="17" /> </a>
										</div></td>
									<td><div align="right">
											<a href="system/login!quit.action" target="_parent"><img
												src="images/quit.gif" alt=" " width="69" height="17" /> </a>
										</div></td>
								</tr>
							</table></td>
				</table></td>
		</tr>
		<tr>
			<td height="35"></td>
			<td bgcolor="white">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="400" height="35" background="images/main1_2.gif"
							style="background-repeat: no-repeat;">&nbsp;</td>
						<td colspan="3"></td>
				</table></td>
		</tr>
		<tr>
			<td height="30" bgcolor="#00706b" colspan="2"><table
					width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="8" height="30"></td>
						<td width="207" background="images/666.gif" onclick="openClose()"
							style="cursor:pointer;background-repeat: no-repeat;"
							title="点击可以打开/隐藏管理菜单"><table width="100%" border="0"
								cellspacing="0" cellpadding="0">
								<tr>
									<td width="15%">&nbsp;</td>
									<td width="52%" height="20" valign="bottom" class="STYLE1">管理菜单</td>
									<td width="33%">&nbsp;</td>
								</tr>
							</table></td>
						<td width="39"><img src="images/444.gif" width="39"
							height="30" /></td>
						<td><table width="100%" border="0" cellspacing="0"
								cellpadding="0">
								<tr>
									<td height="20" valign="bottom"><span class="STYLE1">当前登录用户：${login.name}
											&nbsp;</span></td>
									<td>
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="21"><img src="images/main_13.gif" width="19"
													height="14" /></td>
												<td width="35" class="STYLE7"><div align="center">
														<a
															href="javascript:window.parent.location.href='main.jsp'">首页</a>
													</div></td>
												<td width="21" class="STYLE7"><img
													src="images/main_15.gif" width="19" height="14" /></td>
												<td width="35" class="STYLE7"><div align="center">
														<a href="javascript:history.go(-1);">后退</a>
													</div></td>
												<td width="21" class="STYLE7"><img
													src="images/main_17.gif" width="19" height="14" /></td>
												<td width="35" class="STYLE7"><div align="center">
														<a href="javascript:history.go(1);">前进</a>
													</div></td>
												<td width="21" class="STYLE7"><img
													src="images/main_19.gif" width="19" height="14" /></td>
												<td width="35" class="STYLE7"><div align="center">
														<a href="javascript:window.parent.location.reload();">刷新</a>
														<!-- javascript:window.parent['mainFrame']['rightFrame'].location.reload(); -->
													</div></td>
												<td>&nbsp;</td>
											</tr>
										</table>
									</td>

									<td valign="bottom" class="STYLE1"><div align="right">
											登录IP：${login.ip }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登录时间：
											<fmt:formatDate value="${login.loginTime }"
												pattern="yyyy年MM月dd日 HH:mm:ss" />
										</div></td>
								</tr>
							</table></td>
						<td width="17"></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>

