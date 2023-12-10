package com.craftWine.shop.service;

import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.models.Region;
import com.craftWine.shop.repositories.CraftWineRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class CraftWineServiceImplTest {




    @Mock
    private CraftWineRepository craftWineRepositoryMock;

    @InjectMocks
    private CraftWineServiceImpl craftWineService;

//    @Test
//    void testSaveCraftWine() {
//        // Arrange
//        CraftWine craftWineToSave = new CraftWine(
//                "4585", "first wine", new BigDecimal("789"), "first description", (short) 425, "0.7",
//                "13%", false, false, false, "first winemaking", "first grapeVarieries",
//                "first testingNotes", "first storeAndServeAdvices",
//                "first foodPairing", "first reviewsAndAwards", WineColor.RED, SugarConsistency.SWEET, new ProducedCountry("United Kingdom"),
//                new Region("Manchester"), LocalDateTime.now(), "first imageUrl"
//        );
//
//        // Act
//        craftWineService.save(craftWineToSave);
//
//        // Assert
//        // В данном случае, так как метод save ничего не возвращает, мы можем только проверить, что он был вызван
//        verify(craftWineRepositoryMock, times(1)).save(craftWineToSave);
//    }


}