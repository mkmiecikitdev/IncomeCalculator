package kmiecik.michal.earningscalculator.domain.incomecalculator.countryfinancialdata;

import kmiecik.michal.earningscalculator.domain.incomecalculator.TaxPolicy;

import java.math.BigDecimal;
import java.math.RoundingMode;

class SimpleTaxPolicy implements TaxPolicy {

    private static final BigDecimal HUNDRED = new BigDecimal(100);

    private final BigDecimal taxValue;
    private final BigDecimal fixedCosts;

    SimpleTaxPolicy(BigDecimal taxValue, BigDecimal fixedCosts) {
        this.taxValue = taxValue;
        this.fixedCosts = fixedCosts;
    }

    @Override
    public BigDecimal apply(BigDecimal earningsGross) {
        return calculateAfterTax(earningsGross.subtract(fixedCosts));
    }

    private BigDecimal calculateAfterTax(BigDecimal incomeGross) {
        return incomeGross.multiply(calculatePercentAfterTax()).divide(HUNDRED, RoundingMode.CEILING);
    }

    private BigDecimal calculatePercentAfterTax() {
        return (HUNDRED.subtract(taxValue));
    }

}
