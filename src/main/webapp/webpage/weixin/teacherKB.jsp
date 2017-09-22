<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>课表查询</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=no" />
<link href="plug-in/weixin/chaxun/css/global.css" rel="stylesheet" type="text/css">
<link href="plug-in/weixin/chaxun/css/kb.css" rel="stylesheet" type="text/css">
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
	var xnxqh = $("select[name='select']").val();
	var xnxqhtext = $("select[name='select'] > option:selected").text();
	$(".title > h1").text(xnxqhtext);
	$("#kebiaoHead").siblings("tr").remove();
	$("#kebiaoHead").after("<tr><td colspan='12'>正在查询...</td></tr>");
	$.ajax({
		url : "kebiaoController.do?teacherKB",
		type : "post",
		data : {"xnxqh":xnxqh},
		dataType : "json",
		success : function(data) {
			$("#kebiaoHead").siblings("tr").remove();
			var resultMap = eval("("+data+")");
			if (resultMap.status == "error")
			{				
				$("#kebiaoHead").after("<tr><td colspan='6'>未查询到课表信息</td></tr>");
			}
			else if (resultMap.status == "success")
			{
				var kebiaoList = resultMap.data;
				
				for (var i=0; i<kebiaoList.length; i++)
				{
					var kebiaoItem = kebiaoList[i];
					var _kebiao = '<tr>';
					_kebiao+='<td>'+kebiaoItem.js+'</td>';
					var j = i+1;
					if (j>4)
					{
						j -= 4;
					}
					if (kebiaoItem.z1.trim()!="")
					{
						_kebiao+='<td><div class="cor'+j+'"><p>'+kebiaoItem.z1+'</p></div></td>';
					}else
					{
						_kebiao+='<td></td>';
					}
					if (kebiaoItem.z2.trim()!="")
					{
						_kebiao+='<td><div class="cor'+j+'"><p>'+kebiaoItem.z2+'</p></div></td>';
					}else
					{
						_kebiao+='<td></td>';
					}
					if (kebiaoItem.z3.trim()!="")
					{
						_kebiao+='<td><div class="cor'+j+'"><p>'+kebiaoItem.z3+'</p></div></td>';
					}else
					{
						_kebiao+='<td></td>';
					}
					if (kebiaoItem.z4.trim()!="")
					{
						_kebiao+='<td><div class="cor'+j+'"><p>'+kebiaoItem.z4+'</p></div></td>';
					}else
					{
						_kebiao+='<td></td>';
					}
					if (kebiaoItem.z5.trim()!="")
					{
						_kebiao+='<td><div class="cor'+j+'"><p>'+kebiaoItem.z5+'</p></div></td>';
					}else
					{
						_kebiao+='<td></td>';
					}
					_kebiao+='</tr>';
					$("table").append(_kebiao);
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
        <div class="top2"><h1>课表查询</h1></div>
        <div class="top3"></div>
    </div>
</div>
<div class="selBox">
    <div class="sel">
        <select name="select" id="select" onchange="selectXQ()">
        </select>
    </div>
</div>
<div class="title"><h1>2015-2016第二学期</h1></div>
<div class="kb">
    <div style="overflow-x:scroll;">
    <table width="800" border="0" cellspacing="0" cellpadding="0" class="data">
        <tr id="kebiaoHead">
            <th width="10%">编号</th>
            <th width="18%">周一</th>
            <th width="18%">周二</th>
            <th width="18%">周三</th>
            <th width="18%">周四</th>
            <th width="18%">周五</th>
        </tr>
    </table>
	</div>
</div>
</body>
</html>
