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
	function filechange(file, hidId) {
		var filesss = document.getElementById(file).value;
		if (filesss.length>0 ) {
			document.getElementById(hidId).value = "1";
		}else{
			document.getElementById(hidId).value = "0";
		}
	}

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
	
	function NoSubmit(ev)
	{
	    if( ev.keyCode == 13 )
	    {
	        return false;
	    }
	    return true;
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

	<div>
		<s:iterator value="databases">
			<input type="button" value="${name }" onclick="fun(${id })"
				<s:if test="IsRelated(id)"> style="color: #F00; background-color: #FFC " </s:if> />
		</s:iterator>
	</div>

	<div id="formwrapper">
		<h3>${login.position } -&gt; ${currentStatus }</h3>
		<form action="person/project.action" method="post"
			onkeypress="javascript:return NoSubmit(event);"
			onsubmit="submit.disabled=1" enctype="multipart/form-data">
			<input type="hidden" name="method:updateOther" value=""> <input
				type="hidden" name="id" value="${id }"> <input type="hidden"
				name="activityId" value="${activityId }"> <input
				type="hidden" name="currentStatus" value="${currentStatus }">
			<fieldset>
				<legend>${currentStatus }</legend>
				<div align="center">
					<font color="purple" size="6">${currentStatus }</font>
					<s:if test="IsZFK()">
						<font color="blue" size="6">(<s:property value="getCurrPercentum()" />%)</font>
					</s:if>
				</div>
				<hr>
				<s:if test="IsNotNull()">
					<div align="center">
						<TABLE cellSpacing=0 cellPadding=0 width="93%" align=center
							border=1>
							<tr>
								<td colspan="2" align="center">模板下载</td>
							</tr>

							<s:iterator value="myFilesByStatus" status="st" var="fs">
								<tr>
									<td width="50%"><s:property value="#st.index+1" />.【模板】<s:property
											value="#fs.name" /> <s:property value="#fs.contentType" />
									</td>
									<td><s:if test="#fs.path!=null">
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
						<TABLE cellSpacing=0 cellPadding=0 width="93%" align=center
							border=1>
							<tr>
								<td colspan="3" align="center">上传文件</td>
							</tr>
							<s:iterator value="myFilesByStatus" status="st" var="fs">
								<tr>
									<td width="40%"><s:property value="#st.index+1" />.<s:property
											value="#fs.name" /> <s:property value="#fs.contentType" />
									</td>
									<s:if test="!IsExisted(#projectId,id)">
										<td><font color="red">文件暂缺 </font></td>
										<td><input type="file" name="uploadFiles"
											id='file<s:property value="#st.index+1" />'
											onchange="filechange('file<s:property value="#st.index+1" />', 'flag<s:property value="#st.index+1" />')" />
											<input type="hidden"
											id='flag<s:property value="#st.index+1" />' name="fileUsed"
											value="0" />
										</td>
									</s:if>
									<s:else>
										<td width="15%"><a href="javascript:void(0);"
											onclick='look(<s:property value="getPFileByZFile(#projectId,id)"/>) '>查看</a>
											|<a href="javascript:void(0);"
											onclick='downfile2(<s:property value="getPFileByZFile(#projectId,id)"/>) '>下载</a>
										</td>
										<td><font color="green">重新上传</font><input type="file"
											name="uploadFiles"
											id='file<s:property value="#st.index+1" />'
											onchange="filechange('file<s:property value="#st.index+1" />', 'flag<s:property value="#st.index+1" />')" />
											<input type="hidden"
											id='flag<s:property value="#st.index+1" />' name="fileUsed"
											value="0" />
										</td>

									</s:else>
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
						<input type="hidden" name="payId"
							value='<s:property value="getPaymentId(#projectId,id)"/>' />
						<div align="center">
							收取费用名称:<input type="text" required="required" readonly="readonly"
								value="${name }" />&nbsp;&nbsp;&nbsp;金额:<input type="text"
								name="money" required="required"
								value='<s:property value="getPaymentMoney(#projectId,id)" />' />元&nbsp;&nbsp;&nbsp;付款单位:<input
								type="text" name="payer" required="required"
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
							${zfund.fund_name }:<input type="text" name="fmoney"
									value='${money }' required="required">元&nbsp;&nbsp;&nbsp;
							</s:if>
							<s:else>
								<input type="hidden" name="fmoney">
							</s:else>
							<s:if test="#fund.zfund.ft_use==1">
							${zfund.fund_time }:<input type="text" name="ftime"
									value="${ time}" required="required">&nbsp;&nbsp;&nbsp;
							</s:if>
							<s:else>
								<input type="hidden" name="ftime">
							</s:else>
							<s:if test="#fund.zfund.fo_use==1">
							${zfund.fund_oper }:<input type="text" name="foper"
									value="${oper }" required="required">
							</s:if>
							<s:else>
								<input type="hidden" name="foper">
							</s:else>
						</div>
					</s:iterator>
				</s:if>
				<div class="enter">
					<input type="submit" class="buttom" value="提交" name="submit"
						style="cursor: pointer;" /> <input name="reset" type="reset"
						class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>

</body>
</html>

