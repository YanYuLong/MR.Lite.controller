package cn.wkiki.mrc.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

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

}
