package com.ctsy.microservice.common.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

/**
 * @author zhucc
 * @date 2019-04-23
 */
public class YamlPropertySourceHelper {

	public static String getProperty(String name) {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		String property = "";
		try {
			Resource[] bresources = resolver.getResources(ResourceUtils.CLASSPATH_URL_PREFIX+"bootstrap.yml");
			for(Resource r : bresources) {
				List<PropertySource<?>> bps = new YamlPropertySourceLoader().load("bootstarp", r);
				for(PropertySource<?> p : bps) {
					property = (String) p.getProperty(name);
					if(!StringUtils.isEmpty(property)) {
						return property;
					}
				}
			}
			//如果在bootstrap.yml里找不到，则去application.yml里找
			Resource[] aresources = resolver.getResources(ResourceUtils.CLASSPATH_URL_PREFIX+"application.yml");
			for(Resource r : aresources) {
				List<PropertySource<?>> aps = new YamlPropertySourceLoader().load("application", r);
				for(PropertySource<?> p : aps) {
					property = (String) p.getProperty(name);
					if(!StringUtils.isEmpty(property)) {
						return property;
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return property;
	}
	
	public static List<PropertySource<?>> getPropertySourceList(){
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		List<PropertySource<?>> ps = new ArrayList<>();
		try {
			Resource[] bresources = resolver.getResources(ResourceUtils.CLASSPATH_URL_PREFIX+"bootstrap.yml");
			for(Resource r : bresources) {
				List<PropertySource<?>> bps = new YamlPropertySourceLoader().load("bootstarp", r);
				ps.addAll(bps);
			}
			//如果在bootstrap.yml里找不到，则去application.yml里找
			Resource[] aresources = resolver.getResources(ResourceUtils.CLASSPATH_URL_PREFIX+"application.yml");
			for(Resource r : aresources) {
				List<PropertySource<?>> aps = new YamlPropertySourceLoader().load("application", r);
				ps.addAll(aps);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ps;
	}
	
}
