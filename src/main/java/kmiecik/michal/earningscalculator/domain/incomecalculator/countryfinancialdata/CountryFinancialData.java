package kmiecik.michal.earningscalculator.domain.incomecalculator.countryfinancialdata;

import kmiecik.michal.earningscalculator.domain.incomecalculator.Currency;
import kmiecik.michal.earningscalculator.domain.incomecalculator.TaxPolicy;

public class CountryFinancialData {

    private final TaxPolicy taxPolicy;
    private final Currency currency;

    CountryFinancialData(TaxPolicy taxPolicy, Currency currency) {
        this.currency = currency;
        this.taxPolicy = taxPolicy;
    }

    public TaxPolicy getSimpleTaxPolicy() {
        return taxPolicy;
    }

    public Currency getCurrency() {
        return currency;
    }
}
