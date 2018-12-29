package kmiecik.michal.earningscalculator.domain.incomecalculator;

import io.vavr.control.Either;
import io.vavr.control.Try;
import kmiecik.michal.earningscalculator.domain.errorhandling.AppError;
import kmiecik.michal.earningscalculator.domain.errorhandling.ErrorReason;

import java.math.BigDecimal;

class DailyIncomeGross {

    private static final BigDecimal WORKING_DAYS_IN_MONTH = new BigDecimal(22);

    private final BigDecimal value;

    private DailyIncomeGross(BigDecimal value) {
        this.value = value;
    }

    static Either<AppError, DailyIncomeGross> tryCreate(String dayRateStr) {
        return Try.of(() -> new BigDecimal(dayRateStr))
                .toEither(new AppError(ErrorReason.INVALID_INCOME_FORMAT, dayRateStr))
                .flatMap(DailyIncomeGross::tryCreateValidIncome);

    }

    BigDecimal calculateMonthlyIncomeNet(TaxPolicy taxPolicy) {
        return taxPolicy.apply(calculateMonthlyIncomeGross());
    }

    private static Either<AppError, DailyIncomeGross> tryCreateValidIncome(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) >= 0 ?
                Either.right(new DailyIncomeGross(value)) :
                Either.left(new AppError(ErrorReason.INVALID_INCOME_VALUE, value.toString()));
    }

    private BigDecimal calculateMonthlyIncomeGross() {
        return value.multiply(WORKING_DAYS_IN_MONTH);
    }

}
