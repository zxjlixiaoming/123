package business.master.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import business.common.entity.CommonEntity;
import business.master.dao.GYSK0230ShainSentakuDao;
import business.master.entity.GYSK0230ShainSentakuEntity;

@Service
public class GYSK0230ShainSentakuService {

	@Resource
	private GYSK0230ShainSentakuDao dao;
	//无条件初期显示信息和有条件检索
	public List <GYSK0230ShainSentakuEntity> selAllData (GYSK0230ShainSentakuEntity entity){
		
		List<GYSK0230ShainSentakuEntity> dataList=dao.searchAllData(entity);
		return dataList;
	}
	public List <GYSK0230ShainSentakuEntity> selData (GYSK0230ShainSentakuEntity entity){
		
		List<GYSK0230ShainSentakuEntity> dataList=dao.selCheckData(entity);
		return dataList;
	}
	//部门检索
	public List<CommonEntity> getAllDepartment (){
		List<CommonEntity> departmentList = dao.selDepartment();
		return departmentList;
		
	}
	//插入数据
	public void insertData(GYSK0230ShainSentakuEntity entity){
		dao.insertData(entity);		
	}
	//更新数据
	public void updateData(GYSK0230ShainSentakuEntity entity){
		dao.updateData(entity);
	}
	//删除数据
		public void deleteData(GYSK0230ShainSentakuEntity entity){
				dao.deleteData(entity);
		}
		public void deleteData_1(String userId) {
			dao.deleteData_1(userId);
			
		}
}
