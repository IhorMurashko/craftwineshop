package com.craftWine.shop.service;

import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.exceptions.EmailProblemException;
import com.craftWine.shop.exceptions.NotFoundException;
import com.craftWine.shop.models.*;
import com.craftWine.shop.repositories.WineCommentRepository;
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
class WineCommentServiceImplTest {


    @Mock
    private UserService userService;
    @Mock
    private CraftWineService craftWineService;
    @Mock
    private WineCommentRepository wineCommentRepository;
    @Mock
    private TokenProvider tokenProvider;
    @InjectMocks
    private WineCommentServiceImpl wineCommentService;


    @Captor
    private ArgumentCaptor<WineComment> wineCommentArgumentCaptor;


    private String userEmail;
    private ProducedCountry countryFrance;
    private Region regionOfFrance;
    private CraftWine craftWine;
    private String token;
    private long wineId;
    private String comment;
    private User user;

    @BeforeEach
    void setUp() {
        this.userEmail = "test@email.com";

        this.token = UUID.randomUUID().toString();

        this.wineId = 1L;
        this.comment = "new comment";

        this.user = new User(userEmail, "password", "012345678901", "name",
                "last_name");
        user.setId(1);


        this.countryFrance = new ProducedCountry("France");
        countryFrance.setId(1L);
        countryFrance.setRegions(new HashSet<>());

        this.regionOfFrance = new Region();
        regionOfFrance.setProducedCountry(countryFrance);
        regionOfFrance.setName("region of France");
        regionOfFrance.setId(1);


        craftWine = new CraftWine();
        craftWine.setId(1);
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
    @DisplayName("get EmailProblemException when user email not found")
    void getEmailProblemExceptionWhen_userWithEmailNotFound() {

        doReturn(userEmail).when(tokenProvider).extractUsername(token);
        doReturn(Optional.empty()).when(userService).findUserByEmail(userEmail);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> wineCommentService.addComment(
                wineId, token, comment
        ));


        assertEquals(EmailProblemException.class, exception.getClass());
        assertEquals("Couldn't  find user with email " + userEmail, exception.getMessage());

        verify(tokenProvider, times(1)).extractUsername(anyString());
        verify(userService, times(1)).findUserByEmail(any());
        verifyNoMoreInteractions(tokenProvider, userService);
        verifyNoInteractions(craftWineService, wineCommentRepository);
    }


    @Test
    @DisplayName("get NotFoundException when wine with id not found")
    void getNotFoundExceptionWhen_wineWithIdNotFound() {
        doReturn(userEmail).when(tokenProvider).extractUsername(token);
        doReturn(Optional.of(user)).when(userService).findUserByEmail(userEmail);
        doThrow(new NotFoundException("Could not find wine with id: " + wineId)).when(craftWineService).findById(wineId);


        RuntimeException exception = assertThrows(NotFoundException.class, () -> wineCommentService.addComment(wineId, token, comment));


        assertEquals(NotFoundException.class, exception.getClass());

        verify(tokenProvider, times(1)).extractUsername(anyString());
        verify(userService, times(1)).findUserByEmail(anyString());
        verify(craftWineService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(tokenProvider, userService, craftWineService);
        verifyNoInteractions(wineCommentRepository);
    }


    @Test
    @DisplayName("save wine comment with true result")
    void saveWineCommentByUser() {
        doReturn(userEmail).when(tokenProvider).extractUsername(token);
        doReturn(Optional.of(user)).when(userService).findUserByEmail(userEmail);
        doReturn(craftWine).when(craftWineService).findById(wineId);

        boolean result = wineCommentService.addComment(wineId, token, comment);
        verify(wineCommentRepository).saveAndFlush(wineCommentArgumentCaptor.capture());

        assertTrue(result);
        assertEquals(craftWine, wineCommentArgumentCaptor.getValue().getCraftWine());
        assertEquals(user, wineCommentArgumentCaptor.getValue().getUser());
        assertEquals(comment, wineCommentArgumentCaptor.getValue().getComment());


        verify(tokenProvider, times(1)).extractUsername(any());
        verify(userService, times(1)).findUserByEmail(any());
        verify(craftWineService, times(1)).findById(anyLong());
        verify(wineCommentRepository, times(1)).saveAndFlush(any());


        verifyNoMoreInteractions(tokenProvider, userService, craftWineService, wineCommentRepository);

    }


}