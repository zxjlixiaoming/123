package framework.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import framework.common.CommonUtil;
import framework.common.Constant;
import framework.entity.BaseEntity;
import framework.entity.ExcelDataEntity;
import framework.utils.message.Message;
import framework.utils.message.MsgType;

@Controller
public abstract class AbstractBasePageListExtendController<E extends BaseEntity> extends BaseController {
// 160421 DaiLei Add Begin
    // 小数格式
    DecimalFormat decimalFormat = new DecimalFormat("#0.0#########");
// 160421 DaiLei Add End
    
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
    	// 系统日期格式化设定
    	super.setDateFormate(entity);
    	//初期设定
    	this.init(entity);
    	// 清空信息列表
    	this.clearMessage();
    	this.removeSession(entity,session);
    	//设定页面路径
    	mav.setViewName(this.getViewName());
    	// 保存检索条件
    	E ent = this.saveCondition(entity);
    	super.setDateFormate(ent);
    	session.setAttribute(this.getClass().toString()+"SESSIONKEY", ent);
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
    	// 系统日期格式化设定
    	super.setDateFormate(entity);
    	//设定页面路径
    	mav.setViewName(this.getViewName());
    	
    	List<E> resultList = new ArrayList<E>();
    	Integer size =0;
        	if(Constant.SEARCH_MODE_SEARCH.equals(entity.getMode())){
            	// 清空信息列表
            	this.clearMessage();
            	this.removeSession(entity,session);
        		 if(this.preSearch(mav,entity, req)){
        			entity.setPageNum(1);
	        		resultList = this.search(entity,req);
	            	size = getCount(entity,req);
	            	E ent = this.saveCondition(entity);
	            	super.setDateFormate(ent);
	            	session.setAttribute(this.getClass().toString()+"SESSIONKEY", ent);
        		 }
        	}else{
        		if(Constant.SEARCH_MODE_PAGNATION.equals(entity.getMode())){
        			// 清空信息列表
                	this.clearMessage();
        		}else{
        			this.removeSession(entity,session);
        		}
        		if(session.getAttribute(this.getClass().toString()+"SESSIONKEY")!=null){
        			E ent =	(E)session.getAttribute(this.getClass().toString()+"SESSIONKEY");
					CommonUtil.copyBaseEntity(ent,entity);
					size = getCount(ent,req);
					
					Integer totalPage = (size + entity.getPageSize() - 1) / entity.getPageSize();
			        if (entity.getPageNum()>totalPage) {
			           entity.setPageNum(totalPage);
			           ent.setPageNum(totalPage);
			        }
					
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
    public abstract String getViewName();
    
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
    
	/**
	 * 删除处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 页面请求对象
	 * @return 视图
	 * @throws Exception 
	 */
    @RequestMapping("/delete")
    public ModelAndView del(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception {
    	// 系统日期格式化设定
    	super.setDateFormate(entity);
    	// 清空信息列表
    	this.clearMessage();
    	// 设定view
    	mav.setViewName(this.getViewName());
    	
		if (preDelete(mav, entity, req)) {
			delete(mav, entity, req);
			
			postDelete(mav, entity,req);
		} else {
			
			postDelete(mav, entity,req);
		}
		
		return this.doSearch(mav, entity, req, req.getSession());

    }
    
	/**
	 * 删除前处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 页面请求对象
	 * @return 视图
	 */
    protected boolean preDelete(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {return true;};

	/**
	 * 删除主处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 页面请求对象
	 * @return 视图
	 */
    public abstract boolean delete(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception ;

	/**
	 * 删除后处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 页面请求对象
	 * @return 视图
	 */
    protected void postDelete(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {};
    
    

	/**
	 * 更新处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 页面请求对象
	 * @return 视图
	 * @throws Exception 
	 */
    @RequestMapping("/update")
    public ModelAndView updatemain(@ModelAttribute ModelAndView mav,@ModelAttribute E entity, HttpServletRequest req) throws Exception {
    	// 系统日期格式化设定
    	super.setDateFormate(entity);
    	// 设定view
    	mav.setViewName(this.getViewName());
    	// 画面项目检查
    	this.clearMessage();
    	
		// 画面项目检查
		if (preUpdate(mav, entity, req)) {
			update(mav, entity, req);
			postUpdate(mav, entity,req);
		} else {
			postUpdate(mav, entity,req);
		}
		return this.doSearch(mav, entity, req, req.getSession());

    }
    
	/**
	 * 更新前处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 页面请求对象
	 * @return 视图
	 */
    protected boolean preUpdate(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {return true;};

	/**
	 * 更新主处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 页面请求对象
	 * @return 视图
	 */
    public abstract boolean update(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception ;

	/**
	 * 更新后处理
	 * @param mav 视图对象
	 * @param entity 页面数据对象
	 * @param req 页面请求对象
	 * @return 视图
	 */
    protected void postUpdate(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {};
 
    
    
    /**
     * 导出EXCELL
     * 
     * @param entity
     *            页面entity
     * @param request
     *            页面请求对象
     * @param response
     *            页面响应对象
     * @throws Exception
     */
    @RequestMapping("/exportExcell")
    protected void exportExcel(@ModelAttribute ModelAndView mav,@ModelAttribute E entity,HttpServletRequest request, HttpServletResponse response) throws Exception {

    	// 系统日期格式化设定
    	super.setDateFormate(entity);
    	
        String filePath = "/excelTemplate/" + this.setExcelTemplateName();

        String ctxpath = request.getSession().getServletContext().getRealPath(filePath);

        File reFile = new File(ctxpath);

        XSSFWorkbook wb = CreatListExcell(setDataSource(entity),reFile,response);
        
        postExportExcel(mav,entity,request);
        
	     //excel名称名称取得
	     String pName= reFile.getName(); 
	     if(StringUtils.isEmpty(this.setExcelFileName())){
	     	pName= reFile.getName(); 
	     }else{
	     	pName= this.setExcelFileName(); 
	     }
	    //20160401 文件名上追加导出日期 start
	    int dotLastIndex = pName.lastIndexOf('.');
	    String extension =  pName.substring(dotLastIndex);
	    String name = pName.substring(0,dotLastIndex);
	    DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	    String date = format.format(new Date());
	    pName = name+"_"+date+extension;
	    //20160401 文件名上追加导出日期 end
	    response.reset();   
	    response.setContentType("application/x-msdownload;charset=UTF-8"); 
// 160824 DaiLei Update Begin
//	    response.setHeader("Content-Disposition","attachment; filename="+java.net.URLEncoder.encode(pName, "UTF-8") );//new String(pName.getBytes("gb2312"),"ISO-8859-1")+".xls");   
        if (request.getHeader("User-Agent").indexOf("Chrome") > -1
                || request.getHeader("User-Agent").indexOf("Firefox") > -1) {
            response.setHeader("Content-Disposition", "attachment; filename="
                    + new String(pName.getBytes("UTF-8"), "ISO8859-1"));
        } else {
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(pName, "UTF-8"));
        }
// 160824 DaiLei Update End
	    ServletOutputStream outStream=null;   
	    try{
	        outStream = response.getOutputStream();   
	        wb.write(outStream);   
	    }catch(Exception e)   
	    {   
	     e.printStackTrace();   
	    }finally{   
	        outStream.close();   
	    }   
    }

    /**
     * 设定导出模板名称
     * @return 模板名称
     */
    public abstract String setExcelTemplateName() throws Exception ;
    
    /**
     * 设定导出文件名称
     * @return 文件名称
     */
    protected String setExcelFileName() throws Exception {return null;};
    
    
    /**
     * 自定义模板信息
     * @param wb Excell对象
     * @param sheetIndex sheet序号
     * @param dtLists 模板数据
     */
    public abstract void customExcel(XSSFWorkbook wb,int sheetIndex,List<List<ExcelDataEntity>> dtLists) throws Exception ;
    
    /**
     * 设定模板数据源
     * 
     * @param entity
     *              页面entity
     * @return 主数据源
     */
    public abstract  List<List<ExcelDataEntity>> setDataSource(@ModelAttribute E entity) throws Exception ;
    
    /**
     * 设定帐票参数
     * 
     * @param entity
     *              页面entity
     * @return 参数Map
     * @throws Exception 
     */
    
    private XSSFWorkbook CreatListExcell(List<List<ExcelDataEntity>> dtLists,File template,HttpServletResponse response) throws Exception {
        	InputStream is = new FileInputStream(template);
        	
        	XSSFWorkbook wb = new XSSFWorkbook(is);  
        	// 标题样式  
        	XSSFCellStyle headerStyle = wb.createCellStyle(); 
        	// 取各个sheet对应的数据
        	for(int index=0;index<dtLists.size();index++){
        		List<ExcelDataEntity> dtList = dtLists.get(index);
                XSSFSheet sheet = wb.getSheetAt(index);
                List<XSSFCellStyle> styleList = new ArrayList<XSSFCellStyle>();
                if(dtList==null||dtList.size()==0){
                	continue;
                }else{
                	ExcelDataEntity ent = dtList.get(0);
                	XSSFRow rowx = sheet.getRow(ent.getStartRow()+1);
                	if(ent.getData()==null || ent.getData().size()==0){
                    	continue;
                    }
            		for(int i= 0;i<ent.getData().get(0).size();i++){
                		// 设定border
                		XSSFCellStyle  style= wb.createCellStyle();
                		XSSFCellStyle ceStyle = rowx.getCell(ent.getStartCol()+i).getCellStyle();
                		style.cloneStyleFrom(ceStyle);
                		if(ent.isHasBorder()){
                			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
                			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                        }else{
                        	style.setBorderBottom(HSSFCellStyle.BORDER_NONE);
                        	style.setBorderLeft(HSSFCellStyle.BORDER_NONE);
                			style.setBorderRight(HSSFCellStyle.BORDER_NONE);
                			style.setBorderTop(HSSFCellStyle.BORDER_NONE);
                        }
                		styleList.add(style);
                	}
                	
                	
                }
                //分别取各个列表对应的数据
            	for(int ix=0;ix<dtList.size();ix++){
            		ExcelDataEntity dt = dtList.get(ix);
            		Integer startRow=dt.getStartRow();
                	Integer startCol=dt.getStartCol();
                	
                    // header 设定
                    List<String> header = dt.getHeader();
                    
                    Font headerFont = wb.createFont();
                    headerFont.setFontHeightInPoints((short)11); //字体大小
                    headerFont.setFontName("ＭＳ Ｐゴシック");
                    headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
                    headerFont.setColor(HSSFColor.BLACK.index);    //黑字
                    headerStyle.setFont(headerFont);
                    headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直     
                    headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平  
                    
                    headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                    headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                    headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                    headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                    
                    XSSFRow row = sheet.getRow(startRow.intValue());  
                    if(row == null){
                    	row = sheet.createRow(startRow.intValue());
                    }
                    
                    for(int i=0 ;i<header.size();i++){
                    	XSSFCell ce = row.getCell(i+startCol);
                    	if(ce==null){
                    		ce=row.createCell(i+startCol);
                    	}
                    	ce.setCellStyle(headerStyle);
                    	ce.getCellStyle().setWrapText(true);
                    	ce.setCellValue(new XSSFRichTextString(header.get(i)));
                    	// 列自适应
                    	if(dt.getWidth().size()>0&&dt.getWidth().size()==header.size()){
                    		sheet.setColumnWidth(i+startCol, dt.getWidth().get(i));
                    	}
                    	
                    }
                    
// 160328 DaiLei Delete Begin
//                    List<List<String>> list = dt.getData();
// 160328 DaiLei Delete End
// 160328 DaiLei Add Begin
                    List<List<Object>> list = dt.getData();
// 160328 DaiLei Add End
                    
                    List<Integer> mergStart = new ArrayList<Integer>();
                    for(int i= 0;i<list.get(0).size();i++){
                    	mergStart.add(i,-1);
                    }
                    
                    for(int j=0 ;j<list.size();j++){
                    	Integer jstart = startRow+j+1;
                        XSSFRow row2 = sheet.getRow(jstart.intValue());
                        if(row2 == null){
                        	row2=sheet.createRow(jstart.intValue());
                        }
                        //System.out.println(jstart);
                    	for(int i=0 ;i<list.get(j).size();i++){
                    		Integer istart = startCol+i;
                        	XSSFCell ce = row2.getCell(istart);
                        	if(ce == null){
                        		ce = row2.createCell(istart);
                        	}
                        		/*HSSFCell upCell = sheet.getRow(jstart.shortValue()-1).getCell(istart);
	
                        		cellStyle.cloneStyleFrom(upCell.getCellStyle());*/
                        		ce.setCellStyle(styleList.get(i));
                        		//System.out.println(jstart+":"+istart+"="+row2);

                        	//System.out.println(istart);
                        	//HSSFCell ce = row2.createCell(istart, HSSFCell.ENCODING_UTF_16);
// 160328 DaiLei Delete Begin
//                        	String val = list.get(j).get(i);
//                        	// 如果为数字类型的需要转型，否则excell第一次打开时，格式化不起作用
//                        	short format = ce.getCellStyle().getDataFormat(); 
//                        	if(!StringUtils.isEmpty(val)&&!val.startsWith(dt.getMergMark())){
//                        		if((format==224||format==225||format==226)||(format<=11&&format>=1)||(format<=44&&format>=23)||ce.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//                        			
//                        			if(val.startsWith("=")){
//                        				ce.setCellFormula(val.substring(1));
//                        			}else{
//                        				ce.setCellValue(Double.parseDouble(val));
//                        			}
//                        			
//                        		}else{
//                        			ce.setCellValue(new XSSFRichTextString(val));
//                        		}
//                        	}
//                    		
//
//                    		
//                        	// 合并单元格
//                        	if(val==null){
//                        		mergStart.set(i, j);
//                        	} else if(!val.startsWith(dt.getMergMark())){
//                        		if(mergStart.size()>0&&mergStart.get(i) != j-1){
//                        			sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i)+startRow+1, jstart-1, istart, istart));
//                        		}
//                        		mergStart.set(i, j);
//                        	}
//                        	if(val!=null && val.startsWith(dt.getMergMark())&&j==list.size()-1){
//                        		sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i)+startRow+1, jstart, istart, istart));
//                        	}
// 160328 DaiLei Delete End
// 160328 DaiLei Add Begin
                            Object val = list.get(j).get(i);
                            boolean isStringValue = val instanceof String;
                            String strVal = null;
                            if (isStringValue) {
                                strVal = (String)val;
                            }
                            // 如果为数字类型的需要转型，否则excell第一次打开时，格式化不起作用
                            short format = ce.getCellStyle().getDataFormat(); 
                            if(val != null && (isStringValue == false || !strVal.startsWith(dt.getMergMark()))) {
                                if((format==224||format==225||format==226)||(format<=11&&format>=1)||(format<=44&&format>=23)||ce.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
                                    
                                    if(isStringValue && strVal.startsWith("=")){
                                        ce.setCellFormula(strVal.substring(1));
                                    }else{
                                        ce.setCellValue(Double.parseDouble(String.valueOf(val)));
                                    }
                                    
                                }else{
                                    if (val instanceof Double) {
                                        ce.setCellValue((Double)val);
                                    } else if (val instanceof Integer) {
                                        ce.setCellValue((Integer)val);
                                    } else if (val instanceof BigDecimal) {
                                        ce.setCellValue(((BigDecimal) val).doubleValue());
                                    } else if (val instanceof String) {
                                        ce.setCellValue(new XSSFRichTextString(strVal));
                                    } else {
                                        ce.setCellValue(new XSSFRichTextString(String.valueOf(val)));
                                    }
                                }
                            }
                            // 合并单元格
                            if(val==null){
                                mergStart.set(i, j);
                            } else if(isStringValue == false || !strVal.startsWith(dt.getMergMark())){
                                if(mergStart.size()>0&&mergStart.get(i) != j-1){
                                    sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i)+startRow+1, jstart-1, istart, istart));
                                }
                                mergStart.set(i, j);
                            }
                            if(val!=null && isStringValue && strVal.startsWith(dt.getMergMark()) && j==list.size()-1){
                                sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i)+startRow+1, jstart, istart, istart));
                            }
// 160328 DaiLei Add End
                    	}
                    }
            	}
            	
            	customExcel(wb,index, dtLists);
        	}
        	return wb;
               
    }
    
    /**
     * 导出后台逻辑处理完后处理
     * @param mav
     * @param entity
     * @param req
     * @throws Exception
     */
    protected void postExportExcel(@ModelAttribute ModelAndView mav, @ModelAttribute E entity, HttpServletRequest req) throws Exception {};

    
    /**
     * 上传处理
     * 
     * @param mav 视图对象
     * @param entity 页面entity
     * @param request 页面请求对象
     * @param response 页面响应对象
     */
    @RequestMapping("/import")
    protected ModelAndView upload(@ModelAttribute ModelAndView mav,@ModelAttribute E entity,HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 系统日期格式化设定
    	super.setDateFormate(entity);
      	super.clearMessage();
        mav.setViewName(this.getViewName());
      	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获取文件
        List<MultipartFile> fileList = multipartRequest.getFiles("file");
        CommonsMultipartFile file = null;
        if(fileList.size()==0){
        	this.addPopMessage(new Message("请选择文件", MsgType.WarningAlert, "", ""));
        	return this.doSearch(mav, entity, request, request.getSession());
        }else{
        	file = (CommonsMultipartFile) fileList.get(0);
        	if(file.getSize()==0){
        		this.addPopMessage(new Message("文件内容不能为空", MsgType.WarningAlert, "", ""));
            	return this.doSearch(mav, entity, request, request.getSession());
        	}
        }
        if(checkFileData(file,entity,request)){
        	return this.doSearch(mav, entity, request, request.getSession());
        }else{
        	excuteDB(entity,request);
        };
        return this.doSearch(mav, entity, request, request.getSession());
    }
    
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
    protected String getCellFormatValue(XSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case XSSFCell.CELL_TYPE_NUMERIC:
            case XSSFCell.CELL_TYPE_FORMULA: {
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
// 160421 DaiLei Delete Begin
//                    cellvalue = String.valueOf(cell.getNumericCellValue());
// 160421 DaiLei Delete End
// 160421 DaiLei Add Begin
                    cellvalue = decimalFormat.format(cell.getNumericCellValue());
// 160421 DaiLei Add End
// 160328 DaiLei Add Begin
                    if (cellvalue.endsWith(".0")) {
                        cellvalue = cellvalue.replaceAll("[.]0", "");
                    }
// 160328 DaiLei Add End
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case XSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
    
    /**
     * 自定义清空session
     * @param session
     */
    protected void removeSession(@ModelAttribute E entity,HttpSession session) throws Exception {
    	if(entity.isRemoveCheckboxSession()){
    		String viewName = this.getViewName();
        	int lastslashIndex = viewName.lastIndexOf("/");
        	String pageName = viewName.substring(lastslashIndex+1);
        	session.removeAttribute(Constant.SESSION_CHECKBOX_KEY+pageName);
    	}
    }

}