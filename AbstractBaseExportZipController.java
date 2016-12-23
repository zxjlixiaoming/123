package framework.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

public abstract class AbstractBaseExportZipController<E extends BaseEntity> extends BaseController {
    
    /**
     * 压缩文件导出
     * 
     * @param entity
     *            页面entity
     * @param request
     *            页面请求对象
     * @param response
     *            页面响应对象
     * @throws Exception
     */
    @RequestMapping("/exportZip")
    protected void exportExcel(@ModelAttribute E entity,HttpServletRequest request, HttpServletResponse response) throws Exception {
        // WangYunxia 160831 Add Start
        // 导出前设置session中 exportedFlag任意值
        request.getSession().setAttribute("exportedFlag", "false");
        // WangYunxia 160831 Add End
        // 系统日期格式化设定
        super.setDateFormate(entity);
        ZipOutputStream zipOut = null ; // 声明压缩流对象  
        zipOut = new ZipOutputStream(response.getOutputStream()) ; 

        List<String> filenames = setZipChildFileName(entity);
        List<byte[]> list = this.setDataSource(entity,request);
        //zip文件名称设定
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	    String date = format.format(new Date());
        String pName= this.setZipFileName(entity)+"_"+date+".zip"; 
        try {
            response.reset();   
            response.setContentType("application/zip;charset=UTF-8"); 
            response.setHeader("Content-Disposition","attachment; filename="+java.net.URLEncoder.encode(pName, "UTF-8") );  
            for(int i=0;i<filenames.size();i++){
                zipOut.putNextEntry(new ZipEntry(filenames.get(i)));
                zipOut.write(list.get(i));
            }
            // 关闭输出流  
            zipOut.close() ;   
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // WangYunxia 160831 Add Start
            // 导出后清空exportedFlag
            request.getSession().removeAttribute("exportedFlag");
            // WangYunxia 160831 Add End
        }

    }

    /**
     * 设定zip文件名称
     * 
     * @param entity
     *           页面entity
     * @return zip文件名
     */
    public abstract String setZipFileName(@ModelAttribute E entity) throws Exception ;
    
    /**
     * 设定zip子文件名称
     * 
     * @param entity
     *            页面entity
     * @return zip子文件名
     */
    public abstract List<String> setZipChildFileName(@ModelAttribute E entity) throws Exception ;
    
    /**
     * 设定ZIP文件数据源
     * 
     * @param entity
     *              页面entity
     * @return zip文件数据源
     */
    public abstract  List<byte[]> setDataSource(@ModelAttribute E entity,HttpServletRequest request) throws Exception ;
    
