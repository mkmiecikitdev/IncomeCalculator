package kmiecik.michal.earningscalculator.infrastructure.exchangerateservices.nbp;

import java.util.List;

class NBPResponse {

    private List<NBPRate> rates;

    List<NBPRate> getRates() {
        return rates;
    }

    void setRates(List<NBPRate> rates) {
        this.rates = rates;
    }
}
