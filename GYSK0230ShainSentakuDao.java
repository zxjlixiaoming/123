package business.master.dao;

import java.util.List;

import business.common.entity.CommonEntity;
import business.master.entity.GYSK0230ShainSentakuEntity;

public interface GYSK0230ShainSentakuDao {
	//无条件初期显示信息和有条件检索
	public List<GYSK0230ShainSentakuEntity> searchAllData (GYSK0230ShainSentakuEntity entity);
	
	//public List<GYSK0230ShainSentakuEntity> searchAllData (GYSK0230ShainSentakuEntity entity) throws Exception;
	
	public List<GYSK0230ShainSentakuEntity> selCheckData (GYSK0230ShainSentakuEntity entity);
	//部门检索
	public List<CommonEntity> selDepartment ();
	//插入数据
	public void insertData(GYSK0230ShainSentakuEntity entity);
	//更新数据
	public void updateData(GYSK0230ShainSentakuEntity entity);
	//删除数据
	public void deleteData(GYSK0230ShainSentakuEntity entity);
	public void deleteData_1(String userId);
}
