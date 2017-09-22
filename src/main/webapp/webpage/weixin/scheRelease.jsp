<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="format-detection" content="telephone=no">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>日程管理</title>
<link href="plug-in/weixin/scheduleMana/css/mui.min.css" rel="stylesheet" type="text/css">
<link href="plug-in/weixin/scheduleMana/css/mui.picker.min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="plug-in/weixin/scheduleMana/js/mui.min.js"></script>
<script type="text/javascript" src="plug-in/weixin/scheduleMana/js/mui.picker.min.js"></script>
<style>
body{ font-size:12pt; color:#555; font-family:"Microsoft YaHei";}
input{ -webkit-tap-highlight-color:rgba(255,0,0,0);} 
.main{ width:100%; height:auto; background:#fff; overflow:hidden; margin:15px 0px;}
.line{ height:auto; padding:0px 15px; border-bottom:1px solid #eee;}
.times{ float:left; width:90px; height:46px; line-height:46px;}
.inputTimes{ margin-left:90px; height:46px; text-align:right;}
.inputTimes .selTime{ width:100%; height:46px; border:0px; padding:11px 0px; line-height:24px; font-size:12pt; background:url(plug-in/weixin/scheduleMana/images/you.png) no-repeat right center; background-size:12px 20px;}
.inputTimes .txt{ width:100%; height:46px; border:0px; line-height:46px; font-size:12pt;}
.rcArea{ padding:10px 15px; background:#fff;}
.txtArea{ width:100%; height:auto; line-height:24px; border:0px; font-size:12pt;}
.btn{ margin:0px 15px; height:46px; background:#18a8f2; border-radius:5px;}
.btn:hover{ background:#0693dc;}
.btn .sub{ width:100%; height:46px; border:0px; background:none; font-size:14pt; color:#fff;}
</style>
</head>

<body>
<form id="form1" name="form1" action="scheduleManaController.do?createScheduleMana" method="post">
<div class="main">
    <div class="line">
        <div class="times">日期时间</div>
        <div class="inputTimes"><input type="text" name="workTime" id='setTime' data-options='{}' class="selTime" placeholder="请选择时间(必填)"  readonly="readonly" /></div>
    </div>
    <div class="rcArea">
        <textarea rows="6" maxlength="100" id="message" name="message" placeholder="请输入内容..." class="txtArea"></textarea>
    </div>
</div>
<div class="btn"><input type="button" onclick="submitForm();" class="sub" value="提交日程" /></div>
</form>
<script>
function submitForm()
{
	if ($("#setTime").val()=="")
	{
		alert("请选择日期");
		return false;
	}
	if ($("#message").val()=="")
	{
		alert("请输入内容");
		return false;
	}
	var form = document.getElementById("form1");
	form.method = "post";
	form.submit();
}
(function($) {
	$.init();
	var result = $('#setTime')[0];
	var btns = $('.selTime');
	btns.each(function(i, btn) {
		btn.addEventListener('tap', function() {
			var optionsJson = this.getAttribute('data-options') || '{}';
			var options = JSON.parse(optionsJson);
			var id = this.getAttribute('id');
			/*
			 * 首次显示时实例化组件
			 * 示例为了简洁，将 options 放在了按钮的 dom 上
			 * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
			 */
			var picker = new $.DtPicker(options);
			picker.show(function(rs) {
				/*
				 * rs.value 拼合后的 value
				 * rs.text 拼合后的 text
				 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
				 * rs.m 月，用法同年
				 * rs.d 日，用法同年
				 * rs.h 时，用法同年
				 * rs.i 分（minutes 的第二个字母），用法同年
				 */
				 document.getElementById("setTime").value=rs.text;
				/* 
				 * 返回 false 可以阻止选择框的关闭
				 * return false;
				 */
				/*
				 * 释放组件资源，释放后将将不能再操作组件
				 * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
				 * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
				 * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实例。
				 */
				picker.dispose();
			});
		}, false);
	});
})(mui);	
</script>
</body>
</html>