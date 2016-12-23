package framework.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import framework.entity.BaseEntity;

public abstract class AbstractBaseInsertController<E extends BaseEntity> extends BaseController {
	
	/**
	 * 追加页面初期化
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
	 * @return 视图
	 * @throws Exception 
	 */
    @RequestMapping("/initInsert")
    public ModelAndView init(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception {
    	// 清空信息列表
    	this.clearMessage();
    	// 设定view
    	mav.setViewName(this.getViewName(mav,entity));
    	initInsert(mav,entity,req);
        mav.addObject("msg", this.getMessage());
    	return mav;
    }
    
    /**
     * 追加处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
	 * @return 视图
     * @throws Exception 
     */
    @RequestMapping("/save")
    public ModelAndView saveInsert(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception {
    	
    	// 清空信息列表
    	this.clearMessage();
    	// 设定view
    	mav.setViewName(this.getViewName(mav,entity));
    	
		if (preInsert(mav, entity, req)) {
			insert(mav, entity, req);
			
			postInsert(mav, entity,req);
			
		} else {
			
			postInsert(mav, entity,req);
		}
		
		mav.addObject("msg", this.getMessage());
		return mav;

    }
    /**
     * ajax追加处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
	 * @return 处理结果
     * @throws Exception 
     */
    @RequestMapping("/ajaxSave")
    @ResponseBody
    public Map<String, Object> ajaxSaveInsert(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception {
    	boolean issuccess = true;
    	Map<String, Object> modelMap = new HashMap<String, Object>();  
		// 画面项目检查
    	this.clearMessage();
    	
		if (preInsert(mav, entity, req)) {
			insert(mav, entity, req);
			postInsert(mav, entity,req);
		} else {
			issuccess=false;
			postInsert(mav, entity,req);
		}
				
		modelMap.put("result", issuccess);
		modelMap.put("msg", this.getMessage());
		return modelMap;

    }


	/**
	 * 追加页面初始化处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
	 */
    protected void initInsert(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {};
    
    /**
     * 追加前处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
     * @return
     */
    protected boolean preInsert(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {return true;};

    /**
     * 追加主处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
     * @return
     */
    public abstract boolean insert(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception ;

    /**
     * 追加后处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
     */
    protected void postInsert(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {};
 
    /**
     * 视图取得
	 * @param mav 视图对象
	 * @param entity 页面数据对象
     * @return 视图
     */
    public abstract String getViewName(@ModelAttribute ModelAndView mav, @ModelAttribute E entity) throws Exception ;
    
    
    
    
    
    
}
