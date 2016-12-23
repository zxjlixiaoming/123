package business.master.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import business.common.entity.CommonEntity;
import business.master.entity.GYSK0230ShainSentakuEntity;
@Repository
public class GYSK0230ShainSentakuDaoImpl implements GYSK0230ShainSentakuDao{

	@Autowired
	private SqlSession  sqlSession;
	
	@Override
	//无条件初期显示信息
	public List<GYSK0230ShainSentakuEntity> searchAllData (GYSK0230ShainSentakuEntity entity) {
		return sqlSession.selectList(
				"business.master.dao.searchAllData", entity);
	}

	public List<GYSK0230ShainSentakuEntity> selCheckData (GYSK0230ShainSentakuEntity entity) {
		return sqlSession.selectList(
				"business.master.dao.searchData", entity);
	}
	//部门检索
	@Override
	public List<CommonEntity> selDepartment(){
		return sqlSession.selectList("business.master.dao.selAllDepartment"
				);
		
	};
	//插入数据
	@Override
	public void insertData(GYSK0230ShainSentakuEntity entity){
	 sqlSession.insert("business.master.dao.insertData", entity
				);
		
	}
	//更新数据
	@Override
	public void updateData(GYSK0230ShainSentakuEntity entity){
		sqlSession.update("business.master.dao.updateData", entity
				);
	}
	//删除数据
	@Override
	public void deleteData(GYSK0230ShainSentakuEntity entity){
		sqlSession.delete("business.master.dao.deleteData", entity
				);
	}
	//删除数据
	@Override
	public void deleteData_1(String userId){
		sqlSession.delete("business.master.dao.deleteData",userId
					);
		}
}
