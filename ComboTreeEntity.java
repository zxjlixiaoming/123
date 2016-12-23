package framework.entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Jquery EasyUI库的组合树ComboTree用entity
 * 用于生成Json对象
 * 
 * @author MR.ren
 *
 */
public class ComboTreeEntity{
	// 值
	private String id="";
	// 文本
	private String text="";
	// BOM树使用
	private String itemKbn;
	// heikai 0323 start
	// BOM树使用
	private String dlvrPackageFlg;
	// heikai 0323 end 
	// 子节点
	List<ComboTreeEntity> children = new ArrayList<ComboTreeEntity>();
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the children
	 */
	public List<ComboTreeEntity> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<ComboTreeEntity> children) {
		this.children = children;
	}
	public String getItemKbn() {
		return itemKbn;
	}
	public void setItemKbn(String itemKbn) {
		this.itemKbn = itemKbn;
	}
	public String getDlvrPackageFlg() {
		return dlvrPackageFlg;
	}
	public void setDlvrPackageFlg(String dlvrPackageFlg) {
		this.dlvrPackageFlg = dlvrPackageFlg;
	}

}
