package spring;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseTest {
	
	{
		System.out.println("BaseTest init");
	}
	
	@Autowired
	private ServiceTest service;

	protected boolean baseTest() {
		System.out.println(service.getName());
		return false;
	}
}
