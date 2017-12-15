package spider.htmlbean.panduoduo;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;

import util.FileUtil;

@PipelineName("panduoduoPipeline")
public class PanduoduoPipeline implements Pipeline<Panduoduo> {

	@Override
	public void process(Panduoduo bean) {
		try {
			for (PanduoduoItem item : bean.getPanduoduoItems()) {
				FileUtil.writeFileContent(JSON.toJSONString(item) + "\n", "E://panduoduo.txt", true);
			}

			HttpRequest request = bean.getRequest();
			int currentIndex = Integer
					.parseInt(bean.getCurrPage().replace("第", "").replace("页", "").replaceAll("\\s", ""));
			int nextIndex = currentIndex + 1;
			int totalIndex = Integer
					.parseInt(bean.getTotalPage().replace("共", "").replace("页", "").replaceAll("\\s", ""));
			if (currentIndex <= totalIndex) {
				String nextUrl = "";
				String currUrl = request.getUrl();
				nextUrl = currUrl.substring(0, currUrl.lastIndexOf("/") + 1) + nextIndex;
				SchedulerContext.into(request.subRequest(nextUrl));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}