package etf.iot.cloud.platform.services.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Log utility configuration class
 */
@Configuration
public class LoggerConfig {
    /**
     * Logger bean used for logging
     */
    private final LoggerBean loggerBean=new LoggerBean();

    /**
     * provides LoggerBean object
     * @return LoggerBean
     */
    @Bean
    public LoggerBean loggerBean(){
        return  loggerBean;
    }
}
