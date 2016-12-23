package business.master.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import business.master.entity.GYSK0210KontoroRukosutoEntity;

@Repository
public class GYSK0210KontoroRukosutoDaoImpl implements GYSK0210KontoroRukosutoDao{
	
	/** DB操作工具 */
	@Autowired
    private SqlSession sqlSession;
	
	/**
	 * 全件检索
	 */
	@Override
	public List<GYSK0210KontoroRukosutoEntity> selAll() {
		return sqlSession.selectList("business.master.dao.selectAll");
	}

	/**
	 * 更新
	 */
	@Override
	public int updateItem(GYSK0210KontoroRukosutoEntity entity) {
		return sqlSession.update("business.master.dao.updateItem", entity);
		
	}
	
	/**
	 * 追加
	 */
	@Override
	public int insertItem(GYSK0210KontoroRukosutoEntity entity) {
		 return sqlSession.insert("business.master.dao.insertItem", entity);
	}
	
	/**
	 * 删除
	 */
	@Override
	public void deleteItem(String shayinCd) {
		sqlSession.delete("business.master.dao.deleteItem", shayinCd);
	}

}
