import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import util.GsonUtil;
import util.MD5Util;

import java.io.IOException;
import java.util.*;

public class Test {
	private final static List<Integer> VALID_PROMOTION_TYPE = Lists.newArrayList(1,2);

	public static void main(String[] args) throws Exception {
		SimpleDelayJobData simpleDelayJobData = new SimpleDelayJobData();
		simpleDelayJobData.setEnvironment("proc");
		simpleDelayJobData.setBusinessKey("1000023");
		DelayJobData delayJobData = new DelayJobData();
		delayJobData.setTest("3333");
		simpleDelayJobData.setDelayJobData(delayJobData);
		String json = GsonUtil.toJson(simpleDelayJobData);
		SimpleDelayJobData<DelayJobData> result =  get(json);
		System.out.println(result);
	}

	public static <T> T get(String json){
		T result = GsonUtil.getEntityFromJson(json,new TypeToken<T>(){});
		return result;
	}

	private static long random(long begin, long end) {
		return (long) (begin + Math.random() * (end - begin + 1));
	}
}
