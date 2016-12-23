package business.master.entity;

import java.util.ArrayList;
import java.util.List;

import business.common.dbentity.M_Const;

public class GYSK0210KontoroRukosutoEntity extends M_Const{
	
	private List<GYSK0210KontoroRukosutoEntity>  list = new ArrayList<GYSK0210KontoroRukosutoEntity>();
	private String shayinCd;
	private String ipmortErrorMsg;


	/**
	 * @return ipmortErrorMsg
	 */
	public String getIpmortErrorMsg() {
		return ipmortErrorMsg;
	}

	/**
	 * @param ipmortErrorMsg セットする ipmortErrorMsg
	 */
	public void setIpmortErrorMsg(String ipmortErrorMsg) {
		this.ipmortErrorMsg = ipmortErrorMsg;
	}

	/**
	 * @return shayinCd
	 */
	public String getShayinCd() {
		return shayinCd;
	}

	/**
	 * @param shayinCd セットする shayinCd
	 */
	public void setShayinCd(String shayinCd) {
		this.shayinCd = shayinCd;
	}

	public List<GYSK0210KontoroRukosutoEntity> getList() {
		return list;
	}
	
	public void setList(List<GYSK0210KontoroRukosutoEntity> list) {
		this.list = list;
	}
	
}
