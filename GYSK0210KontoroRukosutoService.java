package business.master.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.master.dao.GYSK0210KontoroRukosutoDao;
import business.master.entity.GYSK0210KontoroRukosutoEntity;


/**
 * master管理service
 * @author 
 *
 */
@Service
public class GYSK0210KontoroRukosutoService {

	@Resource
    private GYSK0210KontoroRukosutoDao dao;
    
    /**
     * 取得master全部初期数据
     * @return 取得的得意先数据
     * @throws Exception 
     */
    public List<GYSK0210KontoroRukosutoEntity> selAll() throws Exception  {
        return dao.selAll();
    };
    
    /**
     * 更新数据
     * @param entity
     * @throws Exception 
     */
    public int update(GYSK0210KontoroRukosutoEntity entity) throws Exception  {
        return dao.updateItem(entity);
    };
    
    /**
     * 插入数据
     * @param entity
     * @throws Exception 
     */
    public int insert(GYSK0210KontoroRukosutoEntity entity) throws Exception {
        return dao.insertItem(entity);
    };
    
    /**
     * 删除数据
     * @param entity
     * @throws Exception 
     */
    public void delete(String shayinCd) throws Exception {
        dao.deleteItem(shayinCd);
    };
}
