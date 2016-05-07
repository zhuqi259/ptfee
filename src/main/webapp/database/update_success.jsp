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
<title></title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script type="text/javascript">
	$(function() {
		//调用父窗口的refresh()方法，刷新组织机构树
		window.parent.refresh();
	});
	
	function fun(id){
		window.location.href="<%=basePath%>system/database!list.action?id="+id;
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

	<!-- 主界面 -->
	<div align="center">

		<TABLE cellSpacing=0 cellPadding=0 width=540 align=center border=0>
			<TBODY>
				<TR>
					<TD vAlign=top height=270>
						<DIV align=center>
							<BR> <IMG height=211 src="common/success.gif" width=329><BR>
							<BR>
							<TABLE cellSpacing=0 cellPadding=0 width="80%" border=0>
								<TBODY>
									<TR>
										<TD><FONT class=p2>&nbsp;&nbsp;&nbsp; </FONT>
										</TD>
									</TR>
									<TR>
										<TD height=8></TD>
									<TR>
										<TD>
											<P>
												<FONT color=#00ff00></FONT>
											</P>
										</TD>
									</TR>
								</TBODY>
							</TABLE>
						</DIV>
					</TD>
				</TR>
				<TR>
					<TD height=5></TD>
				<TR>
					<TD align=middle>
						<CENTER>
							<TABLE cellSpacing=0 cellPadding=0 width=480 border=0>
								<TBODY>
									<TR>
										<TD width=6><IMG height=26 src="common/left.gif" width=7>
										</TD>
										<TD background="common/bg.gif">
											<DIV align=center>
												<FONT class=p6> <A href="javascript:void(0);"
													onclick="fun(${id});return false;">返回上一页</A> </FONT>
											</DIV>
										</TD>
										<TD width=7><IMG src="common/right.gif">
										</TD>
									</TR>
								</TBODY>
							</TABLE>
						</CENTER>
					</TD>
				</TR>
			</TBODY>
		</TABLE>

	</div>

</body>
</html>

