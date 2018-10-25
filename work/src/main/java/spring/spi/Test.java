package spring.spi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.spi.impl.Service;

/**
 * @author: chengwei11
 * @since: 2018/10/24 11:16
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext("spring.spi");
        ServiceAdapterFilter serviceAdapterFilter = appContext.getBean(ServiceAdapterFilter.class);
        Service service = serviceAdapterFilter.getServiceAdapterByBusinessKey("1003", Service.class);
        System.out.println(service.hello());
    }
}
