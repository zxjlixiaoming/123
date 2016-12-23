package framework.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import framework.entity.BaseEntity;

public abstract class AbstractBaseExportTxtController<E extends BaseEntity> extends BaseController {
	private List<TxtEntity> list = new ArrayList<TxtEntity>();
	
    /**
     * txt文件导出
     * 
     * @param entity
     *            页面entity
     * @param request
     *            页面请求对象
     * @param response
     *            页面响应对象
     * @throws Exception
     */
    @RequestMapping("/exportTxt")
    protected void exportTxt(@ModelAttribute E entity,HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 系统日期格式化设定
    	super.setDateFormate(entity);
    	this.list.clear();
        String pName = this.setFileName(entity);
	    //20160401 文件名上追加导出日期 start
	    int dotLastIndex = pName.lastIndexOf('.');
	    String extension =  pName.substring(dotLastIndex);
	    String name = pName.substring(0,dotLastIndex);
	    DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	    String date = format.format(new Date());
	    pName = name+"_"+date+extension;
	    //20160401 文件名上追加导出日期 end
        this.setDataSource(entity,request);
        
	    response.reset();   
	    response.setContentType("text/plain;charset=UTF-8"); 
	    response.setHeader("Content-Disposition","attachment; filename="+java.net.URLEncoder.encode(pName, "UTF-8") );  
	    PrintWriter writer = response.getWriter();
        
        for(int i=0;i<this.list.size();i++){
        	if(i!=0 && i%this.setColSize() == 0){
        		writer.write("\r\n");
        		writer.write(this.list.get(i).getValue());
        	}else{
        		writer.write(this.list.get(i).getValue());
        	}
        }   
        writer.close();
        response.flushBuffer();
        
        
    }

    /**
     * 设定Txt文件名称
     * 
     * @param entity
     *           页面entity
     * @return txt文件名
     */
    public abstract String setFileName(@ModelAttribute E entity) throws Exception ;
    
    /**
     * 设定列数
     * 
     * @return 列数
     */
    public abstract int setColSize() throws Exception ;
    
    
    /**
     * 设定Txt文件数据源
     * 
     * @param entity 页面entity
     * @param request 请求对象
     * 
     * @return txt文件数据源
     */
    public abstract  void setDataSource(@ModelAttribute E entity,HttpServletRequest request) throws Exception ;
    
	/**
	 * 数据列表，右填充
	 * @param value 值
	 * @param size  位数
	 * @return
	 */
    public List<TxtEntity> pushLeftPadData(String value,int size){
    	this.list.add(new TxtEntity(value,size,true));
    	return this.list;
    }
    
	/**
	 * 数据列表，右填充
	 * @param value 值
	 * @param size  位数
	 * @return
	 */
    public List<TxtEntity> pushRightPadData(String value,int size){
    	this.list.add(new TxtEntity(value,size,false));
    	return this.list;
    }
}

/**
 * 填充对象
 * @author Mr.ren
 *
 */
class TxtEntity{
	
	/** 填充值 */
	private String value;
	/** 填充值的位数 */
	private int size;
	
	TxtEntity(String value,int size,boolean isLeftPad){
		if(isLeftPad){
			this.value = StringUtils.leftPad(value, size);
		}else{
			this.value = StringUtils.rightPad(value, size);
		}
		this.size = size;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
