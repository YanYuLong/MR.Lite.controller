package cn.wkiki.mrc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = { "classpath*:/cn/wkiki/mrc/config/CommonConfig.xml" })
public class CommonConfig {

}
