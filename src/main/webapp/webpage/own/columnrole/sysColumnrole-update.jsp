<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>栏目权限添加</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="sysColumnroleController.do?doUpdate" tiptype="3">
		<input id="id" name="id" type="hidden"
			value="${sysColumnrolePage.id }">
		<input id="createName" name="createName" type="hidden"
			value="${sysColumnrolePage.createName }">
		<input id="createDate" name="createDate" type="hidden"
			value="${sysColumnrolePage.createDate }">
		<input id="updateName" name="updateName" type="hidden"
			value="${sysColumnrolePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${sysColumnrolePage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 栏目id:
				</label></td>
				<td class="value"><input id="columnId" name="columnId" value="${sysColumnrolePage.columnId}"
					type="text" style="width: 150px" datatype="*" /> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;"> 栏目id</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 栏目名称:
				</label></td>
				<td class="value"><input id="columnName" name="columnName"  value="${sysColumnrolePage.columnName}"
					type="text" style="width: 150px" datatype="*" /> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;"> 栏目名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 所属角色：
				</label></td>
				<td class="value"><t:dictSelect id="roleId" field="roleId" type="select"
						dictTable="t_s_role" dictField="id" dictText="rolename"
						defaultVal="${sysColumnrolePage.roleId}" hasLabel="false"
						title="所属角色"></t:dictSelect> 
						
						<input id="roleName" name="roleName" type="hidden" value="${sysColumnrolePage.roleName }" />
						
						
						<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">所属角色</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script type="text/javascript">
	$("#roleId").change(function() {
		var rolename=$("#roleId").find("option:selected").text();
		$("#roleName").val(rolename);
		
	});
</script>

<script src="webpage/swm/dome/leave.js"></script>