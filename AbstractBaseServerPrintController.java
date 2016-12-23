package framework.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.awt.AppContext;
import framework.common.CommonUtil;
import framework.entity.BaseEntity;

public abstract class AbstractBaseServerPrintController<E extends BaseEntity> extends BaseController {
	
    /**
     * 打印账票(PDF格式)
     * 
     * @param entity
     *            页面entity
     * @param request
     *            页面请求对象
     * @param response
     *            页面响应对象
     * @throws Exception
     */
// 160606 DaiLei Delete Begin
//    @RequestMapping("/print")
//    protected void printToPdf(@ModelAttribute E entity,HttpServletRequest request, HttpServletResponse response) throws Exception {
// 160606 DaiLei Delete End
// 160606 DaiLei Add Begin
    @RequestMapping("/print")
	@ResponseBody
    protected Map<String, Object> printToPdf(@ModelAttribute E entity,HttpServletRequest request, HttpServletResponse response) throws Exception {
// 160606 DaiLei Add End
    	// 系统日期格式化设定
    	super.setDateFormate(entity);
// 160606 DaiLei Add Begin
    	Map<String, Object> result = new HashMap<String, Object>();
        // 设定打印机名称
        String printerName = this.setPrinterName(entity);
       //获取打印服务
        PrintService printService = null;
        
        if (printerName != null) {
            PrintService[] printlist = PrintServiceLookup.lookupPrintServices(null, null);
            for (int i = 0; i < printlist.length; i++) {
                if (printerName.equals(printlist[i].getName())) {
                    printService = printlist[i];
                    break;
                }
            }
// 160711 DaiLei Add Begin
			// 如果找不到打印机则再次从系统环境里面查询一次
			if (printService == null) {
				AppContext.getAppContext().put(PrintServiceLookup.class.getDeclaredClasses()[0], null);
				printlist = PrintServiceLookup.lookupPrintServices(null, null);
				for (int i = 0; i < printlist.length; i++) {
					if (printerName.equals(printlist[i].getName())) {
						printService = printlist[i];
						break;
					}
				}
			}
// 160711 DaiLei Add End
        }
        if (printService == null) {
        	result.put("Status", "1");
			result.put("Message", messageSource.getMessage("E263", null, getLocale(request)));
        	return result;
        }
// 160606 DaiLei Add End

    	// 得到帐票名称列表
    	List<String> reportNames = this.setReportName(entity);
    	// 得到帐票数据列表
    	List<List<BaseEntity>> reportDatas = this.setDataSource(entity);
    	// pdf打印列表
    	List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
    	List<JasperReport> reportList = new ArrayList<JasperReport>();
    	for(int i=0;i<reportNames.size();i++){
    		String filePath = "/reports/" + reportNames.get(i) + ".jasper";
            String ctxpath = request.getSession().getServletContext().getRealPath(filePath);
            File reFile = new File(ctxpath);
            
            JasperReport report = (JasperReport) JRLoader.loadObject(reFile);
            reportList.add(report);
    	}
    	
    	this.customReport(reportList,reportDatas,entity);
    	
    	for(int i=0;i<reportList.size();i++){
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportDatas.get(i));
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportList.get(i), this.setParameterMap(entity), ds);
            jasperPrintList.add(jasperPrint);
    	}
		
        // 个性化设置
        customJasperPrint(jasperPrintList);
        
// 160606 DaiLei Delete Begin
//        // 设定打印机名称
//        String printerName = this.setPrinterName(entity);
//       //获取打印服务
//        PrintService printService = null;
//        
//        if (null==printerName) {
//            printService = PrintServiceLookup.lookupDefaultPrintService();
//        } else {
//            PrintService[] printlist = PrintServiceLookup.lookupPrintServices(null, null);
//            for (int i = 0; i < printlist.length; i++) {
//                if (printerName.equals(printlist[i].getName())) {
//                    printService = printlist[i];
//                    break;
//                }
//            }
//        }
//        // 如果找不到打印机则再次从系统环境里面查询一次
//        if(printService == null){
//        	AppContext.getAppContext().put(PrintServiceLookup.class.getDeclaredClasses()[0], null);
//        	 if (null==printerName) {
//                 printService = PrintServiceLookup.lookupDefaultPrintService();
//             } else {
//                 PrintService[] printlist = PrintServiceLookup.lookupPrintServices(null, null);
//                 for (int i = 0; i < printlist.length; i++) {
//                     if (printerName.equals(printlist[i].getName())) {
//                         printService = printlist[i];
//                         break;
//                     }
//                 }
//             }
//        }
// 160606 DaiLei Delete End
        JRAbstractExporter je = new JRPrintServiceExporter();
        //设置打印内容
        je.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
        //设置指定打印机  
        je.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
        je.exportReport();

