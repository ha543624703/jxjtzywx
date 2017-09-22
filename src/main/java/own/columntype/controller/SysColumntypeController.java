package own.columntype.controller;
import own.columntype.entity.SysColumntypeEntity;
import own.columntype.service.SysColumntypeServiceI;
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
 * @Description: 栏目类型
 * @author onlineGenerator
 * @date 2017-03-18 10:20:44
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/sysColumntypeController")
public class SysColumntypeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SysColumntypeController.class);

	@Autowired
	private SysColumntypeServiceI sysColumntypeService;
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
	 * 栏目类型列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "sysColumntype")
	public ModelAndView sysColumntype(HttpServletRequest request) {
		String id=request.getParameter("columnId")+"";
		request.setAttribute("columnId", id);
		
		return new ModelAndView("own/columntype/sysColumntypeList");
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
	public void datagrid(SysColumntypeEntity sysColumntype,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SysColumntypeEntity.class, dataGrid);
		//查询条件组装器
		String id=request.getParameter("columnId")+"";
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sysColumntype, request.getParameterMap());
		//cq.eq("columnId",id);
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.sysColumntypeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除栏目类型
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(SysColumntypeEntity sysColumntype, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		sysColumntype = systemService.getEntity(SysColumntypeEntity.class, sysColumntype.getId());
		message = "栏目类型删除成功";
		try{
			sysColumntypeService.delete(sysColumntype);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "栏目类型删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除栏目类型
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "栏目类型删除成功";
		try{
			for(String id:ids.split(",")){
				SysColumntypeEntity sysColumntype = systemService.getEntity(SysColumntypeEntity.class, 
				id
				);
				sysColumntypeService.delete(sysColumntype);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "栏目类型删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加栏目类型
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(SysColumntypeEntity sysColumntype, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "栏目类型添加成功";
		try{
			sysColumntypeService.save(sysColumntype);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "栏目类型添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新栏目类型
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(SysColumntypeEntity sysColumntype, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "栏目类型更新成功";
		SysColumntypeEntity t = sysColumntypeService.get(SysColumntypeEntity.class, sysColumntype.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(sysColumntype, t);
			sysColumntypeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "栏目类型更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 栏目类型新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(SysColumntypeEntity sysColumntype, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sysColumntype.getId())) {
			sysColumntype = sysColumntypeService.getEntity(SysColumntypeEntity.class, sysColumntype.getId());
			req.setAttribute("sysColumntypePage", sysColumntype);
		}
		String columnId=req.getParameter("columnId");
		req.setAttribute("columnId", columnId);
		return new ModelAndView("own/columntype/sysColumntype-add");
	}
	/**
	 * 栏目类型编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(SysColumntypeEntity sysColumntype, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sysColumntype.getId())) {
			sysColumntype = sysColumntypeService.getEntity(SysColumntypeEntity.class, sysColumntype.getId());
			req.setAttribute("sysColumntypePage", sysColumntype);
		}
		return new ModelAndView("own/columntype/sysColumntype-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("own/columntype/sysColumntypeUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public void exportXls(SysColumntypeEntity sysColumntype,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "栏目类型";
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
			CriteriaQuery cq = new CriteriaQuery(SysColumntypeEntity.class, dataGrid);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sysColumntype, request.getParameterMap());
			
			List<SysColumntypeEntity> sysColumntypes = this.sysColumntypeService.getListByCriteriaQuery(cq,false);
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("栏目类型列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), SysColumntypeEntity.class, sysColumntypes);
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
	public void exportXlsByT(SysColumntypeEntity sysColumntype,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "栏目类型";
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
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("栏目类型列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), SysColumntypeEntity.class, null);
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
				List<SysColumntypeEntity> listSysColumntypeEntitys = 
					(List<SysColumntypeEntity>)ExcelImportUtil.importExcelByIs(file.getInputStream(),SysColumntypeEntity.class,params);
				for (SysColumntypeEntity sysColumntype : listSysColumntypeEntitys) {
					sysColumntypeService.save(sysColumntype);
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
