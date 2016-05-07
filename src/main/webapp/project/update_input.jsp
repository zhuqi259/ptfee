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
	
	function downfile(id){
		window.location.href="<%=basePath%>fileDownFull?type=1&id=" + id;
	}
	
	function look(id){
		window.location.href="<%=basePath%>fileLook?type=1&id=" + id;
	}
	
	$(document).ready(function() {
		$("#area").blur(function() {
			aa = $("#area").val();
			bb = $("#fee").val();
			htmlobj = $.ajax({
				url : "<%=basePath%>person/project!getMySfee.action?aa="+aa+"&bb="+bb,
				async : false
			});
			$("#sfee").attr("value", htmlobj.responseText);
		});
		$("#fee").blur(function() {
			aa = $("#area").val();
			bb = $("#fee").val();
			htmlobj = $.ajax({
				url : "<%=basePath%>person/project!getMySfee.action?aa="+aa+"&bb="+bb,
				async : false
			});
			$("#sfee").attr("value", htmlobj.responseText);
		});
	});
	
	function filechange(file, hidId) {
		var filesss = document.getElementById(file).value;
	//	alert(filesss);
		if (filesss.length>0 ) {
			document.getElementById(hidId).value = "1";
		}else{
			document.getElementById(hidId).value = "0";
		}
	}
	
	function fun(id){
		window.location.href="<%=basePath%>system/database!list2.action?id="+id;
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
	width:110px;
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
				<s:if test="IsRelatedGCSL(id)"> style="color: #F00; background-color: #FFC " </s:if> />
		</s:iterator>
	</div>

	<div id="formwrapper">
		<h3>修改工程信息界面</h3>
		<form action="person/projectAUpdate.action" method="post" onkeypress="javascript:return NoSubmit(event);" onsubmit="submit.disabled=1"
			enctype="multipart/form-data">
			<input type="hidden" name="method:update" value=""> <input
				type="hidden" name="id" value="${id }"> <input type="hidden"
				name="activityId" value="${activityId }">
			<fieldset>
				<legend>工程信息</legend>
				<div>

					<TABLE cellSpacing=0 cellPadding=0 width="93%" align=center
						border=1>
						<tr>
							<td width="40%">工程名称：</td>
							<td><input name="proname" id="proname" value="${proname }"
								required="required" size=40 /></td>
						</tr>
						<tr>
							<td>开发单位：</td>
							<td><input name="developer" id="developer"
								value="${developer }" required="required"  size=40 />
							</td>
						</tr>
						<tr>
							<td>受理建筑面积：</td>
							<td><input name="area" id="area" value="${area }" required="required"  size=40 />平方米</td>
						</tr>
						<tr>
							<td>座落地点：</td>
							<td><input name="location" id="location"
								value="${location }" required="required"  size=40 />
							</td>
						</tr>
						<tr>
							<td>收费标准：</td>
							<td><input name="fee" id="fee" value="${fee }" required="required"  size=40 />元/平方米</td>
						</tr>
						<tr>
							<td>收取款项：</td>
							<td><input name="sfee" id="sfee" value="${sfee }" required="required"  size=40 />元</td>
						</tr>
					</TABLE>
					<TABLE cellSpacing=0 cellPadding=0 width="93%" align=center
						border=1>
						<tr>
							<td colspan="3" align="center">上传扫描件列表:</td>
						</tr>
						<s:iterator value="myFilesByGCSL" status="st" var="fs">
							<tr>
								<td><s:property value="#st.index+1" />.<s:property
										value="#fs.name" /> <s:property value="#fs.contentType" /></td>
								<s:if test="!IsExisted(#projectId,id)">
									<td><font color="red">文件暂缺 </font>
									</td>
									<td><input type="file" name="uploadFiles"
										id='file<s:property value="#st.index+1" />'
										onchange="filechange('file<s:property value="#st.index+1" />', 'flag<s:property value="#st.index+1" />')" />
										<input type="hidden"
										id='flag<s:property value="#st.index+1" />' name="fileUsed"
										value="0" /></td>
								</s:if>
								<s:else>
									<td width="15%"><a href="javascript:void(0);"
										onclick='look(<s:property value="getPFileByZFile(#projectId,id)"/>) ;return false;'>查看</a>
										|<a href="javascript:void(0);"
										onclick='downfile(<s:property value="getPFileByZFile(#projectId,id)"/>) ;return false;'>下载</a>
									</td>
									<td><font color="green">重新上传</font><input type="file"
										name="uploadFiles" id='file<s:property value="#st.index+1" />'
										onchange="filechange('file<s:property value="#st.index+1" />', 'flag<s:property value="#st.index+1" />')" />
										<input type="hidden"
										id='flag<s:property value="#st.index+1" />' name="fileUsed"
										value="0" /></td>

								</s:else>
							</tr>

						</s:iterator>
					</TABLE>
				</div>

				<div class="enter">
					<input name="submit" type="submit" class="buttom" value="提交" /> <input
						name="reset" type="reset" class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>

</body>
</html>

