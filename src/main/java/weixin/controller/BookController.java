package weixin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import weixin.util.GetUserInfoService;
import weixin.wxdj.service.wxUserBindServiceI;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

@Controller
@RequestMapping("/bookController")
public class BookController
{
    @Resource
    private wxUserBindServiceI wxuserBindService;
    @Resource
    private GetUserInfoService getUserInfoService;

    /**
     * 我的图书
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "goBook")
    public String goTeacherKB(HttpServletRequest request, Model model)
    {
        String openId = request.getParameter("openid");
        String userId = getUserInfoService.getUserId(openId);

        int t = wxuserBindService.getUserBindByOpenid(openId);
        if (t <= 0)
        {
            return "redirect:weixinLinksucaiController.do?link&id=4028e4b05c867724015c8a643f1c0002";
        }

        int borrowNum = 0;
        int overTimeNum = 0;

        try
        {
            InputStream fis = this.getClass().getResourceAsStream("/dbsqlserver.properties"); // 加载数据库配置文件到内存中
            Properties p = new Properties();
            p.load(fis);

            String driver_class = p.getProperty("book_driver"); // 获取数据库配置文件
            String driver_url = p.getProperty("book_url");
            String database_user = p.getProperty("book_user");
            String database_password = p.getProperty("book_password");

            Class.forName(driver_class);
            Connection con = DriverManager.getConnection(driver_url.trim(), database_user, database_password.trim());

            String sql = "select 读者条码,应归还时间  from 流通库  where 读者条码= (select 读者条码 from 读者库  where 读者库.ZH='" + userId + "')";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            long nowTime = System.currentTimeMillis();
            // 制转换的时间
            while (rs.next())
            {
                borrowNum++;
                String string = rs.getString("应归还时间");

                long shouldReturnTime = format.parse(string).getTime();

                if (shouldReturnTime < nowTime)
                {
                    borrowNum--;
                    overTimeNum++;
                }
            }
        }
        catch (ClassNotFoundException e1)
        {
            e1.printStackTrace();
        }
        catch (SQLException e2)
        {
            e2.printStackTrace();
        }
        catch (IOException e3)
        {
            e3.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        model.addAttribute("overTimeNum", overTimeNum);
        model.addAttribute("borrowNum", borrowNum);

        return "weixin/book";
    }
}