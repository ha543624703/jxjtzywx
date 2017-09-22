<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>成绩查询</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=no" />
<link href="plug-in/weixin/chaxun/css/global.css" rel="stylesheet" type="text/css">
<link href="plug-in/weixin/chaxun/css/cj.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript">
$(function(){
	var date = new Date;
	var year = date.getFullYear();//因为数据库缺少今年的表，所以这里减2,以后要改
	for (var i = year + 1; i >= year - 2; i--)
	{
		var xq = (i-1)+"-"+(i);
		$("select[name='select']").append('<option value="'+xq+'-2">'+xq+'第二学期</option>');
		$("select[name='select']").append('<option value="'+xq+'-1">'+xq+'第一学期</option>');
	}
	selectXQ();
})
function selectXQ(){
	var xnxq = $("select[name='select']").val();
	var xnxqtext = $("select[name='select'] > option:selected").text();
	$(".title > h1").text(xnxqtext);
	$("#cjHead").siblings("tr").remove();
	$("#cjHead").after("<tr><td colspan='12'>正在查询...</td></tr>");
	$.ajax({
		url : "cjController.do?studentCJ",
		type : "post",
		data : {"xnxq":xnxq},
		dataType : "json",
		success : function(data) {
			$("#cjHead").siblings("tr").remove();
			var resultMap = eval("("+data+")");
			if (resultMap.status == "error")
			{
				$("#cjHead").after("<tr><td colspan='12'>未查询到成绩信息</td></tr>");
			}
			else if (resultMap.status == "success")
			{
				var cjList = resultMap.data;
				for (var i=0; i<cjList.length; i++)
				{
					var cjItem = cjList[i];
					var _cj = '<tr>';
					_cj+='<td>'+(i+1)+'</td>';
					_cj+='<td>'+cjItem.XNXQH+'</td>';
					_cj+='<td>'+cjItem.DWMC+'</td>';
					_cj+='<td>'+cjItem.ZYMC+'</td>';
					_cj+='<td>'+cjItem.BJMC+'</td>';
					_cj+='<td>'+cjItem.XH+'</td>';
					_cj+='<td>'+cjItem.XM+'</td>';
					_cj+='<td>'+cjItem.KCJC+'</td>';
					_cj+='<td>'+cjItem.KCXZMC+'</td>';
					_cj+='<td>'+cjItem.ZCJ+'</td>';
					_cj+='</tr>';
					$("table").append(_cj);
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {}
	});
}
</script>
</head>

<body>
<div class="nav">
    <div class="menu">
        <div class="top2"><h1>成绩查询</h1></div>
        <div class="top3"></div>
    </div>
</div>
<div class="selBox">
    <div class="sel">
        <select name="select" class="xz" onchange="selectXQ();">

        </select>
    </div>
</div>
<div class="title"><h1>2015-2016第二学期</h1></div>
<div class="view">
    <div style="overflow-x:scroll;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabs">
	    <tr bgcolor="#b6d6ff" id="cjHead">
	        <th>编号</th>
	        <th>学期</th>
	        <th>院系</th>
	        <th>专业</th>
	        <th>班级</th>
	        <th>学号</th>
	        <th>学生姓名</th>
	        <th>课程学科</th>
	        <th>是否必修</th>
	        <th>成绩</th>
	    </tr>
    </table>
	</div>
</div>
</body>
</html>
