<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="ownSettingList" checkbox="true" fitColumns="false" title="自主报名设置" actionUrl="ownSettingController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="修改人名称"  field="updateName"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="修改日期"  field="updateDate"  hidden="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="报名年级"  field="year"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="报名开始日期"  field="bmStartDate" formatter="yyyy-MM-dd hh:mm:ss" hidden="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="报名结束日期"  field="bmEndDate" formatter="yyyy-MM-dd hh:mm:ss" hidden="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="预分班模式"  field="proClassMode"  hidden="true"  queryMode="single" dictionary="promode" width="120"></t:dgCol>
   <t:dgCol title="预分宿舍模式"  field="proDormitoryMode"  hidden="true"  queryMode="single" dictionary="promode" width="120"></t:dgCol>
   <t:dgCol title="是否启用自助报名"  field="enableBm"  hidden="true" replace="正常_on,关闭_off"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="ownSettingController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="ownSettingController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="ownSettingController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="ownSettingController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="ownSettingController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/own/setting/ownSettingList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#ownSettingListtb").find("input[name='bmStartDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#ownSettingListtb").find("input[name='bmEndDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'ownSettingController.do?upload', "ownSettingList");
}

//导出
function ExportXls() {
	JeecgExcelExport("ownSettingController.do?exportXls","ownSettingList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("ownSettingController.do?exportXlsByT","ownSettingList");
}
 </script>