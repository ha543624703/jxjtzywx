<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="ownConsumerinfoList" checkbox="true" fitColumns="false" title="生活用品信息" actionUrl="ownConsumerinfoController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="修改人名称"  field="updateName"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="修改日期"  field="updateDate"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="学生编号"  field="studentid"  hidden="true" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title"  hidden="true" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="答案"  field="answer"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="隶属年级"  field="settingname"  hidden="true" query="true"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="ownConsumerinfoController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="ownConsumerinfoController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="ownConsumerinfoController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="ownConsumerinfoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="ownConsumerinfoController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/own/consumerinfo/ownConsumerinfoList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
//导出
function ExportXls() {
	JeecgExcelExport("ownConsumerinfoController.do?exportXls","ownConsumerinfoList");
}
 </script>