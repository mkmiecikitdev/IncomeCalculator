package kmiecik.michal.earningscalculator.domain.incomecalculator;

import io.vavr.control.Either;
import io.vavr.control.Try;
import kmiecik.michal.earningscalculator.domain.incomecalculator.countryfinancialdata.Country;
import kmiecik.michal.earningscalculator.domain.incomecalculator.dto.OfferDataDto;
import kmiecik.michal.earningscalculator.domain.incomecalculator.countryfinancialdata.CountryFinancialData;
import kmiecik.michal.earningscalculator.domain.incomecalculator.countryfinancialdata.CountryFinancialDataProvider;
import kmiecik.michal.earningscalculator.domain.errorhandling.AppError;
import kmiecik.michal.earningscalculator.domain.errorhandling.ErrorReason;

import java.math.BigDecimal;

public class IncomeCalculatorFacade {

    private final CountryFinancialDataProvider factoryProvider;
    private final ExchangeRateService exchangeRateService;
    private final OfferDataFormValidator validator;

    public IncomeCalculatorFacade(final CountryFinancialDataProvider factoryProvider, final ExchangeRateService exchangeRateService, OfferDataFormValidator validator) {
        this.factoryProvider = factoryProvider;
        this.exchangeRateService = exchangeRateService;
        this.validator = validator;
    }

    public Either<AppError, BigDecimal> calculateMonthlyIncomeNetInPLN(final OfferDataDto form) {

        return validator.getValidatedForm(form)
                .flatMap(validForm -> DailyIncomeGross.fromDayRate(form.getDailyRateGross())
                        .flatMap(dailyIncomeGross -> tryCalculateForCountry(dailyIncomeGross, form.getCountry().toUpperCase())));

    }

    private Either<AppError, BigDecimal> tryCalculateForCountry(final DailyIncomeGross dailyIncomeGross, final String countryStr) {
        return tryGetCountryFromStr(countryStr)
                .flatMap(country -> calculateForCountry(dailyIncomeGross, country));
    }

    private Either<AppError, Country> tryGetCountryFromStr(final String country) {
        return Try.of(() -> Country.valueOf(country))
                .toEither(new AppError(ErrorReason.COUNTRY_NOT_SUPPORTED, country));
    }

    private Either<AppError, BigDecimal> calculateForCountry(final DailyIncomeGross dailyIncomeGross, final Country country) {
        return factoryProvider.provide(country)
                .map(factory -> calculateByCountryTaxData(dailyIncomeGross, factory))
                .toEither(new AppError(ErrorReason.COUNTRY_NOT_SUPPORTED, country.name()));
    }

    private BigDecimal calculateByCountryTaxData(final DailyIncomeGross dailyIncomeGross, final CountryFinancialData countryFinancialData) {
        final Currency currency = countryFinancialData.getCurrency();
        final BigDecimal beforeExchange = dailyIncomeGross.calculateMonthlyIncomeNet(countryFinancialData.getSimpleTaxPolicy());

        if(isOtherCurrency(currency)) {
            return beforeExchange.multiply(exchangeRateService.getRate(currency));
        }

        return beforeExchange;
    }

    private boolean isOtherCurrency(final Currency currency) {
        return currency != exchangeRateService.referenceCurrency();
    }



}
