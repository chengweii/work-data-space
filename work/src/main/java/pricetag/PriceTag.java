package pricetag;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.ParseException;

public class PriceTag {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final Integer SO_TIMEOUT = 5000;
    private static final Integer CONNECTION_TIMEOUT = 5000;
    /**
     * post
     *
     * @param url
     * @param params
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String post(String url, String params) {
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost httpost = new HttpPost(url);
            HttpParams httpParams = httpost.getParams();
            httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
            httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
            String result = null;
            httpost.setEntity(new StringEntity(params, DEFAULT_CHARSET));
            HttpResponse response = client.execute(httpost);
            result = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        String xml ="";
        String url = "http://47.100.50.216:35005/Market/marketController/updateService.do";
        System.out.println(post(url,xml));
    }
}
