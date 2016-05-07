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
<title>404 Not Found</title>
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

.t {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	color: #CC0000;
}

.c {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: normal;
	color: #000000;
	line-height: 18px;
	text-align: center;
	border: 1px solid #CCCCCC;
	background-color: #FFFFEC;
}

body {
	background-color: #FFFFFF;
	margin-top: 100px;
}
</STYLE>
</head>
<body>
	<TABLE cellSpacing=0 cellPadding=0 width=540 align=center border=0>
		<TBODY>
			<TR>
				<TD vAlign=top>
					<DIV align=center>

						<IMG src="common/404.jpg" />

					</DIV>
				</TD>
			</TR>

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