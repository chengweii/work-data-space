import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import util.GsonUtil;

import java.util.*;

public class Test {
	private final static List<Integer> VALID_PROMOTION_TYPE = Lists.newArrayList(1,2);

	public static void main(String[] args) throws Exception {
		// System.out.println("顶顶顶顶顶【士大夫士大夫】".replaceFirst("^【.*?】",""));
		Demo demo = new Demo();
		demo.name = "测试";
		String dataJson = JSON.toJSONString(demo);
		//Demo d = get(dataJson);
		//System.out.println(d);
		Demo t = JSON.parseObject(JSON.toJSONString(new Object()),Demo.class);
		System.out.println(t);
	}

	public static <T> T get(String json){
		T result = GsonUtil.getEntityFromJson(json,new TypeToken<T>(){});
		return result;
	}

	private static long random(long begin, long end) {
		return (long) (begin + Math.random() * (end - begin + 1));
	}

	@Data
	public static class Demo{
		private String name;
	}
}
