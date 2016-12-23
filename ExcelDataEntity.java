package framework.entity;

import java.util.ArrayList;
import java.util.List;

public class ExcelDataEntity {
	/** 标题行 */
	List<String> header = new ArrayList<String>();
	/** 列宽度设定 */
	List<Integer> width = new ArrayList<Integer>();
	List<List<Object>> data = new ArrayList<List<Object>>();
	/** 开始行 */
	Integer startRow=0;
	/** 开始列 */
	Integer startCol=0;
	/** 是否设定边框 */
	boolean isHasBorder = true;
	/** 合并单元格标志  */
	String mergMark = "$";
	/** 其他信息*/
	List<String> otherInfoList = new ArrayList<String>();



	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public List<Integer> getWidth() {
		return width;
	}

	public void setWidth(List<Integer> width) {
		this.width = width;
	}


	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getStartCol() {
		return startCol;
	}

	public void setStartCol(Integer startCol) {
		this.startCol = startCol;
	}

	public List<List<Object>> getData() {
		return data;
	}

	public void setData(List<List<Object>> data) {
		this.data = data;
	}

	public boolean isHasBorder() {
		return isHasBorder;
	}

	public void setHasBorder(boolean isHasBorder) {
		this.isHasBorder = isHasBorder;
	}

	public String getMergMark() {
		return mergMark;
	}

	public void setMergMark(String mergMark) {
		this.mergMark = mergMark;
	}

	/**
	 * @return the otherInfoList
	 */
	public List<String> getOtherInfoList() {
		return otherInfoList;
	}

	/**
	 * @param otherInfoList the otherInfoList to set
	 */
	public void setOtherInfoList(List<String> otherInfoList) {
		this.otherInfoList = otherInfoList;
	}
	
	
}
