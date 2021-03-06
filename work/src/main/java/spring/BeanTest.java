package spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("beanTest")
@Scope("singleton")
public class BeanTest extends BaseTest{

	{
		System.out.println("BeanTest init");
	}
	
	@Value("beanTest")
	private String name;
	@Value("beanTestValue")
	private String value;

	public String getName() {
		baseTest();
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
