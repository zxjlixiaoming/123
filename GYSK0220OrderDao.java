package business.master.dao;

import java.util.List;

import business.common.entity.CommonEntity;
import business.master.entity.GYSK0220OrderEntity;

/**
 * オーダー選択Dao
 */
public interface GYSK0220OrderDao {
    /**
     * 检索用户基本信息
     * @return List<constMasterEntity>
     */
    public List<GYSK0220OrderEntity> selAll(GYSK0220OrderEntity entity);
    
    /**
     * 检索 部门信息
     * @param entity
     */
    public List<CommonEntity> selDepartment();
    
    
}
