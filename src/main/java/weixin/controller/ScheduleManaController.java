package weixin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import weixin.util.GetUserInfoService;
import weixin.util.TimeUtils;
import weixin.wxdj.service.wxUserBindServiceI;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/scheduleManaController")
public class ScheduleManaController
{
    @Resource
    private wxUserBindServiceI wxuserBindService;
    @Resource
    private GetUserInfoService getUserInfoService;

    /**
     * @param request
     * @param model
     * @return
     * @description:日程管理展示
     * @author duzl
     * @createTime:2017年5月12日 下午4:16:29
     */
    @RequestMapping(params = "goScheduleMana")
    public String goScheduleMana(HttpServletRequest request, Model model)
    {
        String openId = request.getParameter("openid");
        int t = wxuserBindService.getUserBindByOpenid(openId);
        if (t <= 0)
        {
            return "redirect:weixinLinksucaiController.do?link&id=4028e4b05c867724015c8a643f1c0002";
        }
        String userId = getUserInfoService.getUserId(openId);
        List<Map<String, Object>> list = wxuserBindService.queryScheduleMana(userId);

        model.addAttribute("list", list);

        if (list != null && list.size() > 0)
        {
            for (Map<String, Object> map : list)
            {
                map.put("workTimeStr", TimeUtils.formatDate((long) map.get("work_time"), "yyyy-MM-dd HH:mm"));
            }
        }

        return "weixin/scheList";
    }

    /**
     * @return
     * @description:跳转到新增日程管理
     * @author:duzl
     * @createTime:2017年5月15日 上午11:03:00
     */
    @RequestMapping(params = "goCreateScheduleMana")
    public String goCreateScheduleMana()
    {
        return "weixin/scheRelease";
    }

    /**
     * @param request
     * @param message
     * @return
     * @description:新增日程管理
     * @author:duzl
     * @createTime:2017年5月15日 上午11:03:12
     */
    @RequestMapping(params = "createScheduleMana")
    public String createScheduleMana(HttpServletRequest request, String message, String workTime)
    {
        String openId = getUserInfoService.getOpenIdByAccountId(request);

        String utype = getUserInfoService.getStuOrTea(openId);
        String userId = getUserInfoService.getUserId(openId);
        String xm = getUserInfoService.getUserNameByUserId(userId, utype);

        wxuserBindService.insertScheduleMana(userId, xm, message, TimeUtils.getDate(workTime, "yyyy-MM-dd HH:mm").getTime());

        // 重定向到日常管理列表
        return "redirect:weixinLinksucaiController.do?link&id=4028e4b05bfac38a015c0ad560130041";
    }
}