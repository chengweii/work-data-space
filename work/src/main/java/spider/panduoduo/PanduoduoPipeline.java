package spider.panduoduo;

import java.sql.Connection;
import java.util.Date;

import org.apache.log4j.Logger;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.SchedulerContext;
import com.google.common.base.Strings;

import util.DBHelper;

@PipelineName("panduoduoPipeline")
public class PanduoduoPipeline implements Pipeline<Panduoduo> {
	private static Logger LOGGER = Logger.getLogger(PanduoduoPipeline.class);

	@Override
	public void process(Panduoduo bean) {
		try {
			if (bean == null) {
				LOGGER.info("抓取结果为空。");
				return;
			}
			for (PanduoduoItem item : bean.getPanduoduoItems()) {
				saveData(item);
			}

			HttpRequest request = bean.getRequest();
			if (Strings.isNullOrEmpty(bean.getCurrPage()) || Strings.isNullOrEmpty(bean.getTotalPage())) {
				LOGGER.info("抓取结果为空。");
				Main.search();
			} else {
				int currentIndex = Integer
						.parseInt(bean.getCurrPage().replace("第", "").replace("页", "").replaceAll("\\s", ""));
				int nextIndex = currentIndex + 1;
				int totalIndex = Integer
						.parseInt(bean.getTotalPage().replace("共", "").replace("页", "").replaceAll("\\s", ""));
				if (nextIndex <= totalIndex) {
					String nextUrl = "";
					String currUrl = request.getUrl();
					nextUrl = currUrl.substring(0, currUrl.lastIndexOf("/") + 1) + nextIndex;
					SchedulerContext.into(request.subRequest(nextUrl));
					LOGGER.info("抓取下一页：" + nextUrl);
				} else {
					LOGGER.info("抓取完毕。");
				}
			}
		} catch (Exception e) {
			LOGGER.error("抓取异常。", e);
		}
	}

	private static Connection conn = null;
	static {
		conn = DBHelper.getConn();
	}

	private void saveData(PanduoduoItem data) {
		try {
			String sql = "INSERT INTO `test`.`panduoduo` (`title`,`url`, `file_size`, `public_date`) VALUES (?,?, ?, ?);";
			int size = transformSize(data.getSize());
			if (size > 80 && isVedio(data.getTitle())) {
				DBHelper.excuteUpdate(conn, sql, data.getTitle(), Main.BASE_PATH + data.getUrl(), size, new Date());
			} else {
				LOGGER.info("文件太小," + data.getTitle() + ",size:" + size);
			}

		} catch (Exception e) {
			LOGGER.error("抓取异常。" + data.getTitle(), e);
		}
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