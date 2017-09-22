package weixin.cms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import weixin.cms.cmsdata.CmsDataCollectI;
import weixin.cms.cmsdata.impl.CmsArticleDataCollect;
import weixin.cms.cmsdata.impl.CmsIndexDataCollect;
import weixin.cms.cmsdata.impl.CmsMenuDataCollect;
import weixin.cms.cmsdata.impl.CmsPhotoAlbumDataCollect;
import weixin.cms.cmsdata.impl.CmsPhotoDataCollect;
import weixin.cms.common.CmsConstant;
import weixin.cms.common.CmsDataContent;
import weixin.cms.entity.CmsArticleEntity;
import weixin.cms.entity.WeixinCmsSiteEntity;
import weixin.cms.entity.WeixinCmsStyleEntity;
import weixin.cms.service.CmsArticleServiceI;
import weixin.cms.service.WeixinCmsSiteServiceI;
import weixin.cms.service.WeixinCmsStyleServiceI;
import weixin.cms.util.CmsFreemarkerHelper;
import weixin.util.GetUserInfoService;
import weixin.util.WeiXinConstants;
import weixin.wxdj.service.WebGroupService;
import weixin.wxdj.service.wxUserBindServiceI;

import com.alibaba.fastjson.JSONObject;

/**
 * CMS微站核心控制器
 * @author zhangdaihao
 *
 */
@Controller
@RequestMapping("/cmsController")
public class CmsController extends BaseController
{
	@Autowired
	private CmsArticleServiceI cmsArticleService;
	@Autowired
	private WeixinCmsSiteServiceI weixinCmsSiteService;
	@Autowired
	private WeixinCmsStyleServiceI weixinCmsStyleService;
	@Autowired
	private WebGroupService webGroupService;
	@Autowired
	private wxUserBindServiceI wxUserBindService;
	@Autowired
	private GetUserInfoService getUserInfoService;
	@Autowired
	public wxUserBindServiceI wxuserBindService;

	/**
	 * CMS 数据收集器配置
	 */
	private static Map<String, Object> dataCollectContent = new HashMap<String, Object>();
	static
	{
		// 载CMS首页数据
		dataCollectContent.put(CmsConstant.CMS_PAGE_INDEX, new CmsIndexDataCollect());
		// CMS栏目数据
		dataCollectContent.put(CmsConstant.CMS_PAGE_MENU, new CmsMenuDataCollect());
		// CMS文章数据
		dataCollectContent.put(CmsConstant.CMS_PAGE_ARTICLE, new CmsArticleDataCollect());
		// CMS相册
		dataCollectContent.put(CmsConstant.CMS_PHOTO_ALBUM, new CmsPhotoAlbumDataCollect());
		// CMS相册相片
		dataCollectContent.put(CmsConstant.CMS_PHOTO, new CmsPhotoDataCollect());
	}

	/**
	 * 根据访问入口获取rootUrl
	 * @param request
	 * @param page
	 * @return
	 */
	private String getRootUrl(HttpServletRequest request, String page)
	{
		String rootUrl = null;
		if (CmsConstant.CMS_PAGE_INDEX.equals(page) || CmsConstant.CMS_PAGE_MENU.equals(page) || CmsConstant.CMS_PAGE_ARTICLE.equals(page))
		{
			// index menu article
			rootUrl = request.getSession().getServletContext().getRealPath(CmsConstant.CMS_ROOT_URL);
		}
		else if (CmsConstant.CMS_PHOTO_ALBUM.equals(page) || CmsConstant.CMS_PHOTO.equals(page))
		{
			// photoAlbum photo
			rootUrl = request.getSession().getServletContext().getRealPath(CmsConstant.CMS_PHOTO_ROOT_URL);
		}
		else
		{
			// throw IllegalArgumentException()

		}
		return rootUrl;
	}

