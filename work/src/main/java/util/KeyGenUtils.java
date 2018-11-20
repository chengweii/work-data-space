package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: chengwei11
 * @since: 2018/9/17 20:34
 * @description: key生成工具
 */
public class KeyGenUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(KeyGenUtils.class);

    public static final String CACHE_KEY_SPLITOR = ":";
    private static final String CACHE_KEY_PREFIX = "PT" + CACHE_KEY_SPLITOR;

    /**
     * 生成缓存KEY
     *
     * @param groupKeys   业务值组合KEY
     * @return 缓存KEY
     */
    public static String genCacheKeys(String... groupKeys) {
        StringBuilder cacheKeys = new StringBuilder(CACHE_KEY_PREFIX);
        for (String groupKey : groupKeys) {
            cacheKeys.append(groupKey).append(CACHE_KEY_SPLITOR);
        }
        String finalCacheKey = cacheKeys.toString();
        finalCacheKey = finalCacheKey.substring(0, finalCacheKey.length() - 1);

        return finalCacheKey;
    }
}
