<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<style type="text/css">
<!--
#apDiv1 {
	border: 1px solid red;
	width: 100px;
	height: 77px;
	text-align: center;
	padding: 6px;
	background-color: #FCC;
	position: absolute;
	z-index: 99;
	display: none; /*使div初始化时隐藏*/
}

input {
	border: 1px solid; /*注意这里的border宽度，后面要根据这个值额外增加div的left值*/
}
-->
</style>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
<!--
    //dom加载完成时执行
    $(function(){
        //input获取焦点时在其旁边显示div
        $('input:text').focus().click(function(){
            var input = $(this);            
            var offset = input.offset();
            //先后设置div的内容、位置，最后显示出来（渐进效果）
            $('#apDiv1').text(input.attr('name'))
                .css('left',offset.left + input.width() + 2 + 'px')
                .css('top',offset.top + 'px')
                .fadeIn();
        });
        //input失去焦点时隐藏div
        $('input:text').blur(function(){
            $('#apDiv1').css('display','none');
        });
    });
//-->
</script>
</head>

<body>
	<div id="apDiv1"></div>
	<table width="200" border="0" cellspacing="1" cellpadding="3">
		<tr>
			<td><input type="text" name="textfield" id="textfield" />
			</td>
			<td><input type="text" name="textfield2" id="textfield2" />
			</td>
		</tr>
	</table>
</body>
</html>