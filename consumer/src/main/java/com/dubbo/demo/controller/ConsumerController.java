package com.dubbo.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.api.DemoService;



@RestController
public class ConsumerController {
	
	@Reference(interfaceName = "demoServiceImpl")
	private DemoService demoService;
	
	@RequestMapping("/hello") //http://localhost:8080/hello
	public String sayHello(String name) {
		String result = demoService.sayHello(name);
		
		return result;
			
	}
}

