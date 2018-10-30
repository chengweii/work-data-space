package spring.aspect;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.BeanTest;
import spring.aspect.point.PointTest;

/**
 * @author: chengwei11
 * @since: 2018/10/30 10:53
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext("spring.aspect");
        PointTest beanTest = appContext.getBean(PointTest.class);
        beanTest.save("hh","22");
        System.out.println();
    }
}
