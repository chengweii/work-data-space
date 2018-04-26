package sign;

import com.google.common.collect.Maps;
import util.MD5Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class SignTest {
    public static void main(String[] args) {
        Map<String, String> requestParams = new HashMap<String, String>();
        requestParams.put("bonus_id", "113740099");

        System.out.println(getParamsStr(requestParams));
    }

    public static String getParamsStr(Map<String, String> requestParams) {
        Long nowTime = (new Date()).getTime();
        requestParams.put("time", nowTime.toString());

        try {
            String sign = getSign(requestParams);
            StringBuilder sb = new StringBuilder();

            for (String key : requestParams.keySet()) {
                sb.append(key).append("=").append(requestParams.get(key)).append("&");
            }
            String paramStr = sb.toString();
            paramStr = paramStr + "&sign=" + sign;
            return paramStr;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String API_APP_SECURITYKEY = "345ccf1d2f23ad6b6a5bac3771564erp";

    private static String getSign(Map<String, String> requestParams) throws UnsupportedEncodingException {
        Map<String, String> requestParamsNew = Maps.newHashMap();
        for (String key : requestParams.keySet()) {
            requestParamsNew.put(key.toLowerCase().replaceAll("_", ""), requestParams.get(key).toString());
        }

        List<String> params = new ArrayList<String>();
        for (String key : requestParamsNew.keySet()) {
            if ("sign".equals(key)) {
                continue;
            }
            params.add(key);
        }

        Collections.sort(params);

        String str = "";
        for (String paramKey : params) {
            str += paramKey + "=" + requestParamsNew.get(paramKey) + "&";
        }
        str = str.substring(0, str.length() - 1);

        if ("Yes".equals(requestParams.get("IsIosApp"))) {
            str = str.toLowerCase();
        } else {
            str = URLEncoder.encode(str.replaceAll("\\(", "").replaceAll("\\)", ""), "UTF-8").toLowerCase();
        }

        String requestParamsSign = MD5Util.getMD5String(API_APP_SECURITYKEY + MD5Util.getMD5String(str));
        return requestParamsSign;
    }
}
