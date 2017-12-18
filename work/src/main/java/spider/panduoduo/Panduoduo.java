package spider.panduoduo;

import java.util.List;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl = Main.BASE_PATH + "/s/name/{keyword}/{pagenum}", pipelines = "panduoduoPipeline")
public class Panduoduo implements HtmlBean {
	private static final long serialVersionUID = 1L;

	@Request
	private HttpRequest request;

	@HtmlField(cssPath = ".search-page>.row")
	private List<PanduoduoItem> panduoduoItems;

	@Text
	@HtmlField(cssPath = ".page-list>.current")
	private String currPage;

	@Text
	@HtmlField(cssPath = ".page-list>.pcount")
	private String totalPage;

	public String getCurrPage() {
		return currPage;
	}

	public void setCurrPage(String currPage) {
		this.currPage = currPage;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public List<PanduoduoItem> getPanduoduoItems() {
		return panduoduoItems;
	}

	public void setPanduoduoItems(List<PanduoduoItem> panduoduoItems) {
		this.panduoduoItems = panduoduoItems;
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

}
