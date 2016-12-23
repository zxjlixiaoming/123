package framework.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import framework.entity.BaseEntity;

public abstract class AbstractBaseUpdateController<E extends BaseEntity> extends BaseController {
	
    @RequestMapping("/initUpdate")
    public ModelAndView init(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception {
    	// 设定view
    	mav.setViewName(this.getViewName(mav,entity));
    	// 画面项目检查
    	this.clearMessage();
    	initUpdate(mav,entity,req);
    	return mav;
    }
    @RequestMapping("/update")
    public ModelAndView updatemain(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception {
    	// 设定view
    	mav.setViewName(this.getViewName(mav,entity));
    	// 画面项目检查
    	this.clearMessage();
    	
		// 画面项目检查
		if (preUpdate(mav, entity, req)) {
			update(mav, entity, req);
			postUpdate(mav, entity,req);
		} else {
			postUpdate(mav, entity,req);
		}
		mav.addObject("msg", this.getMessage());
		return mav;

    }
    @RequestMapping("/ajaxUpdate")
    @ResponseBody
    public Map<String, Object> ajaxUpdate(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception {
    	Map<String, Object> modelMap = new HashMap<String, Object>();  
		// 画面项目检查
    	this.clearMessage();
    	
		if (preUpdate(mav, entity, req)) {
			update(mav, entity, req);
			postUpdate(mav, entity,req);
		} else {
			postUpdate(mav, entity,req);
		}
    	
		modelMap.put("msg", this.getMessage());
		return modelMap;

    }



    public abstract void initUpdate(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception ;
    
    protected boolean preUpdate(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {return true;};

    public abstract boolean update(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception ;

    protected void postUpdate(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {};
 
    public abstract String getViewName(@ModelAttribute ModelAndView mav, @ModelAttribute E entity) throws Exception ;
    
    
    
    
    
    
}
