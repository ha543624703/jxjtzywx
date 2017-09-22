package own.setting.service.impl;
import own.setting.service.OwnSettingServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import own.setting.entity.OwnSettingEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("ownSettingService")
@Transactional
public class OwnSettingServiceImpl extends CommonServiceImpl implements OwnSettingServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((OwnSettingEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((OwnSettingEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((OwnSettingEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(OwnSettingEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(OwnSettingEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(OwnSettingEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,OwnSettingEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{year}",String.valueOf(t.getYear()));
 		sql  = sql.replace("#{bm_start_date}",String.valueOf(t.getBmStartDate()));
 		sql  = sql.replace("#{bm_end_date}",String.valueOf(t.getBmEndDate()));
 		sql  = sql.replace("#{pro_class_mode}",String.valueOf(t.getProClassMode()));
 		sql  = sql.replace("#{pro_dormitory_mode}",String.valueOf(t.getProDormitoryMode()));
 		sql  = sql.replace("#{enable_bm}",String.valueOf(t.getEnableBm()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}