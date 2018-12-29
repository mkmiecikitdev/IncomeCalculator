package kmiecik.michal.earningscalculator.infrastructure.exchangerateservices.nbp;

import kmiecik.michal.earningscalculator.domain.incomecalculator.ExchangeRateService;
import kmiecik.michal.earningscalculator.domain.incomecalculator.Currency;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

class NBPExchangeRateService implements ExchangeRateService {

    private final WebClient webClient;
    private final String uri;

    NBPExchangeRateService(final String baseUrl, final String uri) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();

        this.uri = uri;
    }

    @Cacheable("currency")
    @Override
    public BigDecimal getRate(final Currency currency) {

        final NBPResponse block = webClient.get()
                .uri(uri, currency.name())
                .retrieve()
                .bodyToMono(NBPResponse.class)
                .block();


        return new BigDecimal(block.getRates().get(0).getMid());
    }

    @Override
    public Currency referenceCurrency() {
        return Currency.PLN;
    }
}
