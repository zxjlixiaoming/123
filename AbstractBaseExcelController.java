package framework.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;





import framework.entity.BaseEntity;
import framework.entity.ExcelDataEntity;

public abstract class AbstractBaseExcelController<E extends BaseEntity> extends BaseController {
	
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
    @RequestMapping("/export")
    protected void exportExcel(@ModelAttribute E entity,HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 系统日期格式化设定
    	super.setDateFormate(entity);
        String filePath = "/excelTemplate/" + this.setExcelTemplateName();

        String ctxpath = request.getSession().getServletContext().getRealPath(filePath);

        File reFile = new File(ctxpath);

        CreatListExcell(setDataSource(entity),reFile,response);
    }

 
    
    /**
     * 设定帐票名称
     */
    public abstract String setExcelTemplateName() throws Exception ;
    
    
    /**
     * 自定义帐票内容
     */
    public abstract void customExcel(HSSFWorkbook wb,int sheetIndex,List<List<ExcelDataEntity>> dtLists) throws Exception ;
    
    /**
     * 设定帐票主数据源
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
     */
    
    private void CreatListExcell(List<List<ExcelDataEntity>> dtLists,File template,HttpServletResponse response){
        try {     
        	InputStream is = new FileInputStream(template);
        	
        	HSSFWorkbook wb = new HSSFWorkbook(is);  
        	for(int index=0;index<dtLists.size();index++){
        		List<ExcelDataEntity> dtList = dtLists.get(index);
                HSSFSheet sheet = wb.getSheetAt(index);
            	for(int ix=0;ix<dtList.size();ix++){
            		ExcelDataEntity dt = dtList.get(ix);
            		Integer startRow=dt.getStartRow();
                	Integer startCol=dt.getStartCol();
                	
                    
                    
                /*    HSSFCellStyle style = wb.createCellStyle(); // 样式对象     
                    Font font = wb.createFont();
                    font.setFontHeightInPoints((short)11); //字体大小
                    font.setFontName("ＭＳ Ｐゴシック");
                    //style.setFont(font);
                    //style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直     
                    //style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平 
                    if(dt.isHasBorder()){
    	                style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	                style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	                style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	                style.setBorderTop(HSSFCellStyle.BORDER_THIN);
                    }*/
                    

                    // header 设定
                    List<String> header = dt.getHeader();
                    HSSFCellStyle headerStyle = wb.createCellStyle(); // 样式对象     
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
                    
                    HSSFRow row = sheet.getRow(startRow.shortValue());  
                    
                    for(int i=0 ;i<header.size();i++){
                    	HSSFCell ce = row.getCell(i+startCol);
                    	ce.setCellStyle(headerStyle);
                    	ce.getCellStyle().setWrapText(true);
                    	ce.setCellValue(new HSSFRichTextString(header.get(i)));
                    	// 列自适应
                    	if(dt.getWidth().size()>0&&dt.getWidth().size()==header.size()){
                    		sheet.setColumnWidth(i+startCol, dt.getWidth().get(i));
                    	}
                    	
                    }
                    
                    List<Integer> mergStart = new ArrayList<Integer>();
                    for(int i= 0;i<dt.getData().get(0).size();i++){
                    	mergStart.add(i,-1);
                    }
                    
// 160328 DaiLei Delete Begin
//                    List<List<String>> list = dt.getData();
// 160328 DaiLei Delete End
// 160328 DaiLei Add Begin
                    List<List<Object>> list = dt.getData();
// 160328 DaiLei Add End
                    for(int j=0 ;j<list.size();j++){
                    	Integer jstart = startRow+j+1;
                        HSSFRow row2 = sheet.getRow(jstart.shortValue());
                        System.out.println(jstart);
                    	for(int i=0 ;i<list.get(j).size();i++){
                    		Integer istart = startCol+i;
                        	HSSFCell ce = row2.getCell(istart);
                        	System.out.println(istart);
                        	//HSSFCell ce = row2.createCell(istart, HSSFCell.ENCODING_UTF_16);
// 160328 DaiLei Delete Begin
//                        	String val = list.get(j).get(i);
//                        	// 如果为数字类型的需要转型，否则excell第一次打开时，格式化不起作用
//                        	short format = ce.getCellStyle().getDataFormat(); 
//                        	if(!StringUtils.isEmpty(val)&&!val.startsWith(dt.getMergMark())){
//                        		if((format==226)||(format<=11&&format>=1)||(format<=44&&format>=23)||ce.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//                        			
//                        			if(val.startsWith("=")){
//                        				ce.setCellFormula(val.substring(1));
//                        			}else{
//                        				ce.setCellValue(Double.parseDouble(val));
//                        			}
//                        			
//                        		}else{
//                        			ce.setCellValue(new HSSFRichTextString(val));
//                        		}
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
                        		if((format==226)||(format<=11&&format>=1)||(format<=44&&format>=23)||ce.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
                        			
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
                                    } else if (val instanceof String) {
                                        ce.setCellValue(new HSSFRichTextString(strVal));
                                    } else {
                                        ce.setCellValue(new HSSFRichTextString(String.valueOf(val)));
                                    }
                                }
                        	}
// 160328 DaiLei Add End
                    		
                			
                    		// 设定border
                    		if(dt.isHasBorder()){
                    			HSSFCellStyle cellStyle = wb.createCellStyle();
                    			cellStyle.cloneStyleFrom(ce.getCellStyle());
                    			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                    			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                    			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                    			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                    			ce.setCellStyle(cellStyle);
                            }
                    		
                    		//ce.setCellStyle(style);
    							/*         货币样式设定$123,22           		 
    							  ce.setCellValue(Double.parseDouble(val));
                        		 HSSFCellStyle cellStyle = wb.createCellStyle();  
                                 HSSFDataFormat format= wb.createDataFormat();  
                                 String str = "($*#,##0.00_)";
                                 cellStyle.setDataFormat((short)7); 
                                 cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                                 cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                                 cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                                 cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                                 ce.setCellStyle(cellStyle);*/ 

                        	// 合并单元格
// 160328 DaiLei Delete Begin
//                        	if(!val.startsWith(dt.getMergMark())){
//                        		if(mergStart.size()>0&&mergStart.get(i) != j-1){
//                        			sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i)+startRow+1, jstart-1, istart, istart));
//                        		}
//                        		
//                        		mergStart.set(i, j);
//                        	}
//                        	if(val.startsWith(dt.getMergMark())&&j==list.size()-1){
//                        		sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i)+startRow+1, jstart, istart, istart));
//                        	}
// 160328 DaiLei Delete End
// 160328 DaiLei Add Begin
                        	if(isStringValue == false || !strVal.startsWith(dt.getMergMark())){
                        		if(mergStart.size()>0&&mergStart.get(i) != j-1){
                        			sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i)+startRow+1, jstart-1, istart, istart));
                        		}
                        		
                        		mergStart.set(i, j);
                        	}
                        	if((isStringValue == false || !strVal.startsWith(dt.getMergMark()))&&j==list.size()-1){
                        		sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i)+startRow+1, jstart, istart, istart));
                        	}
// 160328 DaiLei Add End
                        	
                        }
                    }
            	}
            	
            	customExcel(wb,index, dtLists);
          
                /***这里是问题的关键，将这个工作簿写入到一个流中就可以输出相应的名字，这里需要写路径就ok了。**/ 
              /*  FileOutputStream fileOut = new FileOutputStream("E:\\dd.xls");    
                wb.write(fileOut);    
                fileOut.close();*/
                 
        	}
 
               
               
            //第二种是输出到也面中的excel名称
            String pName= template.getName(); 
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
		    //response.setContentType("application/x-msdownload");   
		    response.setHeader("Content-Disposition","attachment; filename="+java.net.URLEncoder.encode(pName, "UTF-8") );//new String(pName.getBytes("gb2312"),"ISO-8859-1")+".xls");   
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
             
            System.out.print("OK");     
        } catch (Exception ex) {     
            ex.printStackTrace();     
        } 
    }
      
}
