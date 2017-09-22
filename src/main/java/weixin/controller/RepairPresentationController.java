package weixin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import weixin.entity.RepairPresentation;
import weixin.util.GetUserInfoService;
import weixin.wxdj.service.wxUserBindServiceI;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @description:报修
 * @projectName:jxhlwx
 * @className:RepairController.java
 * @author:duzl
 * @createTime:2017年6月8日 上午9:36:12
 * @version 1.0
 */
@Controller
@RequestMapping("/repairPresentationController")
public class RepairPresentationController
{
	@Resource
    private wxUserBindServiceI wxuserBindService;
	@Resource
	private GetUserInfoService getUserInfoService;

	@RequestMapping(params = "goRepair")
	public String goRepair(HttpServletRequest request)
	{
		String openId = request.getParameter("openid");
		int t = wxuserBindService.getUserBindByOpenid(openId);
		if (t <= 0)
		{
			return "redirect:weixinLinksucaiController.do?link&id=4028e4b05c867724015c8a643f1c0002";
		}

		return "weixin/repair";
	}

	@RequestMapping(params = "setRepair")
	public String setRepair(HttpServletRequest request, RepairPresentation repairPresentation)
	{
		String openId = getUserInfoService.getOpenIdByAccountId(request);
		String userId = getUserInfoService.getUserId(openId);
		if (repairPresentation != null)
		{
			repairPresentation.setUserid(userId);
			repairPresentation.setStatus(0);
		}
		int t = wxuserBindService.insertRepairPresentation(repairPresentation);

		if (t <= 0)
		{
			return "weixin/repairFail";
		}

		return "weixin/repairSuccess";
	}

	@RequestMapping(params = "repairList")
	public String queryRepairs(HttpServletRequest request, Model model)
	{
		String openId = getUserInfoService.getOpenIdByAccountId(request);
		String userId = getUserInfoService.getUserId(openId);

		List<Map<String, Object>> wait = wxuserBindService.queryRepairList(userId, 0);
		List<Map<String, Object>> audited = wxuserBindService.queryRepairList(userId, 1);
		List<Map<String, Object>> failed = wxuserBindService.queryRepairList(userId, 2);

		setRepairDetail(wait);
		setRepairDetail(audited);
		setRepairDetail(failed);

		model.addAttribute("wait", wait);
		model.addAttribute("audited", audited);
		model.addAttribute("failed", failed);

		return "weixin/repairList";
	}

    private void setRepairDetail(List<Map<String, Object>> list)
    {
        if (list != null && list.size() > 0)
        {
            for (Map<String, Object> repair : list)
            {
                String repairDetail = "" + repair.get("place") + repair.get("buildingdescribe")
                        + repair.get("hydropower") + repair.get("furniture") + repair.get("other");
                if (repairDetail.length() > 17)
                {
                    repairDetail = repairDetail.substring(0, 15) + "...";
                }
                repair.put("repairDetail", repairDetail);
            }
        }
    }

	@RequestMapping(params = "repairDetail")
	public String getRepair(HttpServletRequest request, Integer id, Model model)
	{
		String openId = getUserInfoService.getOpenIdByAccountId(request);
		String userId = getUserInfoService.getUserId(openId);
		if (id != null)
		{
			Map<String, Object> map = wxuserBindService.getRepair(id);
			model.addAttribute("map", map);
			model.addAttribute("userId", userId);
		}
		return "weixin/repairDetail";
	}

	@RequestMapping(params = "repairAudited")
	public String setRepairAudited(Integer id)
	{
		wxuserBindService.setRepairAudited(id, 1);

		return "redirect:weixinLinksucaiController.do?link&id=4028e4b05cd2e9d7015cd2ec53250001";
	}

	@RequestMapping(params = "repairFailed")
	public String setRepairFailed(Integer id)
	{
		wxuserBindService.setRepairAudited(id, 2);

		return "redirect:weixinLinksucaiController.do?link&id=4028e4b05cd2e9d7015cd2ec53250001";
	}
}