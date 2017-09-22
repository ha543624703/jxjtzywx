package own.militaryinfo.entity;

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
 * @Description: 军训服信息
 * @author onlineGenerator
 * @date 2017-03-17 14:55:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "own_militaryinfo", schema = "")
@SuppressWarnings("serial")
public class OwnMilitaryinfoEntity implements java.io.Serializable {
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
	/**学生编号*/
	@Excel(exportName="学生编号")
	private java.lang.String studentid;
	/**标题*/
	@Excel(exportName="标题")
	private java.lang.String title;
	/**答案*/
	@Excel(exportName="答案")
	private java.lang.String answer;
	/**隶属设置*/
	@Excel(exportName="隶属设置")
	private java.lang.String settingid;
	
	
	private java.lang.String settingname;
	
	//设置名称
	@Column(name ="SETTINGNAME",nullable=true,length=50)
	public java.lang.String getSettingname() {
		return settingname;
	}

	public void setSettingname(java.lang.String settingname) {
		this.settingname = settingname;
	}
	
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
	 *@return: java.lang.String  学生编号
	 */
	@Column(name ="STUDENTID",nullable=true,length=32)
	public java.lang.String getStudentid(){
		return this.studentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  学生编号
	 */
	public void setStudentid(java.lang.String studentid){
		this.studentid = studentid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=true,length=200)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  答案
	 */
	@Column(name ="ANSWER",nullable=true,length=200)
	public java.lang.String getAnswer(){
		return this.answer;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  答案
	 */
	public void setAnswer(java.lang.String answer){
		this.answer = answer;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  隶属设置
	 */
	@Column(name ="SETTINGID",nullable=true,length=32)
	public java.lang.String getSettingid(){
		return this.settingid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  隶属设置
	 */
	public void setSettingid(java.lang.String settingid){
		this.settingid = settingid;
	}
}
