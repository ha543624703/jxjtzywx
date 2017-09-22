<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="sysColumnroleList" checkbox="true" fitColumns="false" title="栏目权限" actionUrl="sysColumnroleController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="修改人名称"  field="updateName"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="修改日期"  field="updateDate"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="角色id"  field="roleId"  hidden="true"  queryMode="single" dictionary="t_s_role,id,rolename" width="120"></t:dgCol>
   <t:dgCol title="角色名称"  field="roleName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="栏目名称"  field="columnName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="栏目id"  field="columnId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="sysColumnroleController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="sysColumnroleController.do?goAdd" funname="add"></t:dgToolBar>
   
   <t:dgToolBar title="栏目类型" icon="icon-add" url="sysColumntypeController.do?sysColumntype" funname="lm"></t:dgToolBar>

   <t:dgToolBar title="编辑" icon="icon-edit" url="sysColumnroleController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="sysColumnroleController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="sysColumnroleController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/own/columnrole/sysColumnroleList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
 function lm(title,url, id,width,height)
 {
			gridname=id;
			var rowsData = $('#'+id).datagrid('getSelections');
			if (!rowsData || rowsData.length==0) {
				tip('请选择对应的栏目');
				return;
			}
			if (rowsData.length>1) {
				tip('请选择一条记录');
				return;
			}
			url += '&columnId='+rowsData[0].columnId;
			createwindow(title,url,840,590);
 }
 
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'sysColumnroleController.do?upload', "sysColumnroleList");
}
 </script>