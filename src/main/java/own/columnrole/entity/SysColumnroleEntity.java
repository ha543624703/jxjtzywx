package own.columnrole.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 栏目权限
 * @author onlineGenerator
 * @date 2017-03-18 10:20:34
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sys_columnrole", schema = "")
@SuppressWarnings("serial")
public class SysColumnroleEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建日期*/
	private java.util.Date createDate;
	/**修改人名称*/
	private java.lang.String updateName;
	/**修改日期*/
	private java.util.Date updateDate;
	/**角色id*/
	@Excel(exportName="角色id")
	private java.lang.String roleId;
	/**角色名称*/
	@Excel(exportName="角色名称")
	private java.lang.String roleName;
	/**栏目名称*/
	@Excel(exportName="栏目名称")
	private java.lang.String columnName;
	/**栏目id*/
	@Excel(exportName="栏目id")
	private java.lang.String columnId;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  角色id
	 */
	@Column(name ="ROLE_ID",nullable=true,length=32)
	public java.lang.String getRoleId(){
		return this.roleId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  角色id
	 */
	public void setRoleId(java.lang.String roleId){
		this.roleId = roleId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  角色名称
	 */
	@Column(name ="ROLE_NAME",nullable=true,length=50)
	public java.lang.String getRoleName(){
		return this.roleName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  角色名称
	 */
	public void setRoleName(java.lang.String roleName){
		this.roleName = roleName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  栏目名称
	 */
	@Column(name ="COLUMN_NAME",nullable=true,length=50)
	public java.lang.String getColumnName(){
		return this.columnName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  栏目名称
	 */
	public void setColumnName(java.lang.String columnName){
		this.columnName = columnName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  栏目id
	 */
	@Column(name ="COLUMN_ID",nullable=true,length=32)
	public java.lang.String getColumnId(){
		return this.columnId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  栏目id
	 */
	public void setColumnId(java.lang.String columnId){
		this.columnId = columnId;
	}
}
