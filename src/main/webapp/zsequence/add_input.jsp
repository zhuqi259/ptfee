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
<title>新增fieldMap信息</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<script type="text/javascript" src="js/jquery.jstree.js"></script>
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

<script type="text/javascript">  
//判断浏览器是否支持 placeholder属性  
function isPlaceholder(){
    var input = document.createElement('input');
    return 'placeholder' in input;
}

if (!isPlaceholder()) {//不支持placeholder 用jquery来完成  
    $(document).ready(function() {
        if(!isPlaceholder()){
            $("input").not("input[type='password']").each(//把input绑定事件 排除password框 
                function(){
                    if($(this).val()=="" && $(this).attr("placeholder")!=""){
                        $(this).val($(this).attr("placeholder"));
                        $(this).focus(function(){
                            if($(this).val()==$(this).attr("placeholder")) $(this).val("");
                        });
                        $(this).blur(function(){
                            if($(this).val()=="") $(this).val($(this).attr("placeholder"));
                        });
                    }
            });
            //对password框的特殊处理1.创建一个text框 2获取焦点和失去焦点的时候切换  
            var pwdField    = $("input[type=password]");
            var pwdVal      = pwdField.attr('placeholder');
            pwdField.after('<input id="pwdPlaceholder" type="text" value='+pwdVal+' autocomplete="off" />'); 
            var pwdPlaceholder = $('#pwdPlaceholder');
            pwdPlaceholder.show();
            pwdField.hide();
              
            pwdPlaceholder.focus(function(){
                pwdPlaceholder.hide();
                pwdField.show();
                pwdField.focus();
            });

            pwdField.blur(function(){
            	if(pwdField.val() == '') {
                    pwdPlaceholder.show();
                    pwdField.hide();
                }
            });        
        }
    });
}

function checkForm(form) {
/*	if (form.name.value == "") {
		alert("请输入属性标识!");
		form.name.focus();
		return false;
	}
	var num = form.name.value;
	//alert(num)
	if(isNaN(num)){
		alert("属性标识必须是整数!");
		form.name.value = "";
		form.name.focus();
		return false;
	}else{
		var r = /^-[0-9]*[1-9][0-9]*$/;
		if(! r.test(num)){
			alert("属性标识必须是小于0的整数!");
			form.name.value = "";
			form.name.focus();
			return false;
		}
	}
	*/
	if (form.displayName.value == "") {
		alert("请输入属性名称!");
		form.name.focus();
		return false;
	}
	
}
</script>
</head>
<body>

	<div id="formwrapper">
		<h3>请设置属性信息</h3>
		<form name="add_fieldMap" action="system/fieldMap.action"
			method="post">
			<input type="hidden" name="method:add" value="">
			<fieldset>
				<legend>请设置属性信息 </legend>

				<div>
					<label for="displayName">属性名称</label> <input type="text"
						name="displayName" id="displayName" required="required" size="30" />
					<br />
				</div>

				<div class="enter">
					<input name="submit" type="submit" class="buttom" value="提交"
						onclick="return checkForm(add_fieldMap)" /> <input name="reset"
						type="reset" class="buttom" value="重置" />
				</div>
			</fieldset>
		</form>
	</div>

</body>
</html>