	/**
	 * 页面调整引擎
	 * 
	 * @param request
	 * @param response
	 * @param page
	 *            模板页
	 * @param id
	 *            数据ID
	 * @param accountid
	 *            微信公众号ID
	 */
	@RequestMapping(params = "goPage")
	public void goPage(HttpServletRequest request, HttpServletResponse response, @RequestParam String page, Model model)
	{
		Map<String, String> params = paramsToMap(request);
		String openId = request.getParameter("userid");
		// 将openid设置到session中
		HttpSession session = request.getSession();
		session.setAttribute(WeiXinConstants.USER_OPENID, openId);
		int t = wxuserBindService.getUserBindByOpenid(openId);
		if (t > 0)
		{
			params.put("isLanded", "isLanded");
			String utype = getUserInfoService.getStuOrTea(openId);
			if ("TEA".equals(utype))
			{
				params.put("isTEA", "TEA");
			}
		}

		// 获取站点的网站样式风格 模块根路径
		String rootUrl = getRootUrl(request, page);
		String styleUrl = null;
		// 站点信息
		WeixinCmsSiteEntity weixinCmsSiteEntity = weixinCmsSiteService.findUniqueByProperty(WeixinCmsSiteEntity.class, "accountid",
				params.get("accountid"));
		// 站点模板样式
		WeixinCmsStyleEntity weixinCmsStyleEntity = null;
		// 模板名字 add by liuqiang
		String templateName = null;
		// mod by liuqiang 如果是微相册的请求用默认模板default
		if (weixinCmsSiteEntity != null && !CmsConstant.CMS_PHOTO_ALBUM.equals(page) && !CmsConstant.CMS_PHOTO.equals(page))
		{
			if (weixinCmsSiteEntity.getSiteTemplateStyle() != null)
			{
				weixinCmsStyleEntity = weixinCmsStyleService.get(WeixinCmsStyleEntity.class, weixinCmsSiteEntity.getSiteTemplateStyle());
			}
			if (weixinCmsStyleEntity != null)
			{
				templateName = ResourceUtil.getWeiXinAccountId() + "/" + weixinCmsStyleEntity.getTemplateUrl();
				styleUrl = rootUrl + "/" + ResourceUtil.getWeiXinAccountId() + "/" + weixinCmsStyleEntity.getTemplateUrl()
						+ CmsConstant.CMS_TEMPL_PACKAGE;
			}
			else
			{
				templateName = CmsConstant.CMS_YUNLIN_TEMPLATE;
				styleUrl = rootUrl + CmsConstant.CMS_YUNLIAN_STYLE;
			}
		}
		else
		{
			templateName = CmsConstant.CMS_YUNLIN_TEMPLATE;
			styleUrl = rootUrl + CmsConstant.CMS_YUNLIAN_STYLE;
		}
		params.put(CmsConstant.CMS_STYLE_NAME, templateName);
		// ---------------------------------------------------------------------------------------------------------
		CmsFreemarkerHelper cmsFreemarkerHelper = new CmsFreemarkerHelper(styleUrl);
		// 调用对应的数据收集器数据
		if (dataCollectContent.get(page) != null)
		{
			CmsDataCollectI cmsDataCollect = (CmsDataCollectI) dataCollectContent.get(page);
			cmsDataCollect.collect(params);
		}

		// 将所有容器的数据访问，加上前缀cmsData，注意大小写
		String html = cmsFreemarkerHelper.parseTemplate(page + CmsConstant.CMS_TEMPL_INDEX, CmsDataContent.loadContent());
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-store");

		PrintWriter writer;
		try
		{
			writer = response.getWriter();
			writer.println(html);
			writer.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 采用ajax方式，获取二级文章标题列表
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "getMenuList")
	public void getMenuList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid)
	{
		Map<String, String> params = paramsToMap(request);
		List<CmsArticleEntity> list = cmsArticleService.listByMap(params, dataGrid.getPage(), dataGrid.getRows());
		int count = cmsArticleService.getCount(params);
		dataGrid.setTotal(count);
		dataGrid.setResults(list);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 采用ajax方式，获取二级文章标题列表
	 * @param request
	 */
	@RequestMapping(params = "getArticleList")
	@ResponseBody
	public String getArticleList(HttpServletRequest request, Integer page, Integer rows)
	{
		if (rows == null || rows == 0)
		{
			rows = 5;
		}
		if (page == null || page == 0)
		{
			page = 0;
		}
		else
		{
			page = rows * (page - 1);
		}
		String tableName = request.getParameter("columnid");
		List<Map<String, Object>> list = null;
		int totalCount = 0;

		if ("schoolNews".equals(tableName))
		{
			list = webGroupService.querySchoolNewsList(page, rows);
			totalCount = webGroupService.querySchoolNewsCount();
		}
		if ("notice".equals(tableName))
		{
			list = webGroupService.queryNoticeList(page, rows);
			totalCount = webGroupService.queryNoticeCount();
		}
		if ("dynamic".equals(tableName))
		{
			list = webGroupService.queryDynamicList(page, rows);
			totalCount = webGroupService.queryDynamicCount();
		}

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("list", list);
		map.put("totalCount", totalCount);

//		// 获取是否绑定账户
//		try
//		{
//			System.out.println("尝试获取openid");
//			String openId = getUserInfoService.getOpenIdByAccountId(request);
//			System.out.println("获取openid成功===="+openId);
//			int t = wxuserBindService.getUserBindByOpenid(openId);
//			if (t > 0)
//			{
//				map.put("isLanded", "isLanded");
//				// 是否是老师
//				String utype = getUserInfoService.getStuOrTea(openId);
//				if ("TEA".equals(utype))
//				{
//					map.put("isTEA", "TEA");
//				}
//				else
//				{
//					map.put("isTEA", "STU");
//				}
//			}
//			else
//			{
//				map.put("isLanded", "isNotLanded");
//			}
//		}
//		catch (Exception e)
//		{
//			System.out.println("获取openid失败");
//		}

		return JSONObject.toJSONString(map);
	}

	@RequestMapping(params = "goMenu")
	public String goMenu(String menu, Integer page, Integer rows, Model model)
	{
		if (rows == null || rows == 0)
		{
			rows = 5;
		}
		if (page == null || page == 0)
		{
			page = 0;
		}
		else
		{
			page = rows * (page - 1);
		}
		List<Map<String, Object>> list = null;
		String menuName = null;

		if ("schoolNews".equals(menu))
		{
			list = webGroupService.querySchoolNewsList(page, rows);
			menuName = "学院新闻";
		}
		if ("notice".equals(menu))
		{
			list = webGroupService.queryNoticeList(page, rows);
			menuName = "通知公告";
		}
		if ("dynamic".equals(menu))
		{
			list = webGroupService.queryDynamicList(page, rows);
			menuName = "部门动态";
		}

		model.addAttribute("list", list);
		model.addAttribute("menu", menu);
		model.addAttribute("menuName", menuName);

		return "weixin/webgroup/menu";
	}

	@RequestMapping(params = "goArticle")
	public String goArticle(HttpServletRequest request, String menu, Long articleid, Model model)
	{
		Map<String, Object> map = null;
		String menuName = null;

		if ("schoolNews".equals(menu))
		{
			map = webGroupService.getSchoolNews(articleid);
			menuName = "学院新闻";
		}
		if ("notice".equals(menu))
		{
			map = webGroupService.getNotice(articleid);
			menuName = "通知公告";
		}
		if ("dynamic".equals(menu))
		{
			map = webGroupService.getDynamic(articleid);
			menuName = "部门动态";
		}

		if (map != null && map.size() > 0)
		{
			long update_time = 0;
			if (map.get("update_time") != null)
			{
				update_time = (long) map.get("update_time");
			}

			Date date = new Date(1000L * update_time);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("updateTimeStr", sdf.format(date));
		}

		// 查询留言
		List<Map<String, Object>> articleMessageList = wxUserBindService.getArticleMessage(articleid);

		model.addAttribute("map", map);
		model.addAttribute("menu", menu);
		model.addAttribute("menuName", menuName);
		model.addAttribute("articleMessageList", articleMessageList);

		try
		{
			String openId = getUserInfoService.getOpenIdByAccountId(request);
			int t = wxuserBindService.getUserBindByOpenid(openId);
			if (t > 0)
			{
				model.addAttribute("isLanded", "isLanded");
			}
		}
		catch (Exception e)
		{

		}

		return "weixin/webgroup/article";
	}

	/**
	 * @description:新增留言
	 * @param request
	 * @param articleid
	 * @param message
	 * @return
	 * @author:duzl
	 * @createTime:2017年5月9日 上午9:07:55
	 */
	@RequestMapping(params = "createArticleMessage")
	@ResponseBody
	public String createArticleMessage(HttpServletRequest request, Model model, String articleid, String message)
	{
		String openId = getUserInfoService.getOpenIdByAccountId(request);
		String userId = getUserInfoService.getUserId(openId);
		String utype = getUserInfoService.getStuOrTea(openId);
		String xm = getUserInfoService.getUserNameByUserId(userId, utype);

		Map<String, Object> returnMap = new HashMap<String, Object>();
		int t = wxUserBindService.insertArticleMessage(userId, xm, articleid, message);

		if (t > 0)
		{
			returnMap.put("status", "success");
			returnMap.put("username", xm);
			returnMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		}
		else
		{
			returnMap.put("status", "fail");
		}

		return JSONObject.toJSONString(returnMap);
	}

	/**
	 * 封装request请求参数到Map里
	 * @param request
	 * @return
	 */
	private Map<String, String> paramsToMap(HttpServletRequest request)
	{
		Map<String, String> params = new HashMap<String, String>();
		// 得到枚举类型的参数名称，参数名称若有重复的只能得到第一个
		@SuppressWarnings("rawtypes")
		Enumeration em = request.getParameterNames();
		while (em.hasMoreElements())
		{
			String paramName = (String) em.nextElement();
			String paramValue = request.getParameter(paramName);
			// 形成键值对应的map
			params.put(paramName, paramValue);
		}
		if (!params.containsKey(ACCOUNTID))
		{
			params.put(ACCOUNTID, ResourceUtil.getWeiXinAccountId());
		}
		return params;
	}
}
