package framework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;





import framework.common.Constant;
import framework.common.PropertiesUtil;
import framework.entity.BaseEntity;
import framework.utils.dialect.Pager;
import framework.utils.message.Message;

@Controller
public abstract class BaseController {

    private final static Log log = LogFactory.getLog(BaseController.class);
    
    @Autowired
    protected MessageSource messageSource;
    
    @Autowired
    private  HttpServletRequest request;
    
    // 信息key
    private static final String MSG_FBSE = "MSGFBSE";
    
    // 信息
    //private JSONArray message=new JSONArray();
    protected void addMessage(String msg){
    	JSONArray message=null;
    	Object obj = request.getSession().getAttribute(MSG_FBSE);
    	if(obj !=null && obj instanceof JSONArray){
    		message = (JSONArray)obj;
    	}else{
    		message =new JSONArray();
    	}
    	message.add(msg);
    	request.getSession().setAttribute(MSG_FBSE, message);
    	
    }
    protected void clearMessage(){
    	request.getSession().setAttribute(MSG_FBSE, new JSONArray());
    }
    protected String getMessage(){
    	JSONArray message=null;
    	Object obj = request.getSession().getAttribute(MSG_FBSE);
    	if(obj !=null && obj instanceof JSONArray){
    		message = (JSONArray)obj;
    	}else{
    		message =new JSONArray();
    	}
    	return message.toString(); 	
    }
    
    protected boolean hasMessage(){
    	JSONArray message=null;
    	Object obj = request.getSession().getAttribute(MSG_FBSE);
    	if(obj !=null && obj instanceof JSONArray){
    		message = (JSONArray)obj;
    	}else{
    		message =new JSONArray();
    	}
    	return message.size()==0?false:true; 	
    }
    
    protected void addPopMessage(Message msg){
    	JSONArray message=null;
    	JSONObject json = JSONObject.fromObject(msg);
    	Object obj = request.getSession().getAttribute(MSG_FBSE);
    	if(obj !=null && obj instanceof JSONArray){
    		message = (JSONArray)obj;
    	}else{
    		message =new JSONArray();
    	}
    	message.add(json);
    	
    }

    /**
     * 初始化分页相关信息
     */
    protected void initPage(ModelAndView mav, BaseEntity entity,Integer listSize) {
    	
        // 当前页与每页件数取得
        Integer pageNum = entity.getPageNum();
        Integer pageSize = entity.getPageSize();
        listSize = listSize==null?0:listSize;
        Integer totalPage = (listSize + pageSize - 1) / pageSize;
        if (null == pageNum) {
            pageNum = Constant.PAGENUM;
        } else if (pageNum > totalPage) {
            pageNum = totalPage;
        }
        mav.addObject("startIndex", Pager.getStartIndex(pageNum, pageSize));
        mav.addObject("pageNum", pageNum);
        mav.addObject("totalPage", totalPage);
        mav.addObject("pageSize", pageSize);
        mav.addObject("totalCount", listSize);

    }

    /**
     * 获取当前国际化语言
     */
    protected Locale getLocale(HttpServletRequest request) {
        Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
        return locale;
    }
    
    /**
     * 获取当前国际化语言
     */
    protected Locale getLocale() {
    	String language = PropertiesUtil.getPropertyValue("language");
    	if(language.equals("japan")){
    		return Locale.JAPAN;
    	}else{
    		return Locale.CHINA;
    	}
    }

    /**
     * 全局异常处理
     */
    @ExceptionHandler
    public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        log.error(sw.toString());

        // 如果是json格式的ajax请求
        if (request.getHeader("accept").indexOf("application/json") > -1
                || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf(
                        "XMLHttpRequest") > -1)) {
            response.setStatus(500);
            response.setContentType("application/json;charset=utf-8");
            try {
                PrintWriter writer = response.getWriter();
                writer.write(e.getMessage());
                writer.flush();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            return null;
        } else {// 如果是普通请求
            request.setAttribute("exceptionMessage", e.getMessage());
            // 根据不同的异常类型可以返回不同界面
            if (e instanceof SQLException) {
                return "login";
            } else {
                return "common/error";
            }
        }
    }
    
    /**
     * 设定系统日期格式化
     * @param entity
     */
    protected void setDateFormate(BaseEntity entity){
    	entity.setViewDateFormateYM(this.messageSource.getMessage("ViewDateFormateYM", null, Locale.CHINA));
    	entity.setViewDateFormateYMD(this.messageSource.getMessage("ViewDateFormateYMD", null, Locale.CHINA));
    	entity.setJavaDateFormateYM(this.messageSource.getMessage("JavaDateFormateYM", null, Locale.CHINA));
    	entity.setJavaDateFormateYMD(this.messageSource.getMessage("JavaDateFormateYMD", null, Locale.CHINA));
    }
    
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        // 设置List的最大长度  
        binder.setAutoGrowCollectionLimit(2000);  
    } 

}