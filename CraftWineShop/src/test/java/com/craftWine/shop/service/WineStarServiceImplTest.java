//package com.craftWine.shop.service;
//
//import com.craftWine.shop.enumTypes.SugarConsistency;
//import com.craftWine.shop.enumTypes.WineColor;
//import com.craftWine.shop.exceptions.CraftWineNotFoundException;
//import com.craftWine.shop.exceptions.EmailProblemException;
//import com.craftWine.shop.models.*;
//import com.craftWine.shop.repositories.WineStarsRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.TestPropertySource;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class WineStarServiceImplTest {
//    @Mock
//    private UserServiceImpl userService;
//    @Mock
//    private CraftWineServiceImpl craftWineService;
//    @Mock
//    private WineStarsRepository wineStarsRepository;
//    @InjectMocks
//    private WineStarServiceImpl wineStarService;
//    @Captor
//    private ArgumentCaptor<String> stringArgumentCaptor;
//    @Captor
//    private ArgumentCaptor<Long> longArgumentCaptor;
//    @Captor
//    private ArgumentCaptor<Short> shortArgumentCaptor;
//    @Captor
//    private ArgumentCaptor<User> userArgumentCaptor;
//    @Captor
//    private ArgumentCaptor<CraftWine> craftWineArgumentCaptor;
//
//    private User user;
//    private String userEmail;
//    private String emptyUserEmail;
//    private long craftWineIdPresent;
//    private long emptyCraftWineId;
//
//    private short wineRate;
//    private CraftWine craftWine;
//
//    @BeforeEach
//    void initUser() {
//        this.user = new User("user@example.com", "password", "123", "first name", "last name");
//        this.userEmail = "user@example.com";
//        this.emptyUserEmail = "test@gmail.com";
//        this.craftWineIdPresent = 1L;
//        this.emptyCraftWineId = 2L;
//        this.wineRate = 3;
//        this.craftWine = new CraftWine(craftWineIdPresent, "4444", "Blandithabitasse", new BigDecimal("262.32"), "Potentinon", (short) 7, "0.7",
//                "13", false, true, false, "wine making", "grape varieties", "tasting notes", "store and serve advices", "food pairing",
//                "reviews and awards", WineColor.RED, SugarConsistency.SWEET, new ProducedCountry("United Kingdom"),
//                new Region("Manchester"), "url");
//    }
//
//
//    @Test
//    @DisplayName("EmailProblemException when Optional<User> is empty because user email not found")
//    void getEmailProblemExceptionWhen_optionUserIsEmptyBecauseEmailNotFound() {
//
//        when(userService.findUserByEmail(emptyUserEmail)).thenReturn(Optional.empty());
//
//        Optional<User> optionalUser = userService.findUserByEmail(emptyUserEmail);
//
//        verify(userService).findUserByEmail(stringArgumentCaptor.capture());
//
//        RuntimeException exception = assertThrows(EmailProblemException.class, () -> {
//                    wineStarService.addRateForTheWine(emptyUserEmail, craftWineIdPresent, wineRate);
//                }
//        );
//
//
//        assertEquals(EmailProblemException.class, exception.getClass());
//        assertEquals("Could not find user with email " + emptyUserEmail, exception.getMessage());
//
//        assertEquals(emptyUserEmail, stringArgumentCaptor.getValue());
//
//
//        verify(userService, times(2)).findUserByEmail(emptyUserEmail);
//        assertFalse(optionalUser.isPresent());
//
//        verifyNoMoreInteractions(userService);
//        verifyNoInteractions(craftWineService, wineStarsRepository);
//    }
//
//
//    @Test
//    @DisplayName("IllegalArgumentException when craftWine isn't available")
//    void getIllegalArgumentExceptionWhen_craftWineIdIsNotPresent() {
//        when(userService.findUserByEmail(userEmail)).thenReturn(Optional.of(user));
//        when(craftWineService.findById(emptyCraftWineId)).thenThrow(new IllegalArgumentException("Could not find wine with id: " + emptyCraftWineId));
//
//        Optional<User> optionalUser = userService.findUserByEmail(userEmail);
//        verify(userService).findUserByEmail(stringArgumentCaptor.capture());
//
//
//        RuntimeException runtimeException = assertThrows(IllegalArgumentException.class, () ->
//        {
//            wineStarService.addRateForTheWine(userEmail, emptyCraftWineId, wineRate);
//        });
//
//
//        assertTrue(optionalUser.isPresent());
//        assertEquals(user, optionalUser.get());
//
//        verify(userService, times(2)).findUserByEmail(userEmail);
//        verifyNoMoreInteractions(userService);
//
//        assertEquals(userEmail, stringArgumentCaptor.getValue());
//        verifyNoInteractions(wineStarsRepository);
//
//
//        assertEquals(IllegalArgumentException.class, runtimeException.getClass());
//        assertEquals("Could not find wine with id: " + emptyCraftWineId, runtimeException.getMessage());
//    }
//
//
//    @Test
//    @DisplayName("find user if user email is available")
//    void getUserByEmailWhen_userEmailIsAvailable() {
//
//        when(userService.findUserByEmail(userEmail)).thenReturn(Optional.of(user));
//        when(craftWineService.findById(craftWineIdPresent)).thenReturn(craftWine);
//
//
//        wineStarService.addRateForTheWine(userEmail, craftWineIdPresent, wineRate);
//        verify(userService).findUserByEmail(stringArgumentCaptor.capture());
//        assertEquals(userEmail, stringArgumentCaptor.getValue());
//        Optional<User> optionalUser = userService.findUserByEmail(userEmail);
//
//        assertTrue(optionalUser.isPresent());
//        assertEquals(user, optionalUser.get());
//        verifyNoMoreInteractions(userService);
//        assertEquals(userEmail, stringArgumentCaptor.getValue());
//    }
//
//
//    @Test
//    @DisplayName("isn't present a rate from the user for the wine")
//    void existStarForTheWineByUserIsNullWhen_userHasNotRatedTheWine() {
//        when(userService.findUserByEmail(userEmail)).thenReturn(Optional.of(user));
//        when(craftWineService.findById(craftWineIdPresent)).thenReturn(craftWine);
//        when(wineStarsRepository.isExistStarForTheWineByUser(user, craftWine)).thenReturn(null);
//
//
//        wineStarService.addRateForTheWine(userEmail, craftWineIdPresent, wineRate);
//        verify(userService).findUserByEmail(stringArgumentCaptor.capture());
//        verify(craftWineService).findById(longArgumentCaptor.capture());
//        verify(wineStarsRepository).isExistStarForTheWineByUser(userArgumentCaptor.capture(), craftWineArgumentCaptor.capture());
//
//        Long existStarForTheWineByUser = wineStarsRepository.isExistStarForTheWineByUser(user, craftWine);
//
//        assertEquals(userEmail, stringArgumentCaptor.getValue());
//        assertEquals(craftWineIdPresent, longArgumentCaptor.getValue());
//        assertEquals(user, userArgumentCaptor.getValue());
//        assertEquals(craftWine, craftWineArgumentCaptor.getValue());
//
//        assertNull(existStarForTheWineByUser);
//
//        verify(userService, times(1)).findUserByEmail(anyString());
//        verify(craftWineService, times(1)).findById(anyLong());
//        verify(wineStarsRepository, times(2)).isExistStarForTheWineByUser(any(User.class), any(CraftWine.class));
//        verify(wineStarsRepository, times(2)).isExistStarForTheWineByUser(any(), any());
//    }
//
//
//    @Test
//    @DisplayName("rate by user is present")
//    void wineRateByUserIsPresent() {
//        when(userService.findUserByEmail(userEmail)).thenReturn(Optional.of(user));
//        when(craftWineService.findById(craftWineIdPresent)).thenReturn(craftWine);
//        when(wineStarsRepository.isExistStarForTheWineByUser(user, craftWine)).thenReturn(craftWineIdPresent);
//
//
//        wineStarService.addRateForTheWine(userEmail, craftWineIdPresent, wineRate);
//        verify(wineStarsRepository).isExistStarForTheWineByUser(userArgumentCaptor.capture(), craftWineArgumentCaptor.capture());
//        verify(userService).findUserByEmail(stringArgumentCaptor.capture());
//        verify(craftWineService).findById(longArgumentCaptor.capture());
//        verify(wineStarsRepository).isExistStarForTheWineByUser(userArgumentCaptor.capture(), craftWineArgumentCaptor.capture());
//
//        Long existStarForTheWineByUser = wineStarsRepository.isExistStarForTheWineByUser(user, craftWine);
//
//
//        assertEquals(userEmail, stringArgumentCaptor.getValue());
//        assertEquals(craftWineIdPresent, longArgumentCaptor.getValue());
//        assertEquals(user, userArgumentCaptor.getValue());
//        assertEquals(craftWine, craftWineArgumentCaptor.getValue());
//        assertEquals(craftWineIdPresent, existStarForTheWineByUser);
//
//
//        verify(userService, times(1)).findUserByEmail(anyString());
//        verifyNoMoreInteractions(userService);
//
//        verify(craftWineService, times(1)).findById(craftWineIdPresent);
//
//        verify(wineStarsRepository, times(2)).isExistStarForTheWineByUser(any(), any());
//
//        verify(wineStarsRepository, times(2)).isExistStarForTheWineByUser(any(), any());
//
//        verify(wineStarsRepository, times(1)).save(any(WineStar.class));
//        verify(craftWineService, times(1)).save(any(CraftWine.class));
//
//    }
//
//
//    @Test
//    @DisplayName("")
//    void averageRateForTheWineIsNull() {
//        when(userService.findUserByEmail(userEmail)).thenReturn(Optional.of(user));
//        when(craftWineService.findById(craftWineIdPresent)).thenReturn(craftWine);
//        when(wineStarsRepository.isExistStarForTheWineByUser(user, craftWine)).thenReturn(craftWineIdPresent);
//        when(wineStarsRepository.getAverageRateForTheWine(craftWine)).thenReturn(null);
//
//
//        wineStarService.addRateForTheWine(userEmail, craftWineIdPresent, wineRate);
//        verify(wineStarsRepository).isExistStarForTheWineByUser(userArgumentCaptor.capture(), craftWineArgumentCaptor.capture());
//        verify(userService).findUserByEmail(stringArgumentCaptor.capture());
//        verify(craftWineService).findById(longArgumentCaptor.capture());
//        verify(wineStarsRepository).isExistStarForTheWineByUser(userArgumentCaptor.capture(), craftWineArgumentCaptor.capture());
//
//
//        Short averageRateForTheWine = wineStarsRepository.getAverageRateForTheWine(craftWine);
//
//        assertNull(averageRateForTheWine);
//
//
//
//
//
//    }
//
//
//}