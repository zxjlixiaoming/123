package business.master.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import business.common.entity.CommonEntity;
import business.master.entity.GYSK0220OrderEntity;
/**
 * オーダー選択DaoImpl
 */
@Repository
public class GYSK0220OrderDaoImpl implements GYSK0220OrderDao{
    
    /** DB操作工具 */
    @Autowired
    private SqlSession sqlSession;
    
    /**
     * 全件检索
     */
    @Override
    public List<GYSK0220OrderEntity> selAll(GYSK0220OrderEntity entity) {
        return sqlSession.selectList("business.master.dao.selOrderAll", entity);
    }
    /**
     * 部门检索
     */
    @Override
    public List<CommonEntity> selDepartment() {
        return sqlSession.selectList("business.master.dao.selDepartment");
    }

}
