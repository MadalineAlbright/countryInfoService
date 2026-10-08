package com.api.countryinfo.webclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.api.countryinfo.generated.*;


public class CountryInfoClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(CountryInfoClient.class);

    public CountryISOCodeResponse getCountryName(String country) {

        CountryISOCode request = new CountryISOCode();
        request.setSCountryName(country);

        log.info("Requesting CountryISOCode for " + country);

        CountryISOCodeResponse response = (CountryISOCodeResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso", request,
                        new SoapActionCallback(
                                "http://webservices.oorsprong.org/websamples.countryinfo/CountryISOCode"));

        return response;
    }

    public FullCountryInfoResponse getFullCountryInfo(String isoCode) {
        FullCountryInfo request = new FullCountryInfo();
        request.setSCountryISOCode(isoCode);

        log.info("Requesting full country information for ISO code: {}", isoCode);

        FullCountryInfoResponse response =
                (FullCountryInfoResponse) getWebServiceTemplate()
                        .marshalSendAndReceive(
                                request,
                                new SoapActionCallback(
                                        "http://www.oorsprong.org/websamples.countryinfo/FullCountryInfo"
                                )
                        );

        return response;
    }
}