    /**
     * 取得帐票列表数据
     * 
     * @param dtLists
     *              列表数据
     * @param wb
     *                 Excel对象          
     * @return Excel数据字节
     */
    protected byte[] CreatListExcell(List<List<ExcelDataEntity>> dtLists,HSSFWorkbook wb){
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 标题样式  
        HSSFCellStyle headerStyle = wb.createCellStyle(); 
        Font headerFont = wb.createFont();
        // 取各个sheet对应的数据
        for(int index=0;index<dtLists.size();index++){
            List<ExcelDataEntity> dtList = dtLists.get(index);
            HSSFSheet sheet = wb.getSheetAt(index);
            List<HSSFCellStyle> styleList = new ArrayList<HSSFCellStyle>();
            if(dtList==null||dtList.size()==0){
                continue;
            }else{
                ExcelDataEntity ent = dtList.get(0);
                HSSFRow rowx = sheet.getRow(ent.getStartRow()+1);
                if(ent.getData()==null || ent.getData().size()==0){
                    continue;
                }
                for(int i= 0;i<ent.getData().get(0).size();i++){
                    // 设定border
                    HSSFCellStyle  style= wb.createCellStyle();
                    HSSFCellStyle ceStyle = rowx.getCell(ent.getStartCol()+i).getCellStyle();
                    style.cloneStyleFrom(ceStyle);
                    if(ent.isHasBorder()){
                        setCellStyle(style);
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
                
                // 设定header样式及字体
                setHeaderStyle(headerStyle, headerFont);
               
                HSSFRow row = sheet.getRow(startRow.shortValue());  
                if(row == null){
                    row = sheet.createRow(startRow.shortValue());
                }
                
                for(int i=0 ;i<header.size();i++){
                    HSSFCell ce = row.getCell(i+startCol);
                    if(ce==null){
                        ce=row.createCell(i+startCol);
                    }
                    setMyCellStyle(index, ce, headerStyle, wb);
                    ce.getCellStyle().setWrapText(true);
                    ce.setCellValue(new HSSFRichTextString(header.get(i)));
                    // 列自适应
                    if(dt.getWidth().size()>0&&dt.getWidth().size()==header.size()){
                        sheet.setColumnWidth(i+startCol, dt.getWidth().get(i));
                    }
                    
                }
                
// 160328 DaiLei Delete Begin
//                List<List<String>> list = dt.getData();
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
                    HSSFRow row2 = sheet.getRow(jstart.shortValue());
                    if(row2 == null){
                        row2=sheet.createRow(jstart.shortValue());
                    }
                    //System.out.println(jstart);
                    for(int i=0 ;i<list.get(j).size();i++){
                        Integer istart = startCol+i;
                        HSSFCell ce = row2.getCell(istart);
                        if(ce == null){
                            ce = row2.createCell(istart);
                        }
                            /*HSSFCell upCell = sheet.getRow(jstart.shortValue()-1).getCell(istart);

                            cellStyle.cloneStyleFrom(upCell.getCellStyle());*/
                            setMyCellStyle(index, ce, styleList.get(i), wb);
                            //System.out.println(jstart+":"+istart+"="+row2);

                        //System.out.println(istart);
                        //HSSFCell ce = row2.createCell(istart, HSSFCell.ENCODING_UTF_16);
// 160328 DaiLei Delete Begin
//                        String val = list.get(j).get(i);
//                        // 如果为数字类型的需要转型，否则excell第一次打开时，格式化不起作用
//                        short format = ce.getCellStyle().getDataFormat(); 
//                        if(!StringUtils.isEmpty(val)&&!val.startsWith(dt.getMergMark())){
//                            if((format==224||format==225||format==226)||(format<=11&&format>=1)||(format<=44&&format>=23)||ce.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
//                                
//                                if(val.startsWith("=")){
//                                    ce.setCellFormula(val.substring(1));
//                                }else{
//                                    ce.setCellValue(Double.parseDouble(val));
//                                }
//                                
//                            }else{
//                                ce.setCellValue(new HSSFRichTextString(val));
//                            }
//                        }
//                        // 合并单元格
//                        if(val==null){
//                            mergStart.set(i, j);
//                        } else if(!val.startsWith(dt.getMergMark())){
//                            if(mergStart.size()>0&&mergStart.get(i) != j-1){
//                                sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i)+startRow+1, jstart-1, istart, istart));
//                            }
//                            mergStart.set(i, j);
//                        }
//                        if(val!=null && val.startsWith(dt.getMergMark())&&j==list.size()-1){
//                            sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i)+startRow+1, jstart, istart, istart));
//                        }
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
                                    // Wangyunxia 20160405 Update Start
                                    //ce.setCellValue(Double.parseDouble(String.valueOf(val)));
                                    if(String.valueOf(val) == "") {
                                        ce.setCellValue("");
                                    } else {
                                        ce.setCellValue(Double.parseDouble(String.valueOf(val)));
                                    }
                                	// Wangyunxia 20160405 Update End
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
            
        }
        try {
            seLastCellStyle(wb);
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }
    
    /**
     * 设定表格标题header的样式及字体
     * @param headerStyle
     * @param headerFont
     */
    protected void setHeaderStyle(HSSFCellStyle headerStyle,Font headerFont){

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
    }
    
    /**
     * 设定表格内容单元格的样式
     * @param style
     */
    protected void setCellStyle(HSSFCellStyle style){
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    }
    
    /**
     * 设定表格内容单元格的样式
     * @param index
     * @param ce
     * @param style
     * @param wb
     */
    protected void setMyCellStyle(int index, HSSFCell ce, HSSFCellStyle style, HSSFWorkbook wb){
        ce.setCellStyle(style);
    }
    
    /**
     * 设定表格内容单元格的样式
     * @param wb
     */
    protected void seLastCellStyle(HSSFWorkbook wb){
    }
    
    /**
     * 设定zip子文件中子文件的名称
     * 
     * @param entity
     *            页面entity
     * @return zip子文件名
     */
    protected  List<String> setZipChildChildFileName(@ModelAttribute E entity) throws Exception{return null;}  ;
    
    /**
     * 生成zip子文件之子文件
     * 
     * @param list
     *            zip子文件数据流
     * @param entity
     *            页面entity
     * @return zip子文件名
     */
    protected byte[] creatZipFile(List<byte[]> list,@ModelAttribute E entity) throws Exception{
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	ZipOutputStream zipOut = new ZipOutputStream(out) ; 

        List<String> filenames = setZipChildChildFileName(entity);
        //zip文件名称设定
        for(int i=0;i<filenames.size();i++){
        	zipOut.putNextEntry(new ZipEntry(filenames.get(i)));
            zipOut.write(list.get(i));
        }     
        zipOut.close();  
        return out.toByteArray();   
    }
}
