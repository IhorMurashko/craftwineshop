package com.craftWine.shop.service.userFavoriteWineService;

import com.craftWine.shop.exceptions.UserNotFoundException;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.User;
import com.craftWine.shop.service.crafWineServices.CraftWineService;
import com.craftWine.shop.service.userServices.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFavoriteWineServiceImplTest {
    @Mock
    private CraftWineService craftWineService;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserFavoriteWineServiceImpl userFavoriteWineServiceImpl;


    private String tokenF;
    private String tokenS;
    private long wineIdItaly;
    private long wineIdPortugal;


    private User userF;
    private User userS;
    private CraftWine craftWineItaly;
    private CraftWine craftWinePortugal;

    @BeforeEach
    void init() {
        this.tokenF = UUID.randomUUID().toString();
        this.tokenS = UUID.randomUUID().toString();


        this.userF = new User();
        this.userS = new User();

        this.wineIdItaly = 1L;
        this.wineIdPortugal = 2L;


        this.craftWineItaly = new CraftWine();
        craftWineItaly.setId(wineIdItaly);

        this.craftWinePortugal = new CraftWine();
        craftWinePortugal.setId(wineIdPortugal);

    }

    @Test
    @DisplayName("positive result add craft wine to user favorites")
    void addWineToFavoriteGetTrue() {

        doReturn(Optional.ofNullable(userF)).when(userService).extractUserFromToken(tokenF);
        doReturn(Optional.ofNullable(craftWineItaly)).when(craftWineService).findById(wineIdItaly);


        boolean resultCraftWineItaly = userFavoriteWineServiceImpl.addWineToFavorite(tokenF, wineIdItaly);

        verify(userService, times(1)).extractUserFromToken(any());
        verify(craftWineService, times(1)).findById(anyLong());
        verify(userService, times(1)).saveUser(any());

        verifyNoMoreInteractions(userService, craftWineService);


        assertTrue(resultCraftWineItaly);
        assertEquals(1, userF.getFavorites().size());
        assertEquals(craftWineItaly, userF.getFavorites().iterator().next());


    }


    @Test
    @DisplayName("add to user favorite - user not found")
    void addToUserFavorite_userNotFound() {


        doReturn(Optional.empty()).when(userService).extractUserFromToken(tokenF);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userFavoriteWineServiceImpl.addWineToFavorite(tokenF, wineIdPortugal));


        assertEquals(UsernameNotFoundException.class, exception.getClass());
        assertNotNull(exception.getMessage());
        assertEquals("Could not find user", exception.getMessage());

    }


    @Test
    @DisplayName("add to user favorite - craft wine not found")
    void addToUserFavorite_craftWineNotFound() {
        doReturn(Optional.ofNullable(userF)).when(userService).extractUserFromToken(tokenF);
        doReturn(Optional.empty()).when(craftWineService).findById(wineIdPortugal);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userFavoriteWineServiceImpl.addWineToFavorite(tokenF, wineIdPortugal));

        assertNotNull(exception);
        assertEquals(UserNotFoundException.class, exception.getClass());
        assertEquals("Could not find craft with id " + wineIdPortugal, exception.getMessage());

        verify(userService, times(1)).extractUserFromToken(any());
        verifyNoMoreInteractions(userService);
    }


    @Test
    @DisplayName("positive result remove craft wine from user favorites")
    void removeCraftWineFromUserFavorites_returnTrue() {

        doReturn(Optional.ofNullable(userF)).when(userService).extractUserFromToken(tokenF);
        doReturn(Optional.ofNullable(craftWineItaly)).when(craftWineService).findById(wineIdItaly);
        doNothing().when(userService).saveUser(userF);

        userF.setFavorites(new HashSet<>() {{
            add(craftWineItaly);
        }});

        boolean result = userFavoriteWineServiceImpl.removeWineFromFavorite(tokenF, wineIdItaly);

        assertTrue(result);
    }


    @Test
    @DisplayName("remove wine from user favorites - user not found")
    void removeCraftWineFromUserFavorites_userNotFound() {

        doReturn(Optional.empty()).when(userService).extractUserFromToken(tokenF);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userFavoriteWineServiceImpl.removeWineFromFavorite(
                tokenF, wineIdItaly
        ));

        assertNotNull(exception);
        assertEquals(UsernameNotFoundException.class, exception.getClass());
        assertEquals("Could not find user", exception.getMessage());

        verify(userService, times(1)).extractUserFromToken(any());
        verifyNoMoreInteractions(userService);
    }


    @Test
    @DisplayName("remove wine from user favorites - wine not found in a db")
    void removeCraftWineFromUserFavorites_wineNotFound() {

        doReturn(Optional.ofNullable(userF)).when(userService).extractUserFromToken(tokenF);
        doReturn(Optional.empty()).when(craftWineService).findById(wineIdItaly);

        RuntimeException exception = assertThrows(RuntimeException.class, ()
                -> userFavoriteWineServiceImpl.removeWineFromFavorite(tokenF, wineIdItaly));

        assertNotNull(exception);
        assertEquals(UserNotFoundException.class, exception.getClass());
        assertEquals("Could not find craft with id " + wineIdItaly, exception.getMessage());

        verify(userService, times(1)).extractUserFromToken(any());
        verify(craftWineService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userService, craftWineService);
    }


    @Test
    @DisplayName("remove wine from user favorites - wine not found in user favorites")
    void removeWineFromUserFavorites_NotFoundException() {
        doReturn(Optional.ofNullable(userF)).when(userService).extractUserFromToken(tokenF);
        doReturn(Optional.ofNullable(craftWineItaly)).when(craftWineService).findById(wineIdItaly);

        userF.setFavorites(Collections.emptySet());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userFavoriteWineServiceImpl
                .removeWineFromFavorite(tokenF, wineIdItaly));

        assertNotNull(exception);
        assertEquals(UserNotFoundException.class, exception.getClass());
        assertEquals("Couldn't find wine in favorites", exception.getMessage());

        verify(userService, times(1)).extractUserFromToken(any());
        verify(craftWineService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userService, craftWineService);
    }


    @Test
    @DisplayName("get all favorites")
    void getAllFavorites() {


        doReturn(Optional.ofNullable(userF)).when(userService).extractUserFromToken(tokenF);


        userF.setFavorites(new HashSet<CraftWine>());

        userF.getFavorites().add(craftWinePortugal);
        userF.getFavorites().add(craftWineItaly);

        Set<CraftWine> result = userFavoriteWineServiceImpl.getFavorites(tokenF);


        assertNotNull(result);
        assertTrue(result.contains(craftWineItaly));
        assertTrue(result.contains(craftWinePortugal));

        assertEquals(2, result.size());


        verify(userService, times(1)).extractUserFromToken(any());
        verifyNoMoreInteractions(userService);
    }
}