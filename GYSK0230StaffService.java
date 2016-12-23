package business.master.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.master.dao.GYSK0230StaffDao;
import business.master.entity.GYSK0230StaffEntity;
import business.master.entity.GYSK0230StaffParamenter;

/**
 * master管理service
 * @author 
 *
 */
@Service
public class GYSK0230StaffService {
	
	@Resource
    private GYSK0230StaffDao dao;
    
    /**
     * 取得master全部初期数据
     * @return 取得的得意先数据
     * @throws Exception 
     */
    public List<GYSK0230StaffEntity> selAll() throws Exception  {
        return dao.selAll();
    };
    
    /**
     * 取得master全部初期数据
     * @return 取得的得意先数据
     * @throws Exception 
     */
    public List<GYSK0230StaffEntity> selList(GYSK0230StaffParamenter param) throws Exception  {
        return dao.selList(param);
    };
}
