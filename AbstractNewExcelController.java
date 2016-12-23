package framework.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import framework.entity.BaseEntity;
import framework.entity.ExcelDataEntity;

public abstract class AbstractNewExcelController<E extends BaseEntity> extends
		BaseController {

	/**
	 * 导出EXCELL
	 * 
	 * @param entity 页面entity
	 * @param request 页面请求对象
	 * @param response 页面响应对象
	 * @throws Exception
	 */
	@RequestMapping("/export")
	protected void exportExcel(@ModelAttribute E entity,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.setDateFormate(entity);
		String filePath = "/excelTemplate/" + this.setExcelTemplateName();
		String ctxpath = request.getSession().getServletContext().getRealPath(filePath);
		File reFile = new File(ctxpath);
		CreatListExcell(setDataSource(entity), reFile, response);
	}
	
	/**
	 * 导出EXCELL
	 * 
	 * @param gyousekiJsonData 業績データ
	 * @param gyousekiJsonRyData 業績データ(従業員)
	 * @param request request
	 * @param response response
	 * @throws Exception
	 */
	@RequestMapping("/exportLocal")
	@ResponseBody
	protected Map<String, Object> exportLocalExcel(
			String gyousekiJsonData, String gyousekiJsonRyData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String filePath = "/excelTemplate/" + this.setExcelTemplateName();
		String ctxpath = request.getSession().getServletContext().getRealPath(filePath);
		File reFile = new File(ctxpath);
		CreatListExcellCustom(setDataSource(gyousekiJsonData, gyousekiJsonRyData), reFile, response);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("result", true);
		modelMap.put("msg", this.getMessage());
		return modelMap;
	}

	/**
	 * 设定帐票名称
	 */
	public abstract String setExcelTemplateName() throws Exception;

	/**
	 * 自定义帐票内容
	 */
	public abstract void customExcel(XSSFWorkbook wb, int sheetIndex, List<List<ExcelDataEntity>> dtLists) throws Exception;

	/**
	 * 设定帐票主数据源
	 * 
	 * @param gyousekiJsonData 業績データ
	 * @param gyousekiJsonRyData 業績データ(従業員)
	 * @return 主数据源
	 */
	public abstract List<List<ExcelDataEntity>> setDataSource(String gyousekiJsonData, String gyousekiJsonRyData) throws Exception;
	
	/**
	 * 设定帐票主数据源
	 * 
	 * @param entity 页面entity
	 * @return 主数据源
	 */
	public abstract List<List<ExcelDataEntity>> setDataSource(@ModelAttribute E entity) throws Exception;

	/**
	 * 设定帐票参数
	 * 
	 * @param entity 页面entity
	 * @return 参数Map
	 */
	private void CreatListExcell(List<List<ExcelDataEntity>> dtLists, File template, HttpServletResponse response) {
		try {
			InputStream is = new FileInputStream(template);
			XSSFWorkbook wb = new XSSFWorkbook(is);
			for (int index = 0; index < dtLists.size(); index++) {
				List<ExcelDataEntity> dtList = dtLists.get(index);
				XSSFSheet sheet = wb.getSheetAt(index);
				for (int ix = 0; ix < dtList.size(); ix++) {
					ExcelDataEntity dt = dtList.get(ix);
					Integer startRow = dt.getStartRow();
					Integer startCol = dt.getStartCol();
					// header 设定
					List<String> header = dt.getHeader();
					XSSFCellStyle headerStyle = wb.createCellStyle(); // 样式对象
					Font headerFont = wb.createFont();
					headerFont.setFontHeightInPoints((short) 11); // 字体大小
					headerFont.setFontName("ＭＳ Ｐゴシック");
					headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
					// headerFont.setColor(XSSFColor.BLACK.index); //黑字
					headerStyle.setFont(headerFont);
					headerStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
					headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平
					headerStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
					headerStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
					headerStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
					headerStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

					XSSFRow row = sheet.createRow(startRow.shortValue());
					for (int i = 0; i < header.size(); i++) {
						XSSFCell ce = row.createCell(i + startCol);
						ce.setCellStyle(headerStyle);
						ce.getCellStyle().setWrapText(true);
						ce.setCellValue(new XSSFRichTextString(header.get(i)));
						// 列自适应
						if (dt.getWidth().size() > 0 && dt.getWidth().size() == header.size()) {
							sheet.setColumnWidth(i + startCol, dt.getWidth().get(i));
						}
					}

					List<Integer> mergStart = new ArrayList<Integer>();
					for (int i = 0; i < dt.getData().get(0).size(); i++) {
						mergStart.add(i, -1);
					}
					List<List<Object>> list = dt.getData();
					for (int j = 0; j < list.size(); j++) {
						Integer jstart = startRow + j + 1;
						XSSFRow row2 = sheet.createRow(jstart.shortValue());
						for (int i = 0; i < list.get(j).size(); i++) {
							Integer istart = startCol + i;
							XSSFCell ce = row2.createCell(istart);
							Object val = list.get(j).get(i);
							boolean isStringValue = val instanceof String;
							String strVal = null;
							if (isStringValue) {
								strVal = (String) val;
							}
							// 如果为数字类型的需要转型，否则excell第一次打开时，格式化不起作用
							short format = ce.getCellStyle().getDataFormat();
							if (val != null && (isStringValue == false || !strVal.startsWith(dt.getMergMark()))) {
								if ((format == 226) || (format <= 11 && format >= 1) || (format <= 44 && format >= 23) 
										|| ce.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
									if (isStringValue && strVal.startsWith("=")) {
										ce.setCellFormula(strVal.substring(1));
									} else {
										ce.setCellValue(Double.parseDouble(String.valueOf(val)));
									}
								} else {
									if (val instanceof Double) {
										ce.setCellValue((Double) val);
									} else if (val instanceof Integer) {
										ce.setCellValue((Integer) val);
									} else if (val instanceof String) {
										ce.setCellValue(new XSSFRichTextString(strVal));
									} else {
										ce.setCellValue(new XSSFRichTextString(String.valueOf(val)));
									}
								}
							}
							// 设定border
							if (dt.isHasBorder()) {
								XSSFCellStyle cellStyle = wb.createCellStyle();
								cellStyle.cloneStyleFrom(ce.getCellStyle());
								cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
								cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
								cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
								cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
								cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
								ce.setCellStyle(cellStyle);
							}
							if (isStringValue == false || !strVal.startsWith(dt.getMergMark())) {
								if (mergStart.size() > 0 && mergStart.get(i) != j - 1) {
									sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i) + startRow + 1, jstart - 1, istart, istart));
								}
								mergStart.set(i, j);
							}
							if ((isStringValue == false || !strVal.startsWith(dt.getMergMark())) && j == list.size() - 1) {
								sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i) + startRow + 1, jstart, istart, istart));
							}
						}
					}
				}
				customExcel(wb, index, dtLists);
			}
			/*** 这里是问题的关键，将这个工作簿写入到一个流中就可以输出相应的名字，这里需要写路径就ok了。 **/
			String pName = template.getName();
			int dotLastIndex = pName.lastIndexOf('.');
			String extension = pName.substring(dotLastIndex);
			String name = pName.substring(0, dotLastIndex);
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = format.format(new Date());
			pName = name + "_" + date + extension;
			FileOutputStream fileOut = new FileOutputStream("D:\\" + pName);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 设定帐票参数
	 * 
	 * @param entity 页面entity
	 * @return 参数Map
	 */
	private void CreatListExcellCustom(List<List<ExcelDataEntity>> dtLists, File template, HttpServletResponse response) {
		try {
			InputStream is = new FileInputStream(template);
			XSSFWorkbook wb = new XSSFWorkbook(is);
			for (int index = 0; index < dtLists.size(); index++) {
				List<ExcelDataEntity> dtList = dtLists.get(index);
				XSSFSheet sheet = wb.getSheetAt(index);
				for (int ix = 0; ix < dtList.size(); ix++) {
					ExcelDataEntity dt = dtList.get(ix);
					Integer startRow = dt.getStartRow();
					Integer startCol = dt.getStartCol();
					// header 设定
					List<String> header = dt.getHeader();
					XSSFCellStyle headerStyle = wb.createCellStyle(); // 样式对象
					Font headerFont = wb.createFont();
					headerFont.setFontHeightInPoints((short) 11); // 字体大小
					headerFont.setFontName("ＭＳ Ｐゴシック");
					headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
					// headerFont.setColor(XSSFColor.BLACK.index); //黑字
					headerStyle.setFont(headerFont);
					headerStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
					headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平
					headerStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
					headerStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
					headerStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
					headerStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

					XSSFRow row = sheet.createRow(startRow.shortValue());
					for (int i = 0; i < header.size(); i++) {
						XSSFCell ce = row.createCell(i + startCol);
						ce.setCellStyle(headerStyle);
						ce.getCellStyle().setWrapText(true);
						ce.setCellValue(new XSSFRichTextString(header.get(i)));
						// 列自适应
						if (dt.getWidth().size() > 0 && dt.getWidth().size() == header.size()) {
							sheet.setColumnWidth(i + startCol, dt.getWidth().get(i));
						}
					}

					List<Integer> mergStart = new ArrayList<Integer>();
					for (int i = 0; i < dt.getData().get(0).size(); i++) {
						mergStart.add(i, -1);
					}
					List<List<Object>> list = dt.getData();
					for (int j = 0; j < list.size(); j++) {
						Integer jstart = startRow + j + 1;
						XSSFRow row2 = sheet.createRow(jstart.shortValue());
						for (int i = 0; i < list.get(j).size(); i++) {
							Integer istart = startCol + i;
							XSSFCell ce = row2.createCell(istart);
							Object val = list.get(j).get(i);
							boolean isStringValue = val instanceof String;
							String strVal = null;
							if (isStringValue) {
								strVal = (String) val;
							}
							// 如果为数字类型的需要转型，否则excell第一次打开时，格式化不起作用
							short format = ce.getCellStyle().getDataFormat();
							if (val != null && (isStringValue == false || !strVal.startsWith(dt.getMergMark()))) {
								if ((format == 226) || (format <= 11 && format >= 1) || (format <= 44 && format >= 23) 
										|| ce.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
									if (isStringValue && strVal.startsWith("=")) {
										ce.setCellFormula(strVal.substring(1));
									} else {
										ce.setCellValue(Double.parseDouble(String.valueOf(val)));
									}
								} else {
									if (val instanceof Double) {
										ce.setCellValue((Double) val);
									} else if (val instanceof Integer) {
										ce.setCellValue((Integer) val);
									} else if (val instanceof String) {
										ce.setCellValue(new XSSFRichTextString(strVal));
									} else {
										ce.setCellValue(new XSSFRichTextString(String.valueOf(val)));
									}
								}
							}
							// 设定border
							if (dt.isHasBorder()) {
								XSSFCellStyle cellStyle = wb.createCellStyle();
								cellStyle.cloneStyleFrom(ce.getCellStyle());
								cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
								cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
								cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
								cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
								if (ix == 1 && i == 1) {
									cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
								} else {
									cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
								}
								ce.setCellStyle(cellStyle);
							}
							if (isStringValue == false || !strVal.startsWith(dt.getMergMark())) {
								if (mergStart.size() > 0 && mergStart.get(i) != j - 1) {
									sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i) + startRow + 1, jstart - 1, istart, istart));
								}
								mergStart.set(i, j);
							}
							if ((isStringValue == false || !strVal.startsWith(dt.getMergMark())) && j == list.size() - 1) {
								sheet.addMergedRegion(new CellRangeAddress(mergStart.get(i) + startRow + 1, jstart, istart, istart));
							}
						}
					}
				}
				customExcel(wb, index, dtLists);
			}
			/*** 这里是问题的关键，将这个工作簿写入到一个流中就可以输出相应的名字，这里需要写路径就ok了。 **/
			String pName = template.getName();
			int dotLastIndex = pName.lastIndexOf('.');
			String extension = pName.substring(dotLastIndex);
			String name = pName.substring(0, dotLastIndex);
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = format.format(new Date());
			pName = name + "_" + date + extension;
			FileOutputStream fileOut = new FileOutputStream("D:\\" + pName);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
