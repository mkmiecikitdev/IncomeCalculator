package kmiecik.michal.earningscalculator.infrastructure.exchangerateservices.nbp;

import kmiecik.michal.earningscalculator.infrastructure.exchangerateservices.ExchangeServicesConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NBPExchangeRateModule {

    @Bean
    NBPExchangeRateService nbpExchangeRateService(ExchangeServicesConfig config) {
        return new NBPExchangeRateService(config.getNbp().getBaseUrl(), config.getNbp().getUri());
    }

}
