package business.master.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import business.master.entity.GYSK0230StaffEntity;
import business.master.entity.GYSK0230StaffParamenter;

@Repository
public class GYSK0230StaffDaoImpl implements GYSK0230StaffDao{
	
	/** DB操作工具 */
	@Autowired
    private SqlSession sqlSession;
	
	/**
	 * 全件检索
	 */
	@Override
	public List<GYSK0230StaffEntity> selAll() {
		return sqlSession.selectList("business.master.dao.selall");
	}
	
	/**
	 * 全件检索
	 */
	@Override
	public List<GYSK0230StaffEntity> selList(GYSK0230StaffParamenter param) {
		return sqlSession.selectList("business.master.dao.sellist",param);
	}
}
