package etf.iot.cloud.platform.services.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {
    private final LoggerBean loggerBean=new LoggerBean();
    @Bean
    public LoggerBean loggerBean(){
        return  loggerBean;
    }
}
