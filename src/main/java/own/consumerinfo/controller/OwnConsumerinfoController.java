package own.consumerinfo.controller;
import own.consumerinfo.entity.OwnConsumerinfoEntity;
import own.consumerinfo.service.OwnConsumerinfoServiceI;
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
 * @Description: 生活用品信息
 * @author onlineGenerator
 * @date 2017-03-17 14:55:20
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/ownConsumerinfoController")
public class OwnConsumerinfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OwnConsumerinfoController.class);

	@Autowired
	private OwnSettingServiceI ownSettingService;
	@Autowired
	private OwnConsumerinfoServiceI ownConsumerinfoService;
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
	 * 生活用品信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "ownConsumerinfo")
	public ModelAndView ownConsumerinfo(HttpServletRequest request) {
		return new ModelAndView("own/consumerinfo/ownConsumerinfoList");
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
	public void datagrid(OwnConsumerinfoEntity ownConsumerinfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OwnConsumerinfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ownConsumerinfo, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.ownConsumerinfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除生活用品信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(OwnConsumerinfoEntity ownConsumerinfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		ownConsumerinfo = systemService.getEntity(OwnConsumerinfoEntity.class, ownConsumerinfo.getId());
		message = "生活用品信息删除成功";
		try{
			ownConsumerinfoService.delete(ownConsumerinfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "生活用品信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除生活用品信息
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "生活用品信息删除成功";
		try{
			for(String id:ids.split(",")){
				OwnConsumerinfoEntity ownConsumerinfo = systemService.getEntity(OwnConsumerinfoEntity.class, 
				id
				);
				ownConsumerinfoService.delete(ownConsumerinfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "生活用品信息删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加生活用品信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(OwnConsumerinfoEntity ownConsumerinfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "生活用品信息添加成功";
		try{
		List<OwnSettingEntity> list=ownSettingService.findByQueryString("from OwnSettingEntity c where c.enableBm='on'");
			String settingId="";
			if(list!=null&&list.size()>0)//查询当前所需设置ID
			{
				settingId=list.get(0).getId();
				ownConsumerinfo.setSettingid(settingId);
				ownConsumerinfo.setSettingname(list.get(0).getYear());
			}
			if(StringUtil.isNotEmpty(settingId))
			{
				ownConsumerinfoService.save(ownConsumerinfo);
			}
			else
			{
				message = "生活用品信息添加失败,暂无可用的自主报到设置！";
				
			}
			
			
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "生活用品信息添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新生活用品信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(OwnConsumerinfoEntity ownConsumerinfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "生活用品信息更新成功";
		OwnConsumerinfoEntity t = ownConsumerinfoService.get(OwnConsumerinfoEntity.class, ownConsumerinfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(ownConsumerinfo, t);
			ownConsumerinfoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "生活用品信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 生活用品信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(OwnConsumerinfoEntity ownConsumerinfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(ownConsumerinfo.getId())) {
			ownConsumerinfo = ownConsumerinfoService.getEntity(OwnConsumerinfoEntity.class, ownConsumerinfo.getId());
			req.setAttribute("ownConsumerinfoPage", ownConsumerinfo);
		}
		return new ModelAndView("own/consumerinfo/ownConsumerinfo-add");
	}
	/**
	 * 生活用品信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(OwnConsumerinfoEntity ownConsumerinfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(ownConsumerinfo.getId())) {
			ownConsumerinfo = ownConsumerinfoService.getEntity(OwnConsumerinfoEntity.class, ownConsumerinfo.getId());
			req.setAttribute("ownConsumerinfoPage", ownConsumerinfo);
		}
		return new ModelAndView("own/consumerinfo/ownConsumerinfo-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("own/consumerinfo/ownConsumerinfoUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public void exportXls(OwnConsumerinfoEntity ownConsumerinfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "生活用品信息";
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
			CriteriaQuery cq = new CriteriaQuery(OwnConsumerinfoEntity.class, dataGrid);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ownConsumerinfo, request.getParameterMap());
			
			List<OwnConsumerinfoEntity> ownConsumerinfos = this.ownConsumerinfoService.getListByCriteriaQuery(cq,false);
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("生活用品信息列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), OwnConsumerinfoEntity.class, ownConsumerinfos);
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
	public void exportXlsByT(OwnConsumerinfoEntity ownConsumerinfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "生活用品信息";
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
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("生活用品信息列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), OwnConsumerinfoEntity.class, null);
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
				List<OwnConsumerinfoEntity> listOwnConsumerinfoEntitys = 
					(List<OwnConsumerinfoEntity>)ExcelImportUtil.importExcelByIs(file.getInputStream(),OwnConsumerinfoEntity.class,params);
				for (OwnConsumerinfoEntity ownConsumerinfo : listOwnConsumerinfoEntitys) {
					ownConsumerinfoService.save(ownConsumerinfo);
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
