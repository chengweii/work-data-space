package spi.impl;

import spi.SpiTestApi;

/**
 * @author: chengwei11
 * @since: 2018/10/18 15:10
 * @description:
 */
public class SpiTestB implements SpiTestApi {
    @Override
    public String hello(String content) {
        return "SpiTestB";
    }
}
