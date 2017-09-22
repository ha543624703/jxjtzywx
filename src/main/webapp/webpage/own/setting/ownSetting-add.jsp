<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>请假管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
	  function ispd() {
		var sdate = $('#bmStartDate').val();
		var edate = $('#bmEndDate').val();
		var d1 = new Date(sdate.replace(/\-/g, "\/"));
		var d2 = new Date(edate.replace(/\-/g, "\/"));
		if (d1 > d2) {
			tip("结束日期不能小于开始日期！");
			 $('#bmEndDate').val("");
			return false;
		}
	}
	
	
	
	
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="ownSettingController.do?doAdd" tiptype="3">
		<input id="id" name="id" type="hidden" value="${ownSetting.id }">
		<input id="createName" name="createName" type="hidden"
			value="${ownSetting.createName }">
		<input id="createDate" name="createDate" type="hidden"
			value="${ownSetting.createDate }">
		<input id="updateName" name="updateName" type="hidden"
			value="${ownSetting.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${ownSetting.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">是否启用报名:
				</label></td>
				<td class="value">
				启用<input id="enableBm" name="enableBm" type="radio" value="on"
					style="width: 30px" class="inputxt" datatype="*">
				关闭<input id="enableBm" name="enableBm" type="radio"  value="off"
					style="width: 30px" class="inputxt" datatype="*">
					
					 <span
					class="Validform_checktip"></span> 
					<label class="Validform_label"
					style="display: none;">是否启用报名</label></td>

			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 报到年级:
				</label></td>
				<td class="value">
					  <select  id="year" name="year" maxlength="4" datatype="n"  >
						     	<%
						     	Calendar a=Calendar.getInstance();
						     	int year=a.get(Calendar.YEAR);
						     	
						     	for(int i=year;i<=year+5;i++)
						     	{
						     	%>
						     	<option><%=i%></option>
						     	
						     	<%} %>
						     </select>
					 <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">报到年级</label>
					
					
					</td>
			<tr>
				<td align="right"><label class="Validform_label">
						班级预分模式： </label></td>
				<td class="value"><t:dictSelect field="proClassMode" 
						type="list" typeGroupCode="promode"
						defaultVal="${ownSetting.proClassMode}" hasLabel="false"
						title="班级预分模式"></t:dictSelect> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">班级预分模式</label>
				</td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label">
						预分宿舍模式： </label></td>
				<td class="value"><t:dictSelect field="proDormitoryMode" 
						type="list" typeGroupCode="promode"
						defaultVal="${leavePage.proDormitoryMode}" hasLabel="false"
						title="预分宿舍模式"></t:dictSelect> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">预分宿舍模式</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						报名开始日期: </label></td>
				<td class="value"><input id="bmStartDate" name="bmStartDate"
					type="text" style="width: 150px" class="Wdate"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="*">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">报名开始日期</label></td>
			<tr>
				<td align="right"><label class="Validform_label">
						报名结束日期: </label></td>
				<td class="value"><input id="bmEndDate" name="bmEndDate"
					type="text" style="width: 150px" class="Wdate" onchange="ispd();"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="*">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">报名结束日期</label></td>
			</tr>

		</table>
	</t:formvalid>
</body>
<script src="webpage/swm/dome/leave.js"></script>