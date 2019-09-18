import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import util.DateUtil;
import util.GsonUtil;

import java.lang.reflect.Method;
import java.util.*;

public class Test {
	private final static List<Integer> VALID_PROMOTION_TYPE = Lists.newArrayList(1,2);
static{
	ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
}
	public static void main(String[] args) throws Exception {
		String json = "{\"@type\":\"Test$ResultWrapper\",\"expireTime\":60000,\"cacheKey\":\"sdfdddd\",\"result\":{\"@type\":\"AuctionInfo\",\"auctionRecordId\":31849500,\"auditState\":5B,\"bidder\":\"80581199-521402\",\"bidderNickName\":\"爱***夏\",\"cappedPrice\":1599.0D,\"cbjPrice\":1599.0D,\"created\":1563877067000,\"creater\":\"lixuejing3\",\"currentPrice\":506.0D,\"duringTime\":3000,\"endTime\":1564475533000,\"id\":115351592,\"lockedNum\":1,\"maxPrice\":600.0D,\"minPrice\":1.0D,\"modified\":1564475533000,\"offerTime\":1564475531000,\"productName\":\"【官翻95新】科沃斯扫地机器人扫拖一体家用智能 扫地拖地吸尘 定时预约 DH43（拖扫）\",\"productType\":3,\"shopBrief\":\"正品行货！售后无忧！品质经营！！！\",\"shopId\":661533,\"shopLogo\":\"/popshop/jfs/t4438/91/1649833268/13919/3258adfb/58e4a556N66252216.png\",\"shopName\":\"科沃斯拍拍优品家电专营店\",\"startPrice\":1.0D,\"startTime\":1564472533000,\"status\":3B,\"stockStorageType\":2,\"usedNo\":50316283023}}";

		ResultWrapper<AuctionInfo> resultWrapper = JSON.parseObject(json,ResultWrapper.class);
		Date dayEnd = new Date(DateUtil.getDateFormat("2019-07-19", DateUtil.DateFormatType.YYYY_MM_DD).getTime() - 1);
		Method[] ms = Test.class.getMethods();
		System.out.println(ms[0].getReturnType().equals(Test.class));
	}

	@FunctionalInterface
	public interface DaoAction<R> {
		/**
		 * 执行缓存源数据查询动作
		 *
		 * @return 缓存源数据结果
		 */
		R execute();
	}

	/**
	 * 缓存数据包装对象
	 *
	 * @param <R> 数据结果
	 */
	@Data
	public static class ResultWrapper<R> {
		private R result;
		private long expireTime;
		//private String cacheKey;
	}

	public static Test nihao(){
		System.out.println(Thread.currentThread() .getStackTrace()[1].getMethodName());
		return null;
	}

	/**
	 * 自定义参数Map
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static class ParamMap<K, V> extends HashMap<K, V> {
		/**
		 * @param <K>
		 * @param <V>
		 * @return
		 */
		public static <K, V> ParamMap<K, V> newParamMap() {
			return new ParamMap<K, V>();
		}

		/**
		 * @param key
		 * @param value
		 * @return
		 */
		public ParamMap<K, V> puts(K key, V value) {
			this.put(key, value);
			return this;
		}
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
