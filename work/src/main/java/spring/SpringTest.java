package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {
	public static void main(String[] args) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext("spring");
		BeanTest beanTest = appContext.getBean(BeanTest.class);
		System.out.println(beanTest.getName());
	}
}
