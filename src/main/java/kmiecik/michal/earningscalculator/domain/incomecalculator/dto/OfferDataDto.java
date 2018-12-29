package kmiecik.michal.earningscalculator.domain.incomecalculator.dto;

public class OfferDataDto {

    private final String dailyRateGross;
    private final String country;

    public OfferDataDto(final String dailyRateGross, final String country) {
        this.dailyRateGross = dailyRateGross;
        this.country = country;
    }

    public String getDailyRateGross() {
        return dailyRateGross;
    }

    public String getCountry() {
        return country;
    }

}
