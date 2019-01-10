package fastjson;

import com.alibaba.fastjson.JSONObject;
import util.GsonUtil;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("MSG", "SDFSDF\ndfdfdfd");
        System.out.println(JSONObject.toJSONString(data));
        Child child = new Child();
        child.c1 = "vvvv";
        child.p1 = "ffff";
        Third t = new Third();
        t.t = child;
        String json = GsonUtil.toJson(t);

        Third g = GsonUtil.getEntityFromJson(json, Third.class);
        g.t.getClass();
        System.out.println(g);
    }

    public static class Third {
        public Parent t;
    }

    public static class Parent {
        public String p1;
    }

    public static class Child extends Parent {
        public String c1;
    }
}
