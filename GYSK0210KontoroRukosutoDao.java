package business.master.dao;

import java.util.List;

import business.master.entity.GYSK0210KontoroRukosutoEntity;

public interface GYSK0210KontoroRukosutoDao {
	/**
     * 检索用户基本信息
     * @return List<constMasterEntity>
     */
	public List<GYSK0210KontoroRukosutoEntity> selAll();
	
	/**
     * 更新用户基本信息
     * @param entity
     */
	public int updateItem(GYSK0210KontoroRukosutoEntity entity);
	
	/**
     * 新建用户基本信息
     * @param entity
     */
	public int insertItem(GYSK0210KontoroRukosutoEntity entity);
	
	/**
     * 删除用户基本信息
     * @param entity
     */
	public void deleteItem(String shayinCd);
}
