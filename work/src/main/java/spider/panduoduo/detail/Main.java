package spider.panduoduo.detail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.geccocrawler.gecco.GeccoEngine;

import util.DBHelper;

public class Main {
	protected static final String BASE_PATH = "http://106.14.95.104:8080";
	protected static final String COOKIE = "bdshare_firstime=1476092847584; BAIDUID=4438CAB8B01BDF576221D9A2A93BFCDD:FG=1; PSTM=1486555859; BIDUPSID=841D208790DB292C63A698899B1BEEC6; panlogin_animate_showed=1; FP_UID=7847fce1984cf3ac886993b3288cc2df; BDUSS=EwbjNCNVIyd1ZRZTA2SXgzUVpUdHlidVJHb1h2SmZRU2FtOWstQXFIMEJEUmhaSVFBQUFBJCQAAAAAAAAAAAEAAAAeKhFBZG5mMTk5MDEyAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGA8FgBgPBYTG; Hm_lvt_f5f83a6d8b15775a02760dc5f490bc47=1499239369; PANWEB=1; MCITY=-%3A; STOKEN=12592436f17a9d922c9ab63fd343112a40d4f91c5fba5e36f0a2772b9f0adead; SCRC=84c5f8496f71d6d69c02220ed95c6f5b; BDSFRCVID=b0ksJeC62iVRmi7A-vzN5Ta9SHGCmenTH6aoxl5BKKTeVyUfcPNcEG0PqM8g0Ku-Nb29ogKK0mOTHvoP; H_BDCLCKID_SF=tR4t_K0-fC03fP36q45H24k0-qrtetJyaR3aonvbWJ5TMCoY3l5VMj-FqqjEWjQnKR5gap3naR5_ShPC-tnWh5LYyPRnKl58QCc2bprJ3l02V-jEe-t2ynQDXxKHq4RMW20jWl7mWPQoVKcnK4-Xj6oQDGrP; BDRCVFR[S_ukKV6dOkf]=mk3SLVN4HKm; BDCLND=8IhwJi267GqUqI9NSs5urMier7jZsw2P; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; PSINO=1; H_PS_PSSID=1428_24565_21081_17001_22525_25177_22160; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0=1512039279,1513578112,1513596985; Hm_lpvt_7a3960b6f067eb0085b7f96ff5e660b0=1513597686; PANPSC=10286139613337635681%3AfmP3UmPTpA0RDw%2FNu8mPdnBNEdwvmnbwaB%2B%2FoDWCwAVKu8WyLCLL4eXStNSa0XHHVNM39DxNh9xGLq3xbs6PL9C4%2Fz3npyWjfXOsWjuKC4FuWt7N7jFSoGVWcHP9mn3MPjCB1D%2FVxYyaB5R8hpvpEDWPC7NKcJhVB7o5UgPoMUGwcc33C7HckA%3D%3D";

	public static List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	public static int keywordIndex = 0;
	static {
		try {
			String sql = "select * from `test`.`panduoduo`";
			items = DBHelper.queryForList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GeccoEngine.create().classpath("spider").start(BASE_PATH + "/go/38343167")
		.thread(1).loop(false).mobile(false).interval(4000).start();
	}
}
