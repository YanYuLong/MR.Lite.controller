package cn.wkiki.mrc.config;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.LogManager;
import org.springframework.context.ApplicationContext;

import cn.wkiki.mrc.protocol.ClientListener;
import cn.wkiki.util.ContextUtils;

//配置初始化数据的servlet
public class InitServlet implements Servlet
{

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public ServletConfig getServletConfig()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException
	{
		ApplicationContext context= ContextUtils.getRootContext(arg0.getServletContext());
		if(context!=null)
		{
			LogManager.getLogger(getClass()).info("准备启动监听模块");
			ClientListener listener= context.getBean("clientListener", cn.wkiki.mrc.protocol.ClientListener.class);
			try
			{
				listener.startListen();
			}
			catch (Exception e) {
				LogManager.getLogger(getClass()).error("启动监听失败，失败异常信息为："+e.getMessage());
			}
			
		}
		ContextUtils.m_ServletContext=arg0.getServletContext();
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException
	{
	}

}
