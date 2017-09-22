<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="ownProcessList" checkbox="true" sortName="processStep" fitColumns="false" title="报到流程设置" actionUrl="ownProcessController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="修改人名称"  field="updateName"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="修改日期"  field="updateDate"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="流程名称"  field="processName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="流程步骤"  field="processStep"   hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="前置步骤id"  field="preId"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="前置步骤名称"  field="preName"  hidden="true"  queryMode="single"  width="250"></t:dgCol>
   <t:dgCol title="所属设置"  field="ownSettingEntity.year"  hidden="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="状态"  field="status"  replace="正常_on,关闭_off" hidden="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="ownProcessController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="ownProcessController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="ownProcessController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="ownProcessController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="ownProcessController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
   <t:dgToolBar title="对应页面" icon="icon-edit" url="ownProcessContentController.do?goAdd" funname="dyym"></t:dgToolBar>
   
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/own/process/ownProcessList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
 function dyym(title,url, id,width,height)
 {
			gridname=id;
			var rowsData = $('#'+id).datagrid('getSelections');
			if (!rowsData || rowsData.length==0) {
				tip('请选择对应的项目');
				return;
			}
			if (rowsData.length>1) {
				tip('请选择一条记录');
				return;
			}
			
			url += '&id='+rowsData[0].id;
			createwindow(title,url,840,590);
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'ownProcessController.do?upload', "ownProcessList");
}

//导出
function ExportXls() {
	JeecgExcelExport("ownProcessController.do?exportXls","ownProcessList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("ownProcessController.do?exportXlsByT","ownProcessList");
}
 </script>