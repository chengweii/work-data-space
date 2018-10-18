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
        for (SpiTestApi in : loaders) {
            System.out.println(in.getClass().getSimpleName());
        }
    }
}
