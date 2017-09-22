package own.processcontent.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import own.process.entity.OwnProcessEntity;

/**
 * @Title: Entity
 * @Description: 流程挂接内容
 * @author onlineGenerator
 * @date 2017-03-10 09:50:11
 * @version V1.0
 *
 */
@Entity
@Table(name = "own_Process_Content", schema = "")
@SuppressWarnings("serial")
public class OwnProcessContentEntity implements java.io.Serializable {
	/** 主键 */
	private java.lang.String id;
	/** 创建人名称 */
	private java.lang.String createName;
	/** 创建日期 */
	private java.util.Date createDate;
	/** 修改人名称 */
	private java.lang.String updateName;
	/** 修改日期 */
	private java.util.Date updateDate;
	/** 流程主表 */
//	@Excel(exportName = "流程主表")
//	private java.lang.String processId;

	private OwnProcessEntity ownProcessEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROCESS_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public OwnProcessEntity getOwnProcessEntity() {
		return ownProcessEntity;
	}
	public void setOwnProcessEntity(OwnProcessEntity ownProcessEntity) {
		this.ownProcessEntity = ownProcessEntity;
	}
	/** 流程内容 */
	@Excel(exportName = "流程内容")
	private java.lang.String content;
	/** 状态 */
	@Excel(exportName = "状态")
	private java.lang.String status;

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 36)
	public java.lang.String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             主键
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 创建人名称
	 */
	@Column(name = "CREATE_NAME", nullable = true, length = 50)
	public java.lang.String getCreateName() {
		return this.createName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             创建人名称
	 */
	public void setCreateName(java.lang.String createName) {
		this.createName = createName;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 创建日期
	 */
	@Column(name = "CREATE_DATE", nullable = true, length = 20)
	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date
	 *             创建日期
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 修改人名称
	 */
	@Column(name = "UPDATE_NAME", nullable = true, length = 50)
	public java.lang.String getUpdateName() {
		return this.updateName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             修改人名称
	 */
	public void setUpdateName(java.lang.String updateName) {
		this.updateName = updateName;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 修改日期
	 */
	@Column(name = "UPDATE_DATE", nullable = true, length = 20)
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date
	 *             修改日期
	 */
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 流程主表
	 */
//	@Column(name = "PROCESS_ID", nullable = true, length = 32)
//	public java.lang.String getProcessId() {
//		return this.processId;
//	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             流程主表
	 */
//	public void setProcessId(java.lang.String processId) {
//		this.processId = processId;
//	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 流程内容
	 */
	@Column(name = "CONTENT", nullable = true, length = 32)
	public java.lang.String getContent() {
		return this.content;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             流程内容
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 状态
	 */
	@Column(name = "STATUS", nullable = true, length = 32)
	public java.lang.String getStatus() {
		return this.status;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             状态
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
}
