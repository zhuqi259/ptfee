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
<title>${status }</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>

<script type="text/javascript">
	function fun(id){
		window.location.href="<%=basePath%>system/database!list2.action?id="+id;
	}
	
	function downfile(id){
		window.location.href="<%=basePath%>fileDownFull?type=2&id="+id;
	}
	
	function downfile2(id){
		window.location.href="<%=basePath%>fileDownFull?type=1&id=" + id;
	}
	
	function look(id){
		window.location.href="<%=basePath%>fileLook?type=1&id=" + id;
	}
	
	function look2(id){
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

input[type="button"] {
	background-color: #AAA;
	width: 110px;
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
				<s:if test="IsRelated(id)"> style="color: #F00; background-color: #FFC " </s:if> />
		</s:iterator>
	</div>

	<div id="formwrapper">
		<h2 style="color: purple">工程名称:${info.project.proname }</h2>
		<h3>${info.approver.person.parent.name } -&gt; ${currentStatus }</h3>
		<fieldset>
			<legend>${currentStatus }</legend>
			<div align="center">
					<font color="purple" size="6">${currentStatus }</font>
				</div>
				<hr>
			<s:if test="IsNotNull()">
				<div align="center">
					<TABLE cellSpacing=0 cellPadding=0 width="95%" align=center
						border=1>
						<tr>
							<td colspan="2" align="center">模板下载</td>
						</tr>

						<s:iterator value="myFilesByStatus" status="st" var="fs">
							<tr>
								<td width="50%"><s:property value="#st.index+1" />.【模板】<s:property
										value="#fs.name" /> <s:property value="#fs.contentType" /></td>
								<td><s:if test="!IsLost(path)">
										<a href="javascript:void(0);"
											onclick="look2(${id});return false;">查看</a>|
											<a href="javascript:void(0);"
											onclick="downfile(${id});return false;">下载</a>
									</s:if> <s:else>
										<font color="red">文件暂缺 </font>
									</s:else></td>
							</tr>
						</s:iterator>
					</TABLE>
				</div>
				<div align="center">
					<TABLE cellSpacing=0 cellPadding=0 width="95%" align=center
						border=1>
						<tr>
							<td colspan="2" align="center">上传文件</td>
						</tr>
						<s:iterator value="myFilesByStatus" status="st" var="fs">
							<tr>
								<td width="50%"><s:property value="#st.index+1" />.<s:property
										value="#fs.name" /> <s:property value="#fs.contentType" /></td>
								<td><s:if test="IsExisted(#projectId,id)">
										<a href="javascript:void(0);"
											onclick='look(<s:property value="getPFileByZFile(#projectId,id)"/>) ;return false;'>查看</a>
											|<a href="javascript:void(0);"
											onclick='downfile2(<s:property value="getPFileByZFile(#projectId,id)"/>) ;return false;'>下载</a>
									</s:if> <s:else>
										<font color="red">文件暂缺 </font>
									</s:else></td>
							</tr>
						</s:iterator>
					</TABLE>
				</div>
			</s:if>
			<br> <br>
			<s:if test="IsZPaymentNotNull()">
				<div align="center">
					<font size="5px" color="red">财务信息</font>
				</div>
				<s:iterator value="myZPaymentsByStatus" var="payment">
					<input type="hidden" name="payId" value="${id }" />
					<div align="center">
						收取费用名称:<input type="text" required="required" readonly="readonly"
							value="${name }" />&nbsp;&nbsp;&nbsp;金额:<input type="text"
							name="money" required="required" readonly="readonly"
							value='<s:property value="getPaymentMoney(#projectId,id)" />' />元&nbsp;&nbsp;&nbsp;付款单位:<input
							type="text" name="payer" required="required" readonly="readonly"
							value='<s:property value="getPaymentPayer(#projectId,id)" />' />&nbsp;&nbsp;&nbsp;付款时间:<input
							type="text" name="payTime" required="required"
							value='<s:property value="getPaymentTime(#projectId,id)" />' />
					</div>
				</s:iterator>
			</s:if>
			<s:if test="IsZfundNotNull()">
				<div align="center">
					<font size="5px" color="red">附加信息</font>
				</div>
				<s:iterator value="myFundsByStatus" var="fund">
					<input type="hidden" name="fundId" value='${id }' />
					<div align="center">
						<s:if test="#fund.zfund.fn_use==1">
							${zfund.fund_name }:<input type="text" name="fmoney" readonly="readonly"
								value='${money }' required="required">元&nbsp;&nbsp;&nbsp;
							</s:if>
						<s:else>
							<input type="hidden" name="fmoney">
						</s:else>
						<s:if test="#fund.zfund.ft_use==1">
							${zfund.fund_time }:<input type="text" name="ftime" readonly="readonly"
								value="${ time}" required="required">&nbsp;&nbsp;&nbsp;
							</s:if>
						<s:else>
							<input type="hidden" name="ftime">
						</s:else>
						<s:if test="#fund.zfund.fo_use==1">
							${zfund.fund_oper }:<input type="text" name="foper" readonly="readonly"
								value="${oper }" required="required">
						</s:if>
						<s:else>
							<input type="hidden" name="foper">
						</s:else>
					</div>
				</s:iterator>
			</s:if>
			<div class="enter">
				<input type="submit" class="buttom" value="提交" /> <input
					name="reset" type="reset" class="buttom" value="重置" />
			</div>

		</fieldset>
	</div>

</body>
</html>

