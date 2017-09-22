package weixin.util;

import weixin.jdbc.EducaJDBC;

import javax.servlet.jsp.jstl.sql.Result;
import java.util.ArrayList;
import java.util.List;

public class DdUtils
{
    /**
     * @param mobile
     * @return
     * @description:钉钉根据电话获取用户ID
     * @author:duzl
     * @createTime:2017年7月17日 下午4:47:01
     */
    public static String getUserId(String mobile)
    {
        String sql = "select * from SZ_JSJBXX where YDDH= ?";

        List<Object> sqlValues = new ArrayList<>(1);
        sqlValues.add(mobile);

        EducaJDBC dBjdbcCont = new EducaJDBC();

        dBjdbcCont.setSql(sql);
        dBjdbcCont.setSqlValues(sqlValues);

        Result rst = null;
        try
        {
            rst = dBjdbcCont.executeQuery();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        String userId = "";

        if (rst != null && rst.getRowCount() > 0)
        {
            userId = rst.getRows()[0].get("JSBH") + "";
        }

        System.out.println("userId:" + userId);
        return userId;
    }
}
