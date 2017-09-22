<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>用户绑定</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
  <meta name="format-detection" content="telephone=no" />
  <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=no" />
  <t:base type="jquery"></t:base>
  <link href="plug-in/weixin/userbind/css/css.css" rel="stylesheet" type="text/css">
  <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
  <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
  <script type="text/javascript" charset="utf-8" src="plug-in/ueditor/lang/zh-cn/zh-cn.js"></script>
 </head>
 <body>
<form method="post" id="form1">
<div class="userMain">
<div class="bd">
    <div class="userName">
        <div class="inp"><input type="text" placeholder="请输入用户名或工号" class="txt"  id="txtuserName" name="txtuserName" /></div>    
    </div>
</div>
<div class="bd">
    <div class="userPwd">
        <div class="inp"><input type="password" placeholder="请输入密码" class="txt"  id="txtuserPwd" name="txtuserPwd" /></div>   
        <input type="hidden" value="<%=request.getParameter("appid") %>" id="appid" name="appid"> 
        <input type="hidden" value="<%=request.getParameter("openid") %>" id="openid" name="openid">
    </div>
</div>
</div>
<div class="btn">
    <input type="button" value="用户绑定" class="sub"  id="btnsubmit" onclick="btnSubimt();" />
</div>
<div class="message"><span>提示：用户名为学号或者工号!</span></div>
</form>
 <script type="text/javascript">
function btnSubimt()
{
    var AjaxURL= "wxUserBindController.do?dosave";
      $.ajax({
          type: "POST",
          dataType: "json",
          url: AjaxURL,
          data: $('#form1').serialize(),
          success: function (data) {
              if(data.msg=="绑定成功")
              {
                 alert(data.msg);
                 $("#btnsubmit").attr("disabled", true);
                 var openid = $("#openid").val();
                 location.href = "wxUserBindController.do?userbindOK&accountid=402881e8461795c201461795c2e90000&openid="+openid;
             }
             else
             {
                 alert(data.msg);
                 $("#btnsubmit").attr("disabled", false);
             }

          },
          error: function(data) {
              alert("error:"+data.responseText);
           }

      });
  }


  
  </script>
</body>