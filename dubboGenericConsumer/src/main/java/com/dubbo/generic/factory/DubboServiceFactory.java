package com.dubbo.generic.factory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;

//@Component
//@PropertySource("classpath:dubbo.properties")
public class DubboServiceFactory {

	private ApplicationConfig application;
	private RegistryConfig registry;
	
	//@Value("application.name")
	private String appName = "genericConsumer";
	
	//@Value("registry.address")
	private String registryAddress="zookeeper://127.0.0.1:5181";

	//@Value("registry.group")
	private String group="generic";


	private static class SingletonHolder {
		private static DubboServiceFactory INSTANCE = new DubboServiceFactory();
	}

	private DubboServiceFactory() {
		Properties prop = new Properties();
		ClassLoader loader = DubboServiceFactory.class.getClassLoader();
		System.out.println("registryAddress:"+registryAddress);
//
//		try {
//			System.out.println("registryAddress:"+registryAddress);
//			//prop.load(loader.getResourceAsStream("dubboconf.properties"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName(appName);
		
		// 这里配置了dubbo的application信息*(demo只配置了name)*，因此demo没有额外的dubbo.xml配置文件
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress(registryAddress);
		registryConfig.setGroup(group);
		// 这里配置dubbo的注册中心信息，因此demo没有额外的dubbo.xml配置文件

		this.application = applicationConfig;
		this.registry = registryConfig;

	}

	public static DubboServiceFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public Object genericInvoke(String interfaceClass, String methodName, List<Map<String, Object>> parameters) {

//		ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
//		reference.setApplication(application);
//		reference.setRegistry(registry);
//		reference.setInterface(interfaceClass); // 接口名
//		reference.setGeneric(false); // 声明为泛化接口
//
//		// ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
//		// 需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
//		// API方式编程时，容易忽略此问题。
//		// 这里使用dubbo内置的简单缓存工具类进行缓存
//
//		ReferenceConfigCache cache = ReferenceConfigCache.getCache();
//		GenericService genericService = cache.get(reference);
//		// 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
//
//		int len = parameters.size();
//		String[] invokeParamTyeps = new String[len];
//		Object[] invokeParams = new Object[len];
//		for (int i = 0; i < len; i++) {
//			invokeParamTyeps[i] = parameters.get(i).get("ParamType") + "";
//			invokeParams[i] = parameters.get(i).get("Object");
//		}
//		return genericService.$invoke(methodName, invokeParamTyeps, invokeParams);
//		
		
		
		ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
       
        reference.setApplication(application);
        reference.setRegistry(registry);
        // 设置调用的reference属性，下面只设置了协议、接口名、版本、超时时间
        reference.setProtocol("dubbo");
        reference.setInterface("com.dubbo.api.DemoService");
       // reference.setVersion("1.0.0");
        reference.setTimeout(1000);
        // 声明为泛化接口
        reference.setGeneric(true);
        // GenericService可以接住所有的实现
        GenericService genericService = reference.get();
       
        Object result = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{"123"});
        
        
        return result;
        
	}

}
