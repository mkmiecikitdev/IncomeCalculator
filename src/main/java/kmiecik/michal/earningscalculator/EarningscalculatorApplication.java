package kmiecik.michal.earningscalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EarningscalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EarningscalculatorApplication.class, args);
	}

}

