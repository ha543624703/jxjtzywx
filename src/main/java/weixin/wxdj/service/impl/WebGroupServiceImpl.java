package weixin.wxdj.service.impl;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;

import weixin.jdbc.WebGroupJDBC;
import weixin.wxdj.service.WebGroupService;

@Service("webGroupService")
public class WebGroupServiceImpl extends CommonServiceImpl implements WebGroupService
{
	/**
	 * @description:新闻
	 * @param id
	 * @return
	 * @author:duzl
	 * @createTime:2017年4月14日 下午3:26:10
	 */
	@Override
	public Map<String, Object> getSchoolNews(long id)
	{
		WebGroupJDBC webGroupJDBC = new WebGroupJDBC();

		String sql = "SELECT a.*, REPLACE(b.content,'=\"<!--#p8_attach#-->','=\"http://www.jxhlxy.com.cn/attachment') content FROM p8_cms_item_article_ a INNER JOIN p8_cms_item_article_addon b ON a.id=b.iid WHERE a.id ="
				+ id + " AND cid='34'";

		webGroupJDBC.setSql(sql);

		List<Map<String, Object>> list = webGroupJDBC.executeQuery();

		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}

		return null;
	}

	@Override
	public List<Map<String, Object>> querySchoolNewsList(int page, int pageSize)
	{
		WebGroupJDBC webGroupJDBC = new WebGroupJDBC();

		String sql = "SELECT * FROM p8_cms_item_article_ WHERE cid='34' ORDER BY update_time DESC LIMIT " + page + "," + pageSize;

		webGroupJDBC.setSql(sql);

		return webGroupJDBC.executeQuery();
	}

	@Override
	public int querySchoolNewsCount()
	{
		WebGroupJDBC webGroupJDBC = new WebGroupJDBC();

		String sql = "SELECT COUNT(1) num FROM p8_cms_item_article_ WHERE cid='34'";

		webGroupJDBC.setSql(sql);

		List<Map<String, Object>> list = webGroupJDBC.executeQuery();

		if (list != null && list.size() > 0)
		{
			return ((Long) list.get(0).get("num")).intValue();
		}

		return 0;
	}

	/**
	 * @description:公告
	 * @param id
	 * @return
	 * @author:duzl
	 * @createTime:2017年4月14日 下午3:26:03
	 */
	@Override
	public Map<String, Object> getNotice(long id)
	{
		WebGroupJDBC webGroupJDBC = new WebGroupJDBC();

		String sql = "SELECT a.*, REPLACE(b.content,'=\"<!--#p8_attach#-->','=\"http://www.jxhlxy.com.cn/attachment') content FROM p8_cms_item_article_ a INNER JOIN p8_cms_item_article_addon b ON a.id=b.iid WHERE a.id =" + id
				+ " AND cid='128'";

		webGroupJDBC.setSql(sql);

		List<Map<String, Object>> list = webGroupJDBC.executeQuery();

		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}

		return null;
	}

	@Override
	public List<Map<String, Object>> queryNoticeList(int page, int pageSize)
	{
		WebGroupJDBC webGroupJDBC = new WebGroupJDBC();

		String sql = "SELECT * FROM p8_cms_item_article_ WHERE cid='128' ORDER BY update_time DESC LIMIT " + page + "," + pageSize;

		webGroupJDBC.setSql(sql);

		return webGroupJDBC.executeQuery();
	}

	@Override
	public int queryNoticeCount()
	{
		WebGroupJDBC webGroupJDBC = new WebGroupJDBC();

		String sql = "SELECT COUNT(1) num FROM p8_cms_item_article_ WHERE cid='128'";

		webGroupJDBC.setSql(sql);

		List<Map<String, Object>> list = webGroupJDBC.executeQuery();

		if (list != null && list.size() > 0)
		{
			return ((Long) list.get(0).get("num")).intValue();
		}

		return 0;
	}

	/**
	 * @description:部门动态
	 * @param id
	 * @return
	 * @author:duzl
	 * @createTime:2017年4月14日 下午3:26:18
	 */
	@Override
	public Map<String, Object> getDynamic(long id)
	{
		WebGroupJDBC webGroupJDBC = new WebGroupJDBC();

		String sql = "SELECT a.*, REPLACE(b.content,'=\"<!--#p8_attach#-->','=\"http://www.jxhlxy.com.cn/attachment') content FROM p8_cms_item_article_ a INNER JOIN p8_cms_item_article_addon b ON a.id=b.iid WHERE a.id =" + id
				+ " AND cid='1130'";

		webGroupJDBC.setSql(sql);

		List<Map<String, Object>> list = webGroupJDBC.executeQuery();

		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}

		return null;
	}

	@Override
	public List<Map<String, Object>> queryDynamicList(int page, int pageSize)
	{
		WebGroupJDBC webGroupJDBC = new WebGroupJDBC();

		String sql = "SELECT * FROM p8_cms_item_article_ WHERE cid='1130' ORDER BY update_time DESC LIMIT " + page + "," + pageSize;

		webGroupJDBC.setSql(sql);

		return webGroupJDBC.executeQuery();
	}

	@Override
	public int queryDynamicCount()
	{
		WebGroupJDBC webGroupJDBC = new WebGroupJDBC();

		String sql = "SELECT COUNT(1) num FROM p8_cms_item_article_ WHERE cid='1130'";

		webGroupJDBC.setSql(sql);

		List<Map<String, Object>> list = webGroupJDBC.executeQuery();

		if (list != null && list.size() > 0)
		{
			return ((Long) list.get(0).get("num")).intValue();
		}

		return 0;
	}
}
