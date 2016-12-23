package framework.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import framework.entity.BaseEntity;
import framework.utils.message.Message;
import framework.utils.message.MsgType;

public abstract class AbstractBaseUploadController<E extends BaseEntity> extends BaseController {
    /**
     * 上传初期化
     * 
     * @param mav 视图对象
     * @param entity 页面entity
     * @param request 页面请求对象
     * @param response 页面响应对象
     * @throws Exception 
     */
    @RequestMapping("/initUpload")
    protected ModelAndView initUpload(@ModelAttribute ModelAndView mav,@ModelAttribute E entity,HttpServletRequest request, HttpServletResponse response) throws Exception {
       super.clearMessage();
       return this.setUploadView(mav, request);
    }
	
    /**
     * 上传处理
     * 
     * @param mav 视图对象
     * @param entity 页面entity
     * @param request 页面请求对象
     * @param response 页面响应对象
     */
    @RequestMapping("/upload")
    protected void upload(@ModelAttribute E entity,HttpServletRequest request, HttpServletResponse response) throws Exception {
    	response.setContentType("text/html");
      	super.clearMessage();

      	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获取文件
        List<MultipartFile> fileList = multipartRequest.getFiles("file");
        CommonsMultipartFile file = null;
        if(fileList.size()==0){
        	this.addPopMessage(new Message("请选择文件", MsgType.WarningAlert, "", ""));
        	response.getWriter().write(this.getMessage());
        	return;
        }else{
        	file = (CommonsMultipartFile) fileList.get(0);
        	if(file.getSize()==0){
        		this.addPopMessage(new Message("文件内容不能为空", MsgType.WarningAlert, "", ""));
        		response.getWriter().write(this.getMessage());
            	return;
        	}
        }
        if(checkFileData(file,entity,request)){
        	response.getWriter().write(this.getMessage());
        	return;
        }else{
        	excuteDB(entity,request);
        };
        response.getWriter().write(this.getMessage());
        
    }

 
    
    /**
     * 设定上传视图
     * 
     * @param mav 视图对象
     * @param req 页面请求对象
     */
    public abstract ModelAndView setUploadView(@ModelAttribute ModelAndView mav, HttpServletRequest req) throws Exception ;
    
    
    /**
     * 验证上传数据
     * 
     * @param file 上传文件
     * @param entity 数据保存对象
     */
    public abstract boolean checkFileData(CommonsMultipartFile file,@ModelAttribute E entity,HttpServletRequest req) throws Exception ;
    
    /**
     * 上传数据数据库操作
     * 
     * @param entity 上传数据
     */
    public abstract  void excuteDB(@ModelAttribute E entity,HttpServletRequest req) throws Exception ;
    
    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    protected String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    
                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                    
                }
                // 如果是纯数字
                else {
                    // 取得当前Cell的数值
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case HSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
      
}
