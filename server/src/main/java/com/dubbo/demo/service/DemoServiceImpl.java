package com.dubbo.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.dubbo.api.DemoService;

//@Service(interfaceName = "demoServiceImpl")
public class DemoServiceImpl implements DemoService{

	@Override
	public String sayHello(String name) {
		
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());

		return "hello "+name;
	}	

}

