package weixin.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import weixin.jdbc.WagesJDBC;
import weixin.util.GetUserInfoService;
import weixin.wxdj.service.wxUserBindServiceI;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wagesController")
public class WagesController
{
    @Resource
    private wxUserBindServiceI wxuserBindService;
    @Resource
    private GetUserInfoService getUserInfoService;

    @RequestMapping(params = "goWages")
    public ModelAndView goWages(HttpServletRequest request)
    {
        String openId = request.getParameter("openid");// 微信openid

        int t = wxuserBindService.getUserBindByOpenid(openId);
        if (t <= 0)
        {
            return new ModelAndView("redirect:weixinLinksucaiController.do?link&id=4028e4b05c867724015c8a643f1c0002");
        }

        return new ModelAndView("weixin/wages");
    }

    @RequestMapping(params = "getWages")
    @ResponseBody
    public String getWages(HttpServletRequest request, String year)
    {
        String openId = getUserInfoService.getOpenIdByAccountId(request);
        String userId = getUserInfoService.getUserId(openId);

        List<Object> sqlValues = new ArrayList<>();
        sqlValues.add(userId);
        sqlValues.add(year);

        WagesJDBC jdbc = new WagesJDBC();
        String sql = "SELECT a.* FROM jxjs_jbgz a inner join wage_userid b on a.bh=b.wagesid "
                + "where b.userid=? and a.S1=? ORDER BY a.S2 DESC";
        jdbc.setSql(sql);
        jdbc.setSqlValues(sqlValues);

        List<Map<String, Object>> list = jdbc.executeQuery();

        Map<String, Object> returnMap = new HashMap<>();

        returnMap.put("list", list);

        return JSONObject.toJSONString(returnMap);
    }
}