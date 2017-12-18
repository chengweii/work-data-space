package spider.panduoduo.detail;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.google.common.base.Strings;

import spider.HttpUtil;
import util.DBHelper;

@PipelineName("panduoduoDetailPipeline")
public class PanduoduoDetailPipeline implements Pipeline<PanduoduoDetail> {
	private static Logger LOGGER = Logger.getLogger(PanduoduoDetailPipeline.class);

	@Override
	public void process(PanduoduoDetail bean) {
		try {
			if (bean == null) {
				LOGGER.info("抓取结果为空。");
				return;
			}

			HttpRequest request = bean.getRequest();

			checkInvaild(bean.getUrl(), request.getParameter("sourcenum"));

			int currentIndex = Main.keywordIndex;
			Main.keywordIndex++;
			if (currentIndex < Main.items.size()) {
				String sourcenum = Main.items.get(currentIndex).get("url").toString()
						.replace("http://www.panduoduo.net/r/", "");
				String nextUrl = Main.BASE_PATH + "/go/" + sourcenum;
				Map<String, String> param = new HashMap<String, String>();
				param.put("sourcenum", sourcenum);
				request.setParameters(param);
				request.clearHeader();
				request.addHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1979.0 Safari/537.36");
				SchedulerContext.into(request.subRequest(nextUrl));
			}
		} catch (Exception e) {
			LOGGER.error("抓取异常。", e);
		}
	}

	private static Connection conn = null;
	static {
		conn = DBHelper.getConn();
	}

	private void updateData(String sourcenum) {
		try {
			String sql = "delete from `test`.`panduoduo` where url like '%" + sourcenum + "'";
			DBHelper.excuteUpdate(conn, sql);
		} catch (Exception e) {
			LOGGER.error("更新异常。" + sourcenum, e);
		}
	}

	private boolean checkInvaild(String url, String sourcenum) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1979.0 Safari/537.36");
		headers.put("Cookie", Main.COOKIE);
		String content = HttpUtil.get(url, "", headers);
		boolean invaild = content != null && content.contains("百度网盘-链接不存在");
		if (invaild) {
			LOGGER.info("文件失效," + sourcenum);
			updateData(sourcenum);
		}
		return invaild;
	}

	private boolean isVedio(String title) {
		String ltitle = title.toLowerCase();
		return ltitle.contains(".mp4") || ltitle.contains(".mkv") || ltitle.contains(".rmvb")
				|| ltitle.contains(".avi");
	}

	private String getDate(String publicDate) {
		return "";
	}

	private int transformSize(String size) {
		if (Strings.isNullOrEmpty(size)) {
			return 0;
		}
		size = size.replaceAll("\\s", "");
		String unit = size.replaceAll("[0-9]", "");
		int unitCount = Integer.parseInt(size.replaceAll("[^0-9]", ""));
		SizeUnit sizeUnit = SizeUnit.fromCode(unit);
		return unitCount * sizeUnit.getValue();
	}

	public static enum SizeUnit {
		B("Bytes", 0), KB("KB", 0), MB("MB", 1), GB("GB", 1024);
		private SizeUnit(String code, int value) {
			this.code = code;
			this.value = value;
		}

		private String code;
		private int value;

		public String getCode() {
			return code;
		}

		public int getValue() {
			return value;
		}

		public static SizeUnit fromCode(String code) {
			for (SizeUnit entity : SizeUnit.values()) {
				if (entity.getCode().equals(code)) {
					return entity;
				}
			}
			return B;
		}
	}
}