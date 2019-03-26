import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import util.GsonUtil;

import java.util.*;

public class Test {
	private final static List<Integer> VALID_PROMOTION_TYPE = Lists.newArrayList(1,2);

	public static void main(String[] args) throws Exception {
		System.out.println("顶顶顶顶顶【士大夫士大夫】".replaceFirst("^【.*?】",""));
	}

	public static <T> T get(String json){
		T result = GsonUtil.getEntityFromJson(json,new TypeToken<T>(){});
		return result;
	}

	private static long random(long begin, long end) {
		return (long) (begin + Math.random() * (end - begin + 1));
	}
}
