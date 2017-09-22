package own.setting.controller;
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
 * @Description: 自主报名设置
 * @author onlineGenerator
 * @date 2017-03-09 15:46:36
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/ownSettingController")
public class OwnSettingController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OwnSettingController.class);

	@Autowired
	private OwnSettingServiceI ownSettingService;
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
	 * 自主报名设置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "ownSetting")
	public ModelAndView ownSetting(HttpServletRequest request) {
		return new ModelAndView("/own/setting/ownSettingList");
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
	public void datagrid(OwnSettingEntity ownSetting,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OwnSettingEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ownSetting, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.ownSettingService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除自主报名设置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(OwnSettingEntity ownSetting, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		ownSetting = systemService.getEntity(OwnSettingEntity.class, ownSetting.getId());
		message = "自主报名设置删除成功";
		try{
			ownSettingService.delete(ownSetting);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "自主报名设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除自主报名设置
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "自主报名设置删除成功";
		try{
			for(String id:ids.split(",")){
				OwnSettingEntity ownSetting = systemService.getEntity(OwnSettingEntity.class, 
				id
				);
				ownSettingService.delete(ownSetting);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "自主报名设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加自主报名设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(OwnSettingEntity ownSetting, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "自主报名设置添加成功";
		try{
			List<OwnSettingEntity> list=ownSettingService.findByQueryString("from OwnSettingEntity t where t.year='"+ownSetting.getYear()+"'");
			if(list!=null&&list.size()>0)
			{
			
				message="自主报名设置添加失败，该学年下已经存在相关的设置！";
				j.setSuccess(false);
				
			}
			else
			{
				ownSettingService.save(ownSetting);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "自主报名设置添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新自主报名设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(OwnSettingEntity ownSetting, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		int b=0;
		message = "自主报名设置更新成功";
		OwnSettingEntity t = ownSettingService.get(OwnSettingEntity.class, ownSetting.getId());
		try {
			if(ownSetting.getEnableBm().equals("on"))
			{
				
				List<OwnSettingEntity> list=ownSettingService.findByQueryString("from OwnSettingEntity t where t.enableBm='on' and id!='"+t.getId()+"'");
				if(list!=null&&list.size()>0)
				{
					message = "自主报名设置更新失败,当前已经存在正在使用的设置。请先禁用后再更新！";
					b=1;
				}
			}
		
			if(b!=1)//只能启用一个设置功能
			{
				MyBeanUtils.copyBeanNotNull2Bean(ownSetting, t);
				ownSettingService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "自主报名设置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 自主报名设置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(OwnSettingEntity ownSetting, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(ownSetting.getId())) {
			ownSetting = ownSettingService.getEntity(OwnSettingEntity.class, ownSetting.getId());
			req.setAttribute("ownSettingPage", ownSetting);
		}
		return new ModelAndView("/own/setting/ownSetting-add");
	}
	/**
	 * 自主报名设置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(OwnSettingEntity ownSetting, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(ownSetting.getId())) {
			ownSetting = ownSettingService.getEntity(OwnSettingEntity.class, ownSetting.getId());
			req.setAttribute("ownSettingPage", ownSetting);
		}
		return new ModelAndView("own/setting/ownSetting-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("/own/setting/ownSettingUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public void exportXls(OwnSettingEntity ownSetting,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "自主报名设置";
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
			CriteriaQuery cq = new CriteriaQuery(OwnSettingEntity.class, dataGrid);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ownSetting, request.getParameterMap());
			
			List<OwnSettingEntity> ownSettings = this.ownSettingService.getListByCriteriaQuery(cq,false);
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("自主报名设置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), OwnSettingEntity.class, ownSettings);
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
	public void exportXlsByT(OwnSettingEntity ownSetting,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "自主报名设置";
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
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("自主报名设置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), OwnSettingEntity.class, null);
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
				List<OwnSettingEntity> listOwnSettingEntitys = 
					(List<OwnSettingEntity>)ExcelImportUtil.importExcelByIs(file.getInputStream(),OwnSettingEntity.class,params);
				for (OwnSettingEntity ownSetting : listOwnSettingEntitys) {
					ownSettingService.save(ownSetting);
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
