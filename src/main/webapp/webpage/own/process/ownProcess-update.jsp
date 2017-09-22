<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>请假管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

</head>
<body>
	<t:formvalid formid="formoprocess" dialog="true" usePlugin="password"
		layout="table" action="ownProcessController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden" value="${ownProcessPage.id }">
		<input id="createName" name="createName" type="hidden"
			value="${ownProcessPage.createName }">
		<input id="createDate" name="createDate" type="hidden"
			value="${ownProcessPage.createDate }">
		<input id="updateName" name="updateName" type="hidden"
			value="${ownProcessPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${ownProcessPage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">是否生效:
				</label></td>
				<td class="value">启用<input id="status" name="status"
					type="radio" value="on" style="width: 30px" class="inputxt"
					datatype="*"> 关闭<input id="status" name="status"
					type="radio" value="off" style="width: 30px" class="inputxt"
					datatype="*"> <span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">是否生效</label></td>

			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 所属设置：
				</label></td>
				<td class="value"><t:dictSelect field="ownSettingEntity.id"
						type="select" dictTable="own_setting" dictField="id"
						dictText="year" defaultVal="${ownProcessPage.ownSettingEntity.id}"
						hasLabel="false" title="所属设置"></t:dictSelect> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">前置步骤</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 流程名称:
				</label></td>
				<td class="value"><input id="processName" name="processName"
					value="${ownProcessPage.processName}" type="text"
					style="width: 150px" datatype="*" /> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">流程名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 流程步骤：
				</label></td>
				<td class="value"><input id="processStep" name="processStep"
					value="${ownProcessPage.processStep}" type="text"
					style="width: 150px" datatype="*" /><span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">流程步骤</label></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label"> 前置步骤：
				</label></td>
				<td class="value" id="divpreids"><t:dictSelect field="preId" type="checkbox"
						dictTable="own_Process" dictField="id" dictText="process_name"
						defaultVal="${ownProcessPage.preId}" hasLabel="false" title="前置步骤"></t:dictSelect>
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">前置步骤</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script type="text/javascript">
	//编写自定义JS代码
	var ckt = "${ownProcessPage.status}";
	if (ckt == "on") {
		$("input[name='status']").get(0).checked = true;
	} else {
		$("input[name='status']").get(1).checked = true;
	}
	
	$(document).ready(function(){
		//debugger;
			var t="${ownProcessPage.id }";
			
			$('input[name="preId"]').each(function(){ 
				if(t==$(this).val()) 
				{
					//alert($(this).val());
					$(this).remove();
				}
		});
		var w="${ownProcessPage.processName}";
		$("#divpreids").html($("#divpreids").html().replace(w,"")); 
	});
	
</script>
<script src="webpage/swm/dome/leave.js"></script>