package com.craftWine.shop.service.promotionServices;

import com.craftWine.shop.dto.timePromotionAPI.CountryInfoDTO;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.service.producedCountryServices.ProducedCountryService;
import com.craftWine.shop.utils.SwitchCaseToCapitalize;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckInformationAboutTheCountryImpl implements CheckInformationAboutTheCountry {

    private final ProducedCountryService producedCountryService;


    @Override
    public ProducedCountry findTheCountryAndCoordinatesOfTheCapitalByItsName(String country) {


        final String countryName = SwitchCaseToCapitalize.switchCaseToCapitalize(country);

        Optional<ProducedCountry> countryOptional = producedCountryService.findByName(countryName);

        if (countryOptional.isPresent()) {
            throw new IllegalArgumentException("Country " + countryName + " has already exists");
        }

        final String exceptionMessage = "Couldn't find country with name " + countryName;

        final String restCountries = "https://restcountries.com/v3.1/name/" + countryName + "?fields=name,capitalInfo";


        ProducedCountry producedCountry;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            final ClassicHttpRequest httpGet = ClassicRequestBuilder.get(restCountries).build();


            producedCountry = httpClient.execute(httpGet, response -> {


                final HttpEntity entity = response.getEntity();

                if (entity == null) {
                    throw new IllegalArgumentException(exceptionMessage);
                }

                String jsonString = EntityUtils.toString(entity);

                ObjectMapper objectMapper = new ObjectMapper();
                List<CountryInfoDTO> countryInfoDTOList = objectMapper.readValue(jsonString, new TypeReference<List<CountryInfoDTO>>() {
                });

                EntityUtils.consume(entity);

                if (!countryInfoDTOList.isEmpty()) {
                    CountryInfoDTO countryInfoDTO = countryInfoDTOList.get(0);


                    return new ProducedCountry(
                            SwitchCaseToCapitalize.switchCaseToCapitalize(countryInfoDTO.countryName().common()),
                            countryInfoDTO.capitalInfo().latlng().get(0),
                            countryInfoDTO.capitalInfo().latlng().get(1)
                    );


                } else {
                    throw new IllegalArgumentException(exceptionMessage);
                }
            });
        } catch (IOException exception) {
            log.debug(exception.getMessage());
            return null;
        }
        producedCountryService.save(producedCountry);
        return producedCountry;
    }
}
