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
	
	function look(id){
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
		<h2 style="color: purple">工程名称:${proname }</h2>
		<h3>${login.position } -&gt; ${currentStatus }</h3>
		<form action="person/project.action" method="post"
			onkeypress="javascript:return NoSubmit(event);"
			onsubmit="submit.disabled=1" enctype="multipart/form-data">
			<input type="hidden" name="method:other" value="">
			<s:token></s:token>
			<input type="hidden" name="id" value="${id }"> <input
				type="hidden" name="currentStatus" value="${currentStatus }">
			<input type="hidden" name="tid" value="${tid }">
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
						<TABLE cellSpacing=0 cellPadding=0 width="80%" align=center
							border=1>
							<tr>
								<td colspan="2" align="center">模板下载</td>
							</tr>

							<s:iterator value="myFilesByStatus" status="st" var="fs">
								<tr>
									<td width="50%"><s:property value="#st.index+1" />.【模板】<s:property
											value="#fs.name" />
									</td>
									<s:if test="IsLost(#fs.path)">
										<td><font color="red">文件暂缺 </font></td>
									</s:if>
									<s:else>
										<td><a href="javascript:void(0);"
											onclick="look(${id});return false;">查看</a>| <a
											href="javascript:void(0);"
											onclick="downfile(${id});return false;">下载</a></td>
									</s:else>
								</tr>
							</s:iterator>
						</TABLE>
					</div>
					<div align="center">
						<TABLE cellSpacing=0 cellPadding=0 width="80%" align=center
							border=1>
							<tr>
								<td colspan="2" align="center">上传文件</td>
							</tr>
							<s:iterator value="myFilesByStatus" status="st" var="fs">
								<tr>
									<td><s:property value="#st.index+1" />.<s:property
											value="#fs.name" />
									</td>
									<td><input type="file" name="uploadFiles"
										required="required"
										id='file<s:property value="#st.index+1" />'
										onchange="filechange('file<s:property value="#st.index+1" />', 'flag<s:property value="#st.index+1" />')" />
										<input type="hidden"
										id='flag<s:property value="#st.index+1" />' name="fileUsed"
										value="0" />
									</td>
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
					<s:iterator value="myZPaymentsByStatus" var="zpayment">
						<div align="center">
							收取费用名称:<input type="text" value="${name }" readonly="readonly" />
							&nbsp;&nbsp;&nbsp;金额:<input type="text" name="money"
								required="required" />元&nbsp;&nbsp;&nbsp;付款单位:<input
								type="text" name="payer" required="required" />
							&nbsp;&nbsp;&nbsp;付款时间:<input type="text" name="payTime"
								required="required" />
						</div>
					</s:iterator>
				</s:if>
				<s:if test="IsZfundNotNull()">
					<div align="center">
						<font size="5px" color="red">附加信息</font>
					</div>
					<s:iterator value="myZFundsByStatus" var="zfund">
						<div align="center">
							<s:if test="#zfund.fn_use==1">
							${zfund.fund_name }:<input type="text" name="fmoney">元&nbsp;&nbsp;&nbsp;
							</s:if>
							<s:else>
								<input type="hidden" name="fmoney">
							</s:else>
							<s:if test="#zfund.ft_use==1">
							${zfund.fund_time }:<input type="text" name="ftime">&nbsp;&nbsp;&nbsp;
							</s:if>
							<s:else>
								<input type="hidden" name="ftime">
							</s:else>
							<s:if test="#zfund.fo_use==1">
							${zfund.fund_oper }:<input type="text" name="foper">
							</s:if>
							<s:else>
								<input type="hidden" name="foper">
							</s:else>
						</div>
					</s:iterator>
				</s:if>
				<div class="enter">
					<input type="submit" class="buttom" name="submit"
						style="cursor: pointer;" value="提交" /> <input name="reset"
						type="reset" class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>

</body>
</html>

