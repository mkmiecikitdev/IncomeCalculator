package kmiecik.michal.earningscalculator.infrastructure.rest;

public class RestResponse {

    private final String income;

    public RestResponse(String income) {
        this.income = income;
    }

    public String getIncome() {
        return income;
    }
}
