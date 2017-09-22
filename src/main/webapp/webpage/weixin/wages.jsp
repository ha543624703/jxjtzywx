<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
<title>江西卫生职业学院-工资查询</title>
<link href="plug-in/weixin/wages/css/wage.css" rel="stylesheet" type="text/css">
<link href="plug-in/weixin/userbind/css/css.css" rel="stylesheet" type="text/css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript">
$(function(){
	var date = new Date;
	var year = date.getFullYear();
	for (var i = year; i >= year - 3; i--)
	{
		$("select[name='select']").append('<option value="'+i+'">'+i+'</option>');
	}
	selectXQ();
})
function selectXQ(){
	$("#queryError").hide();
	$(".tabBox").hide();
	$("#queryIng").show();
	$("#wagesHead").siblings("tr").remove();
	var year = $("select[name='select']").val();
	$.ajax({
		url : "wagesController.do?getWages",
		type : "post",
		data : {"year":year},
		dataType : "json",
		success : function(data) {
			var resultMap = eval("("+data+")");
			var wagesList = resultMap.list;
			if (wagesList != null && wagesList.length > 0)
			{
				$("#queryError").hide();
				$(".tabBox").show();
				$("#queryIng").hide();
				for (var i=0; i<wagesList.length; i++)
				{
					var item = wagesList[i];
					var _wages = '<tr>';
					_wages+='<td>'+item.S59+'</td>';
					_wages+='<td>'+item.BH+'</td>';
					_wages+='<td>'+item.BM+'</td>';
					_wages+='<td>'+item.XM+'</td>';
					_wages+='<td>'+item.S1+'</td>';
					_wages+='<td>'+item.S2+'</td>';
					_wages+='<td>'+item.S3+'</td>';
					_wages+='<td>'+item.S4+'</td>';
					_wages+='<td>'+item.S5+'</td>';
					_wages+='<td>'+item.S6+'</td>';
					_wages+='<td>'+item.S7+'</td>';
					_wages+='<td>'+item.S8+'</td>';
					_wages+='<td>'+item.S9+'</td>';
					_wages+='<td>'+item.S10+'</td>';
					_wages+='<td>'+item.S11+'</td>';
					_wages+='<td>'+item.S12+'</td>';
					_wages+='<td>'+item.S13+'</td>';
					_wages+='<td>'+item.S14+'</td>';
					_wages+='<td>'+item.S15+'</td>';
					_wages+='<td>'+item.S16+'</td>';
					_wages+='<td>'+item.S17+'</td>';
					_wages+='<td>'+item.S18+'</td>';
					_wages+='<td>'+item.S19+'</td>';
					_wages+='<td>'+item.S20+'</td>';
					_wages+='<td>'+item.S21+'</td>';
					_wages+='<td>'+item.S22+'</td>';
					_wages+='<td>'+item.S23+'</td>';
					_wages+='<td>'+item.S24+'</td>';
					_wages+='<td>'+item.S25+'</td>';
					_wages+='<td>'+item.S26+'</td>';
					_wages+='<td>'+item.S27+'</td>';
					_wages+='<td>'+item.S28+'</td>';
					_wages+='<td>'+item.S29+'</td>';
					_wages+='<td>'+item.S30+'</td>';
					_wages+='<td>'+item.S31+'</td>';
					_wages+='<td>'+item.S32+'</td>';
					_wages+='<td>'+item.S33+'</td>';
					_wages+='<td>'+item.S34+'</td>';
					_wages+='<td>'+item.S35+'</td>';
					_wages+='<td>'+item.S36+'</td>';
					_wages+='<td>'+item.S37+'</td>';
					_wages+='<td>'+item.S38+'</td>';
					_wages+='<td>'+item.S39+'</td>';
					_wages+='<td>'+item.S40+'</td>';
					_wages+='<td>'+item.S41+'</td>';
					_wages+='<td>'+item.S42+'</td>';
					_wages+='<td>'+item.S43+'</td>';
					_wages+='<td>'+item.S44+'</td>';
					_wages+='<td>'+item.S45+'</td>';
					_wages+='<td>'+item.S46+'</td>';
					_wages+='<td>'+item.S47+'</td>';
					_wages+='<td>'+item.S48+'</td>';
					_wages+='<td>'+item.S49+'</td>';
					_wages+='<td>'+item.S50+'</td>';
					_wages+='<td>'+item.S51+'</td>';
					_wages+='<td>'+item.S52+'</td>';
					_wages+='<td>'+item.S53+'</td>';
					_wages+='<td>'+item.S54+'</td>';
					_wages+='<td>'+item.S55+'</td>';
					_wages+='<td>'+item.S56+'</td>';
					_wages+='<td>'+item.S57+'</td>';
					_wages+='<td>'+item.S58+'</td>';
					
					_wages+='<td>'+item.S60+'</td>';
					_wages+='<td>'+item.S61+'</td>';
					_wages+='</tr>';
					$("table").append(_wages);
				}
			}
			else
			{
				$("#queryError").show();
				$(".tabBox").hide();
				$("#queryIng").hide();
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
        <div class="top2"><h1>工资查询</h1></div>
        <div class="top3"></div>
    </div>
</div>
<div class="global">
    <div class="selBox">
        <div class="sel">
        <select name="select" onchange="selectXQ();">

        </select>
        </div>
    </div>
    <div class="tabBox">
        <table border="0" cellspacing="0" cellpadding="0" class="tabsData">
            <tr id="wagesHead">
            	<th>实发工资</th>
                <th>工资编号</th>
                <th>所属部门</th>
                <th>姓名</th>
                <th>年</th>
                <th>月</th>
                <th>岗位工资</th>
                <th>薪级工资</th>
                <th>职务十(中系)</th>
                <th>教护龄</th>
                <th>基本工资合计</th>
                <th>职务工资</th>
                <th>保留津贴</th>
                <th>职务10(其他)</th>
                <th>菜蓝子</th>
                <th>物价补贴</th>
                <th>卫生贴</th>
                <th>通讯费</th>
                <th>月奖</th>
                <th>特贴</th>
                <th>基础性绩效合计</th>
                <th>考勤</th>
                <th>其它收入</th>
                <th>班主任津贴</th>
                <th>实习班主任</th>
                <th>成教班主任津贴、监考</th>
                <th>课时费</th>
                <th>监考费</th>
                <th>军训补助</th>
                <th>运动会补贴</th>
                <th>值班费</th>
                <th>成教课时费</th>
                <th>误餐补贴</th>
                <th>中餐</th>
                <th>教研室负责人补贴</th>
                <th>奖励性绩效合计</th>
                <th>精神文明奖</th>
                <th>综治奖</th>
                <th>节能奖</th>
                <th>降温、取暖费</th>
                <th>住房补贴</th>
                <th>科研奖</th>
                <th>政府奖励合计</th>
                <th>失业金</th>
                <th>失业险固定</th>
                <th>公积金</th>
                <th>工会费</th>
                <th>保险</th>
                <th>税款</th>
                <th>电费</th>
                <th>水费</th>
                <th>医药费</th>
                <th>规范津补贴减少额</th>
                <th>养老保险预缴</th>
                <th>扣款</th>
                <th>病事假扣款</th>
                <th>职业年金</th>
                <th>扣款合计</th>
                <th>停发工资</th>
                <th>公积金差额</th>
                <th>应税工资</th>
                <th>计税工资</th>
                
                <th>备注</th>
                <th>是否失保计算</th>
            </tr>
        </table>
    </div>
</div>
<div id="queryIng" class="message"><span>正在查询...</span></div>
<div id="queryError" class="message"><span>未查询到工资信息！</span></div>
</body>
</html>
