package framework.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import framework.entity.BaseEntity;

public abstract class AbstractBaseDeleteController<E extends BaseEntity> extends BaseController {
	
    @RequestMapping("/delete")
    public ModelAndView del(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception {
    	
    	// 清空信息列表
    	this.clearMessage();
    	// 设定view
    	mav.setViewName(this.getViewName(mav,entity));
    	
		if (preDelete(mav, entity, req)) {
			delete(mav, entity, req);
			
			postDelete(mav, entity,req);
		} else {
			
			postDelete(mav, entity,req);
		}
		
		mav.addObject("msg", this.getMessage());
		return mav;

    }
    @RequestMapping("/ajaxDelete")
    @ResponseBody
    public Map<String, Object> ajaxDelete(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception {
    	boolean issuccess = true;
    	Map<String, Object> modelMap = new HashMap<String, Object>();  
		// 画面项目检查
    	this.clearMessage();
    	
		if (preDelete(mav, entity, req)) {
			delete(mav, entity, req);
			postDelete(mav, entity,req);
		} else {
			issuccess=false;
			postDelete(mav, entity,req);
		}
				
		modelMap.put("result", issuccess);
		modelMap.put("msg", this.getMessage());
		return modelMap;

    }



    
    protected boolean preDelete(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {return true;};

    public abstract boolean delete(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception ;

    protected void postDelete(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {};
 
    public abstract String getViewName(@ModelAttribute ModelAndView mav, @ModelAttribute E entity) throws Exception ;
    
    
    
    
    
    
}
