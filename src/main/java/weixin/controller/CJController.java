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

@Controller
@RequestMapping("/cjController")
public class CJController
{
	@Resource
	private wxUserBindServiceI wxuserBindService;
	@Resource
	private GetUserInfoService getUserInfoService;

	/**
	 * 进入成绩查询页
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goStudentCJ")
	public String goTeacherKB(HttpServletRequest request)
	{
		String openId = request.getParameter("openid");
		int t = wxuserBindService.getUserBindByOpenid(openId);
		if (t <= 0)
		{
			return "redirect:weixinLinksucaiController.do?link&id=4028e4b05c867724015c8a643f1c0002";
		}

		return "weixin/cj";
	}

	/**
	 * 成绩查询
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "studentCJ")
	@ResponseBody
	public String teacherKB(HttpServletRequest request, String xnxq)
	{
		Map<String, Object> returnMap = new HashMap<>();

		String openId = getUserInfoService.getOpenIdByAccountId(request);
		String utype = getUserInfoService.getStuOrTea(openId);
		String userId = getUserInfoService.getUserId(openId);
		String sql;
		// 老师
		if ("TEA".equals(utype))
		{
			sql = "select XNXQH,DWMC,ZYMC,BJMC,XH,b.XM,JSXM,KCJC,KCXZMC,XF,ZCJ from V_CJ_XSCJZB_LIST b inner join SZ_JSJBXX a on a.JSDM=b.JSDM  where a.JSBH= ? AND XNXQH='"
					+ xnxq + "' AND substring(XNXQH,0,5)>=(year(GETDATE())-5) order by XNXQH,BJMC ";
		}
		else
		{
			sql = "select XNXQH,DWMC,ZYMC,BJMC,XH,XM,JSXM,KCJC,KCXZMC,XF,ZCJ from V_CJ_XSCJZB_LIST b where XH= ? AND XNXQH='" + xnxq
					+ "' order by XNXQH";
		}

		List<Object> sqlValues = new ArrayList<>();
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

		if (rst != null && rst.getRowCount() > 0)
		{
			List<Map<String, Object>> data = new ArrayList<>();
			for (int i = 0; i < rst.getRowCount(); i++)
			{
				Map<String, Object> hmap = new HashMap<>(11);

				@SuppressWarnings("unchecked")
				SortedMap<String, Object>[] map = rst.getRows();

				hmap.put("XNXQH", map[i].get("XNXQH") + "");
				hmap.put("DWMC", map[i].get("DWMC") + "");
				hmap.put("ZYMC", map[i].get("ZYMC") + "");
				hmap.put("BJMC", map[i].get("BJMC") + "");
				hmap.put("XH", map[i].get("XH") + "");
				hmap.put("XM", map[i].get("XM") + "");
				hmap.put("JSXM", map[i].get("JSXM") + "");
				hmap.put("KCJC", map[i].get("KCJC") + "");
				hmap.put("KCXZMC", map[i].get("KCXZMC") + "");
				hmap.put("XF", map[i].get("XF") + "");
				hmap.put("ZCJ", map[i].get("ZCJ") + "");

				data.add(hmap);
			}

			returnMap.put("status", "success");
			returnMap.put("data", data);
		}
		else
		{
			returnMap.put("status", "error");
			returnMap.put("data", "未查询到成绩信息");
		}

		return JSONObject.toJSONString(returnMap);
	}
}