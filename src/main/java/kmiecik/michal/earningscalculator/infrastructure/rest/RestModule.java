package kmiecik.michal.earningscalculator.infrastructure.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RestModule {

    @Bean
    ResponseResolver responseResolver() {
        return new ResponseResolver();
    }

}
