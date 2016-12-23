package business.master.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.common.entity.CommonEntity;
import business.master.dao.GYSK0220OrderDao;
import business.master.entity.GYSK0220OrderEntity;


/**
 * オーダー選択Service
 */
@Service
public class GYSK0220OrderService {

    @Resource
    private GYSK0220OrderDao dao;
    
    /**
     * 取得オーダー全部初期数据
     * @return 取得的得意先数据
     * @throws Exception 
     */
    public List<GYSK0220OrderEntity> selAll(GYSK0220OrderEntity entity) throws Exception  {
        List<GYSK0220OrderEntity> result =  dao.selAll(entity);
        return result;
    };
    
     /**
     * 取得部门名称，CD
     * @param entity
     * @return
     */
    public List<CommonEntity> getDepartMent() {
        List<CommonEntity> result = dao.selDepartment();
        return result;
    }
}
