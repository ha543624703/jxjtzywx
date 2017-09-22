package weixin.controller;

import net.sf.json.JSONObject;
import org.jeecgframework.core.util.HttpRequestor;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/waitThingController")
public class WaitThingController
{
    @Resource
    private wxUserBindServiceI wxuserBindService;
    @Resource
    private GetUserInfoService getUserInfoService;

    /**
     * 进入待办事项页
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "goWaitThing")
    public String goWaitThing(HttpServletRequest request, Model model)
    {
        String openId = request.getParameter("openid");
        int t = wxuserBindService.getUserBindByOpenid(openId);
        if (t <= 0)
        {
            return "redirect:weixinLinksucaiController.do?link&id=4028e4b05c867724015c8a643f1c0002";
        }

        String userId = getUserInfoService.getUserId(openId);
        model.addAttribute("userId", userId);

        return "weixin/wait";
    }

    /**
     * @param jsdm
     * @param ym
     * @param pagesize
     * @return
     * @description:查询学工待办
     * @author:duzl
     * @createTime:2017年5月22日 上午11:01:54
     */
    @RequestMapping(params = "getWaitThing")
    @ResponseBody
    public String getWaitThing(String jsdm, Integer ym, Integer pagesize)
    {
        if (pagesize == null || pagesize == 0)
        {
            pagesize = 10;
        }
        if (ym == null || ym == 0)
        {
            ym = 1;
        }

        Map<String, String> map = new HashMap<>(4);
        map.put("jsdm", jsdm);
        map.put("pagesize", pagesize + "");
        map.put("ym", ym + "");
        map.put("SHZTLX", 0 + "");
        Document document = new HttpRequestor().webService("http://192.168.100.49:8000/PhoneService.asmx/GetSHXQ", "192.168.100.49:8000", map);
        if (document != null)
        {
            Elements container = document.getElementsByTag("string");
            return container.text();
        }
        return null;
    }

    @RequestMapping(params = "getWaitThingCount")
    @ResponseBody
    public int getWaitThingCount(String jsdm)
    {
        Map<String, String> map = new HashMap<>(4);
        map.put("jsdm", jsdm);
        map.put("pagesize", "10000");
        map.put("ym", "1");
        map.put("SHZTLX", 0 + "");

        try
        {
            Document document = new HttpRequestor().webService("http://192.168.100.49:8000/PhoneService.asmx/GetSHXQ", "192.168.100.49:8000", map);
            if (document != null)
            {
                Elements container = document.getElementsByTag("string");
                String json = container.text();
                JSONObject jSONObject = JSONObject.fromObject(json);
                String count = jSONObject.getString("count").replace("\"", "");
                return Integer.valueOf(count);
            }
            return 0;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    /**
     * 进入OA待办事项页
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "goOAWaitThing")
    public String goOAWaitThing(HttpServletRequest request, Model model)
    {
        String openId = request.getParameter("openid");
        int t = wxuserBindService.getUserBindByOpenid(openId);
        if (t <= 0)
        {
            return "redirect:weixinLinksucaiController.do?link&id=4028e4b05c867724015c8a643f1c0002";
        }

        String userId = getUserInfoService.getUserId(openId);
        model.addAttribute("userId", userId);

        return "weixin/OAwait";
    }

    @RequestMapping(params = "getOAWaitThing")
    @ResponseBody
    public String getOAWaitThing(String jsdm)
    {
        String json = null;
        try
        {
            json = new HttpRequestor().doGet("http://192.168.100.47:8080/jxoa/ApprovalServlet?action=dblb&username=" + jsdm);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return json;
    }

    @RequestMapping(params = "getSFZJHByXH")
    @ResponseBody
    public String getSFZJHByXH(String xh)
    {
        String sql = "SELECT SFZJH FROM XS_XSJBXX WHERE XH=?";

        List<Object> sqlValues = new ArrayList<>();
        sqlValues.add(xh);

        EducaJDBC dBjdbcCont = new EducaJDBC();

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

        if (rst != null && rst.getRowCount() > 0)
        {
            SortedMap<String,Object>[] map = rst.getRows();

            return map[0].get("sfzjh") + "";
        }

        return "";
    }
}