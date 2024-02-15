package com.craftWine.shop.service.promotionServices;

import com.craftWine.shop.dto.timePromotionAPI.CountryCurrentTimeDTO;
import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.mapper.ProducedCountryMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.service.crafWineServices.CraftWineService;
import com.craftWine.shop.service.producedCountryServices.ProducedCountryService;
import com.craftWine.shop.utils.PercentageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//TODO: SLF4G
public class WineTimePromotionHandlerImpl implements WineTimePromotionHandler {

    private final ProducedCountryService producedCountryService;
    private final CraftWineService craftWineService;
    private final ProducedCountryMapper producedCountryMapper;
    private final CraftWineMapper craftWineMapper;
    private final float percentageWineTimeDiscount = 6.59f;

    @Async
    @Scheduled(cron = "0 58 * * * *")
    @Override
    public void refreshCountry() {


        List<ProducedCountry> producedCountryByPromotionTimeTrue = producedCountryService.findByPromotionTime(true);

        resetDiscountPromotionForTheCurrentTime(producedCountryByPromotionTimeTrue);


        LocalTime startingPromotionTime = LocalTime.of(17, 58);
        LocalTime finishingPromotionTime = LocalTime.of(19, 0);

        final List<ProducedCountry> allCountries = producedCountryService.findAll();

        final List<ProducedCountry> promotionCountries = new ArrayList<>(allCountries.size());

        for (ProducedCountry country : allCountries) {

            String request = "https://timeapi.io/api/Time/current/coordinate?latitude=" + country.getCapitalLAT()
                    + "&longitude=" + country.getCapitalLNG();


            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {


                final ClassicHttpRequest getRequest = ClassicRequestBuilder.get(request).build();


                LocalTime currentTime = httpClient.execute(getRequest, response -> {


                    final HttpEntity entity = response.getEntity();

                    if (entity == null) {
                        throw new IllegalArgumentException("entity is empty");
                    }

                    String jsonString = EntityUtils.toString(entity);

                    ObjectMapper objectMapper = new ObjectMapper();
                    CountryCurrentTimeDTO countryCurrentTimeDTO = objectMapper.readValue(jsonString, CountryCurrentTimeDTO.class);

                    EntityUtils.consume(entity);


                    if (countryCurrentTimeDTO != null) {
                        return LocalTime.parse(countryCurrentTimeDTO.localTime(), DateTimeFormatter.ofPattern("HH:mm"));
                    } else {
                        throw new IllegalStateException("Couldn't parse country time");
                    }
                });

                if (currentTime.isAfter(startingPromotionTime) && currentTime.isBefore(finishingPromotionTime)) {
                    promotionCountries.add(country);
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }


        applyWineTimeDiscount(promotionCountries);
    }

    @Override
    public List<?> getPromotions() {

        List<ProducedCountry> producedCountryByPromotionTime = producedCountryService.findByPromotionTime(true);

        List<CraftWine> craftWineByWineTimePromotion = craftWineService.findCraftWineByWineTimePromotion(true);

        return new ArrayList<>() {{
            add(producedCountryByPromotionTime.stream().map(producedCountryMapper::toDTO).collect(Collectors.toSet()));
            add(craftWineByWineTimePromotion.stream().map(craftWineMapper::toDTOResponse).collect(Collectors.toList()));
        }};

    }

    @Override
    public void resetDiscountPromotionForTheCurrentTime(List<ProducedCountry> producedCountryList) {

        if (producedCountryList.isEmpty()) {
            return;
        }

        for (ProducedCountry producedCountry : producedCountryList) {

            List<CraftWine> currentDiscountPromotionTimeWinesList = craftWineService.getByProducedCountry(producedCountry);

            for (CraftWine craftWine : currentDiscountPromotionTimeWinesList) {

                craftWine.setWineTimePromotion(false);

                if (craftWine.getAdminDiscountPercentage() > percentageWineTimeDiscount) {
                    continue;
                }
                if (craftWine.getAdminDiscountPercentage() > 0) {

                    BigDecimal percentageFromPrice = PercentageHandler.getPercentageFromPrice(craftWine.getOriginalPrice(),
                            craftWine.getAdminDiscountPercentage());

                    craftWine.setPrice(percentageFromPrice);

                } else {
                    craftWine.setPrice(craftWine.getOriginalPrice());
                }

            }

            craftWineService.saveAllAndFlush(currentDiscountPromotionTimeWinesList);
            producedCountry.setPromotionTime(false);
        }

        producedCountryService.saveAllAndFlush(producedCountryList);

    }

    @Override
    public void applyWineTimeDiscount(List<ProducedCountry> producedCountryList) {

        if (producedCountryList.isEmpty()) {
            return;
        }

        for (ProducedCountry producedCountry : producedCountryList) {

            List<CraftWine> byProducedCountry = craftWineService.getByProducedCountry(producedCountry);


            for (CraftWine craftWine : byProducedCountry) {

                craftWine.setWineTimePromotion(true);

                if (craftWine.getAdminDiscountPercentage() > percentageWineTimeDiscount) {
                    continue;
                }

                BigDecimal percentageFromPrice = PercentageHandler.getPercentageFromPrice(craftWine.getOriginalPrice(),
                        percentageWineTimeDiscount);

                craftWine.setPrice(percentageFromPrice);
            }

            producedCountry.setPromotionTime(true);
            craftWineService.saveAllAndFlush(byProducedCountry);
        }
        producedCountryService.saveAllAndFlush(producedCountryList);
    }

}