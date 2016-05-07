<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出现错误啦！</title>
<STYLE type=text/css>
INPUT {
	FONT-SIZE: 12px
}

TD {
	FONT-SIZE: 12px
}

.p2 {
	FONT-SIZE: 12px
}

.p6 {
	FONT-SIZE: 12px;
	COLOR: #1b6ad8
}

A {
	COLOR: #1b6ad8;
	TEXT-DECORATION: none
}

A:hover {
	COLOR: red
}
</STYLE>
</head>
<body>
	<TABLE cellSpacing=0 cellPadding=0 width=540 align=center border=0>
		<TBODY>
			<TR>
				<TD vAlign=top height=270>
					<DIV align=center>
						<BR>
						<IMG height=211 src="common/error.gif" width=329><BR>
						<BR>
						<TABLE cellSpacing=0 cellPadding=0 width="80%" border=0>
							<TBODY>
								<TR>
									<TD><FONT class=p2>&nbsp;&nbsp;&nbsp; 出错的原因是：</FONT>
									</TD>
								</TR>
								<TR>
									<TD height=8></TD>
								<TR>
									<TD>
										<P>
											<FONT color=#ff0000><IMG height=13
												src="common/emessage.gif" width=12>&nbsp;${exception.message}</FONT>
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
											<FONT class=p6> <A href="javascript:history.go(-1)">返回上一页</A>
											</FONT>
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
</body>
</html>