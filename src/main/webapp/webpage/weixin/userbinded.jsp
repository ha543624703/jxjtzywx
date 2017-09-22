<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
   <title>账号已绑定</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
  <meta name="format-detection" content="telephone=no" />
  <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=no" />
  <link href="plug-in/weixin/userbind/css/css.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
 </head>
 <body>
	<div class="message1"><span>当前帐号已经绑定无需再绑定！</span></div>
	<div class="btn">
	    <input type="button" value="退出账号" class="sub"  id="btnsubmit" onclick="userbinddelete('${openId}')" />
	</div>
</body>
<script type="text/javascript">
function userbinddelete(openId){
	$.ajax({
		url : "wxUserBindController.do?deleteUserBind",
		type : "post",
		data : {"openId":openId},
		dataType : "json",
		success : function(data) {
			if (data.success == true)
			{
				alert("退出账号成功");
				$("#btnsubmit").attr("disabled", true);
				window.location.reload();
			}
			else
			{
				alert("退出账号失败");
				$("#btnsubmit").attr("disabled", false);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {}
	});
}
</script>
</html>