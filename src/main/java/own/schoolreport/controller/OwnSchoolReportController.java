package own.schoolreport.controller;
import own.schoolreport.entity.OwnSchoolReportEntity;
import own.schoolreport.service.OwnSchoolReportServiceI;
import own.setting.entity.OwnSettingEntity;
import own.setting.service.OwnSettingServiceI;

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
 * @Description: 到校情况表
 * @author onlineGenerator
 * @date 2017-03-17 14:54:59
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/ownSchoolReportController")
public class OwnSchoolReportController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OwnSchoolReportController.class);

	@Autowired
	private OwnSettingServiceI ownSettingService;
	@Autowired
	private OwnSchoolReportServiceI ownSchoolReportService;
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
	 * 到校情况表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "ownSchoolReport")
	public ModelAndView ownSchoolReport(HttpServletRequest request) {
		return new ModelAndView("own/schoolreport/ownSchoolReportList");
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
	public void datagrid(OwnSchoolReportEntity ownSchoolReport,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OwnSchoolReportEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ownSchoolReport, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.ownSchoolReportService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除到校情况表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(OwnSchoolReportEntity ownSchoolReport, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		ownSchoolReport = systemService.getEntity(OwnSchoolReportEntity.class, ownSchoolReport.getId());
		message = "到校情况表删除成功";
		try{
			ownSchoolReportService.delete(ownSchoolReport);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "到校情况表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除到校情况表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "到校情况表删除成功";
		try{
			for(String id:ids.split(",")){
				OwnSchoolReportEntity ownSchoolReport = systemService.getEntity(OwnSchoolReportEntity.class, 
				id
				);
				ownSchoolReportService.delete(ownSchoolReport);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "到校情况表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加到校情况表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(OwnSchoolReportEntity ownSchoolReport, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "到校情况表添加成功";
		try{
			
			List<OwnSettingEntity> list=ownSettingService.findByQueryString("from OwnSettingEntity c where c.enableBm='on'");
			String settingId="";
			if(list!=null&&list.size()>0)//查询当前所需设置ID
			{
				settingId=list.get(0).getId();
				ownSchoolReport.setSettingid(settingId);
				ownSchoolReport.setSettingname(list.get(0).getYear());
			}
			
			if(StringUtil.isNotEmpty(settingId))
			{
				ownSchoolReportService.save(ownSchoolReport);
			}
			else
			{
				message = "到校情况表添加失败,暂无可用的自主报到设置！";
				
			}
			
			
			
			
		
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "到校情况表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新到校情况表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(OwnSchoolReportEntity ownSchoolReport, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "到校情况表更新成功";
		OwnSchoolReportEntity t = ownSchoolReportService.get(OwnSchoolReportEntity.class, ownSchoolReport.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(ownSchoolReport, t);
			ownSchoolReportService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "到校情况表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 到校情况表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(OwnSchoolReportEntity ownSchoolReport, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(ownSchoolReport.getId())) {
			ownSchoolReport = ownSchoolReportService.getEntity(OwnSchoolReportEntity.class, ownSchoolReport.getId());
			req.setAttribute("ownSchoolReportPage", ownSchoolReport);
		}
		return new ModelAndView("own/schoolreport/ownSchoolReport-add");
	}
	/**
	 * 到校情况表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(OwnSchoolReportEntity ownSchoolReport, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(ownSchoolReport.getId())) {
			ownSchoolReport = ownSchoolReportService.getEntity(OwnSchoolReportEntity.class, ownSchoolReport.getId());
			req.setAttribute("ownSchoolReportPage", ownSchoolReport);
		}
		return new ModelAndView("own/schoolreport/ownSchoolReport-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("own/schoolreport/ownSchoolReportUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public void exportXls(OwnSchoolReportEntity ownSchoolReport,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "到校情况表";
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
			CriteriaQuery cq = new CriteriaQuery(OwnSchoolReportEntity.class, dataGrid);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ownSchoolReport, request.getParameterMap());
			
			List<OwnSchoolReportEntity> ownSchoolReports = this.ownSchoolReportService.getListByCriteriaQuery(cq,false);
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("到校情况表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), OwnSchoolReportEntity.class, ownSchoolReports);
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
	public void exportXlsByT(OwnSchoolReportEntity ownSchoolReport,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "到校情况表";
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
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("到校情况表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), OwnSchoolReportEntity.class, null);
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
				List<OwnSchoolReportEntity> listOwnSchoolReportEntitys = 
					(List<OwnSchoolReportEntity>)ExcelImportUtil.importExcelByIs(file.getInputStream(),OwnSchoolReportEntity.class,params);
				for (OwnSchoolReportEntity ownSchoolReport : listOwnSchoolReportEntitys) {
					ownSchoolReportService.save(ownSchoolReport);
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
