package jkd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.MD5Util;

public class App {
	public static void main(String[] args) throws Exception {
		String url = "http://merc.api.test.jiukuaidao.com/merchants_ii_shop/change_mobile.htm?api_ref=1&app_key=a06bc55afd0925b0391b9b1d1821c1e1410918ff&cps_code=applestore&device_token=c61e5a132d048382124c26cd0c16f67c7478b35f6b370a424fe797dc9f3a5410&device_type=iphone&guid=741ffedf0bf44765b91438bbf61f01f4&mobile1=13681497381&mobile=15750403192&pid=3&time=1514997220&user_code=k68mn40pi9q116mhhsr71514431279267&user_id=32453&version=2.1.0&sign=12910358e50899e954101f9c0345c879";
		Map<String, String> params = urlSplit(url);
		String sign = validateSign(params);
		System.out.print(sign);
	}

	private static final String PartnerSecurityKey = "345ccf1d2f23ad6b6a5bac37715643b3";

	public static String validateSign(Map<String, String> signParams) {
		List<String> params = new ArrayList<String>();
		for (String key : signParams.keySet()) {
			if ("sign".equals(key)) {
				continue;
			}
			params.add(key);
		}

		Collections.sort(params);
		StringBuilder paramBuilder = new StringBuilder("");
		for (String paramKey : params) {
			paramBuilder.append(paramKey).append("=").append(signParams.get(paramKey)).append("&");
		}
		String str = paramBuilder.toString();
		str = str.substring(0, str.length() - 1).toLowerCase();
		String maySign = "";
		synchronized (maySign) {
			maySign = MD5Util.getMD5String(PartnerSecurityKey + MD5Util.getMD5String(str));
		}
		return maySign;
	}

	private static String truncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;
		strURL = strURL.trim().toLowerCase();
		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				for (int i = 1; i < arrSplit.length; i++) {
					strAllParam = arrSplit[i];
				}
			}
		}
		return strAllParam;
	}

	public static Map<String, String> urlSplit(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();
		String[] arrSplit = null;
		String strUrlParam = truncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");
			if (arrSplitEqual.length > 1) {
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
			} else {
				if (arrSplitEqual[0] != "") {
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}
}
