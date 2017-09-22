package own.processcontent.controller;
import own.process.entity.OwnProcessEntity;
import own.processcontent.entity.OwnProcessContentEntity;
import own.processcontent.service.OwnProcessContentServiceI;
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
 * @Description: 流程挂接内容
 * @author onlineGenerator
 * @date 2017-03-10 09:50:11
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/ownProcessContentController")
public class OwnProcessContentController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OwnProcessContentController.class);

	@Autowired
	private OwnProcessContentServiceI ownProcessContentService;
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
	 * 流程挂接内容列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "ownProcessContent")
	public ModelAndView ownProcessContent(HttpServletRequest request) {
		return new ModelAndView("own/processcontent/ownProcessContentList");
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
	public void datagrid(OwnProcessContentEntity ownProcessContent,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OwnProcessContentEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ownProcessContent, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.ownProcessContentService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除流程挂接内容
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(OwnProcessContentEntity ownProcessContent, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		ownProcessContent = systemService.getEntity(OwnProcessContentEntity.class, ownProcessContent.getId());
		message = "流程挂接内容删除成功";
		try{
			ownProcessContentService.delete(ownProcessContent);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "流程挂接内容删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除流程挂接内容
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "流程挂接内容删除成功";
		try{
			for(String id:ids.split(",")){
				OwnProcessContentEntity ownProcessContent = systemService.getEntity(OwnProcessContentEntity.class, 
				id
				);
				ownProcessContentService.delete(ownProcessContent);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "流程挂接内容删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加流程挂接内容
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(OwnProcessContentEntity ownProcessContent, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "流程挂接内容添加成功";
		try{

			if(StringUtil.isNotEmpty(ownProcessContent.getId())) 
			{
				
				OwnProcessContentEntity en=ownProcessContentService.getEntity(OwnProcessContentEntity.class, ownProcessContent.getId());
				en.setContent(ownProcessContent.getContent());
				ownProcessContentService.saveOrUpdate(en);
				message="流程挂接内容修改成功";
			}
			else
			{
				String fpageid=request.getParameter("fpageid");
				ownProcessContent.setStatus("Y");
				OwnProcessEntity en=new OwnProcessEntity();
				en.setId(fpageid);
				ownProcessContent.setOwnProcessEntity(en);
				ownProcessContentService.save(ownProcessContent);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "流程挂接内容添加/修改失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新流程挂接内容
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(OwnProcessContentEntity ownProcessContent, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "流程挂接内容更新成功";
		OwnProcessContentEntity t = ownProcessContentService.get(OwnProcessContentEntity.class, ownProcessContent.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(ownProcessContent, t);
			ownProcessContentService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "流程挂接内容更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 流程挂接内容新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(OwnProcessContentEntity ownProcessContent, HttpServletRequest req) {
		//流程主表ID
		String fid=req.getParameter("id");
		List<OwnProcessContentEntity> list=ownProcessContentService.findByQueryString(" from OwnProcessContentEntity c where c.ownProcessEntity.id='"+fid+"'");
		if(list!=null&&list.size()>0)
		{
			req.setAttribute("ownProcessContentPage", list.get(0));
			
		}
		req.setAttribute("fpageid", fid);
		return new ModelAndView("own/processcontent/ownProcessContent-add");
	}
	/**
	 * 流程挂接内容编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(OwnProcessContentEntity ownProcessContent, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(ownProcessContent.getId())) {
			ownProcessContent = ownProcessContentService.getEntity(OwnProcessContentEntity.class, ownProcessContent.getId());
			req.setAttribute("ownProcessContentPage", ownProcessContent);
		}
		return new ModelAndView("own/processcontent/ownProcessContent-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		return new ModelAndView("own/processcontent/ownProcessContentUpload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public void exportXls(OwnProcessContentEntity ownProcessContent,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "流程挂接内容";
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
			CriteriaQuery cq = new CriteriaQuery(OwnProcessContentEntity.class, dataGrid);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ownProcessContent, request.getParameterMap());
			
			List<OwnProcessContentEntity> ownProcessContents = this.ownProcessContentService.getListByCriteriaQuery(cq,false);
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("流程挂接内容列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), OwnProcessContentEntity.class, ownProcessContents);
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
	public void exportXlsByT(OwnProcessContentEntity ownProcessContent,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid) {
		response.setContentType("application/vnd.ms-excel");
		String codedFileName = null;
		OutputStream fOut = null;
		try {
			codedFileName = "流程挂接内容";
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
			workbook = ExcelExportUtil.exportExcel(new ExcelTitle("流程挂接内容列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
					"导出信息"), OwnProcessContentEntity.class, null);
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
				List<OwnProcessContentEntity> listOwnProcessContentEntitys = 
					(List<OwnProcessContentEntity>)ExcelImportUtil.importExcelByIs(file.getInputStream(),OwnProcessContentEntity.class,params);
				for (OwnProcessContentEntity ownProcessContent : listOwnProcessContentEntitys) {
					ownProcessContentService.save(ownProcessContent);
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
