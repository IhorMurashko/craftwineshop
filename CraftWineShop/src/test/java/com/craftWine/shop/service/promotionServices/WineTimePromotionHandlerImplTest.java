package com.craftWine.shop.service.promotionServices;

import com.craftWine.shop.mapper.CraftWineMapper;
import com.craftWine.shop.mapper.ProducedCountryMapper;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.service.crafWineServices.CraftWineService;
import com.craftWine.shop.service.producedCountryServices.ProducedCountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class WineTimePromotionHandlerImplTest {


    private List<ProducedCountry> producedCountryList;

    private List<CraftWine> craftWineList;


    @Mock
    private ProducedCountryService producedCountryService;
    @Mock
    private CraftWineService craftWineService;
    @Mock
    private ProducedCountryMapper producedCountryMapper;
    @Mock
    private CraftWineMapper craftWineMapper;
    @InjectMocks
    WineTimePromotionHandlerImpl wineTimePromotionHandler;

    @BeforeEach
    void initialize() {

        producedCountryList = new ArrayList<>(6);
        ProducedCountry countryUkraine = new ProducedCountry("Ukraine", 50.43f, 30.52f);
        ProducedCountry countryItaly = new ProducedCountry("Italy", 41.9f, 12.48f);
        ProducedCountry countrySpain = new ProducedCountry("Spain", 40.4f, -3.68f);
        ProducedCountry countryGeorgia = new ProducedCountry("Georgia", 41.68f, 44.83f);
        ProducedCountry countryAustria = new ProducedCountry("Austria", 48.2f, 16.37f);
        ProducedCountry countryPoland = new ProducedCountry("Poland", 52.25f, 21.0f);

        producedCountryList.add(countryUkraine);
        producedCountryList.add(countryItaly);
        producedCountryList.add(countrySpain);
        producedCountryList.add(countryGeorgia);
        producedCountryList.add(countryAustria);
        producedCountryList.add(countryPoland);

        craftWineList = new ArrayList<>();

        craftWineList.add(new CraftWine() {{
            setCountry(countryUkraine);
        }});
        craftWineList.add(new CraftWine() {{
            setCountry(countryAustria);
        }});
        craftWineList.add(new CraftWine() {{
            setCountry(countryPoland);
        }});
        craftWineList.add(new CraftWine() {{
            setCountry(countryGeorgia);
        }});
        craftWineList.add(new CraftWine() {{
            setCountry(countryItaly);
        }});
        craftWineList.add(new CraftWine() {{
            setCountry(countrySpain);
        }});
    }


//    @Test
//    void getGoodResult() {
//
//        doReturn(new ArrayList<>()).when(producedCountryService).findByPromotionTime(true);
//        doReturn(producedCountryList).when(producedCountryService).findAll();
//
//
//
//
//    }

}