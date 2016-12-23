package business.master.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import framework.controller.AbstractBasePageListController;
import framework.entity.ExcelDataEntity;
import framework.utils.message.Message;
import framework.utils.message.MsgType;
import business.common.service.CommonService;
import business.master.entity.GYSK0210KontoroRukosutoEntity;
import business.master.service.GYSK0210KontoroRukosutoService;

/**
 * master信息一览
 * 
 */
@Controller
@RequestMapping("/gysk0210master")
public class GYSK0210KontoroRukosutoController extends
		AbstractBasePageListController<GYSK0210KontoroRukosutoEntity> {

	/** 画面操作服务类 */
	@Autowired
	private GYSK0210KontoroRukosutoService masterService;

	/** 共通操作服务类 */
	@Autowired
	private CommonService commonService;

	/**
	 * 视图设定
	 */
	@Override
	public String getViewName() {
		return "master/GYSK0210KontoroRukosuto";
	}

	/**
	 * 初期化设定
	 * 
	 * @param entity
	 *            页面数据对象
	 */
	@Override
	protected void init(GYSK0210KontoroRukosutoEntity entity) {

		// 初期化时是否检索数据
		entity.setSearchInitData(true);
	}

	/**
	 * 检索前数据处理
	 * 
	 * @param mav
	 *            视图对象
	 * @param entity
	 *            页面数据对象
	 * @param req
	 *            页面请求对象
	 * @return true/false
	 */
	@Override
	protected boolean preSearch(ModelAndView mav,
			GYSK0210KontoroRukosutoEntity entity, HttpServletRequest req) {

		// 自定义页面条数
		entity.setPageSize(22);

		return true;
	}

	@Override
	public List<GYSK0210KontoroRukosutoEntity> search(
			GYSK0210KontoroRukosutoEntity entity, HttpServletRequest req)
			throws Exception {

		// 一览列表
		List<GYSK0210KontoroRukosutoEntity> list = new ArrayList<GYSK0210KontoroRukosutoEntity>();

		list = masterService.selAll();
		// 查询结果不存在
		if (null == list || 0 == list.size()) {
			super.addPopMessage(new Message("検索結果が存在しない ", MsgType.InfoAlert,
					"clickBtnOK", ""));
		}
		return list;
	}

	/**
	 * 检索后数据处理
	 * 
	 * @param resultList
	 *            一览对象列表
	 * @param mav
	 *            视图对象
	 * @param entity
	 *            页面数据对象
	 * @param req
	 *            页面请求对象
	 */
	@Override
	protected void postSearch(List<GYSK0210KontoroRukosutoEntity> resultList,
			ModelAndView mav, GYSK0210KontoroRukosutoEntity entity,
			HttpServletRequest req) {
		// 页面数据设定等
		mav.addObject("entity", entity);
	}

	/**
	 * 检索条件保存
	 * 
	 * @param entity
	 *            页面数据对象
	 * @return 检索条件保存对象
	 */
	@Override
	protected GYSK0210KontoroRukosutoEntity saveCondition(
			GYSK0210KontoroRukosutoEntity entity) {
		GYSK0210KontoroRukosutoEntity ent = new GYSK0210KontoroRukosutoEntity();
		return ent;
	}

	@Override
	public void setSort(GYSK0210KontoroRukosutoEntity entity) throws Exception {
		// 默认排序设定
		entity.setSort("SHAINCD asc");
	}

	/**
	 * 插入数据
	 * 
	 * @param mav
	 *            视图对象
	 * @param entity
	 *            页面数据对象
	 * @param req
	 *            页面请求对象
	 * @param session
	 * @return 视图
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public ModelAndView insert(ModelAndView mav,
			GYSK0210KontoroRukosutoEntity entity, HttpServletRequest req,
			HttpSession session) throws Exception {
		// 插入数据保存到session
		req.getSession().setAttribute("shayinCd", entity.getSHAINCD());
		req.getSession().setAttribute("shayinNm", entity.getSHAINNM());
		req.getSession().setAttribute("const", entity.getCONST());

		// 一览列表
		List<GYSK0210KontoroRukosutoEntity> oldList = new ArrayList<GYSK0210KontoroRukosutoEntity>();
		oldList = masterService.selAll();
		boolean flag = doCheckItem(entity, oldList);
		if (flag) {
			String userId = req.getSession().getAttribute("UserId") == null ? ""
					: req.getSession().getAttribute("UserId").toString();
			entity.setUPDATEUSER(userId);
			entity.setCREATEUSER(userId);
			masterService.insert(entity);
		} else {
			// 数据已经存在，不能插入
			super.addPopMessage(new Message("該当データが既に存在して ，データを挿入して失敗する ",
					MsgType.InfoAlert, "clickBtnOK", ""));
			return mav;

		}
		super.initSearch(mav, entity, req, session);
		return mav;
	}

	/**
	 * 更新前数据处理
	 * 
	 * @param mav
	 *            视图对象
	 * @param entity
	 *            页面数据对象
	 * @param req
	 *            页面请求对象
	 * @return true/false
	 */
	@Override
	protected boolean preUpdate(ModelAndView mav,
			GYSK0210KontoroRukosutoEntity entity, HttpServletRequest req) {
		return true;
	}

	@Override
	public boolean update(ModelAndView mav,
			GYSK0210KontoroRukosutoEntity entity, HttpServletRequest req)
			throws Exception {

		// 更新数据保存到session
		req.getSession().setAttribute("shayinCd", entity.getSHAINCD());
		req.getSession().setAttribute("shayinNm", entity.getSHAINNM());
		req.getSession().setAttribute("const", entity.getCONST());

		String userId = req.getSession().getAttribute("UserId") == null ? ""
				: req.getSession().getAttribute("UserId").toString();
		entity.setUPDATEUSER(userId);
		int flag = masterService.update(entity);
		if (flag == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 删除前数据处理
	 * 
	 * @param mav
	 *            视图对象
	 * @param entity
	 *            页面数据对象
	 * @param req
	 *            页面请求对象
	 * @return true/false
	 */
	@Override
	protected boolean preDelete(ModelAndView mav,
			GYSK0210KontoroRukosutoEntity entity, HttpServletRequest req) {
		return true;
	}

	@Override
	public boolean delete(ModelAndView mav,
			GYSK0210KontoroRukosutoEntity entity, HttpServletRequest req)
			throws Exception {

		String shayincd = entity.getShayinCd();
		String[] stringArr = shayincd.split(",");
		for (int i = 0, j = stringArr.length; i < j; i++) {
			masterService.delete(stringArr[i]);
		}

		return true;
	}

	/**
	 * 删除后数据处理
	 * 
	 * @param mav
	 *            视图对象
	 * @param entity
	 *            页面数据对象
	 * @param req
	 *            页面请求对象
	 */
	@Override
	protected void postDelete(ModelAndView mav,
			GYSK0210KontoroRukosutoEntity entity, HttpServletRequest req) {

		// 页面数据设定等
		mav.addObject("entity", entity);
	}

	@Override
	public boolean checkFileData(CommonsMultipartFile file,
			GYSK0210KontoroRukosutoEntity entity, HttpServletRequest req)
			throws Exception {
		// 在session中取得更新者和插入者的信息
		String userId = req.getSession().getAttribute("UserId") == null ? ""
				: req.getSession().getAttribute("UserId").toString();

		// 判断文件类型
		String fileName = file.getOriginalFilename();
		String file_typename = fileName.substring(fileName.lastIndexOf('.'),
				fileName.length());
		if (!".xlsx".equals(file_typename) && !".xls".equals(file_typename)) {
			// 弹出错误信息，终止程序
			super.addPopMessage(new Message("ファイル型エラー   " + file_typename
					+ ",  もう一度導入Excelファイル ", MsgType.InfoAlert, "clickBtnOK",
					""));
			return true;
		}
		
		// 读取文件信息
		List<List<GYSK0210KontoroRukosutoEntity>> listFromFile = this
				.readExcel(file, file_typename);

		// 一览列表
		List<GYSK0210KontoroRukosutoEntity> oldList = new ArrayList<GYSK0210KontoroRukosutoEntity>();
		oldList = masterService.selAll();
		for (List<GYSK0210KontoroRukosutoEntity> list : listFromFile) {
			for (GYSK0210KontoroRukosutoEntity item : list) {

				if (item.getIpmortErrorMsg() != null
						&& !item.getIpmortErrorMsg().isEmpty()) {
					super.addPopMessage(new Message(item.getIpmortErrorMsg(),
							MsgType.InfoAlert, "clickBtnOK", ""));
					return true;
				}
				item.setUPDATEUSER(userId);
				item.setCREATEUSER(userId);
				boolean flag = doCheckItem(item, oldList);
				if (flag) {
					// 新规
					masterService.insert(item);
				} else {
					// 更新
					masterService.update(item);
				}
			}
		}

		return false;
	}

	public List<List<GYSK0210KontoroRukosutoEntity>> readExcel(
			CommonsMultipartFile file, String file_typename) {

		List<List<GYSK0210KontoroRukosutoEntity>> resultList = new ArrayList<List<GYSK0210KontoroRukosutoEntity>>();

		try {
			// 创建输入流，读取Excel
			InputStream is = file.getInputStream();
			// Excel的页签数量
			int sheet_size = 0;
			// 每个页签创建一个Sheet对象
			Sheet sheet = null;
			if (".xlsx".equals(file_typename)) {
				XSSFWorkbook wb = new XSSFWorkbook(is);
				// Excel的页签数量
				sheet_size = wb.getNumberOfSheets();
				for (int index = 0; index < sheet_size; index++) {
					List<GYSK0210KontoroRukosutoEntity> sheetList = new ArrayList<GYSK0210KontoroRukosutoEntity>();
					// 每个页签创建一个Sheet对象
					sheet = wb.getSheetAt(index);
					sheetList = doReadSheet(sheet);
					resultList.add(sheetList);
					if (sheetList.get(index).getIpmortErrorMsg() != null
							&& !sheetList.get(index).getIpmortErrorMsg()
									.isEmpty()) {
						break;
					}
				}
			} else {
				HSSFWorkbook wb = new HSSFWorkbook(is);
				// Excel的页签数量
				sheet_size = wb.getNumberOfSheets();
				for (int index = 0; index < sheet_size; index++) {
					List<GYSK0210KontoroRukosutoEntity> sheetList = new ArrayList<GYSK0210KontoroRukosutoEntity>();
					// 每个页签创建一个Sheet对象
					sheet = wb.getSheetAt(index);
					sheetList = doReadSheet(sheet);
					resultList.add(sheetList);
					if (sheetList.get(index).getIpmortErrorMsg() != null
							&& !sheetList.get(index).getIpmortErrorMsg()
									.isEmpty()) {
						break;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public List<GYSK0210KontoroRukosutoEntity> doReadSheet(Sheet sheet) {
		List<GYSK0210KontoroRukosutoEntity> resultList = new ArrayList<GYSK0210KontoroRukosutoEntity>();
		if(sheet.getLastRowNum()==0){
			GYSK0210KontoroRukosutoEntity entity = new GYSK0210KontoroRukosutoEntity();
			entity.setIpmortErrorMsg("データは存在しない ");
			resultList.add(entity);
			return resultList;
		}
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			GYSK0210KontoroRukosutoEntity entity = new GYSK0210KontoroRukosutoEntity();
			// 判断sheet页是不是三列数据
			int cul =sheet.getRow(0).getPhysicalNumberOfCells();
			if(cul!=3){
				entity.setIpmortErrorMsg("3列データを必要がある ，該当ファイルは"+cul+"列データ,フォーマットエラー");
				resultList.add(entity);
				break;
			};
			
			// sheet.getColumns()返回该页的总列数
			Row coloum = sheet.getRow(i);

			// 对单元格内容进行编辑
			// 社員番号
			if (coloum.getCell(0) == null) {
				entity.setIpmortErrorMsg("第"+i + "行：社員番号は必須入力，失敗を導入する ");
				resultList.add(entity);
				break;
			} else if (coloum.getCell(0).toString().length() > 6) {
				entity.setIpmortErrorMsg("第"+i + "行：社員番号の長さ最大は6位だ，失敗を導入する ");
				resultList.add(entity);
				break;
			} else {
				entity.setSHAINCD(coloum.getCell(0).toString());
			}

			// 社員番号
			if (coloum.getCell(1).toString().length() > 10) {
				entity.setIpmortErrorMsg("第"+i + "行：社員名 の長さ最大は10位だ，失敗を導入する ");
				resultList.add(entity);
				break;
			} else {
				entity.setSHAINNM(coloum.getCell(1).toString());
			}

			// 控制成本
			Cell cell_cb = coloum.getCell(2);
			if (cell_cb == null || cell_cb.toString().isEmpty()
					|| cell_cb.toString() == "null") {
				entity.setCONST(0);
			} else if (!StringUtils.isNumeric(cell_cb.toString().trim())) {
				entity.setIpmortErrorMsg("第"+i + "行：コントロールコストは半角純数字として、再入力してください，失敗を導入する ");
				resultList.add(entity);
				break;
			} else if (cell_cb.toString().trim().length() > 10) {
				entity.setIpmortErrorMsg("第"+i + "行：コントロールコストの長さ最大は10位だ，失敗を導入する ");
				resultList.add(entity);
				break;
			} else {
				String cost = coloum.getCell(2).toString();
				String[] arr = cost.split("\\.");
				entity.setCONST(Integer.valueOf(arr[0]));
			}

			resultList.add(entity);
		}
		return resultList;
	}

	private boolean doCheckItem(GYSK0210KontoroRukosutoEntity entity,
			List<GYSK0210KontoroRukosutoEntity> oldList) throws Exception {

		String newCd = entity.getSHAINCD();

		// 如果需要插入的数据在数据库中已经存在，就更新该数据。否则就插入一条。 返回值 true： 新规 返回值 false ：更新
		boolean flag = true;
		if (!oldList.isEmpty()) {
			for (GYSK0210KontoroRukosutoEntity oldItem : oldList) {

				if (newCd.equals(oldItem.getSHAINCD())) {
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
		}
		return flag;
	}

	@Override
	public void excuteDB(GYSK0210KontoroRukosutoEntity entity,
			HttpServletRequest req) throws Exception {
	}

	@Override
	public Integer getCount(GYSK0210KontoroRukosutoEntity entity,
			HttpServletRequest req) throws Exception {
		return 0;
	}

	@Override
	public String setExcelTemplateName() throws Exception {
		return null;
	}

	@Override
	public void customExcel(HSSFWorkbook wb, int sheetIndex,
			List<List<ExcelDataEntity>> dtLists) throws Exception {
	}

	@Override
	public List<List<ExcelDataEntity>> setDataSource(
			GYSK0210KontoroRukosutoEntity entity) throws Exception {
		return null;
	}
}
