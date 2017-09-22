<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>栏目权限添加</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="sysColumntypeController.do?doUpdate" tiptype="3">
		<input id="id" name="id" type="hidden"
			value="${sysColumntypePage.id }">
		<input id="createName" name="createName" type="hidden"
			value="${sysColumntypePage.createName }">
		<input id="createDate" name="createDate" type="hidden"
			value="${sysColumntypePage.createDate }">
		<input id="updateName" name="updateName" type="hidden"
			value="${sysColumntypePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${sysColumntypePage.updateDate }">
			
		<input id="columnId" name="columnId" type="hidden"
			value="${sysColumntypePage.columnId }">	
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 类型ID:
				</label></td>
				<td class="value"><input id="typeid" name="typeid" type="text" value="${sysColumntypePage.typeid}"
					style="width: 150px" datatype="*" /> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;"> 类型ID</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 类型名称:
				</label></td>
				<td class="value"><input id="typename" name="typename" value="${sysColumntypePage.typename}"
					type="text" style="width: 150px" datatype="*" /> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;"> 类型名称</label></td>
			</tr>

		</table>
	</t:formvalid>
</body>
<script type="text/javascript">
</script>
<script src="webpage/swm/dome/leave.js"></script>