package weixin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.sql.Result;

import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weixin.guanjia.account.entity.WeixinAccountEntity;
import weixin.guanjia.account.service.WeixinAccountServiceI;
import weixin.jdbc.EducaJDBC;
import weixin.p3.oauth2.rule.RemoteWeixinMethod;
import weixin.wxdj.service.wxUserBindServiceI;

/**
 * @description:获取登录用户openId的接口
 * @projectName:jeewx
 * @className:GetOpenIdService.java
 * @author:duzl
 * @createTime:2017年4月7日 下午2:57:14
 * @version 1.0
 */
@Service("getUserInfoService")
public class GetUserInfoService
{
	@Autowired
	private RemoteWeixinMethod remoteWeixinMethod;
	@Autowired
	private WeixinAccountServiceI weixinAccountService;
	@Autowired
	private wxUserBindServiceI wxuserBindService;

	/**
	 * @description:获取绑定时填写的用户信息
	 * @param request
	 * @return
	 * @author:duzl
	 * @createTime:2017年4月7日 下午5:22:46
	 */
	public String getUserId(String openId)
	{
		String userId = "";

		List<Map<String, Object>> list = wxuserBindService.getUserWxList(openId);

		if (list != null && list.size() > 0)
		{
			userId = list.get(0).get("userid") + "";
		}

		return userId;
	}

	/**
	 * @description:获取是学生还是老师
	 * @param request
	 * @return
	 * @author:duzl
	 * @createTime:2017年4月7日 下午3:09:48
	 */
	public String getStuOrTea(String openId)
	{
		String utype = "TEA";

		List<Map<String, Object>> list = wxuserBindService.getUserWxList(openId);
		if (list != null && list.size() > 0)
		{
			utype = list.get(0).get("usertype") + "";
		}
		System.out.println("usertype==" + utype);
		return utype;
	}

	/**
	 * @description:获取openId
	 * @param request
	 * @return
	 * @author:duzl
	 * @createTime:2017年4月7日 下午3:09:38
	 */
	public String getOpenIdByAccountId(HttpServletRequest request)
	{
		// 获取请求路径
		String backurl = request.getScheme() + "://" + request.getServerName() + request.getRequestURI() + "?" + request.getQueryString();		// URL配置ID
		// 微信用户 Openid
		String openid = ResourceUtil.getUserOpenId();
		// 跳转出链接
		String outer_link_deal = null;
		// 如果为空则调用author2.0接口
		if (oConvertUtils.isEmpty(openid))
		{
			// 微信公众账号ID
			String accountid = request.getParameter("accountid");

			// update-begin-------author:scott-----------date:20151012--------for:如果连接带参数jwid，则通过jwid原始ID获取公众号信息------------
			// 如果带有参数jwid，标示指定公众号，逻辑如下
			String jwid = request.getParameter("jwid");
			if (oConvertUtils.isNotEmpty(jwid))
			{
				WeixinAccountEntity weixinAccountEntity = weixinAccountService.getWeixinAccountByWeixinOldId(jwid);
				if (weixinAccountEntity != null)
				{
					accountid = weixinAccountEntity.getId();
				}
			}

			outer_link_deal = remoteWeixinMethod.callWeixinAuthor2ReturnUrl(request, accountid, backurl);
		}
		if (oConvertUtils.isEmpty(outer_link_deal))
		{
			openid = ResourceUtil.getUserOpenId();
		}

		return openid;
	}

	/**
	 * @description:根据用户id和类型查询用户姓名
	 * @param userId
	 * @param utype
	 * @return
	 * @author:duzl
	 * @createTime:2017年5月15日 上午11:00:24
	 */
	public String getUserNameByUserId(String userId, String utype)
	{
		String sql = "";
		// 老师
		if ("TEA".equals(utype))
		{
			sql = "SELECT XM FROM SZ_JSJBXX WHERE JSBH = ?";
		}
		else
		{
			sql = "SELECT XM FROM XS_XSJBXX WHERE XH = ?";
		}

		List<Object> sqlValues = new ArrayList<Object>(1);
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

		String xm = "";

		if (rst != null && rst.getRowCount() > 0)
		{
			@SuppressWarnings("unchecked")
			SortedMap<String, Object>[] map = rst.getRows();
			xm = (String) map[0].get("XM");
		}

		return xm;
	}
}
