package etf.iot.cloud.platform.services.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of object mapper bean
 */
@Configuration
public class ModelMapperBean {
    /**
     * Bean that provides mapping between objects of similar classes
     *
     * @return object mapper
     */
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return  modelMapper;
    }
}
