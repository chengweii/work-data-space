package fastjson;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("MSG","SDFSDF\ndfdfdfd");
        System.out.println(JSONObject.toJSONString(data));
    }

}
