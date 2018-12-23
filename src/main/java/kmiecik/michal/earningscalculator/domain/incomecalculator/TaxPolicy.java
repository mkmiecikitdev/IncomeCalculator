package kmiecik.michal.earningscalculator.domain.incomecalculator;

import io.vavr.Function1;

import java.math.BigDecimal;


@FunctionalInterface
public interface TaxPolicy extends Function1<BigDecimal, BigDecimal> {

}
