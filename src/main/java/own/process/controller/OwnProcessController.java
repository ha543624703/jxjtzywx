package own.process.controller;
import own.process.entity.OwnProcessEntity;
import own.process.service.OwnProcessServiceI;
import own.setting.entity.OwnSettingEntity;

import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExcelTitle;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 报到流程设置
 * @author onlineGenerator
 * @date 2017-03-10 09:49:57
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/ownProcessController")
public class OwnProcessController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OwnProcessController.class);

	@Autowired
	private OwnProcessServiceI ownProcessService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 报到流程设置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "ownProcess")
	public ModelAndView ownProcess(HttpServletRequest request) {
		return new ModelAndView("own/process/ownProcessList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(OwnProcessEntity ownProcess,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OwnProcessEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ownProcess, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.ownProcessService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除报到流程设置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(OwnProcessEntity ownProcess, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		ownProcess = systemService.getEntity(OwnProcessEntity.class, ownProcess.getId());
		message = "报到流程设置删除成功";
		try{
			ownProcessService.delete(ownProcess);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "报到流程设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除报到流程设置
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "报到流程设置删除成功";
		try{
			for(String id:ids.split(",")){
				OwnProcessEntity ownProcess = systemService.getEntity(OwnProcessEntity.class, 
				id
				);
				ownProcessService.delete(ownProcess);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "报到流程设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加报到流程设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(OwnProcessEntity ownProcess, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "报到流程设置添加成功";
		try{
			String ownSettingId=request.getParameter("ownSettingEntity.id");
			OwnSettingEntity entity=new OwnSettingEntity();
			entity.setId(ownSettingId);
			ownProcess.setOwnSettingEntity(entity);
			
			String ownpreids=ownProcess.getPreId();
			String preName="";
			if(StringUtil.isNotEmpty(ownpreids))
			{
			
				for(int i=0;i<ownpreids.split(",").length;i++)
				{
					
					OwnProcessEntity entityProc=ownProcessService.getEntity(OwnProcessEntity.class, ownpreids.split(",")[i]);
					if(entityProc!=null)
					{
						
						preName+=entityProc.getProcessName()+",";
						
					}
					
				}
				preName=preName.substring(0,preName.length()-1);
				
			}
			ownProcess.setPreName(preName);
			
			ownProcessService.save(ownProcess);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "报到流程设置添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新报到流程设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(OwnProcessEntity ownProcess, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "报到流程设置更新成功";
		OwnProcessEntity t = ownProcessService.get(OwnProcessEntity.class, ownProcess.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(ownProcess, t);
			
			String ownpreids=ownProcess.getPreId();
			String preName="";
			if(StringUtil.isNotEmpty(ownpreids))
			{
			
				for(int i=0;i<ownpreids.split(",").length;i++)
				{
					
					OwnProcessEntity entityProc=ownProcessService.getEntity(OwnProcessEntity.class, ownpreids.split(",")[i]);
					if(entityProc!=null)
					{
						
						preName+=entityProc.getProcessName()+",";
						
					}
				
				}
				preName=preName.substring(0,preName.length()-1);
				
			}
			ownProcess.setPreName(preName);
			t.setPreName(preName);

			
			ownProcessService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "报到流程设置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 报到流程设置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(OwnProcessEntity ownProcess, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(ownProcess.getId())) {
			ownProcess = ownProcessService.getEntity(OwnProcessEntity.class, ownProcess.getId());
			req.setAttribute("ownProcessPage", ownProcess);
		}
		return new ModelAndView("own/process/ownProcess-add");
	}
	/**
	 * 报到流程设置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(OwnProcessEntity ownProcess, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(ownProcess.getId())) {
			ownProcess = ownProcessService.getEntity(OwnProcessEntity.class, ownProcess.getId());
			req.setAttribute("ownProcessPage", ownProcess);
		}
		return new ModelAndView("own/process/ownProcess-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("own/process/ownProcessUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public void exportXls(OwnProcessEntity ownProcess,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "报到流程设置";
			// 根据浏览器进行转码，使其支持中文文件名
			if (BrowserUtils.isIE(request)) {
				response.setHeader(
						"content-disposition",
						"attachment;filename="
								+ java.net.URLEncoder.encode(codedFileName,
										"UTF-8") + ".xls");
			} else {
				String newtitle = new String(codedFileName.getBytes("UTF-8"),
						"ISO8859-1");
				response.setHeader("content-disposition",
						"attachment;filename=" + newtitle + ".xls");
			}
			// 产生工作簿对象
			HSSFWorkbook workbook = null;
			CriteriaQuery cq = new CriteriaQuery(OwnProcessEntity.class, dataGrid);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ownProcess, request.getParameterMap());
			
			List<OwnProcessEntity> ownProcesss = this.ownProcessService.getListByCriteriaQuery(cq,false);
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("报到流程设置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), OwnProcessEntity.class, ownProcesss);
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (Exception e) {
		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {

			}
		}
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public void exportXlsByT(OwnProcessEntity ownProcess,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "报到流程设置";
			// 根据浏览器进行转码，使其支持中文文件名
			if (BrowserUtils.isIE(request)) {
				response.setHeader(
						"content-disposition",
						"attachment;filename="
								+ java.net.URLEncoder.encode(codedFileName,
										"UTF-8") + ".xls");
			} else {
				String newtitle = new String(codedFileName.getBytes("UTF-8"),
						"ISO8859-1");
				response.setHeader("content-disposition",
						"attachment;filename=" + newtitle + ".xls");
			}
			// 产生工作簿对象
			HSSFWorkbook workbook = null;
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("报到流程设置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), OwnProcessEntity.class, null);
			fOut = response.getOutputStream();
			workbook.write(fOut);
		} catch (Exception e) {
		} finally {
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {

			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setSecondTitleRows(1);
			params.setNeedSave(true);
			try {
				List<OwnProcessEntity> listOwnProcessEntitys = 
					(List<OwnProcessEntity>)ExcelImportUtil.importExcelByIs(file.getInputStream(),OwnProcessEntity.class,params);
				for (OwnProcessEntity ownProcess : listOwnProcessEntitys) {
					ownProcessService.save(ownProcess);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
}
