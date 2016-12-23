package business.master.entity;

import java.util.ArrayList;
import java.util.List;

import business.common.dbentity.MS_ORDER;
/**
 * オーダー選択Entity
 */
public class GYSK0220OrderEntity extends MS_ORDER{

    /**
     * name
     */
    public String name;
    
    /**
     * name
     */
    public String departmentNM;
    
    
    /**
     * txtDepartmentCD
     */
    public String txtDepartmentCD;
    
    /**
     * txtDepartmentNM
     */
    public String txtDepartmentNM;
    
    /**
     * txtOrderNM
     */
    public String txtOrderNM;
    
    /**
     * txtOrderCD
     */
    public String txtOrderCD;
    
    /**
     * name
     */
    public String departmentCD;
    
    /**
     * name
     */
    public String counts;
    /**
     * @return counts
     */
    public String getCounts() {
        return counts;
    }

    /**
     * @param counts セットする counts
     */
    public void setCount(String counts) {
        this.counts = counts;
    }

    /**
     * @return txtDepartmentCD
     */
    public String getTxtDepartmentCD() {
        return txtDepartmentCD;
    }

    /**
     * @param txtDepartmentCD セットする txtDepartmentCD
     */
    public void setTxtDepartmentCD(String txtDepartmentCD) {
        this.txtDepartmentCD = txtDepartmentCD;
    }

    /**
     * @return txtDepartmentNM
     */
    public String getTxtDepartmentNM() {
        return txtDepartmentNM;
    }

    /**
     * @param txtDepartmentNM セットする txtDepartmentNM
     */
    public void setTxtDepartmentNM(String txtDepartmentNM) {
        this.txtDepartmentNM = txtDepartmentNM;
    }

    /**
     * @return txtOrderNM
     */
    public String getTxtOrderNM() {
        return txtOrderNM;
    }

    /**
     * @param txtOrderNM セットする txtOrderNM
     */
    public void setTxtOrderNM(String txtOrderNM) {
        this.txtOrderNM = txtOrderNM;
    }

    /**
     * @return txtOrderCD
     */
    public String getTxtOrderCD() {
        return txtOrderCD;
    }

    /**
     * @param txtOrderCD セットする txtOrderCD
     */
    public void setTxtOrderCD(String txtOrderCD) {
        this.txtOrderCD = txtOrderCD;
    }

    /**
     * @return departmentCD
     */
    public String getDepartmentCD() {
        return departmentCD;
    }

    /**
     * @param departmentCD セットする departmentCD
     */
    public void setDepartmentCD(String departmentCD) {
        this.departmentCD = departmentCD;
    }
    /**
     * @return departmentNM
     */
    public String getDepartmentNM() {
        return departmentNM;
    }

    /**
     * @param departmentNM セットする departmentNM
     */
    public void setDepartmentNM(String departmentNM) {
        this.departmentNM = departmentNM;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name セットする name
     */
    public void setName(String name) {
        this.name = name;
    }

    private List<GYSK0220OrderEntity>  list = new ArrayList<GYSK0220OrderEntity>();
    
    public List<GYSK0220OrderEntity> getList() {
        return list;
    }
    
    public void setList(List<GYSK0220OrderEntity> list) {
        this.list = list;
    }
}
