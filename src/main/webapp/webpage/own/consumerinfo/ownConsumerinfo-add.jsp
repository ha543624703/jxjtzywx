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
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="ownConsumerinfoController.do?doAdd" tiptype="3">
		<input id="id" name="id" type="hidden"
			value="${ownConsumerinfoPage.id }">
		<input id="createName" name="createName" type="hidden"
			value="${ownConsumerinfoPage.createName }">
		<input id="createDate" name="createDate" type="hidden"
			value="${ownConsumerinfoPage.createDate }">
		<input id="updateName" name="updateName" type="hidden"
			value="${ownConsumerinfoPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${ownConsumerinfoPage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 学生编码:
				</label></td>
				<td class="value"><input id="studentid" name="studentid"
					type="text" style="width: 150px" datatype="*" /> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">学生编码</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 标题:
				</label></td>
				<td class="value"><input id="title" name="title"
					type="text" style="width: 150px" datatype="*" /> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">标题</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 答案:
				</label></td>
				<td class="value"><input id="answer" name="answer"
					type="text" style="width: 150px" datatype="*" /> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">答案</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/swm/dome/leave.js"></script>