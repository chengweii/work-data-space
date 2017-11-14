package spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("serviceTest")
@Scope("singleton")
public class ServiceTest {
	{
		System.out.println("ServiceTest init");
	}
	
	@Value("serviceTest")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
