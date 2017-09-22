package weixin.jdbc;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class EducaJDBC
{
    private String sql; // 要传入的sql语句
    private List<Object> sqlValues; // sql语句的参数
    private Connection con; // 连接对象

    public void setSql(String sql)
    {
        this.sql = sql;
    }

    public void setSqlValues(List<Object> sqlValues)
    {
        this.sqlValues = sqlValues;
    }

    public void setCon(Connection con)
    {
        this.con = con;
    }

    public EducaJDBC()
    {
        this.con = this.getConnection();
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    private Connection getConnection()
    {
        String driver_class = null;
        String driver_url = null;
        String database_user = null;
        String database_password = null;
        try
        {
            InputStream fis = this.getClass().getResourceAsStream("/dbsqlserver.properties"); // 加载数据库配置文件到内存中
            Properties p = new Properties();
            p.load(fis);

            driver_class = p.getProperty("driver_class"); // 获取数据库配置文件
            driver_url = p.getProperty("driver_url");
            database_user = p.getProperty("database_user");
            database_password = p.getProperty("database_password");

            Class.forName(driver_class);
            con = DriverManager.getConnection(driver_url.trim(), database_user, database_password.trim());
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * 关闭数据库
     *
     * @param con
     * @param pst
     * @param rst
     */
    private void closeAll(Connection con, PreparedStatement pst, ResultSet rst)
    {
        if (rst != null)
        {
            try
            {
                rst.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        if (pst != null)
        {
            try
            {
                pst.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        if (con != null)
        {
            try
            {
                con.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查找
     *
     * @return
     */
    public Result executeQuery()
    {
        Result result = null;
        ResultSet rst = null;
        PreparedStatement pst = null;
        try
        {
            pst = con.prepareStatement(sql);
            if (sqlValues != null && sqlValues.size() > 0)
            {
                // 当sql语句中存在占位符时
                setSqlValues(pst, sqlValues);
            }
            rst = pst.executeQuery();
            result = ResultSupport.toResult(rst); // 一定要在关闭数据库之前完成转换
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            this.closeAll(con, pst, rst);
        }

        return result;
    }

    /**
     * 增删改
     *
     * @return
     */
    public int executeUpdate()
    {
        int result = -1;
        PreparedStatement pst = null;
        try
        {
            pst = con.prepareStatement(sql);
            if (sqlValues != null && sqlValues.size() > 0)
            { // 当sql语句中存在占位符时
                setSqlValues(pst, sqlValues);
            }
            result = pst.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            this.closeAll(con, pst, null);
        }

        return result;
    }

    /**
     * 给sql语句中的占位符赋值
     *
     * @param pst
     * @param sqlValues
     */
    private void setSqlValues(PreparedStatement pst, List<Object> sqlValues)
    {
        for (int i = 0; i < sqlValues.size(); i++)
        {
            try
            {
                pst.setObject(i + 1, sqlValues.get(i));
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
