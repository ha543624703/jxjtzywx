<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>请假管理</title>
<t:base type="ckeditor,jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="ownProcessContentController.do?doAdd" tiptype="3">
		<input id="id" name="id" type="hidden" value="${ownProcessContentPage.id }">
		<input id="fpageid" name="fpageid" type="hidden"
			value="${fpageid}">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">页面内容
				</label></td>
				<td class="value">
				
				 <t:ckeditor name="content" isfinder="false" type="width:800,height:400" value="${ownProcessContentPage.content}"></t:ckeditor>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">页面内容</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/swm/dome/leave.js"></script>