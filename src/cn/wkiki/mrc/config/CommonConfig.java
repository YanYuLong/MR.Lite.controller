package cn.wkiki.mrc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ImportResource(locations = { "classpath*:/cn/wkiki/mrc/config/CommonConfig.xml" })
@ComponentScan(basePackages={"cn.wkiki.mrc.protocol"})
public class CommonConfig {

}
