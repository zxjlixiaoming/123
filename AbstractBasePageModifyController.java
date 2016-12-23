package framework.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import framework.common.Constant;
import framework.entity.BaseEntity;

public abstract class AbstractBasePageModifyController<E extends BaseEntity> extends BaseController {
	
	/**
	 * 追加页面初期化
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
	 * @return 视图
	 * @throws Exception 
	 */
    @RequestMapping("/initModify")
    public ModelAndView init(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception {
    	// 系统日期格式化设定
    	super.setDateFormate(entity);
    	// 清空信息列表
    	this.clearMessage();
    	// 设定view
    	mav.setViewName(this.getViewName(mav,entity));
    	if(Constant.MODIFY_MODE_INSERT.equals(entity.getMode())){
    		initInsert(mav,entity,req);
    	}
    	
    	if(Constant.MODIFY_MODE_UPDATE.equals(entity.getMode())){
    		initUpdate(mav,entity,req);
    	}
    	
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
    @RequestMapping("/modify")
    public ModelAndView modifyExcute(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception {
    	// 系统日期格式化设定
    	super.setDateFormate(entity);
    	
    	// 清空信息列表
    	this.clearMessage();
    	// 设定view
    	mav.setViewName(this.getViewName(mav,entity));
    	if(Constant.MODIFY_MODE_INSERT.equals(entity.getMode())){
    		if (preInsert(mav, entity, req)) {
    			insert(mav, entity, req);
    			
    			postInsert(mav, entity,req);
    			
    		} else {
    			
    			postInsert(mav, entity,req);
    		}
    	}
    	if(Constant.MODIFY_MODE_UPDATE.equals(entity.getMode())){
    		if (preUpdate(mav, entity, req)) {
    			update(mav, entity, req);
    			
    			postUpdate(mav, entity,req);
    			
    		} else {
    			
    			postUpdate(mav, entity,req);
    		}
    	}
    	
		mav.addObject("msg", this.getMessage());
		return mav;

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
    public abstract boolean	insert(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception ;

    /**
     * 追加后处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
     */
    protected void postInsert(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {};
 
	/**
	 * 修正页面初始化处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
	 */
    protected void initUpdate(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {};
    
    /**
     * 更新前处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
     * @return
     */
    protected boolean preUpdate(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {return true;};

    /**
     * 更新主处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
     * @return
     */
    public abstract boolean	update(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception ;

    /**
     * 更新后处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 请求对象
     */
    protected void postUpdate(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {};
 
    /**
     * 视图取得
	 * @param mav 视图对象
	 * @param entity 页面数据对象
     * @return 视图
     */
    public abstract String getViewName(@ModelAttribute ModelAndView mav, @ModelAttribute E entity) throws Exception ;
    
    
    
    
}
