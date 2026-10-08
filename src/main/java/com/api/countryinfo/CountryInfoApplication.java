package com.api.countryinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CountryInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountryInfoApplication.class, args);
	}

//	Logger logger = LoggerFactory.getLogger(CountryInfoApplication.class);

//	@Bean
//    ApplicationRunner lookup(CountryInfoClient countryClient) {
//		return args -> {
//			List<String> countryOption = args.getOptionValues("country");
//			String country = (countryOption == null || countryOption.isEmpty()) ? "Kenya" : countryOption.get(0);
//			CountryISOCodeResponse response = countryClient.getCountryName(country);
//            logger.info("Country [%s] has currency [%s].".formatted(country, response.getCountryISOCodeResult()));
//		};
//	}

}
