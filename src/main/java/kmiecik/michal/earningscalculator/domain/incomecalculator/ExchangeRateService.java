package kmiecik.michal.earningscalculator.domain.incomecalculator;


import java.math.BigDecimal;

public interface ExchangeRateService {

    BigDecimal getRate(Currency currency);

    Currency referenceCurrency();

}
