package business.master.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import business.master.entity.GYSK0230StaffEntity;
import business.master.entity.GYSK0230StaffParamenter;
import business.master.service.GYSK0230StaffService;
import framework.controller.AbstractBasePageListController;
import framework.entity.ExcelDataEntity;

/**
 * master信息一览
 * 
 */
@Controller
@RequestMapping("/gysk0230")
public class GYSK0230Controller extends AbstractBasePageListController<GYSK0230StaffEntity> {
     /** 画面操作服务类 */
    @Autowired
    private GYSK0230StaffService masterService;
    
    /**
     * 视图设定
     */
    @Override
    public String getViewName() {
        return "master/GYSK0230Staff";
    }
    
    /**
     * 一览页面初期化
     * @param mav 视图对象
     * @param entity 页面数据对象
     * @param req 页面请求对象
     * @param session 
     * @return 视图
     * @throws Exception 
     */
    @RequestMapping("/aa")
    public ModelAndView aa(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName(getViewName());
        return mav;
    }
    
    /**
     * 初期化设定
     * @param entity 页面数据对象
     */
    @Override
    public void init(GYSK0230StaffEntity entity){
    // 初期化时是否检索数据
    entity.setSearchInitData(true);
    }
    /**
     * 检索前数据处理
     * @param mav 视图对象
     * @param entity 页面数据对象
     * @param req 页面请求对象
     * @return true/false
     */
    @Override
    protected boolean preSearch(ModelAndView mav,GYSK0230StaffEntity entity, HttpServletRequest req) {
        //自定义页面条数
        entity.setPageSize(22);

        return true;
    }
    
    @Override
    public List<GYSK0230StaffEntity> search(GYSK0230StaffEntity entity,
            HttpServletRequest req) throws Exception {
        GYSK0230StaffParamenter param = new GYSK0230StaffParamenter(); 
        String aa =req.getParameter("ctl00$cphMain$btnOK");
    
        // 従業員一览列表
         List<GYSK0230StaffEntity> list = new ArrayList<GYSK0230StaffEntity>();
          if ("検索".equals(aa)) {
             param.setJyugyoin_bangou(req.getParameter("txtMemberID"));
             param.setSeij(req.getParameter("txtSurname"));
             param.setMeij(req.getParameter("txtName"));
            list = masterService.selList(param);
        } else  {
             list = masterService.selAll();
             entity.setFlag(1);
        }

              // 查询结果不存在
           if(null == list || 0 == list.size()){
                // TODO 需要弹出消息 
                // super.addPopMessage(new Message(messageSource.getMessage("I001",null, getLocale()),MsgType.InfoAlert,"clickBtnOK",""));
            }
            return list;
    }

     /**
        * 检索后数据处理
        * @param resultList 一览对象列表
        * @param mav 视图对象
        * @param entity 页面数据对象
        * @param req 页面请求对象
        */
       @Override
       protected void postSearch(List<GYSK0230StaffEntity> resultList,ModelAndView mav,GYSK0230StaffEntity entity, HttpServletRequest req) {
          // 页面数据设定等
          mav.addObject("entity",entity);
       }
       /**
        * 检索条件保存
        * @param entity 页面数据对象
        * @return 检索条件保存对象
        */
       @Override
       protected GYSK0230StaffEntity saveCondition(GYSK0230StaffEntity entity) {
           GYSK0230StaffEntity ent = new GYSK0230StaffEntity();
           return ent;
       }
    @Override
    public void setSort(GYSK0230StaffEntity entity) throws Exception {
        // 默认排序设定
        entity.setSort("SHAINCD asc");
    }
    
    @Override
    public Integer getCount(GYSK0230StaffEntity entity, HttpServletRequest req)
            throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public boolean delete(ModelAndView mav, GYSK0230StaffEntity entity,
            HttpServletRequest req) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public String setExcelTemplateName() throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public void customExcel(HSSFWorkbook wb, int sheetIndex,
            List<List<ExcelDataEntity>> dtLists) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
    }

    @Override
    public List<List<ExcelDataEntity>> setDataSource(GYSK0230StaffEntity entity)
            throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public boolean checkFileData(CommonsMultipartFile file, GYSK0230StaffEntity entity,
            HttpServletRequest req) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public void excuteDB(GYSK0230StaffEntity entity, HttpServletRequest req)
            throws Exception {
        // TODO 自動生成されたメソッド・スタブ
    }

    @Override
    public boolean update(ModelAndView mav, GYSK0230StaffEntity entity,
            HttpServletRequest req) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }
}
