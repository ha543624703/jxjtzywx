package weixin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import weixin.jdbc.EducaJDBC;
import weixin.util.GetUserInfoService;
import weixin.wxdj.service.wxUserBindServiceI;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.sql.Result;
import java.util.*;

@Controller
@RequestMapping("/xQFXController")
public class XQFXController
{
    @Resource
    private wxUserBindServiceI wxuserBindService;
    @Resource
    private GetUserInfoService getUserInfoService;

    /**
     * 进入校情分析页
     */
    @RequestMapping(params = "goXQFX")
    public String goTeacherKB(HttpServletRequest request, Model model)
    {
        String openId = request.getParameter("openid");
        int t = wxuserBindService.getUserBindByOpenid(openId);
        if (t <= 0)
        {
            return "redirect:weixinLinksucaiController.do?link&id=4028e4b05c867724015c8a643f1c0002";
        }

        String utype = getUserInfoService.getStuOrTea(openId);

        Result rst = null;

        if ("TEA".equals(utype))
        {
            String sql = "SELECT c.DWJC,b.SZNJ,COUNT(1) 总人数, "
                    + "(SELECT COUNT(1) FROM XS_XSJBXX d INNER JOIN XS_XJJBXX f ON d.XSDM=f.XSDM WHERE f.YXDM=c.DWDM AND f.sznj=b.SZNJ AND d.xbdm='1')男生人数, "
                    + "(SELECT COUNT(1) FROM XS_XSJBXX d INNER JOIN XS_XJJBXX f ON d.XSDM=f.XSDM WHERE f.YXDM=c.DWDM AND f.sznj=b.SZNJ AND d.xbdm='2')女生人数, "
                    + "(SELECT COUNT(1) FROM XS_XSJBXX d INNER JOIN XS_XJJBXX f ON d.XSDM=f.XSDM WHERE f.YXDM=c.DWDM AND f.sznj=b.SZNJ AND d.xbdm='3')性别保密人数 "
                    + "FROM XS_XSJBXX a INNER JOIN XS_XJJBXX b ON a.XSDM=b.XSDM "
                    + "INNER JOIN ZY_YXSDWXX c ON c.DWDM=b.YXDM WHERE b.XSDQZTDM='01' AND b.XJZT='1' "
                    + "GROUP BY b.SZNJ,c.DWJC,c.DWDM ORDER BY c.DWJC,b.SZNJ DESC";

            EducaJDBC dBjdbcCont = new EducaJDBC();
            dBjdbcCont.setSql(sql);

            try
            {
                rst = dBjdbcCont.executeQuery();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if (rst != null && rst.getRowCount() > 0)
        {
            List<Map<String, Object>> data = new ArrayList<>();
            for (int i = 0; i < rst.getRowCount(); i++)
            {
                Map<String, Object> hmap = new HashMap<>(6);

                SortedMap[] map = rst.getRows();
                String DWJC = map[i].get("DWJC") + "";
                String SZNJ = map[i].get("SZNJ") + "";
                String totalNum = map[i].get("总人数") + "";
                String maleNum = map[i].get("男生人数") + "";
                String femaleNum = map[i].get("女生人数") + "";
                String secrecyNum = map[i].get("性别保密人数") + "";

                hmap.put("DWJC", DWJC);
                hmap.put("SZNJ", SZNJ);
                hmap.put("totalNum", totalNum);
                hmap.put("maleNum", maleNum);
                hmap.put("femaleNum", femaleNum);
                hmap.put("secrecyNum", secrecyNum);
                data.add(hmap);
            }

            model.addAttribute("data", data);
        }

        return "weixin/xqfx";
    }
}