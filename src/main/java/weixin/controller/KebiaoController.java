package weixin.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import weixin.jdbc.EducaJDBC;
import weixin.util.GetUserInfoService;
import weixin.wxdj.service.wxUserBindServiceI;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.sql.Result;
import java.util.*;

/**
 * 微信课表
 */
@Controller
@RequestMapping("/kebiaoController")
public class KebiaoController
{
	@Resource
	private wxUserBindServiceI wxuserBindService;
	@Resource
	private GetUserInfoService getUserInfoService;

	/**
	 * 进入课表页
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goTeacherKB")
	public String goTeacherKB(HttpServletRequest request)
	{
		String openId = request.getParameter("openid");
		int t = wxuserBindService.getUserBindByOpenid(openId);
		if (t <= 0)
		{
			return "redirect:weixinLinksucaiController.do?link&id=4028e4b05c867724015c8a643f1c0002";
		}

		String utype = getUserInfoService.getStuOrTea(openId);

		if ("TEA".equals(utype))
		{
			return "weixin/teacherKB";
		}

		return "weixin/studentKB";
	}

	/**
	 * 教师课表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "teacherKB")
	@ResponseBody
	public String teacherKB(HttpServletRequest request, String xnxqh)
	{
		String openId = getUserInfoService.getOpenIdByAccountId(request);

		String sql = "SELECT CASE WHEN SUBSTRING(a.KCSJBH,2,2)='AB' then '1-2节' WHEN SUBSTRING(a.KCSJBH,2,2)='CD' THEN '3-4节' "
				+ "WHEN SUBSTRING(a.KCSJBH,2,2)='EF' THEN '5-6节' WHEN SUBSTRING(a.KCSJBH,2,2)='GH' then '7-8节' end as js,"
				+ "Max(CASE SUBSTRING(a.KCSJBH,1,1) WHEN '1' THEN KCMC+'['+JXBMC+KCSJ+KKZC+']('+JIAOSMC+')' ELSE '' END) AS 'z1',"
				+ "Max(CASE SUBSTRING(a.KCSJBH,1,1) WHEN '2' THEN KCMC+'['+JXBMC+KCSJ+KKZC++']('+JIAOSMC+')' ELSE '' END) AS 'z2',"
				+ "Max(CASE SUBSTRING(a.KCSJBH,1,1) WHEN '3' THEN KCMC+'['+JXBMC+KCSJ+KKZC++']('+JIAOSMC+')' ELSE '' END) AS 'z3',"
				+ "Max(CASE SUBSTRING(a.KCSJBH,1,1) WHEN '4' THEN KCMC+'['+JXBMC+KCSJ+KKZC++']('+JIAOSMC+')' ELSE '' END) AS 'z4',"
				+ "Max(CASE SUBSTRING(a.KCSJBH,1,1) WHEN '5' THEN KCMC+'['+JXBMC+KCSJ+KKZC++']('+JIAOSMC+')' ELSE '' END) AS 'z5' "
				+ "from V_JX_PK_PKKKTZD_KB_JIAOS a where XNXQH = ? AND a.JSBH = ? GROUP BY SUBSTRING(a.KCSJBH,2,2)";

		List<Object> sqlValues = new ArrayList<>();

		String userId = getUserInfoService.getUserId(openId);

		sqlValues.add(xnxqh);
		sqlValues.add(userId);

		EducaJDBC dBjdbcCont = new EducaJDBC();

		dBjdbcCont.setSql(sql);
		dBjdbcCont.setSqlValues(sqlValues);

		Result rst = null;
		try
		{
			rst = dBjdbcCont.executeQuery();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return rsToJson(rst);
	}

	/**
	 * 学生课表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "studentKB")
	@ResponseBody
	public String studentKB(HttpServletRequest request, String xnxqh)
	{
		String openId = getUserInfoService.getOpenIdByAccountId(request);

		String zzname = "JX_KKTZD_XS" + xnxqh.substring(0, 4) + xnxqh.substring(10);

		String sql = "SELECT CASE WHEN SUBSTRING(ccc.KCSJBH,2,len(ccc.KCSJBH)-1)='AB' THEN '1-2节' WHEN SUBSTRING(ccc.KCSJBH,2,len(ccc.KCSJBH)-1)='CD' THEN '3-4节' "
				+ "WHEN SUBSTRING(ccc.KCSJBH,2,len(ccc.KCSJBH)-1)='EF' then '5-6节' WHEN SUBSTRING(ccc.KCSJBH,2,len(ccc.KCSJBH)-1)='EFG' then '5-7节'"
				+ "WHEN SUBSTRING(ccc.KCSJBH,2,len(ccc.KCSJBH)-1)='EFGH' then '5-8节' WHEN SUBSTRING(ccc.KCSJBH,2,len(ccc.KCSJBH)-1)='GH' then '7-8节' "
				+ "WHEN SUBSTRING(ccc.KCSJBH,2,len(ccc.KCSJBH)-1)='IJ' then '9-10节' end as js,"
				+ "Max(CASE SUBSTRING(ccc.KCSJBH,1,1) WHEN '1' THEN KCMC+'['+JXBMC+KCSJ+KKZC+']('+JIAOSMC+')' ELSE '' END) AS 'z1',"
				+ "Max(CASE SUBSTRING(ccc.KCSJBH,1,1) WHEN '2' THEN KCMC+'['+JXBMC+KCSJ+KKZC++']('+JIAOSMC+')' ELSE '' END) AS 'z2',"
				+ "Max(CASE SUBSTRING(ccc.KCSJBH,1,1) WHEN '3' THEN KCMC+'['+JXBMC+KCSJ+KKZC++']('+JIAOSMC+')' ELSE '' END) AS 'z3',"
				+ "Max(CASE SUBSTRING(ccc.KCSJBH,1,1) WHEN '4' THEN KCMC+'['+JXBMC+KCSJ+KKZC++']('+JIAOSMC+')' ELSE '' END) AS 'z4',"
				+ "Max(CASE SUBSTRING(ccc.KCSJBH,1,1) WHEN '5' THEN KCMC+'['+JXBMC+KCSJ+KKZC++']('+JIAOSMC+')' ELSE '' END) AS 'z5' "
				+ "FROM V_JX_PK_PKKKTZD_KB_JIAOS ccc INNER JOIN (SELECT aaa.* FROM "
				+ zzname
				+ " aaa INNER JOIN XS_XSJBXX ddd ON ddd.XSDM=aaa.XSDM "
				+ "WHERE ddd.XH= ? AND aaa.xnxqh= ? AND aaa.sfxz='1') bbb ON bbb.tzdid=ccc.tzdid WHERE ccc.xnxqh= ? GROUP BY SUBSTRING(ccc.KCSJBH,2,len(ccc.KCSJBH)-1)";

		EducaJDBC dBjdbcCont = new EducaJDBC();

		List<Object> sqlValues = new ArrayList<>();

		String userId = getUserInfoService.getUserId(openId);
		sqlValues.add(userId);
		sqlValues.add(xnxqh);
		sqlValues.add(xnxqh);

		dBjdbcCont.setSql(sql);
		dBjdbcCont.setSqlValues(sqlValues);

		Result rst;
		try
		{
			rst = dBjdbcCont.executeQuery();
		}
		catch (Exception e)
		{
			rst = null;
			e.printStackTrace();
		}

		return rsToJson(rst);
	}

	private String rsToJson(Result rst)
	{
		Map<String, Object> returnMap = new HashMap<>();

		if (rst != null && rst.getRowCount() > 0)
		{
			List<Map<String, Object>> data = new ArrayList<>();
			for (int i = 0; i < rst.getRowCount(); i++)
			{
				Map<String, Object> hmap = new HashMap<>(6);
				@SuppressWarnings("unchecked")
				SortedMap<String, Object>[] map = rst.getRows();
				String js = map[i].get("js") + "";
				String z1 = map[i].get("z1") + "";
				String z2 = map[i].get("z2") + "";
				String z3 = map[i].get("z3") + "";
				String z4 = map[i].get("z4") + "";
				String z5 = map[i].get("z5") + "";

				hmap.put("js", js);
				hmap.put("z1", z1);
				hmap.put("z2", z2);
				hmap.put("z3", z3);
				hmap.put("z4", z4);
				hmap.put("z5", z5);
				data.add(hmap);
			}

			returnMap.put("status", "success");
			returnMap.put("data", data);
		}
		else
		{
			returnMap.put("status", "error");
			returnMap.put("data", "未查询到课表信息");
		}

		return JSONObject.toJSONString(returnMap);
	}
}