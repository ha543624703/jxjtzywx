package weixin.controller;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.HttpRequestor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import weixin.wxdj.service.wxUserBindServiceI;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/wxUserBindController")
public class WxUserBindController
{
    @Resource
    private wxUserBindServiceI wxuserBindService;

    @RequestMapping(params = "userbind")
    public ModelAndView userbind(HttpServletRequest request)
    {
        String openId = request.getParameter("openid");// 微信openid
        int i = wxuserBindService.getUserBindByOpenid(openId);
        if (i > 0)
        {
            return new ModelAndView("weixin/userbinded", "openId", openId);
        }

        return new ModelAndView("weixin/userbind", "openId", openId);
    }

    @RequestMapping(params = "userbindOK")
    public String userbindOK(String accountid, String openid)
    {
        return "redirect:cmsController.do?goPage&page=index&accountid=" + accountid + "&userid=" + openid;
        //return "weixin/userbindOK";
    }

    @RequestMapping(params = "errorConn")
    public String errorConn(HttpServletRequest request)
    {
        String openId = request.getParameter("openid");// 微信openid
        int i = wxuserBindService.getUserBindByOpenid(openId);
        if (i > 0)
        {
            return "weixin/errorConn";
        }

        return "weixin/userbind";
    }

    @RequestMapping(params = "dosave")
    @ResponseBody
    public AjaxJson dosave(HttpServletRequest request)
    {
        AjaxJson j = new AjaxJson();
        String message = "绑定成功";
        String accountid = request.getParameter("appid");// 微信主ID
        String openId = request.getParameter("openid");// 微信openid
        int t = wxuserBindService.getUserBindByOpenid(openId);
        if (t > 0)
        {
            j.setMsg("当前账号已经绑定无需再次绑定!");
            return j;
        }

        String username = request.getParameter("txtuserName");
        String userpwd = request.getParameter("txtuserPwd");
        String urlReturn = "http://218.87.30.159:8080/cas/loginCheckServlet?userID=" + username + "&passWD=" + userpwd + "";
        int r = -1;
        String utype = "TEA";
        try
        {
            String ajaxString = new HttpRequestor().doGet(urlReturn);

            if (ajaxString.contains("flag:'0'"))
            {
                r = 1;
            }
            if (ajaxString.contains("TEA"))
            {
                utype = "TEA";
            }
            else
            {
                utype = "STU";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (r > 0)
        {
            int i = wxuserBindService.insertUserBind(openId, username, accountid, utype);
            if (i <= 0)
            {
                message = "绑定失败!";
            }
        }
        else
        {
            message = "账号验证失败！";
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "deleteUserBind")
    @ResponseBody
    public AjaxJson deleteUserBind(String openId)
    {
        int t = wxuserBindService.removeBindByOpenid(openId);
        AjaxJson j = new AjaxJson();
        if (t > 0)
        {
            j.setMsg("success");
        }
        else
        {
            j.setMsg("fail");
        }

        return j;
    }
}
