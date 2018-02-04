package cn.wkiki.mrc.config;

import java.util.LinkedList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import cn.wkiki.mrc.interceptor.AuthPointInterceptor;
import cn.wkiki.util.ContextUtils;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"cn.wkiki.mrc.mvc","cn.wkiki.mrc.api"})
public class WebConfig extends WebMvcConfigurerAdapter
{
	@Bean
	public ViewResolver viewResolver()
	{
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
	{
		// TODO Auto-generated method stub
		configurer.enable();
	}
	// 添加阿里的fastjson支持spring的 converter
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
	{
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		converters.add(converter);
		List<MediaType> mediaTypes = new LinkedList<>();
		mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		converter.setSupportedMediaTypes(mediaTypes);
		super.extendMessageConverters(converters);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		//添加控制节点认证拦截器
		org.springframework.context.ApplicationContext context= ContextUtils.getRootContext();
		AuthPointInterceptor authPointInterceptor= context.getBean(AuthPointInterceptor.class);
		registry.addInterceptor(authPointInterceptor).addPathPatterns("/auth/dev/*");
		super.addInterceptors(registry);
	}
	
}
