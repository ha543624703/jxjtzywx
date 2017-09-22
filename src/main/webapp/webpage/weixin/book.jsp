<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>图书查看</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
  <meta name="format-detection" content="telephone=no" />
  <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=no" />
<link href="plug-in/weixin/chaxun/css/global.css" rel="stylesheet" type="text/css">
<link href="plug-in/weixin/chaxun/css/cj.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
</head>

<body>
<div class="nav">
    <div class="menu">
        <div class="top2"><h1>我的图书</h1></div>
        <div class="top3"></div>
    </div>
</div>
<div class="view">
	<div class="tabs1" style="overflow:hidden;height:160px" >
		<h1 style="text-align:center; padding:30px 0px; font-size:14px; color:#333;">待归还图书<span style="color:#a00;">${borrowNum }</span>本</h1>
		<h1 style="text-align:center; padding:30px 0px; font-size:14px; color:#333;">过期图书<span style="color:#a00;">${overTimeNum }</span>本</h1>
	</div>
</div>
</body>
</html>
