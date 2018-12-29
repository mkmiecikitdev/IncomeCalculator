package kmiecik.michal.earningscalculator.domain.incomecalculator;

import io.vavr.control.Either;
import kmiecik.michal.earningscalculator.domain.errorhandling.AppError;
import kmiecik.michal.earningscalculator.domain.errorhandling.ErrorReason;
import kmiecik.michal.earningscalculator.domain.incomecalculator.dto.OfferDataDto;

class OfferDataFormValidator {

    private static final String MESSAGE = "null";

    Either<AppError, OfferDataDto> getValidatedForm(final OfferDataDto form) {

        if (form == null)
            return createError(ErrorReason.FORM_CANNOT_BE_NULL);

        if (form.getCountry() == null)
            return createError(ErrorReason.COUNTRY_CANNOT_BE_NULL);

        if (form.getDailyRateGross() == null)
            return createError(ErrorReason.INCOME_CANNOT_BE_NULL);

        return Either.right(form);

    }

    private Either<AppError, OfferDataDto> createError(final ErrorReason errorReason) {
        return Either.left(new AppError(errorReason, MESSAGE));
    }


}
