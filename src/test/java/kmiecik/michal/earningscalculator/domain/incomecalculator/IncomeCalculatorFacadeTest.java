package kmiecik.michal.earningscalculator.domain.incomecalculator;

import io.vavr.control.Either;
import kmiecik.michal.earningscalculator.domain.errorhandling.AppError;
import kmiecik.michal.earningscalculator.domain.errorhandling.ErrorReason;
import kmiecik.michal.earningscalculator.domain.incomecalculator.dto.OfferDataDto;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class IncomeCalculatorFacadeTest {

    private IncomeCalculatorFacade facade;

    @Before
    public void setup() {
        facade = new IncomeCalculatorModule().fakeIncomeCalculatorFacade();
    }

    @Test
    public void shouldCalculateMonthlyIncomeNetInPoland() {

        OfferDataDto form = new OfferDataDto("125", "pl");

        final Either<AppError, BigDecimal> result = facade.calculateMonthlyIncomeNetInPLN(form);

        assertValidCalculations(result, "1255.5");

    }

    @Test
    public void shouldCalculateMonthlyIncomeNetInGermany() {

        OfferDataDto form = new OfferDataDto("100", "de");

        final Either<AppError, BigDecimal> result = facade.calculateMonthlyIncomeNetInPLN(form);

        assertValidCalculations(result, "5040");

    }

    @Test
    public void shouldCalculateMonthlyIncomeNetInUnitedKingdom() {

        OfferDataDto form = new OfferDataDto("200", "uk");

        final Either<AppError, BigDecimal> result = facade.calculateMonthlyIncomeNetInPLN(form);

        assertValidCalculations(result, "14250");

    }

    @Test
    public void shouldReturnFormCannotBeNull() {

        final Either<AppError, BigDecimal> error = facade.calculateMonthlyIncomeNetInPLN(null);

        assertInvalidCalculations(error, ErrorReason.FORM_CANNOT_BE_NULL, "null");

    }

    @Test
    public void shouldReturnCountryCannotBeNull() {

        OfferDataDto form = new OfferDataDto("100", null);

        final Either<AppError, BigDecimal> error = facade.calculateMonthlyIncomeNetInPLN(form);

        assertInvalidCalculations(error, ErrorReason.COUNTRY_CANNOT_BE_NULL, "null");
    }

    @Test
    public void shouldReturnIncomeCannotBeNull() {

        OfferDataDto form = new OfferDataDto(null, "pl");

        final Either<AppError, BigDecimal> error = facade.calculateMonthlyIncomeNetInPLN(form);

        assertInvalidCalculations(error, ErrorReason.INCOME_CANNOT_BE_NULL, "null");
    }

    @Test
    public void shouldReturnInvalidIncomeValue() {

        OfferDataDto form = new OfferDataDto("-300", "pl");

        final Either<AppError, BigDecimal> error = facade.calculateMonthlyIncomeNetInPLN(form);

        assertInvalidCalculations(error, ErrorReason.INVALID_INCOME_VALUE, "-300");

    }

    @Test
    public void shouldReturnInvalidIncomeFormat() {

        OfferDataDto form = new OfferDataDto("3dd", "de");

        final Either<AppError, BigDecimal> error = facade.calculateMonthlyIncomeNetInPLN(form);

        assertInvalidCalculations(error, ErrorReason.INVALID_INCOME_FORMAT, "3dd");

    }

    @Test
    public void shouldReturnCountryNotSupported() {

        OfferDataDto form = new OfferDataDto("500", "swe");

        final Either<AppError, BigDecimal> error = facade.calculateMonthlyIncomeNetInPLN(form);

        assertInvalidCalculations(error, ErrorReason.COUNTRY_NOT_SUPPORTED, "SWE");

    }

    private void assertValidCalculations(Either<AppError, BigDecimal> eitherResult, String value) {
        assertThat(eitherResult.isRight()).isTrue();
        final BigDecimal result = eitherResult.get();
        assertThat(result).isNotNull();
        assertThat(result).isEqualByComparingTo(new BigDecimal(value));
    }


    private void assertInvalidCalculations(Either<AppError, BigDecimal> eitherResult, ErrorReason errorReason, String message) {
        assertThat(eitherResult.isLeft()).isTrue();
        final AppError appError = eitherResult.getLeft();
        assertThat(appError).isNotNull();
        assertThat(appError.getErrorReason()).isEqualByComparingTo(errorReason);
        assertThat(appError.getMessage()).isEqualTo(message);
    }


}
