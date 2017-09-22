package weixin.wxdj.service;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import weixin.entity.RepairPresentation;

public interface wxUserBindServiceI extends CommonService
{
	/*
	 *根据Openid与用户ID进行数据绑定 *
	 */
	int insertUserBind(String openid, String userid, String accountid, String userType);

	/*
	 * 根据openid进行判断是否已经绑定
	 */
	int getUserBindByOpenid(String openid);

	/*
	 *解除绑定 (根据用户微信公众号)
	 */
	int removeBindByOpenid(String openid);

	// 根据openid获取学生信息
	List<Map<String, Object>> getUserWxList(String openid);

	/**
	 * @description:签到
	 * @return
	 * @author:duzl
	 * @createTime:2017年4月8日 下午3:46:08
	 */
	int insertAutograph(String openid, String userid, String accountid);

	/**
	 * @description:文章留言
	 * @param userid
	 * @param username
	 * @param articleid
	 * @param message
	 * @return
	 * @author:duzl
	 * @createTime:2017年5月8日 下午4:12:45
	 */
	int insertArticleMessage(String userid, String username, String articleid, String message);

	/**
	 * @description:查询文章留言
	 * @param articleid
	 * @return
	 * @author:duzl
	 * @createTime:2017年5月8日 下午5:34:09
	 */
	List<Map<String, Object>> getArticleMessage(long articleid);

	/**
	 * @description:新增日程管理
	 * @param userid
	 * @param username
	 * @param message
	 * @return
	 * @author:duzl
	 * @createTime:2017年5月15日 上午10:41:30
	 */
	int insertScheduleMana(String userid, String username, String message, long workTime);

	/**
	 * @description:查询日程管理
	 * @param userId
	 * @return
	 * @author:duzl
	 * @createTime:2017年5月15日 上午10:42:29
	 */
	List<Map<String, Object>> queryScheduleMana(String userId);

	/**
	 * @description:新增报修
	 * @param repairPresentation
	 * @return
	 * @author:duzl
	 * @createTime:2017年6月8日 上午8:41:22
	 */
	int insertRepairPresentation(RepairPresentation repairPresentation);

	/**
	 * @description:查询报修列表
	 * @param userId
	 * @param status 状态-1查全部
	 * @return
	 * @author:钢银商城交易项目组 杜章亮
	 * @createTime:2017年6月22日 下午3:44:02
	 */
	List<Map<String, Object>> queryRepairList(String userId, int status);

	/**
	 * @description:查询报修
	 * @param id
	 * @return
	 * @author:钢银商城交易项目组 杜章亮
	 * @createTime:2017年6月22日 下午4:06:20
	 */
	Map<String, Object> getRepair(int id);

	/**
	 * @description:审核报修
	 * @param id
	 * @param status
	 * @return
	 * @author:云联高科 杜章亮
	 * @createTime:2017年6月22日 下午4:19:35
	 */
	int setRepairAudited(int id, int status);
}
