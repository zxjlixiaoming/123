package business.master.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import business.common.entity.CommonEntity;
import business.common.service.CommonService;
import business.master.entity.GYSK0220OrderEntity;
import business.master.service.GYSK0220OrderService;
import framework.controller.AbstractBasePageListController;
import framework.entity.ExcelDataEntity;

/**
 * オーダー選択Controller
 */
@Controller
@RequestMapping("/gysk0220Order")
public class GYSK0220OrderController extends AbstractBasePageListController<GYSK0220OrderEntity> {

     /** 画面操作服务类 */
    @Autowired
    private GYSK0220OrderService orderService;
    
    /** 共通操作服务类 */
    @Autowired
    private CommonService commonService;
    /**
     * 视图设定
     */
    @Override
    public String getViewName() {
        return "master/GYSK0220Order";
    }
    
    /**
     * 初期化设定
     * @param entity 页面数据对象
     */
    @Override
    protected void init(GYSK0220OrderEntity entity){
        
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
    protected boolean preSearch(ModelAndView mav,GYSK0220OrderEntity entity, HttpServletRequest req) {

        return true;
    }
    
    @Override
    public List<GYSK0220OrderEntity> search(GYSK0220OrderEntity entity,
            HttpServletRequest req) throws Exception {
        
           // オーダー一览列表
           List<GYSK0220OrderEntity> list = new ArrayList<GYSK0220OrderEntity>();
           
           list = orderService.selAll(entity);
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
       protected void postSearch(List<GYSK0220OrderEntity> resultList,ModelAndView mav,GYSK0220OrderEntity entity, HttpServletRequest req) {
          // 页面数据设定等
           int count=resultList.size();
           mav.addObject("counts", count);
          mav.addObject("entity",entity);
       }
       
       /**
        * 检索条件保存
        * @param entity 页面数据对象
        * @return 检索条件保存对象
        */
       @Override
       protected GYSK0220OrderEntity saveCondition(GYSK0220OrderEntity entity) {
           GYSK0220OrderEntity ent = new GYSK0220OrderEntity();
           ent.setTxtDepartmentCD(entity.getTxtDepartmentCD());
           ent.setTxtOrderNM(entity.getTxtOrderNM());
           ent.setTxtOrderCD(entity.getTxtOrderCD());
           return ent;
       }
       
       /**
        * 部門列表取得
        * @return 部門列表
        */
       @ModelAttribute("departmentList")
       public List<CommonEntity> getComboBoxList() {
           List<CommonEntity> delFlgList = orderService.getDepartMent();
           return delFlgList;
       }
    @Override
    public void setSort(GYSK0220OrderEntity entity) throws Exception {
        // 默认排序设定
        entity.setSort("SHAINCD asc");
    }

    @Override
    public boolean delete(ModelAndView mav, GYSK0220OrderEntity entity,
            HttpServletRequest req) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public boolean update(ModelAndView mav, GYSK0220OrderEntity entity,
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
    public List<List<ExcelDataEntity>> setDataSource(GYSK0220OrderEntity entity)
            throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public boolean checkFileData(CommonsMultipartFile file,
            GYSK0220OrderEntity entity, HttpServletRequest req) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    @Override
    public void excuteDB(GYSK0220OrderEntity entity, HttpServletRequest req)
            throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        
    }
    
    @Override
    public Integer getCount(GYSK0220OrderEntity entity, HttpServletRequest req)
            throws Exception {
        return null;
    }
    
}
