package business.master.entity;

import java.util.ArrayList;
import java.util.List;

import business.common.dbentity.DB_GYSK0230ShainSentakuEntity;

public class GYSK0230ShainSentakuEntity extends DB_GYSK0230ShainSentakuEntity {
	
    public List<GYSK0230ShainSentakuEntity> list = new ArrayList<GYSK0230ShainSentakuEntity>();
	private String userId;
	private String userName;
	private String department;
	private String position;
	private String userFirstName;
	private String userLastName;
	
	public GYSK0230ShainSentakuEntity() {
		super();
	}
	public GYSK0230ShainSentakuEntity(String userId, String userName,
			String department, String position) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.department = department;
		this.position = position;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public List<GYSK0230ShainSentakuEntity> getList() {
		return list;
	}
	public void setList(List<GYSK0230ShainSentakuEntity> list) {
		this.list = list;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	
}
