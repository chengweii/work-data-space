package spring.autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.BeanTest;

/**
 * 描述信息
 *
 * @author chengwei11
 * @date 2019/12/31
 */
public class AutowireTest {
    @Autowired
    public AutowireServiceTest autowireServiceTest;

    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext("spring/autowire");
        AutowireServiceTest beanTest = appContext.getBean(AutowireServiceTest.class);
        AutowireTest autowireTest = new AutowireTest();
        beanTest.capableBeanFactory.autowireBean(autowireTest);
        System.out.println(autowireTest.autowireServiceTest.getName());
    }
}
