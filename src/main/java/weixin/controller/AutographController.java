package weixin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import weixin.util.GetUserInfoService;
import weixin.wxdj.service.wxUserBindServiceI;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/autographController")
public class AutographController
{
	@Resource
	private wxUserBindServiceI wxuserBindService;
	@Resource
	private GetUserInfoService getUserInfoService;

	/**
	 * 上课签到
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goAutograph")
	public ModelAndView goTeacherKB(HttpServletRequest request)
	{
		String accountid = request.getParameter("appid");// 微信主ID
		String openId = request.getParameter("openid");// 微信openid
		String userId = getUserInfoService.getUserId(openId);

		int t = wxuserBindService.getUserBindByOpenid(openId);
		if (t <= 0)
		{
			return new ModelAndView("redirect:weixinLinksucaiController.do?link&id=4028e4b05c867724015c8a643f1c0002");
		}

		int x = wxuserBindService.insertAutograph(openId, userId, accountid);

		String message = "签到成功";

		if (x <= 0)
		{
			message = "签到失败";
		}

		return new ModelAndView("weixin/autograph", "message", message);
	}
}