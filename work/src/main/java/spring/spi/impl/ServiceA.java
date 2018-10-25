package spring.spi.impl;

import org.springframework.stereotype.Component;
import spring.spi.ServiceAdapter;

/**
 * @author: chengwei11
 * @since: 2018/10/24 11:13
 * @description:
 */
@Component
public class ServiceA implements Service, ServiceAdapter {
    @Override
    public String getBusinessKey() {
        return "1001";
    }

    @Override
    public String hello() {
        return "ServiceA";
    }
}
