<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title>学工待办</title>
<link type="text/css" rel="stylesheet" href="template/cms/default/css/weixin_cms.css" />
<link href="plug-in/weixin/waitthing/css/db.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
</head>

<body>
<div class="db">
    <div class="more"><span><a id="j-viewmore" href="javascript:;" >加载更多</a></span></div>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
    //返回顶部按钮逻辑
    $('#j-gotop').on("click", function () {
        window.scrollTo(0, 0); 
    });
    $("#j-viewmore").on("click",function(){
    	getWait();
	});
    $("#j-viewmore").click();
})
var jsdm = "${userId}";
var ym = 1;
var pagesize = 10;
var SHZTLX = 0;
function getWait()
{
	if ($("#j-viewmore").hasClass("disabled")) {
		return;
	}
	$("#j-viewmore").html("努力加载中…").addClass("disabled");
	$.ajax({
		url : "waitThingController.do?getWaitThing",
		type : "post",
		data : {"jsdm":jsdm, "ym":ym, "pagesize":pagesize, "SHZTLX":SHZTLX},
		dataType : "json",
		success : function(data) {
			if (data == "")
			{
				$("#j-viewmore").html("没有待办事项").addClass("disabled");
				return;
			}
        	var mapValue = eval("("+data+")");
        	var total = parseInt(mapValue.count);
        	var value = mapValue.rows;
			if (value.length == 0)
			{
				$("#j-viewmore").html("没有待办事项").addClass("disabled");
				return;
			}
        	for (var i=0; i<value.length; i++)
			{
        		var zt = value[i].ZT;
        		var sszt = "待审核";
        		if (zt == "2")
        		{
        			sszt = "已审核";
        		}
            	var _waitThing = '<div class="info">';
            	_waitThing += '<p>'+value[i].sqr+'提交的【'+value[i].djlx+'】'+sszt+'</p>';
            	_waitThing += '<div class="info_down">';
            	_waitThing += '<span class="infotime">'+value[i].ssrq+'</span>';
            	_waitThing += '<span class="infostate"><strong>'+sszt+'</strong></span>';
            	_waitThing += '</div>';
            	_waitThing += '</div>';
            	$(".more").before(_waitThing);
			}
	        if ((pagesize * ym) >= total){
	        	$("#j-viewmore").html("已显示全部").addClass("disabled");
	        	$("#j-viewmore").off('click');
			} else {
				ym++;
				$("#j-viewmore").html("查看更多").removeClass("disabled");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {}
	});
}
</script>