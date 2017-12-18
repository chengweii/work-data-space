package spider.panduoduo.detail;

import com.geccocrawler.gecco.annotation.Attr;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl = Main.BASE_PATH + "/go/{sourcenum}", pipelines = "panduoduoDetailPipeline")
public class PanduoduoDetail implements HtmlBean {
	private static final long serialVersionUID = 1L;

	@Request
	private HttpRequest request;

	@Attr(value = "href")
	@HtmlField(cssPath = "a")
	private String url;

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
