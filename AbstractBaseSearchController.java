package framework.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import framework.common.CommonUtil;
import framework.entity.BaseEntity;

@Controller
public abstract class AbstractBaseSearchController<E extends BaseEntity> extends BaseController {
	
	/**
	 * 一览页面初期化
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 页面请求对象
	 * @param session 
	 * @return 视图
	 * @throws Exception 
	 */
    @RequestMapping("/initSearch")
    public ModelAndView initSearch(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req,HttpSession session) throws Exception  {
    	//初期设定
    	this.init(entity);
    	// 清空信息列表
    	this.clearMessage();
    	//设定页面路径
    	mav.setViewName(this.getViewName());
    	// 保存检索条件
    	session.setAttribute(this.getClass().toString()+"SESSIONKEY", this.saveCondition(entity));
    	this.setSort(entity);
    	
    	List<E> resultList = new ArrayList<E>();
    	Integer size =0;
    	this.preSearch(mav,entity, req);
    	// 初期化是否检索数据
    	if(entity.isSearchInitData()){
    		resultList = this.search(entity,req);
        	size =getCount(entity,req);
    	}
    	
        this.postSearch(resultList,mav,entity, req);

        // 设置页面信息
        this.initPage(mav, entity, size);
        
        mav.addObject("list", resultList);
        mav.addObject("msg", this.getMessage());
        return mav;
    }
	
	/**
	 * 一览检索
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 页面请求对象
	 * @param session 
	 * @return 视图
	 * @throws Exception 
	 */
    @RequestMapping("/list")
    public ModelAndView doSearch(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req,HttpSession session) throws Exception {
    	// 清空信息列表
    	this.clearMessage();
    	//设定页面路径
    	mav.setViewName(this.getViewName());
    	
    	List<E> resultList = new ArrayList<E>();
    	Integer size =0;
        	if("0".equals(entity.getMode())){
        		 if(this.preSearch(mav,entity, req)){
	        		resultList = this.search(entity,req);
	            	size = getCount(entity,req);
	            	session.setAttribute(this.getClass().toString()+"SESSIONKEY", this.saveCondition(entity));
        		 }
        	}else{
        		if(session.getAttribute(this.getClass().toString()+"SESSIONKEY")!=null){
        			E ent =	(E)session.getAttribute(this.getClass().toString()+"SESSIONKEY");
					CommonUtil.copyBaseEntity(ent,entity);
	        		resultList = this.search(ent,req);
	            	size = getCount(ent,req);
            	}
        	}
        	
        	
        
        this.postSearch(resultList,mav,entity, req);

        // 设置页面信息
        this.initPage(mav, entity, size);
        
        mav.addObject("list", resultList);
        mav.addObject("msg", this.getMessage());
        return mav;
    }


	 /**
     * 检索前数据处理
     * @param mav 视图对象
     * @param entity 页面数据对象
     * @param req 页面请求对象
     * @return true/false
     */
    protected boolean preSearch(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception  {
    	return true;
    }


    /**
     * 一览数据检索
     * @param entity 页面数据对象
     *            
     * @return 一览数据
     */
    public abstract List<E> search(@ModelAttribute E entity,HttpServletRequest req) throws Exception ;
    
    /**
     * 一览数据总条数取得
     * @param entity 页面数据对象
     *            
     * @return 一览数据总条数
     */
    public abstract Integer getCount(@ModelAttribute E entity,HttpServletRequest req) throws Exception ;
    
    /**
     * 视图设定
     */
    public abstract String getViewName() throws Exception ;
    
	 /**
     * 检索后数据处理
     * @param resultList 一览对象列表
     * @param mav 视图对象
     * @param entity 页面数据对象
     * @param req 页面请求对象
     */
    protected void postSearch(List<E> resultList,@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception  {
       
    }
    
    /**
     * 检索条件保存
     * @param entity 页面数据对象
     * @return 检索条件保存对象
     */
    protected E saveCondition(E entity) throws Exception  {
       return entity;
    }
    
    /**
     * table排序，默认排序设定
     * @param entity 页面数据对象
     */
    public abstract void setSort(E entity) throws Exception ;
    
    /**
     * 初期化设定
     * @param entity 页面数据对象
     */
    protected void init(E entity) throws Exception {
    	// 初期化时是否检索数据
    	entity.setSearchInitData(false);
    };

}