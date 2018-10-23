package spi;

import java.util.ServiceLoader;

/**
 * @author: chengwei11
 * @since: 2018/10/18 15:10
 * @description:
 */
public class Test {
    public static void main(String[] args) throws Exception {
        ServiceLoader<SpiTestApi> loaders = ServiceLoader.load(SpiTestApi.class);
        SpiTestApi spiTestApi = null;
        for (SpiTestApi in : loaders) {
            if (Integer.valueOf(1001).equals(in.getAppId())) {
                spiTestApi = in;

            }
        }

        if (spiTestApi == null) {
            System.out.println("没有找到指定appid的SpiTest");
            return;
        }

        System.out.println(spiTestApi.hello(""));
    }
}
