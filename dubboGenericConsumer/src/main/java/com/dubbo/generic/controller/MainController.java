package com.dubbo.generic.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dubbo.generic.dto.RequestDto;
import com.dubbo.generic.factory.DubboServiceFactory;

@RestController
public class MainController {
//	 
//	{
//	    "interfaceName": "demoServiceImpl", 
//	    "methodName": "sayHello", 
//	    "methodParams": [
//	        {
//	            "name": "yujinyu oyeh"
//	        }
//	    ]
//	}
	
//	@Autowired
//	DubboServiceFactory serviceFactory;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/gw", method = RequestMethod.POST)
     public Object getUser(HttpServletRequest request) {
		//获取到JSONObject
	    JSONObject jsonParam = this.getJSONParam(request);
	    String interfaceName = (String) jsonParam.get("interfaceName");
	    String methodName = jsonParam.getString("methodName");
		 System.out.println("interfaceName:"+interfaceName);
         Map map = new HashMap<>();
         map.put("ParamType", "java.lang.String");  //后端接口参数类型
         map.put("Object", "yujinyu orya");  //用以调用后端接口的实参

         List<Map<String, Object>> paramInfos= new ArrayList<>();
         paramInfos.add(map);

         DubboServiceFactory dubbo = DubboServiceFactory.getInstance();

         return dubbo.genericInvoke(interfaceName,methodName, paramInfos);
         
         
     }
	
	
	public JSONObject getJSONParam(HttpServletRequest request){
	    JSONObject jsonParam = null;
	    try {
	        // 获取输入流
	        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

	        // 写入数据到Stringbuilder
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = streamReader.readLine()) != null) {
	            sb.append(line);
	        }
	        jsonParam = JSONObject.parseObject(sb.toString());
	        // 直接将json信息打印出来
	        System.out.println(jsonParam.toJSONString());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return jsonParam;
	}
	
		
		
}

