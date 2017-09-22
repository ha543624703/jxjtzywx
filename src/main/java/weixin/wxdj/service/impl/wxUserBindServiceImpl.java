package weixin.wxdj.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weixin.entity.RepairPresentation;
import weixin.wxdj.service.wxUserBindServiceI;

@Service("wxUserBindService")
@Transactional
public class wxUserBindServiceImpl extends CommonServiceImpl implements wxUserBindServiceI
{
	@Override
	public int insertUserBind(String openid, String userid, String accountid, String userType)
	{
		String randomSeed = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

		String sql = "INSERT INTO weixin_userbind (id,userid,openid,accountid,createdate,usertype) value('" + randomSeed + "','" + userid + "','"
				+ openid + "','" + accountid + "',now(),'" + userType + "')";
		int t = this.executeSql(sql);
		return t;
	}

	@Override
	public int getUserBindByOpenid(String openid)
	{
		String sql = "SELECT * FROM weixin_userbind WHERE OPENID='" + openid + "'";
		List<Map<String, Object>> list = this.findForJdbc(sql);
		if (list != null && list.size() > 0)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	@Override
	public List<Map<String, Object>> getUserWxList(String openid)
	{
		String sql = "SELECT * FROM weixin_userbind WHERE OPENID='" + openid + "'";
		List<Map<String, Object>> list = this.findForJdbc(sql);
		return list;
	}

	@Override
	public int removeBindByOpenid(String openid)
	{
		String sql = "DELETE FROM weixin_userbind WHERE OPENID='" + openid + "'";
		int t = this.executeSql(sql);
		return t;
	}

	@Override
	public int insertAutograph(String openid, String userid, String accountid)
	{
		long now = System.currentTimeMillis();
		String sql = "INSERT INTO class_autograph (userid,openid,accountid,createtime) value('" + userid + "','" + openid + "','" + accountid + "',"
				+ now + ")";
		int t = this.executeSql(sql);
		return t;
	}

	@Override
	public int insertArticleMessage(String userid, String username, String articleid, String message)
	{
		String sql = "INSERT INTO article_message (userid,username,articleid,message,create_time) value('" + userid + "','" + username + "','"
				+ articleid + "','" + message + "',now())";
		int t = this.executeSql(sql);
		return t;
	}

	@Override
	public List<Map<String, Object>> getArticleMessage(long articleid)
	{
		String sql = "SELECT * FROM article_message WHERE articleid='" + articleid + "' ORDER BY id DESC";
		List<Map<String, Object>> list = this.findForJdbc(sql);
		return list;
	}

	@Override
	public int insertScheduleMana(String userid, String username, String message, long workTime)
	{
		long now = System.currentTimeMillis();
		String sql = "INSERT INTO schedule_management (userid,username,message,work_time,create_time) value('" + userid + "','" + username + "','"
				+ message + "'," + workTime + "," + now + ")";
		int t = this.executeSql(sql);
		return t;
	}

	@Override
	public List<Map<String, Object>> queryScheduleMana(String userId)
	{
		String sql = "SELECT * FROM schedule_management WHERE userid='" + userId + "' ORDER BY work_time DESC";
		List<Map<String, Object>> list = this.findForJdbc(sql);
		return list;
	}

	@Override
	public int insertRepairPresentation(RepairPresentation repairPresentation)
	{
		String sql = "INSERT INTO repair_presentation (classno,userid,name,phone,place,buildingno,buildingdescribe,hydropower,furniture"
				+ ",other,explaindetail,picture,repairtime,createtime,repairacceptance,repairedacceptance,status) values ('"
				+ repairPresentation.getClassno()
				+ "','"
				+ repairPresentation.getUserid()
				+ "','"
				+ repairPresentation.getName()
				+ "','"
				+ repairPresentation.getPhone()
				+ "','"
				+ repairPresentation.getPlace()
				+ "','"
				+ repairPresentation.getBuildingno()
				+ "','"
				+ repairPresentation.getBuildingdescribe()
				+ "','"
				+ repairPresentation.getHydropower()
				+ "','"
				+ repairPresentation.getFurniture()
				+ "','"
				+ repairPresentation.getOther()
				+ "','"
				+ repairPresentation.getExplaindetail()
				+ "','"
				+ repairPresentation.getPicture()
				+ "','"
				+ repairPresentation.getRepairtime()
				+ "',now(),'"
				+ repairPresentation.getRepairacceptance()
				+ "','"
				+ repairPresentation.getRepairedacceptance() + "'," + repairPresentation.getStatus() + ")";
		int t = this.executeSql(sql);
		return t;
	}

	@Override
	public List<Map<String, Object>> queryRepairList(String userId, int status)
	{
		String sql = "SELECT * FROM repair_presentation WHERE status=" + status + " ORDER BY id DESC";

		if (status < 0)
		{
			sql = "SELECT * FROM repair_presentation ORDER BY id DESC";
		}
		List<Map<String, Object>> list = this.findForJdbc(sql);
		return list;
	}

	@Override
	public Map<String, Object> getRepair(int id)
	{
		String sql = "SELECT * FROM repair_presentation WHERE id=" + id;
		List<Map<String, Object>> list = this.findForJdbc(sql);

		if (list != null && list.size() > 0)
		{
			return list.get(0);
		}
		return null;
	}

	@Override
	public int setRepairAudited(int id, int status)
	{
		String sql = "UPDATE repair_presentation SET status=" + status + " WHERE id=" + id;
		int t = this.executeSql(sql);
		return t;
	}
}