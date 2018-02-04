package cn.wkiki.mrc.config;

import java.util.concurrent.TimeUnit;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import cn.wkiki.mrc.protocol.SocketPool;
import cn.wkiki.util.ConfigInfo;

@Configuration
@ImportResource(locations = { "classpath*:/cn/wkiki/mrc/config/CommonConfig.xml" })
@ComponentScan(basePackages={"cn.wkiki.mrc.protocol","cn.wkiki.mrc.protocol.impl","cn.wkiki.mrc.dao","cn.wkiki.mrc.interceptor"})
public class CommonConfig {
	
	@Bean("socketPool")
	public SocketPool getSocketPool(ConfigInfo configInfo )
	{
		long scanInterval = 0;
		scanInterval = configInfo.getScanInterval();
		LogManager.getLogger(getClass()).info("获取到scanInterval："+scanInterval);
		return new SocketPool(scanInterval);
	}
	
	@Bean("threadPool")
	public java.util.concurrent.ThreadPoolExecutor getThreadPool(ConfigInfo configInfo)
	{
		int corePoolSize=configInfo.getCorePoolSize();
		int maximumPoolSize=configInfo.getMaxPoolSize();
		int keepAliveTime=configInfo.getKeepAliveTime();
		TimeUnit unit=TimeUnit.SECONDS;
		java.util.concurrent.LinkedBlockingDeque<Runnable> workQueue=new java.util.concurrent.LinkedBlockingDeque<>();
		return new java.util.concurrent.ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	@Bean("DBCP")
	public BasicDataSource getDataSource(ConfigInfo configInfo)
	{
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(configInfo.getSqlConnectionStr());
		dataSource.setUsername(configInfo.getSqlUserName());
		dataSource.setPassword(configInfo.getSqlUserPwd());
		dataSource.setInitialSize(5);
		dataSource.setMaxActive(10);
		return dataSource;
	}

	@Bean("jdbcTemplate")
	@Autowired
	public JdbcTemplate jdbcTemplate(javax.sql.DataSource source)

	{
		return new JdbcTemplate(source);
	}
	
	@Bean("namedJdbcTemplate")
	@Autowired
	public NamedParameterJdbcTemplate namedJdbcTemplate(javax.sql.DataSource source)
	{
		return new NamedParameterJdbcTemplate(source);
	}
}
