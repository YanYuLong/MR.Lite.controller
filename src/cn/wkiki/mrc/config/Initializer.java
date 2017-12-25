package cn.wkiki.mrc.config;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.catalina.core.ApplicationContext;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.sun.glass.ui.Application;

import cn.wkiki.util.ContextUtils;

//初始化配置类
public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return new Class<?>[] { CommonConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings()
	{
		return new String[] { "/" };
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException
	{
		super.onStartup(servletContext);
		//注册log4j
		String appPath = servletContext.getRealPath("/");
		String propertiesPath = appPath+"\\WEB-INF\\"+"log4j.properties";
		PropertyConfigurator.configure(propertiesPath);
		Enumeration<String> enumeration = servletContext.getAttributeNames();
		List<String> list = new LinkedList<>();
		while(enumeration.hasMoreElements())
		{
			list.add(enumeration.nextElement());
		}
		
	}
	
}
