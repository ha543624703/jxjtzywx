<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>校情分析</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=no" />
<link href="plug-in/weixin/chaxun/css/global.css" rel="stylesheet" type="text/css">
<link href="plug-in/weixin/chaxun/css/cj.css" rel="stylesheet" type="text/css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
</head>

<body>
<c:if test="${not empty data}">
<div class="nav">
    <div class="menu">
        <div class="top2"><h1>在校学生情况</h1></div>
        <div class="top3"></div>
    </div>
</div>
<div class="view">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabs">
	    <tr bgcolor="#b6d6ff" id="cjHead">
	        <th>编号</th>
	        <th>院系</th>
	        <th>年份</th>
	        <th>总人数</th>
	        <th>男生人数</th>
	        <th>女生人数</th>
	        <th>性别保密人数</th>
	    </tr>
  	    <c:forEach var="item" items="${data}" varStatus="status" begin="0"> 
	    	<tr>
	    		<td>${status.index+1}</td>
	    		<td>${item.DWJC}</td>
	    		<td>${item.SZNJ}</td>
	    		<td>${item.totalNum}</td>
	    		<td>${item.maleNum}</td>
	    		<td>${item.femaleNum}</td>
	    		<td>${item.secrecyNum}</td>
			</tr>
		</c:forEach>
    </table>
</div>
</c:if>
<c:if test="${empty data}">
<div style="font-size:14pt; padding:30px 0px; color:#f00; text-align: center;">对不起，您没有权限查看！</div>
</c:if>
</body>
</html>