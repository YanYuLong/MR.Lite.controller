package cn.wkiki.mrc.config;

import org.apache.log4j.LogManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import cn.wkiki.mrc.protocol.SocketPool;
import cn.wkiki.util.ConfigInfo;

@Configuration
@ImportResource(locations = { "classpath*:/cn/wkiki/mrc/config/CommonConfig.xml" })
@ComponentScan(basePackages={"cn.wkiki.mrc.protocol"})
public class CommonConfig {
	
	@Bean("socketPool")
	public SocketPool getSocketPool(ConfigInfo configInfo )
	{
		long scanInterval = 0;
		scanInterval = configInfo.getScanInterval();
		LogManager.getLogger(getClass()).info("获取到scanInterval："+scanInterval);
		return new SocketPool(scanInterval);
	}
}
