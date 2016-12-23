package business.master.dao;

import java.util.List;

import business.master.entity.GYSK0230StaffEntity;
import business.master.entity.GYSK0230StaffParamenter;

public interface GYSK0230StaffDao {
    /**
     * 检索用户基本信息
     * @return List<constMasterEntity>
     */
    public List<GYSK0230StaffEntity> selAll();

    /**
     * 检索用户基本信息
     * @return List<constMasterEntity>
     */
    public List<GYSK0230StaffEntity> selList(GYSK0230StaffParamenter param);
}
