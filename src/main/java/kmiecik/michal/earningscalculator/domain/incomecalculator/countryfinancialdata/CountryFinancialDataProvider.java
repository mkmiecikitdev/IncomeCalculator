package kmiecik.michal.earningscalculator.domain.incomecalculator.countryfinancialdata;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import kmiecik.michal.earningscalculator.domain.incomecalculator.Currency;
import kmiecik.michal.earningscalculator.infrastructure.taxconfig.TaxConfig;

import java.math.BigDecimal;


public class CountryFinancialDataProvider {

    private final Map<Country, CountryFinancialData> factoryMap;

    public CountryFinancialDataProvider(TaxConfig taxConfig) {
        this.factoryMap = HashMap.of(
            Country.PL, new CountryFinancialData(new SimpleTaxPolicy(new BigDecimal(taxConfig.getPl().getValue()), new BigDecimal(taxConfig.getPl().getFixedCosts())), Currency.PLN),
            Country.DE, new CountryFinancialData(new SimpleTaxPolicy(new BigDecimal(taxConfig.getDe().getValue()), new BigDecimal(taxConfig.getDe().getFixedCosts())), Currency.EUR),
            Country.UK, new CountryFinancialData(new SimpleTaxPolicy(new BigDecimal(taxConfig.getUk().getValue()), new BigDecimal(taxConfig.getUk().getFixedCosts())), Currency.GBP)
        );
    }

    public Option<CountryFinancialData> provide(Country country) {
        return factoryMap.get(country);
    }

}
