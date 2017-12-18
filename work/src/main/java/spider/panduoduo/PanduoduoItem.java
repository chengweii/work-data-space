package spider.panduoduo;

import com.geccocrawler.gecco.annotation.Attr;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

public class PanduoduoItem implements HtmlBean {
	private static final long serialVersionUID = 1L;

	@RequestParameter("keyword")
	private String keyword;
	@RequestParameter("pagenum")
	private String pagenum;

	@Attr(value = "title")
	@HtmlField(cssPath = "h3>a")
	private String title;

	@Attr(value = "href")
	@HtmlField(cssPath = "h3>a")
	private String url;

	@Text
	@HtmlField(cssPath = "p>.size")
	private String size;

	@Text
	@HtmlField(cssPath = "p>.small")
	private String small;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPagenum() {
		return pagenum;
	}

	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
