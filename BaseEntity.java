package framework.entity;

import framework.common.Constant;

/**
 * BaseEntity基类
 *
 * @author FBSE
 *
 */
public class BaseEntity{

	// 分页(总件数)
    private Integer totalCount;

    // 分页(总页数)
    private Integer totalPage;

    // 分页(每页件数)
    private Integer pageSize;

    // 分页(当前页数)
    private Integer pageNum;

    // 排序字段
    private String sort;

    // 处理类型
    private String mode;

    // 分页标志(0不分页 1分页)
    private Integer pageFlg=0;

    // 检索页面的初期处理是否需要检索数据（false:不检索  true:检索)
    private boolean searchInitData = false;

    // 处理是否成功（true:成功  false:失败)
    private boolean success = true;

    // 是否清空SESSION中checkbox的信息
    private boolean removeCheckboxSession = true;

    // 系统设定的页面日期类型的格式
    private String viewDateFormateYMD;
    private String viewDateFormateYM;
    private String javaDateFormateYMD;
    private String javaDateFormateYM;

    // 存储过程用
    // 存储过程输入参数:页面ID
    private String pageId;
    // 存储过程输入参数:方法CD
    private String functionCd;
    // 存储过程输出参数:数量
    private Integer count;
    // 存储过程输出参数:错误代码
    private Integer errorCode;
    // 存储过程输出参数:错误行号
    private Integer errorLine;
    // 存储过程输出参数:错误信息
    private String errorMessage;
    // 存储过程输出参数:业务检查信息
    private String businessMsg;
    /**
     * @return totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount
     *            分页(总件数)
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return totalPage
     */
    public Integer getTotalPage() {
        return totalPage;
    }

    /**
     * @param totalPage
     *            分页(总页数)
     */
    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * @return pageSize
     */
    public Integer getPageSize() {
    	 // 每页件数以及指定页码的判断，NULL时设定初期值
        if (null == pageSize) {
            return Constant.PAGESIZE;

        }else{
        	return pageSize;
        }
    }

    /**
     * @param pageSize
     *            分页(每页件数)
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return pageNum
     */
    public Integer getPageNum() {
    	 // 每页件数以及指定页码的判断，NULL时设定初期值
        if (null == pageNum) {
            return Constant.PAGENUM;

        }else{
        	return pageNum;
        }

    }

    /**
     * @param pageNum
     *            分页(当前页数)
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

	public Integer getOffset() {
		if(this.pageNum==null||this.pageSize==null){
			return new Integer(0);
		}else{
			return (this.pageNum-1)*this.pageSize;
		}


	}

	public Integer getLimit() {
		return this.pageSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Integer getPageFlg() {
		return pageFlg;
	}

	public void setPageFlg(Integer pageFlg) {
		this.pageFlg = pageFlg;
	}

	public boolean isSearchInitData() {
		return searchInitData;
	}

	public void setSearchInitData(boolean searchInitData) {
		this.searchInitData = searchInitData;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	// 存储过程用
	/**
	 * @return the pageId
	 */
	public String getPageId() {
		return pageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	/**
	 * @return the functionCd
	 */
	public String getFunctionCd() {
		return functionCd;
	}

	/**
	 * @param functionCd the functionCd to set
	 */
	public void setFunctionCd(String functionCd) {
		this.functionCd = functionCd;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the errorCode
	 */
	public Integer getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorLine
	 */
	public Integer getErrorLine() {
		return errorLine;
	}

	/**
	 * @param errorLine the errorLine to set
	 */
	public void setErrorLine(Integer errorLine) {
		this.errorLine = errorLine;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the removeCheckboxSession
	 */
	public boolean isRemoveCheckboxSession() {
		return removeCheckboxSession;
	}

	/**
	 * @param removeCheckboxSession the removeCheckboxSession to set
	 */
	public void setRemoveCheckboxSession(boolean removeCheckboxSession) {
		this.removeCheckboxSession = removeCheckboxSession;
	}

	/**
	 * @return the viewDateFormateYMD
	 */
	public String getViewDateFormateYMD() {
		return viewDateFormateYMD;
	}

	/**
	 * @param viewDateFormateYMD the viewDateFormateYMD to set
	 */
	public void setViewDateFormateYMD(String viewDateFormateYMD) {
		this.viewDateFormateYMD = viewDateFormateYMD;
	}

	/**
	 * @return the viewDateFormateYM
	 */
	public String getViewDateFormateYM() {
		return viewDateFormateYM;
	}

	/**
	 * @param viewDateFormateYM the viewDateFormateYM to set
	 */
	public void setViewDateFormateYM(String viewDateFormateYM) {
		this.viewDateFormateYM = viewDateFormateYM;
	}

	/**
	 * @return the javaDateFormateYMD
	 */
	public String getJavaDateFormateYMD() {
		return javaDateFormateYMD;
	}

	/**
	 * @param javaDateFormateYMD the javaDateFormateYMD to set
	 */
	public void setJavaDateFormateYMD(String javaDateFormateYMD) {
		this.javaDateFormateYMD = javaDateFormateYMD;
	}

	/**
	 * @return the javaDateFormateYM
	 */
	public String getJavaDateFormateYM() {
		return javaDateFormateYM;
	}

	/**
	 * @param javaDateFormateYM the javaDateFormateYM to set
	 */
	public void setJavaDateFormateYM(String javaDateFormateYM) {
		this.javaDateFormateYM = javaDateFormateYM;
	}

	/**
	 * @return businessMsg
	 */
	public String getBusinessMsg() {
	    return businessMsg;
	}

	/**
	 * @param businessMsg 赋值 businessMsg
	 */
	public void setBusinessMsg(String businessMsg) {
	    this.businessMsg = businessMsg;
	}




}
