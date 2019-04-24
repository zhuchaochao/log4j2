package com.ctsy.microservice.common.log4j2;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.AbstractLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import com.ctsy.microservice.common.helper.YamlPropertySourceHelper;
import com.ctsy.microservice.core.util.IpUtil;

/**
 * @author zhucc
 * @date 2019-04-23
 */
@Plugin(name = "spring" ,category = StrLookup.CATEGORY)
public class Log4j2ThreadLookup extends AbstractLookup{

	private static List<PropertySource<?>> propertySourceList = new ArrayList<>(0);
	
	static {
		propertySourceList = YamlPropertySourceHelper.getPropertySourceList();
	}
	
	private String getLocalIp() {
		return IpUtil.getLocalIP();
	}
	
	private String getProperty(String key) {
		String property = "";
		if(StringUtils.isEmpty(key)) {
			return property;
		}
		if("localIp".equals(key)) {
			property = getLocalIp();
		}else {
			for(PropertySource ps : propertySourceList) {
				property = (String) ps.getProperty(key);
				if(!StringUtils.isEmpty(property)) {
					return property;
				}
			}
		}
		return property;
	}
	
	@Override
	public String lookup(LogEvent event, String key) {
		return getProperty(key);
	}
	
}
