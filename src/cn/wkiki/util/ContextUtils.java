package cn.wkiki.util;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.springframework.context.ApplicationContext;

public class ContextUtils
{
	/**
	 * 不要手动修改次变量
	 */
	public static ServletContext m_ServletContext;
	
	/**
	 * 获取当前web环境下的根Ioc容器
	 * @return
	 */
	public static ApplicationContext getRootContext()
	{
		return getRootContext(m_ServletContext);
	}
	/**
	 * 获取当前web环境下的web容器的Ioc容器
	 * @return
	 */
	public  static ApplicationContext getWebContainerContext()
	{
		return getWebContainerContext(m_ServletContext);
	}
	
	/**
	 * 获取指定web环境下的根Ioc容器
	 * @param servletContext web容器环境
	 * @return
	 */
	public static ApplicationContext getRootContext(ServletContext servletContext)
	{
		Object rootContext = servletContext.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
		if(rootContext instanceof ApplicationContext)
		{
			return (ApplicationContext) rootContext;
		}
		else {
			LogManager.getLogger(ContextUtils.class).error("获取程序根Ioc容器错误");
			return null;
		}
	}
	
	/**
	 * 获取指定web环境下的web容器的Ioc容器
	 * @param servletContext web容器环境
	 * @return
	 */
	public static ApplicationContext getWebContainerContext(ServletContext servletContext)
	{
		Object rootContext = servletContext.getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcherServlet");
		if(rootContext instanceof ApplicationContext)
		{
			return (ApplicationContext) rootContext;
		}
		else {
			LogManager.getLogger(ContextUtils.class).error("获取程序web容器Ioc器错误");
			return null;
		}
	}
}