// 160606 DaiLei Add Begin
        result.put("Status", "0");
        return result;
// 160606 DaiLei Add End
    }

    /**
     * 设定帐票名称
     */
    public abstract List<String> setReportName(@ModelAttribute E entity) throws Exception ;
    
    /**
     * 设定帐票主数据源
     * 
     * @param entity
     *              页面entity
     * @return 主数据源 
     */
    public abstract List<List<BaseEntity>> setDataSource(@ModelAttribute E entity) throws Exception ;
    
    /**
     * 设定帐票参数
     * 
     * @param entity
     *              页面entity
     * @return 参数Map
     */
    protected Map<String, Object>  setParameterMap(@ModelAttribute E entity) throws Exception {return null;};
    
    /**
     * 个性化设置
     * 
     * @param jasperPrintList
     *              打印列表
     */
    protected void customJasperPrint(List<JasperPrint> jasperPrintList) throws Exception {}
    
    /**
     * 指定打印机名字
     * 
     *@return   打印机名字
     */
    protected String setPrinterName(@ModelAttribute E entity) throws Exception {return null;}
    
    /**
     * 自定义帐票属性
     * 
     * @param list
     *              帐票对象
     */
    protected void  customReport(List<JasperReport> list,List<List<BaseEntity>> reportDatas,@ModelAttribute E entity) throws Exception {
    	
    	// 从reportDatas里面得到需要设定的style，然后设定对应帐票的样式。
       //字体style关键字: plain bold italic line-through underline
    	
    	/*for(int i=0;i<list.size();i++){
    		// 从帐票数据里面得到相应帐票需要设定的样式
    		String style = "xxx,xxxpt,style=xxxxxx";
    		JRBand[] bands = list.get(i).getAllBands();
    		for(int j=0;j<bands.length;j++){
    			JRBand band = bands[j];
    			JRBaseTextField rectangle = (JRBaseTextField)band.getElementByKey("orderNoKey");
    			this.setReportFieldStyle(rectangle, style);
    			rectangle = (JRBaseTextField)band.getElementByKey("itemCDKey");
    			this.setReportFieldStyle(rectangle, style);
    		}
    	}*/
    };

	/**
	 * 帐票项目自定义函数
	 * @param field 帐票项目对象
	 * @param style 样式
	 * @throws Exception
	 */
	protected void setReportFieldStyle(JRBaseTextField field, String style)
			throws Exception {
		//plain bold italic line-through underline
		if (field != null && style != null) {
			String[] styles = style.split(",");
			if(styles.length>0 && !StringUtils.isEmpty(styles[0])){
				field.setFontName(styles[0]);
			}
			
			if(styles.length>1 && !StringUtils.isEmpty(styles[1])){
				field.setFontSize(Float.valueOf(styles[1].replace("pt", "")));
			}
			
			if(styles.length>2 && !StringUtils.isEmpty(styles[2])){
				if(styles[2].contains("plain")){
					// 粗体
					field.setBold(false);
					// 斜体
					field.setItalic(false);
				}
				if(styles[2].contains("bold")){
					// 粗体
					field.setBold(true);
				}
				if(styles[2].contains("italic")){
					// 斜体
					field.setItalic(true);
				}
				if(styles[2].contains("line-through")){
					// 删除线
					field.setStrikeThrough(true);
				}
				if(styles[2].contains("underline")){
					// 下标线
					field.setUnderline(true);
				}
			}
		}

	}
}
