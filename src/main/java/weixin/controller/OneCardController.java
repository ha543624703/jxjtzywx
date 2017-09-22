package weixin.controller;

import org.jeecgframework.core.util.HttpRequestor;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import weixin.util.GetUserInfoService;
import weixin.wxdj.service.wxUserBindServiceI;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/oneCardController")
public class OneCardController
{
    @Resource
    private wxUserBindServiceI wxuserBindService;
    @Resource
    private GetUserInfoService getUserInfoService;

    /**
     * 进入一卡通查询
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "goOneCard")
    public String goTeacherKB(HttpServletRequest request, Model model)
    {
        String openId = request.getParameter("openid");
        int t = wxuserBindService.getUserBindByOpenid(openId);
        if (t <= 0)
        {
            return "redirect:weixinLinksucaiController.do?link&id=4028e4b05c867724015c8a643f1c0002";
        }

        String userId = getUserInfoService.getUserId(openId);

        double ye = getWaitThingCount(userId);
        DecimalFormat df = new DecimalFormat("######0.00");
        String yetwo = df.format(ye);

        model.addAttribute("ye", yetwo);

        return "weixin/onecard";
    }

    @RequestMapping(params = "getOneCard")
    @ResponseBody
    public double getWaitThingCount(String userId)
    {
        Map<String, String> map = new HashMap<>(1);
        map.put("card", userId);
        double ye = 0.00D;
        try
        {
            Document document = new HttpRequestor().webService("http://192.168.100.23:8055/card.asmx/getCard", "192.168.100.23:8055", map);
            if (document != null)
            {
                Elements container = document.getElementsByTag("string");
                if (container != null)
                {
                    String EBAGAMT = container.text().replace("\"", "");
                    if (!"".equals(EBAGAMT))
                    {
                        try
                        {
                            ye = Double.valueOf(EBAGAMT) / 100;
                        }
                        catch (Exception e)
                        {
                            return ye;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            return ye;
        }
        return ye;
    }
}