package business.master.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import framework.controller.AbstractBaseSearchController;
import business.common.entity.CommonEntity;
import business.master.entity.GYSK0230ShainSentakuEntity;
import business.master.service.GYSK0230ShainSentakuService;
@Controller
@RequestMapping("/GYSK0230ShainSentaku")
public class GYSK0230ShainSentakuController extends AbstractBaseSearchController<GYSK0230ShainSentakuEntity> {	
	 /** 画面操作服务类 */
	@Autowired
	private GYSK0230ShainSentakuService gYSK0230ShainSentakuService;
//	private GYSK0230ShainSentakuEntity gYSK0230ShainSentakuEntity;
	
	/**
     * 视图设定
     */
    @Override
    public String getViewName() {
        return "master/GYSK0230ShainSentaku";
    }
    
    /**
     * 初期化设定
     * @param entity 页面数据对象
     */
    @Override
    protected void init(GYSK0230ShainSentakuEntity entity){
    	// 初期化时是否检索数据
    	entity.setSearchInitData(true);
    	
    
    }

	@Override
	
	public List<GYSK0230ShainSentakuEntity> search(
			GYSK0230ShainSentakuEntity entity, HttpServletRequest req)
			throws Exception {
		//无条件初期显示信息和有条件检索
    	List<GYSK0230ShainSentakuEntity> list = new ArrayList<GYSK0230ShainSentakuEntity>();
    	//获取用户输入
        String userId = req.getParameter("userId");
        String userFirstName =req.getParameter("firstUserName");
        String userLastName =req.getParameter("lastUserName");
        String department =req.getParameter("department");
    	//entity.setUserId("F0601");
    	//entity.setUserFirstName("李");
        req.setAttribute("userId", userId);
        req.setAttribute("firstUserName",userFirstName);
        req.setAttribute("lastUserName",userLastName);
        req.setAttribute("department",department);
        if ("".equals(userId)||"".equals(userFirstName)||"".equals(userLastName)||"".equals(department))
        {
        	//设置dao层sql查询语句参数entity属性值
        	entity.setUserId(userId);
        	entity.setUserFirstName(userFirstName);
        	entity.setUserLastName(userLastName);
        	entity.setDepartment(department);
        	list= gYSK0230ShainSentakuService.selData(entity);
        	
        } else{
        	list= gYSK0230ShainSentakuService.selAllData(entity);
        }
    	return list;
	}
	@ModelAttribute("departmentList")
	public List<CommonEntity> setAllDepartment(){
		List<CommonEntity> departmentLists = gYSK0230ShainSentakuService.getAllDepartment();
		return departmentLists;
	}
	

	@Override
	protected void postSearch(List<GYSK0230ShainSentakuEntity> resultList,ModelAndView mav,GYSK0230ShainSentakuEntity entity, HttpServletRequest req) {
       // 页面数据设定等
        int count=resultList.size();
        mav.addObject("counts", count);
       mav.addObject("entity",entity);
    }
	@RequestMapping("/insertData")
	public ModelAndView insertData( GYSK0230ShainSentakuEntity entity, ModelAndView mav,
			HttpServletRequest req){
		String userId = req.getParameter("userId");
        String userFirstName =req.getParameter("firstUserName");
        String userLastName =req.getParameter("lastUserName");
        String department =req.getParameter("department");
        
        entity.setUserId(userId);
    	entity.setUserFirstName(userFirstName);
    	entity.setUserLastName(userLastName);
    	entity.setDepartment(department);
		gYSK0230ShainSentakuService.insertData(entity);
		mav.setViewName("/master/GYSK0230ShainSentaku");
		return mav;
	} 
	
	@RequestMapping("/updateData")
	public ModelAndView updateData( GYSK0230ShainSentakuEntity entity, ModelAndView mav,
			HttpServletRequest req){
		String userId = req.getParameter("userId");
        String userFirstName =req.getParameter("firstUserName");
        String userLastName =req.getParameter("lastUserName");
        String department =req.getParameter("department");
        
        entity.setUserId(userId);
    	entity.setUserFirstName(userFirstName);
    	entity.setUserLastName(userLastName);
    	entity.setDepartment(department);
		gYSK0230ShainSentakuService.updateData(entity);
		mav.setViewName("/master/GYSK0230ShainSentaku");
		return mav;
	} 
	@RequestMapping("/deleteData")
	public ModelAndView deleteData( GYSK0230ShainSentakuEntity entity, ModelAndView mav,
			HttpServletRequest req){
		String userId = req.getParameter("userId");
        String userFirstName =req.getParameter("firstUserName");
        String userLastName =req.getParameter("lastUserName");
        String department =req.getParameter("department");
        
        entity.setUserId(userId);
    	entity.setUserFirstName(userFirstName);
    	entity.setUserLastName(userLastName);
    	entity.setDepartment(department);
		gYSK0230ShainSentakuService.deleteData(entity);
		mav.setViewName("/master/GYSK0230ShainSentaku");
		return mav;
	} 
	@RequestMapping("/deleteData_1")
	public ModelAndView deleteData_1( GYSK0230ShainSentakuEntity entity, ModelAndView mav,
			HttpServletRequest req){
		String userId = req.getParameter("userId");
		String[] stringArr = userId.split(",");		
      for (int i = 0;i < stringArr.length;i++) {
      gYSK0230ShainSentakuService.deleteData_1(stringArr[i]);
  }
		mav.setViewName("/master/GYSK0230ShainSentaku");
		return mav;
	} 
//    public boolean delete_1(ModelAndView mav, GYSK0120MainUserEntity entity, HttpServletRequest req) throws Exception {
//        String kanriBangou = entity.getKanriBangou();
//        String[] stringArr = kanriBangou.split(",");
//        for (int i = 0, j = stringArr.length; i < j; i++) {
//            mainUserService.delMember(stringArr[i]);
//        }
//        return true;
//    }

	
	@Override
	public Integer getCount(GYSK0230ShainSentakuEntity entity,
			HttpServletRequest req) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setSort(GYSK0230ShainSentakuEntity entity) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
