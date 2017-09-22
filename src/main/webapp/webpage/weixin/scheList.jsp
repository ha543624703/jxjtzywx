<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="format-detection" content="telephone=no">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>日程管理</title>
<style>
*{ padding:0px; margin:0px;}
body{ font-family:"Microsoft YaHei"; background:#efeff4;}
.nav{ width:100%; height:48px; background:#165aa1; text-align:center;}
.menu{ padding:10px 15px;}
.top1{ float:left; width:12px; height:28px;}
.top1 a{ display:block; width:12px; height:28px; background:url(plug-in/weixin/scheduleMana/images/return.png) no-repeat 0px center; background-size:12px 20px;}
.top2{ width:auto; height:28px; margin:0px 54px; line-height:28px;}
.top2 h1{ font-size:14pt; color:#fff;}
.top3{ float:right; width:54px; height:28px; text-align:center;}
.top3 a,.top3 a:hover{ display:block; width:52px; height:26px; line-height:26px; color:#fff; border:1px solid #fff; border-radius:26px; text-decoration:none;}
.rcManage{ margin:0px 15px; height:auto; overflow:hidden;}
.rcManage a,.rcManage a:hover{ display:block; color:#666; text-decoration:none;}
.rcManage li{ margin-top:15px;}
.rcBox{ background:#fff; border-top:3px solid #449afb;}
.rcTime{ padding:10px; font-size:13pt; line-height:24px; border-bottom:1px solid #eee;}
.rcTime strong{ color:#449afb;}
.rcTime i{ color:#a9a9a9; font-style:normal;}
.rcContent{ padding:10px; height:auto; line-height:24px; font-size:12pt; color:#666;}
.rcMore{ width:100%; height:40px; text-align:center; background:#fff; margin:15px 0px;}
.rcMore span{ display:block; line-height:40px; font-size:12pt;}
.rcMore a,.more a:hover{ display:block; color:#555;}
</style>
</head>

<body>
<div class="nav">
    <div class="menu">
        <div class="top3"><a href="http://wx.jxhlxy.com.cn/jeewx/weixinLinksucaiController.do?link&id=4028e4b05c1a9443015c1aac3b640007">新增</a></div>
        <div class="top2"><h1>日程管理</h1></div>
    </div>
</div>
<div class="rcManage">
    <ul>
    	<c:forEach var="item" items="${list}">
        <li>
            <a href="#">
            <div class="rcBox">
                <div class="rcTime"><strong>日程：<i>${item.workTimeStr}</i></strong></div>   
                <div class="rcContent">
                    <p>${item.message}</p>
                </div>               
            </div>
            </a>
        </li>
        </c:forEach>
    </ul>
</div>
</body>
</html>
