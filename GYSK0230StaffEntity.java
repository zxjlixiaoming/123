package business.master.entity;

import business.common.dbentity.M_Const;

public class GYSK0230StaffEntity extends M_Const{
    private String num;
    private String kanri_bangou;
    private String jyugyoin_bangou;
    private String mibunsyomeisyo_bangou;
    private String  sei;
    private String  mei;
    private String  zkey;
    private String  seij;
    private String  meij;
    private String  name;
    private String  sei_pin;
    private String  mei_pin;
    private String  seij_pin;
    private String  meij_pin;
    private String  seinengappi;
    private String  nyuusya_nengappi;
    private String  seisyain_nengappi;
    private String  syukkoumoto;
    private String  departmentcd;
    private String  departmentstartdate;
    private String  departmentenddate;
    private String  departmentnm;
    private String  genyakusyoku;
    private String  positionlevelcd;
    private String  noryokukeisu;
    private int  flag;
    private String  ginkou_mei;
    private String  siten_mei;
    private String  kouza_code;
    private String  pasuwa_Do;
    private String  kengen;
    private String  jyusyo;
    private String  koseki;
    private String  tanann;
    private String  naisen1;
    private String  naisen2;
    private String  e_mail;
    private String  tokucho;
    private String  tokuchoj;
    private String  photo_path;
    private String  delflg;
    private String  createuser;
    private String  createtime;
    private String  updateuser;
    private String  updatetime;
    private String  c1;
    private String  c2;
    private String  c3;
    private String  c4;
    private String  c5;

    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public String getDepartmentnm() {
        return departmentnm;
    }
    public void setDepartmentnm(String departmentnm) {
        this.departmentnm = departmentnm;
    }
    public String getKanri_bangou() {
        return kanri_bangou;
    }
    public void setKanri_bangou(String kanri_bangou) {
        this.kanri_bangou = kanri_bangou;
    }
    public String getJyugyoin_bangou() {
        return jyugyoin_bangou;
    }
    public void setJyugyoin_bangou(String jyugyoin_bangou) {
        this.jyugyoin_bangou = jyugyoin_bangou;
    }
    public String getMibunsyomeisyo_bangou() {
        return mibunsyomeisyo_bangou;
    }
    public void setMibunsyomeisyo_bangou(String mibunsyomeisyo_bangou) {
        this.mibunsyomeisyo_bangou = mibunsyomeisyo_bangou;
    }
    public String getSei() {
        return sei;
    }
    public void setSei(String sei) {
        this.sei = sei;
    }
    public String getMei() {
        return mei;
    }
    public void setMei(String mei) {
        this.mei = mei;
    }
    public String getSeij() {
        return seij;
    }
    public void setSeij(String seij) {
        this.seij = seij;
    }
    public String getMeij() {
        return meij;
    }
    public void setMeij(String meij) {
        this.meij = meij;
    }
    public String getSei_pin() {
        return sei_pin;
    }
    public void setSei_pin(String sei_pin) {
        this.sei_pin = sei_pin;
    }
    public String getMei_pin() {
        return mei_pin;
    }
    public void setMei_pin(String mei_pin) {
        this.mei_pin = mei_pin;
    }
    public String getSeij_pin() {
        return seij_pin;
    }
    public void setSeij_pin(String seij_pin) {
        this.seij_pin = seij_pin;
    }
    public String getMeij_pin() {
        return meij_pin;
    }
    public void setMeij_pin(String meij_pin) {
        this.meij_pin = meij_pin;
    }
    public String getSeinengappi() {
        return seinengappi;
    }
    public void setSeinengappi(String seinengappi) {
        this.seinengappi = seinengappi;
    }
    public String getNyuusya_nengappi() {
        return nyuusya_nengappi;
    }
    public void setNyuusya_nengappi(String nyuusya_nengappi) {
        this.nyuusya_nengappi = nyuusya_nengappi;
    }
    public String getSeisyain_nengappi() {
        return seisyain_nengappi;
    }
    public void setSeisyain_nengappi(String seisyain_nengappi) {
        this.seisyain_nengappi = seisyain_nengappi;
    }
    public String getSyukkoumoto() {
        return syukkoumoto;
    }
    public void setSyukkoumoto(String syukkoumoto) {
        this.syukkoumoto = syukkoumoto;
    }
    public String getDepartmentcd() {
        return departmentcd;
    }
    public void setDepartmentcd(String departmentcd) {
        this.departmentcd = departmentcd;
    }
    public String getDepartmentstartdate() {
        return departmentstartdate;
    }
    public void setDepartmentstartdate(String departmentstartdate) {
        this.departmentstartdate = departmentstartdate;
    }
    public String getDepartmentenddate() {
        return departmentenddate;
    }
    public void setDepartmentenddate(String departmentenddate) {
        this.departmentenddate = departmentenddate;
    }
    public String getGenyakusyoku() {
        return genyakusyoku;
    }
    public void setGenyakusyoku(String genyakusyoku) {
        this.genyakusyoku = genyakusyoku;
    }
    public String getPositionlevelcd() {
        return positionlevelcd;
    }
    public void setPositionlevelcd(String positionlevelcd) {
        this.positionlevelcd = positionlevelcd;
    }
    public String getNoryokukeisu() {
        return noryokukeisu;
    }
    public void setNoryokukeisu(String noryokukeisu) {
        this.noryokukeisu = noryokukeisu;
    }
    public String getGinkou_mei() {
        return ginkou_mei;
    }
    public void setGinkou_mei(String ginkou_mei) {
        this.ginkou_mei = ginkou_mei;
    }
    public String getSiten_mei() {
        return siten_mei;
    }
    public void setSiten_mei(String siten_mei) {
        this.siten_mei = siten_mei;
    }
    public String getKouza_code() {
        return kouza_code;
    }
    public void setKouza_code(String kouza_code) {
        this.kouza_code = kouza_code;
    }
    public String getPasuwa_Do() {
        return pasuwa_Do;
    }
    public void setPasuwa_Do(String pasuwa_Do) {
        this.pasuwa_Do = pasuwa_Do;
    }
    public String getKengen() {
        return kengen;
    }
    public void setKengen(String kengen) {
        this.kengen = kengen;
    }
    public String getJyusyo() {
        return jyusyo;
    }
    public void setJyusyo(String jyusyo) {
        this.jyusyo = jyusyo;
    }
    public String getKoseki() {
        return koseki;
    }
    public void setKoseki(String koseki) {
        this.koseki = koseki;
    }
    public String getTanann() {
        return tanann;
    }
    public void setTanann(String tanann) {
        this.tanann = tanann;
    }
    public String getNaisen1() {
        return naisen1;
    }
    public void setNaisen1(String naisen1) {
        this.naisen1 = naisen1;
    }
    public String getNaisen2() {
        return naisen2;
    }
    public void setNaisen2(String naisen2) {
        this.naisen2 = naisen2;
    }
    public String getE_mail() {
        return e_mail;
    }
    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }
    public String getTokucho() {
        return tokucho;
    }
    public void setTokucho(String tokucho) {
        this.tokucho = tokucho;
    }
    public String getTokuchoj() {
        return tokuchoj;
    }
    public void setTokuchoj(String tokuchoj) {
        this.tokuchoj = tokuchoj;
    }
    public String getPhoto_path() {
        return photo_path;
    }
    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }
    public String getDelflg() {
        return delflg;
    }
    public void setDelflg(String delflg) {
        this.delflg = delflg;
    }
    public String getCreateuser() {
        return createuser;
    }
    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }
    public String getCreatetime() {
        return createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getUpdateuser() {
        return updateuser;
    }
    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser;
    }
    public String getUpdatetime() {
        return updatetime;
    }
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
    public String getC1() {
        return c1;
    }
    public void setC1(String c1) {
        this.c1 = c1;
    }
    public String getC2() {
        return c2;
    }
    public void setC2(String c2) {
        this.c2 = c2;
    }
    public String getC3() {
        return c3;
    }
    public void setC3(String c3) {
        this.c3 = c3;
    }
    public String getC4() {
        return c4;
    }
    public void setC4(String c4) {
        this.c4 = c4;
    }
    public String getC5() {
        return c5;
    }
    public void setC5(String c5) {
        this.c5 = c5;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getZkey() {
        return zkey;
    }
    public void setZkey(String zkey) {
        this.zkey = zkey;
    }
}
