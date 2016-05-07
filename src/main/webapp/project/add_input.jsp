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
	function accMul(arg1, arg2) {
		var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
		try {
			m += s1.split(".")[1].length
		} catch (e) {
		}
		try {
			m += s2.split(".")[1].length
		} catch (e) {
		}
		return Number(s1.replace(".", "")) * Number(s2.replace(".", ""))
				/ Math.pow(10, m)
	}
	function fun() {
		var area = document.getElementById("area").value;
		var fee = document.getElementById("fee").value;
		var sfee = document.getElementById("sfee");
		sfee.value = accMul(area,fee);
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
		<h3>录入工程信息界面</h3>
		<form id="form1" action="person/project.action" method="post" enctype="multipart/form-data" onkeypress="javascript:return NoSubmit(event);" onsubmit="submit.disabled=1">
			<input type="hidden" name="method:add" value="">
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
								value="${developer }" required="required" size=40 />
							</td>
						</tr>
						<tr>
							<td>受理建筑面积：</td>
							<td><input name="area" id="area" value="${area }" required="required" size=40 />平方米</td>
						</tr>
						<tr>
							<td>座落地点：</td>
							<td><input name="location" id="location"
								value="${location }" required="required" size=40 />
							</td>
						</tr>
						<tr>
							<td>收费标准：</td>
							<td><input type="text" name="fee" id="fee" value="${fee }" required="required"
								size=40 />元/平方米</td>
						</tr>
						<tr>
							<td>收取款项：</td>
							<td><input type="text" name="sfee" id="sfee" required="required"
								value="${sfee }" size=40 />元</td>
						</tr>
						<tr>
							<td colspan="2" align="center">上传扫描件列表:</td>
						</tr>
						<s:iterator value="myFilesByGCSL" status="st" var="files">
							<tr>
								<td><s:property value="#st.index+1" />.<s:property
										value="#files.name" />
									<s:property value="#files.contentType" /></td>
								<td><input type="file" name="uploadFiles"
									id='file<s:property value="#st.index+1" />'
									onchange="filechange('file<s:property value="#st.index+1" />', 'flag<s:property value="#st.index+1" />')" />
									<input type="hidden"
									id='flag<s:property value="#st.index+1" />' name="fileUsed"
									value="0" /></td>
							</tr>

						</s:iterator>

						<!--  	<tr>
							<td>1、新装用电业务申请书:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>2、用电设备清单:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>3、单位介绍信:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>4、经办人身份证复印件:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>5、营业执照正副本复印件:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>6、组织机构代码证正副本复印件:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>7、政府主管部门立项或批复文件:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>8、企业法人身份证复印件:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>9、受理税务登记证复印件:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>10、一般纳税人资格证书复印件:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>11、总平图复印件，建筑总平面图:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>12、建设工程规划许可证复印件:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>13、建设用地规划许可证复印件:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>14、建筑工程施工许可证:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						<tr>
							<td>15、小区内线照明总平图:</td>
							<td><input type="file" name="files" /></td>
						</tr>
						-->
					</TABLE>
				</div>
				<div>
					<label for="workflows"  style="width: 100px">选择工作流</label> <select
						name="workflowId">
						<s:iterator value="workflows">
							<option value="<s:property value="id"/>">
								<s:property value="name" />
							</option>
						</s:iterator>
					</select> <br />
				</div>
				<div class="enter">
					<input name="submit" type="submit" class="buttom" value="提交"
						style="cursor: pointer;" /> <input name="reset" type="reset"
						class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>

</body>
</html>

