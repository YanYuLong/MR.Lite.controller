package cn.wkiki.mrc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.wkiki.util.ConfigInfo;

@Component
public class AuthPointInterceptor implements HandlerInterceptor
{
	@Autowired ConfigInfo configInfo;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		String remoteAddr= request.getRemoteAddr();
		if(!configInfo.getAuthorizeManagerIP().contains(remoteAddr))
		{
			String resultString =JSON.toJSONString(new Object()
			{
				public String Status="Error";
				public String Message ="请求非法！！请保证在系统设置的可信Controller节点中发出此请求";
				public  int StatusCode=402;
				public Object value= null;
			});
			response.getWriter().write(resultString);
			response.addHeader("Content-Type", "application/json;charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.flushBuffer();
			return false;
		}	
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception
	{
		// TODO Auto-generated method stub
		
	}

}
