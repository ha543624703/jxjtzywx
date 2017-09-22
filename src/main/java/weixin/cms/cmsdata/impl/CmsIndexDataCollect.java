package weixin.cms.cmsdata.impl;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.util.ApplicationContextUtil;

import weixin.cms.cmsdata.CmsDataCollectI;
import weixin.cms.common.CmsConstant;
import weixin.cms.common.CmsDataContent;
import weixin.cms.entity.AdEntity;
import weixin.cms.entity.CmsMenuEntity;
import weixin.cms.service.AdServiceI;
import weixin.cms.service.CmsMenuServiceI;

/**
 * CMS首页数据收集器
 * @author zhangdaihao
 *
 */
public class CmsIndexDataCollect implements CmsDataCollectI
{
	@Override
	public void collect(Map<String, String> params)
	{
		// 注意其他方法调用只能写在里面
		AdServiceI adService = (AdServiceI) ApplicationContextUtil.getContext().getBean("adService");
		CmsMenuServiceI cmsMenuService = (CmsMenuServiceI) ApplicationContextUtil.getContext().getBean("cmsMenuService");

		List<AdEntity> adList = adService.list(params);
		CmsDataContent.put(CmsConstant.CMS_DATA_LIST_AD, adList);
		List<CmsMenuEntity> menuList = cmsMenuService.list(params);
		CmsDataContent.put(CmsConstant.CMS_DATA_LIST_MENU, menuList);
		String res = CmsConstant.CMS_ROOT_PATH + "/" + params.get(CmsConstant.CMS_STYLE_NAME);
		// 资源路径
		CmsDataContent.put(CmsConstant.BASE, res);

		// 是否绑定，是学生还是老师
		String isLanded = params.get("isLanded");
		if (isLanded != null && !"".equals(isLanded) && !"null".equals(isLanded))
		{
			CmsDataContent.put("isLanded", "isLanded");
			String isTEA = params.get("isTEA");
			if (isTEA != null && !"".equals(isTEA) && !"null".equals(isTEA))
			{
				CmsDataContent.put("isTEA", "TEA");
			}
			else
			{
				CmsDataContent.put("isTEA", "STU");
			}
		}
		else
		{
			CmsDataContent.remove("isLanded");
			CmsDataContent.remove("isTEA");
		}
	}
}
