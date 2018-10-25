package spring.spi;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author: chengwei11
 * @since: 2018/10/24 11:12
 * @description:
 */
@Component
public class ServiceAdapterFilter implements ApplicationContextAware {
    private ApplicationContext applicationContext = null;

    public <T> T getServiceAdapterByBusinessKey(String businessKey, Class<?> serviceType) {
        String[] beanNames = applicationContext.getBeanNamesForType(serviceType);
        for (String name : beanNames) {
            ServiceAdapter serviceAdapter = (ServiceAdapter) applicationContext.getBean(name);
            if (serviceAdapter.getBusinessKey().equals(businessKey)) {
                return (T) serviceAdapter;
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}