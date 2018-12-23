package kmiecik.michal.earningscalculator.domain.incomecalculator;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import kmiecik.michal.earningscalculator.domain.incomecalculator.countryfinancialdata.CountryFinancialDataProvider;
import kmiecik.michal.earningscalculator.infrastructure.taxconfig.TaxConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;


@Configuration
class IncomeCalculatorModule {

    @Bean
    IncomeCalculatorFacade incomeCalculatorFacade(final TaxConfig taxConfig, final ExchangeRateService exchangeRateService) {
        return new IncomeCalculatorFacade(new CountryFinancialDataProvider(taxConfig), exchangeRateService, new OfferDataFormValidator());
    }

    IncomeCalculatorFacade fakeIncomeCalculatorFacade() {
        return new IncomeCalculatorFacade(new CountryFinancialDataProvider(fakeTaxConfig()), new FakeExchangeRateService(), new OfferDataFormValidator());
    }


    private TaxConfig fakeTaxConfig() {

        final TaxConfig.Country pl = createCountryFinancialData(19, 1200);
        final TaxConfig.Country de = createCountryFinancialData(20, 800);
        final TaxConfig.Country uk = createCountryFinancialData(25, 600);

        TaxConfig taxConfig = new TaxConfig();
        taxConfig.setPl(pl);
        taxConfig.setDe(de);
        taxConfig.setUk(uk);

        return taxConfig;
    }


    private TaxConfig.Country createCountryFinancialData(int tax, int fixedCots) {
        TaxConfig.Country financialData = new TaxConfig.Country();
        financialData.setValue(tax);
        financialData.setFixedCosts(fixedCots);
        return financialData;
    }

    private static class FakeExchangeRateService implements ExchangeRateService {

        private final Map<Currency, BigDecimal> rates = HashMap.of(
                Currency.EUR, new BigDecimal(4.5f),
                Currency.GBP, new BigDecimal(5f),
                Currency.PLN, BigDecimal.ONE
        );

        @Override
        public BigDecimal getRate(Currency currency) {
            return rates.get(currency)
                    .getOrElseThrow(IllegalArgumentException::new);
        }

        @Override
        public Currency referenceCurrency() {
            return Currency.PLN;
        }
    }


}
