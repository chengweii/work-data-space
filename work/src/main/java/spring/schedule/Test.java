package spring.schedule;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(TaskTest.class);
    }
}
