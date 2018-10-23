package spi.impl;

import spi.SpiTestApi;

/**
 * @author: chengwei11
 * @since: 2018/10/18 15:10
 * @description:
 */
public class SpiTestA implements SpiTestApi {

    @Override
    public Integer getAppId() {
        return 1001;
    }

    @Override
    public String hello(String content) {
        return "SpiTestA";
    }
}
