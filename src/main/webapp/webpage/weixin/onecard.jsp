<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
<title>一卡通余额</title>
<style>
*{ padding:0px; margin:0px;}
body{ font-family:"微软雅黑"; font-size:16px; background:#efeff4; -webkit-text-size-adjust:none; -moz-text-size-adjust:none; -ms-text-size-adjust:none; text-size-adjust: none; min-width:300px;}
input{ font-family:"微软雅黑"; font-size:16px; color:#555; outline:none;}
img{ border:0px;}
li{ list-style:none;}
a,a:hover{ color:#607fa6; text-decoration:none;}
.global{ width:100%; height:auto;}
.clear{ clear:both;}

.ye{ width:100%; height:200px; text-align:center; position:absolute; top:50%; margin-top:-180px;}
.ye img{ width:100px; height:100px;}
.user{ padding-top:20px; font-size:14pt; line-height:20px;}
.user span{ color:#333;}
.number{ padding:10px 0px; font-size:36pt; line-height:60px; font-family:"微软雅黑";}
.number span{ color:#333; font-weight:bold;}
</style>
</head>

<body>
<div class="ye">
    <center><img src="plug-in/weixin/onecard/images/yue.png" /></center>
    <div class="user"><span>我的余额</span></div>
    <div class="number"><span>${ye}</span></div>
</div>
</body>
</html>
