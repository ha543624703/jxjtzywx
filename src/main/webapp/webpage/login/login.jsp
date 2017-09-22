<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<link rel="shortcut icon" href="resources/fc/images/icon/favicon.ico">
<title>云联微信管理平台</title>
<link href="plug-in/login/css/login.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="plug-in/login/js/jquery.min.js"></script>
<script type="text/javascript" src="plug-in/login/js/wxlogin.js"></script>
</head>
<div class="global">
    <div class="logo"><img src="plug-in/login/images/logo.png" /></div>
    <div class="main">
	    <form name="formLogin" id="formLogin" action="loginController.do?login" check="loginController.do?checkuser" method="post">
        <input name="userKey" type="hidden" id="userKey" value="D1B5CC2FE46C4CC983C073BCA897935608D926CD32992B5900" />
        <div class="user">
            <div class="login">
                <div class="titles"></div>
                <div class="box"> 
                    <h1>登录</h1>
                    <div class="line">
                        <div class="inp1"><input type="text" class="txt" placeholder="请输入用户名" name="userName" id="userName" title="用户名" iscookie="true" value="" /></div>
                        <div class="inp2"><input type="password" class="txt" placeholder="请输入密码" name="password" id="password" title="密码" value="" /></div>
                        <div class="inp3">
                            <div class="num">
                                <input type="text" class="rand" placeholder="请输入验证码" name="randCode" id="randCode" />
                            </div>
                            <div class="code"><img id="randCodeImage" src="randCodeImage" /></div>
                        </div>
                    </div>
                    <div class="btn">
                        <ul>
                           <span> <a style="cursor: pointer;" id="but_login">登录</a></span>
                           <span> <a style="cursor: pointer;" id="forgetpass">重置</a></span>
                        </ul>
                    </div> 
                    <div class="message"><span></span></div>
                </div>
            </div>
        </div>
		</form>
    </div>
    <div class="footer">
        <p>地址：江西省南昌市青山湖区高新大道589号南大科技园1号楼6楼</p>
        <p>版权信息：江西云联高科有限公司 Copyright©2017  赣ICP备10201316号</p>
    </div>
</div>
<!-- Link JScript-->
    <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
    <script type="text/javascript" src="plug-in/login/js/login.js"></script>
</body>
</html>