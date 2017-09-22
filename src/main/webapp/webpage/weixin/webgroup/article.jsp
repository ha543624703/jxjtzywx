<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>${menuName}</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="format-detection" content="telephone=no">
        <meta name="viewport" content="wilih=device-wilih initial-scale=1.0 maximum-scale=1.0 user-scalable=no">
  		<link type="text/css" rel="stylesheet" href="template/cms/default/css/weixin_cms.css" />
  		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
	    <script type='text/javascript' src='plug-in/jquery/jquery-1.8.3.js'></script>
	    <style type="text/css">
	    body {
			padding: 0;
			font-family: 'Microsoft YaHei';
			background: #fff;
		}
	    .title{
		    display: block;
			font-size:16pt;
			padding:15px 20px;
			font-weight: bold;
			text-align:center;
			line-height:30px;
	    }
	    .activity-info {
	        margin:0px 20px;
	        background:#f4f4f4;
			border: 1px solid #e2e2e2;
			text-align:center;
	    }
	    .activity-meta {
			display: inline-block;
			line-height: 16px;
			vertical-align: middle;
			text-align:center;
			margin-left: 8px;
			padding-top: 2px;
			padding-bottom: 2px;
			color: #8c8c8c;
			font-size: 11px;
		}
		.content{
			margin: 0 20px;
			padding: 5px 0px;
		}
		.content img{ width:100% !important;height:auto !important;}
		*{ padding:0px; margin:0px;}
		body{ font-family:"Microsoft YaHei";}
		input,textarea{ font-family:"Microsoft YaHei"; outline:none; -webkit-tap-highlight-color:rgba(255,0,0,0);}
		.ly_box{ width:100%; height:auto; margin:10px 0px; overflow:hidden;}
		.ly_titles{ padding:10px 0px; margin:0px 10px;}
		.ly_titles h1{ font-size:12pt; line-height:18px; color:#555;}
		.ly_contents{ padding:10px; margin:0px 10px; background:#fff; border:1px solid #ddd; border-radius:3px;}
		.ly_txtArea{ width:100%; height:auto; line-height:24px; border:0px; font-size:11pt;}
		.ly_btn1{ float:right; margin:10px 10px; width:auto; height:34px; display:inline-block; background:#f8f8f8; border:1px solid #ddd; border-radius:3px; text-align:center;}
		.ly_btns01{ width:auto; padding:0px 12px; height:34px; background:none; border:0px; color:#555; font-size:11pt; cursor:pointer;}
		.ly_login{ width:100%; height:auto; margin:10px 0px; overflow:hidden;}
		.ly_btn2{ float:right; margin:10px 10px; width:auto; height:34px; display:inline-block; background:#18a8f2; border:1px solid #18a8f2; border-radius:3px; text-align:center;}
		.ly_btns02{ width:auto; padding:0px 12px; height:34px; background:none; border:0px; color:#fff; font-size:11pt; cursor:pointer;}
		.ly_classic{ padding:15px; height:auto; overflow:hidden; background:#f3f3f3;}
		.ly_classic h3{ font-size:12pt; padding:10px 0px; color:#999; text-align:center; font-weight:normal;}
		.lyb{ height:auto; padding:10px 0px; overflow:hidden; border-top:1px dashed #ddd; line-height:24px; font-size:11pt;}
		.ly_name{ color:#0052ff;}
		.ly_cont{ color:#555;}
		.ly_time{ float:right; color:#999;}
	    </style>
	</head>
	<body>
		<header class="w-header" mon="type=header">
			<a class="arrow-wrap" href="javascript:history.back()" mon="content=back">
			<span class="arrow-left"></span>
			</a>
			<div class="text">${menuName}</div>
		</header>
		<div class="detail-area bulk_order_details">
			<div class="title">${map.title }</div>
			<div class="activity-info">
				<span id="post-date" class="activity-meta no-extra">日期：${map.updateTimeStr} 作者：${map.author}
				<br/>发布人：${map.username}  浏览量：${map.views}</span>
			</div>
			<div class="content">${map.content }</div>
		</div>
<%-- 	<c:if test="${not empty isLanded}">
		<form id="articleMessage" method="post">
			<div class="ly_box">
			    <div class="ly_titles"><h1>留言板：</h1></div>
			    <div class="ly_contents">
			        <textarea rows="2" placeholder="请输入留言内容..." maxlength="200" id="ly_txtArea" class="ly_txtArea"></textarea>
			    </div>
			    <div class="ly_btn1"><input type="button" onclick="articleMessage();" class="ly_btns01" value="点我留言" /></div>
			</div>
		</form>
	</c:if>
	<c:if test="${empty isLanded}">
		<div class="ly_login">
	    	<div class="ly_btn2"><input type="button" class="ly_btns02" onclick="window.location='weixinLinksucaiController.do?link&id=4028e4b05c867724015c8a643f1c0002'" value="我要绑定账号留言" /></div>
		</div>
	</c:if>
		<div class="ly_classic">
		    <h3 id="messageContent">留言内容</h3>
		   <c:forEach var="item" items="${articleMessageList}"> 		    
		    <div class="lyb">
		      <div class="ly_name">${item.username}</div>
		      <div class="ly_cont">${item.message}</div>
		      <div class="ly_time">${item.create_time}</div>
		    </div>
		   </c:forEach>
		</div> --%>
	</body>
</html>
<script type="text/javascript">
var countTime = 60;
function remainTime(){
	$(".ly_btns01").attr("disabled", true);
	$(".ly_btns01").val("点我留言("+countTime+")");
    if(countTime<=0)
    {
        $(".ly_btns01").attr("disabled", false);
    	$(".ly_btns01").val("点我留言");
        return;
    }
	countTime--;
    setTimeout("remainTime()",1000);
}
function articleMessage()
{
	var articleid = "${map.id}";
	var message = $("#ly_txtArea").val();
	if (message == "")
	{
		return;
	}
	countTime = 60;
	remainTime();
	$.ajax({
		url : "cmsController.do?createArticleMessage",
		type : "post",
		data : {"articleid":articleid, "message":message},
		dataType : "json",
		success : function(data) {
			var resultMap = eval("("+data+")");
			if (resultMap.status == "fail")
			{
				alert("留言失败！");
			}
			else if (resultMap.status == "success")
			{
				var _articleMess = '<div class="lyb">';
				_articleMess += '<div class="ly_name">'+resultMap.username+'</div>';
				_articleMess += '<div class="ly_cont">'+message+'</div>';
				_articleMess += '<div class="ly_time">'+resultMap.createTime+'</div>';
				_articleMess += '</div>';
				$("#messageContent").after(_articleMess);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {}
	});
}
</script>