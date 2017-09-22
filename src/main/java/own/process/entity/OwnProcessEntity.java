package own.process.entity;

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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import org.jeecgframework.poi.excel.annotation.Excel;

import own.setting.entity.OwnSettingEntity;

/**   
 * @Title: Entity
 * @Description: 报到流程设置
 * @author onlineGenerator
 * @date 2017-03-10 09:49:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "own_Process", schema = "")
@SuppressWarnings("serial")
public class OwnProcessEntity implements java.io.Serializable {
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
	/**流程名称*/
	@Excel(exportName="流程名称")
	private java.lang.String processName;
	/**流程步骤*/
	@Excel(exportName="流程步骤")
	private java.lang.Integer processStep;
	/**前置步骤id*/
	@Excel(exportName="前置步骤id")
	private java.lang.String preId;
	/**前置步骤名称*/
	private java.lang.String preName;
//	/**所属设置*/
//	@Excel(exportName="所属设置")
//	private java.lang.String settingId;
	
	
	private OwnSettingEntity ownSettingEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SETTING_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public OwnSettingEntity getOwnSettingEntity() {
		return ownSettingEntity;
	}
	public void setOwnSettingEntity(OwnSettingEntity ownSettingEntity) {
		this.ownSettingEntity = ownSettingEntity;
	}
	
	
	
	/**状态*/
	@Excel(exportName="状态")
	private java.lang.String status;
	
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
	 *@return: java.lang.String  流程名称
	 */
	@Column(name ="PROCESS_NAME",nullable=true,length=32)
	public java.lang.String getProcessName(){
		return this.processName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  流程名称
	 */
	public void setProcessName(java.lang.String processName){
		this.processName = processName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  流程步骤
	 */
	@Column(name ="PROCESS_STEP",nullable=true,length=32)
	public java.lang.Integer getProcessStep(){
		return this.processStep;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  流程步骤
	 */
	public void setProcessStep(java.lang.Integer processStep){
		this.processStep = processStep;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  前置步骤id
	 */
	@Column(name ="PRE_ID",nullable=true,length=200)
	public java.lang.String getPreId(){
		return this.preId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  前置步骤id
	 */
	public void setPreId(java.lang.String preId){
		this.preId = preId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  前置步骤名称
	 */
	@Column(name ="PRE_NAME",nullable=true,length=200)
	public java.lang.String getPreName(){
		return this.preName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  前置步骤名称
	 */
	public void setPreName(java.lang.String preName){
		this.preName = preName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属设置
	 */
//	@Column(name ="SETTING_ID",nullable=true,length=32)
//	public java.lang.String getSettingId(){
//		return this.settingId;
//	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属设置
	 */
//	public void setSettingId(java.lang.String settingId){
//		this.settingId = settingId;
//	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="STATUS",nullable=true,length=32)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
}
