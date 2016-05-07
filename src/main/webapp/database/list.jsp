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
<title>七库文件管理</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
<script type="text/javascript">

	function fun(id){
		window.location.href="<%=basePath%>system/database!addInput.action?id="+id;
	}

	function del(fid){		
		if (!confirm("请确认是否要删除该文件文件！")) {
			return;
		}		
		var id = $("#id").val();
		window.location.href="<%=basePath%>system/database!del.action?fid="+fid+"&id="+id;
	}
	
	
	function filechange(file, hidId) {
		var filesss = document.getElementById(file).value;
	//	alert(filesss);
		if (filesss.length>0 ) {
			document.getElementById(hidId).value = "1";
		}else{
			document.getElementById(hidId).value = "0";
		}
	}
	
	function downfile(id){
		window.location.href="<%=basePath%>fileDownFull?type=2&id=" + id;
	}
	
	function look(id){
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
	width: 100%;
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
		<h3>七库文件管理</h3>
		<form action="system/database.action" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="method:update" value=""> <input
				type="hidden" id="id" name="id" value="${id }" />
			<fieldset>
				<legend>${name } </legend>
				<div>
					【注】请在工作流->流程节点文件管理下首先创建文件，然后在此处上传其在七库中的模板文件，同时也可以增加并上传相关文件(而没有在流程中出现)
				</div>
				<div>
					<table cellSpacing=0 cellPadding=0 width="93%" align=center
						border=1>
						<s:iterator value="allZfiles" status="st" var="fs">
							<tr>
								<td><s:property value="#st.index+1" /> . <input
									type="text" name="zname" value="${name }" required="required"
									size=35 />${contentType }</td>
								<s:if test="IsLost(#fs.path)">
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
										onclick="look(${id});return false;">查看</a>| <a
										href="javascript:void(0);"
										onclick="downfile(${id});return false;">下载</a>
									</td>
									<td><font color="green">重新上传</font><input type="file"
										name="uploadFiles" id='file<s:property value="#st.index+1" />'
										onchange="filechange('file<s:property value="#st.index+1" />', 'flag<s:property value="#st.index+1" />')" />
										<input type="hidden"
										id='flag<s:property value="#st.index+1" />' name="fileUsed"
										value="0" />
									</td>

								</s:else>
								<s:if test="#fs.activity==null">
									<td><a href="javascript:void(0);"
										onclick="del(${id});return false;">删除</a></td>
								</s:if>
							</tr>

						</s:iterator>
					</table>
				</div>
				<s:if test="allZfiles.size!=0">
					<div class="enter">
						<input name="submit" type="submit" class="buttom" value="更新" /> <input
							name="reset" type="reset" class="buttom" value="重置" />
					</div>
				</s:if>

				<div class="enter">
					<br>
					<hr>
					<input type="button" value="新增文件" onclick="fun(${id })"
						style="color: red; font-size: 20px;height: 50px" />
				</div>

				<DIV align=center>
					<FONT class=p6> <A
						href="<%=basePath%>system/database.action">返回上一页</A> </FONT>
				</DIV>
			</fieldset>
		</form>

	</div>
</body>
</html>

