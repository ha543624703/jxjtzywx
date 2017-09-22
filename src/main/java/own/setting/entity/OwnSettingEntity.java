package own.setting.entity;

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
 * @Description: 自主报名设置
 * @author onlineGenerator
 * @date 2017-03-09 15:46:36
 * @version V1.0   
 *
 */
@Entity
@Table(name = "own_Setting", schema = "")
@SuppressWarnings("serial")
public class OwnSettingEntity implements java.io.Serializable {
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
	/**报名年级*/
	@Excel(exportName="报名年级")
	private java.lang.String year;
	/**报名开始日期*/
	@Excel(exportName="报名开始日期")
	private java.util.Date bmStartDate;
	/**报名结束日期*/
	@Excel(exportName="报名结束日期")
	private java.util.Date bmEndDate;
	/**预分班模式*/
	@Excel(exportName="预分班模式")
	private java.lang.String proClassMode;
	/**预分宿舍模式*/
	@Excel(exportName="预分宿舍模式")
	private java.lang.String proDormitoryMode;
	/**是否启用自助报名*/
	@Excel(exportName="是否启用自助报名")
	private java.lang.String enableBm;
	
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
	 *@return: java.lang.String  报名年级
	 */
	@Column(name ="YEAR",nullable=false,length=32)
	public java.lang.String getYear(){
		return this.year;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报名年级
	 */
	public void setYear(java.lang.String year){
		this.year = year;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  报名开始日期
	 */
	@Column(name ="BM_START_DATE",nullable=false,length=32)
	public java.util.Date getBmStartDate(){
		return this.bmStartDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  报名开始日期
	 */
	public void setBmStartDate(java.util.Date bmStartDate){
		this.bmStartDate = bmStartDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  报名结束日期
	 */
	@Column(name ="BM_END_DATE",nullable=false,length=32)
	public java.util.Date getBmEndDate(){
		return this.bmEndDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  报名结束日期
	 */
	public void setBmEndDate(java.util.Date bmEndDate){
		this.bmEndDate = bmEndDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预分班模式
	 */
	@Column(name ="PRO_CLASS_MODE",nullable=false,length=32)
	public java.lang.String getProClassMode(){
		return this.proClassMode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预分班模式
	 */
	public void setProClassMode(java.lang.String proClassMode){
		this.proClassMode = proClassMode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预分宿舍模式
	 */
	@Column(name ="PRO_DORMITORY_MODE",nullable=false,length=32)
	public java.lang.String getProDormitoryMode(){
		return this.proDormitoryMode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预分宿舍模式
	 */
	public void setProDormitoryMode(java.lang.String proDormitoryMode){
		this.proDormitoryMode = proDormitoryMode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否启用自助报名
	 */
	@Column(name ="ENABLE_BM",nullable=false,length=32)
	public java.lang.String getEnableBm(){
		return this.enableBm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否启用自助报名
	 */
	public void setEnableBm(java.lang.String enableBm){
		this.enableBm = enableBm;
	}
}
