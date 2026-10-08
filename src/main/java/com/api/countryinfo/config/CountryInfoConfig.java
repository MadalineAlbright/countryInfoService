package com.api.countryinfo.config;

import com.api.countryinfo.webclient.CountryInfoClient;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CountryInfoConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package configured in the build.gradle/pom.xml
        marshaller.setContextPath("com.api.countryinfo.generated");
        return marshaller;
    }

    @Bean
    public CountryInfoClient countryClient(WebServiceTemplateBuilder builder, Jaxb2Marshaller marshaller) {
        builder = builder.setMarshaller(marshaller).setUnmarshaller(marshaller);

        CountryInfoClient client = new CountryInfoClient();
        client.setWebServiceTemplate(builder.build());
        client.setDefaultUri("http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso");
        return client;
    }

}