package com.craftWine.shop.service;

import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.exceptions.EmailProblemException;
import com.craftWine.shop.models.*;
import com.craftWine.shop.repositories.WineStarsRepository;
import com.craftWine.shop.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WineStarServiceImplTest {


    @Mock
    private UserService userService;
    @Mock
    private CraftWineService craftWineService;
    @Mock
    private WineStarsRepository wineStarsRepository;
    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private WineStarServiceImpl wineStarServiceImpl;


    private String email;
    private User user;
    private CraftWine craftWine;
    private String token;

    private ProducedCountry countryFrance;
    private Region regionOfFrance;
    private long wineId;

    private WineStar wineStar;
    private short rate;


    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<CraftWine> craftWineArgumentCaptor;


    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @Captor
    private ArgumentCaptor<Short> shortArgumentCaptor;


    @BeforeEach
    void setup() {

        this.wineId = 1;
        this.rate = 3;

        this.email = "email@gmail.com";
        this.user = new User(email, "password", "012345678901", "name",
                "last_name");
        user.setId(1);

        this.token = UUID.randomUUID().toString();

        this.countryFrance = new ProducedCountry("France");
        countryFrance.setId(1L);
        countryFrance.setRegions(new HashSet<>());

        this.regionOfFrance = new Region();
        regionOfFrance.setProducedCountry(countryFrance);
        regionOfFrance.setName("region of France");
        regionOfFrance.setId(1);


        craftWine = new CraftWine();
        craftWine.setId(wineId);
        craftWine.setWineName("wine name");
        craftWine.setPrice(new BigDecimal("120.0"));
        craftWine.setDiscount(0.0f);
        craftWine.setWineDescription("description");
        craftWine.setQuantity((short) 5);
        craftWine.setBottleCapacity("0.7");
        craftWine.setAlcohol("12");
        craftWine.setNewCollection(true);
        craftWine.setBestSeller(false);
        craftWine.setSale(false);
        craftWine.setWinemaking("winemaking");
        craftWine.setGrapeVarieties("grapeVarieties");
        craftWine.setTastingNotes("testingNotes");
        craftWine.setStoreAndServeAdvices("storeAndServeAdvices");
        craftWine.setFoodPairing("foodPairing");
        craftWine.setReviewsAndAwards("reviewsAndAwards");
        craftWine.setWineColor(WineColor.RED);
        craftWine.setSugarConsistency(SugarConsistency.DRY);
        craftWine.setRegion(regionOfFrance);
        craftWine.setRate((short) 0);

    }


    @Test
    @DisplayName("EmailProblemException user email not found")
    void emailNotFound() {

        doReturn(email).when(tokenProvider).extractUsername(token);
        doReturn(Optional.empty()).when(userService).findUserByEmail(email);

        RuntimeException exception = assertThrows(RuntimeException.class, ()
                -> wineStarServiceImpl.addRateForTheWine(token, wineId, rate));


        assertEquals(EmailProblemException.class, exception.getClass());


        verify(userService, times(1)).findUserByEmail(any());
        verifyNoMoreInteractions(userService);
        verifyNoInteractions(craftWineService, wineStarsRepository);

    }


    @Test
    @DisplayName("wine rate isn't present for the wine from the user")
    void wineStartIsNotExistForTheWineFromTheUser() {
        wineStar = new WineStar(user, craftWine, rate);

        short avgRate = (short) ((craftWine.getRate() + rate) / 2);

        doReturn(email).when(tokenProvider).extractUsername(token);
        doReturn(Optional.of(user)).when(userService).findUserByEmail(email);
        doReturn(craftWine).when(craftWineService).findById(wineId);
        doReturn(Optional.empty()).when(wineStarsRepository).isExistStarForTheWineByUser(
                user, craftWine);
        doReturn(wineStar).when(wineStarsRepository).save(wineStar);
        doReturn(true).when(craftWineService).save(craftWine);
        doReturn(avgRate).when(wineStarsRepository).getAverageRateForTheWine(craftWine);

        wineStarServiceImpl.addRateForTheWine(token, wineId, rate);

        verify(userService).findUserByEmail(stringArgumentCaptor.capture());
        verify(craftWineService).findById(longArgumentCaptor.capture());
        verify(wineStarsRepository).isExistStarForTheWineByUser(userArgumentCaptor.capture(),
                craftWineArgumentCaptor.capture());


        assertEquals(avgRate, craftWine.getRate());
        assertEquals(user.getEmail(), stringArgumentCaptor.getValue());
        assertEquals(wineId, longArgumentCaptor.getValue());
        assertEquals(user, userArgumentCaptor.getValue());
        assertEquals(craftWine, craftWineArgumentCaptor.getValue());


        verify(userService, times(1)).findUserByEmail(any());
        verify(craftWineService, times(1)).findById(wineId);
        verify(craftWineService, times(1)).save(any());
        verify(wineStarsRepository, times(1)).isExistStarForTheWineByUser(any(User.class),
                any(CraftWine.class));
        verify(wineStarsRepository, times(1)).save(any());
        verify(wineStarsRepository, times(1)).getAverageRateForTheWine(any());
    }


}