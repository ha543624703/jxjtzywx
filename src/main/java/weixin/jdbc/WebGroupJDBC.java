package weixin.jdbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class WebGroupJDBC
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

	public WebGroupJDBC()
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

			driver_class = p.getProperty("webgroup_driver"); // 获取数据库配置文件
			driver_url = p.getProperty("webgroup_dburl");
			database_user = p.getProperty("webgroup_user");
			database_password = p.getProperty("webgroup_password");

			Class.forName(driver_class);
			con = DriverManager.getConnection(driver_url.trim(), database_user, database_password.trim());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
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
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		if (pst != null)
		{
			try
			{
				pst.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		if (con != null)
		{
			try
			{
				con.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查找
	 * @param sql
	 * @param sqlValues
	 * @return
	 */
	public List<Map<String, Object>> executeQuery()
	{
		List<Map<String, Object>> list = null;
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
			if (rst != null)
			{
				list = new ArrayList<Map<String, Object>>();
				ResultSetMetaData md = rst.getMetaData();
				int columnCount = md.getColumnCount();
				while (rst.next())
				{
					Map<String, Object> rowData = new HashMap<String, Object>();
					for (int i = 1; i <= columnCount; i++)
					{
						rowData.put(md.getColumnName(i), rst.getObject(i));
					}

					list.add(rowData);
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.closeAll(con, pst, rst);
		}

		return list;
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
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
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
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}
