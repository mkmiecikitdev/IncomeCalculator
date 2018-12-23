package kmiecik.michal.earningscalculator.infrastructure.taxconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("tax")
public class TaxConfig {

    private Country pl;

    private Country de;

    private Country uk;

    public Country getPl() {
        return pl;
    }

    public void setPl(Country pl) {
        this.pl = pl;
    }

    public Country getDe() {
        return de;
    }

    public void setDe(Country de) {
        this.de = de;
    }

    public Country getUk() {
        return uk;
    }

    public void setUk(Country uk) {
        this.uk = uk;
    }

    public static class Country {

        private int value;
        private int fixedCosts;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getFixedCosts() {
            return fixedCosts;
        }

        public void setFixedCosts(int fixedCosts) {
            this.fixedCosts = fixedCosts;
        }

    }

}
