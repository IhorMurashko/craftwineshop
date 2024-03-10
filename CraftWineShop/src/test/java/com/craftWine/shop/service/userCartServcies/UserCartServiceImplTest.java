package com.craftWine.shop.service.userCartServcies;

import com.craftWine.shop.dto.userCartDTO.WineItemForUserCart;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.User;
import com.craftWine.shop.models.UserCart;
import com.craftWine.shop.repositories.UserCartRepository;
import com.craftWine.shop.service.crafWineServices.CraftWineService;
import com.craftWine.shop.service.userServices.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCartServiceImplTest {
    @Mock
    private UserCartRepository userCartRepository;
    @Mock
    private UserService userService;
    @Mock
    private CraftWineService craftWineService;

    @InjectMocks
    private UserCartServiceImpl userCartService;


    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;
    @Captor
    private ArgumentCaptor<Long> wineIdArgumentCaptor;
    @Captor
    ArgumentCaptor<UserCart> userCartArgumentCaptor;


    private String token;
    private User user;
    private CraftWine craftWine;
    private final long wineId = 1L;
    private UserCart userCart;
    private WineItemForUserCart wineItemForUserCart;

    @BeforeEach
    void init() {
        this.token = UUID.randomUUID().toString();
        this.craftWine = new CraftWine();
        this.craftWine.setId(wineId);

        this.userCart = new UserCart();
        this.userCart.setWinesWithQuantity(new HashMap<>());
        this.userCart.setUser(user);
        this.userCart.setId(1L);

        this.user = new User();
        this.user.setId(1L);
        this.user.setUserCart(new UserCart());
        this.user.setUserCart(userCart);

        this.wineItemForUserCart = new WineItemForUserCart(wineId, (short) 4);
    }


    @Test
    void userCartService_addWineToTheCart_success() {

        doReturn(Optional.ofNullable(user)).when(userService).extractUserFromToken(token);
        doReturn(Optional.ofNullable(craftWine)).when(craftWineService).findById(wineId);


        boolean result = userCartService.addWineToTheCart(token, wineItemForUserCart);
        verify(userService).extractUserFromToken(stringArgumentCaptor.capture());
        verify(craftWineService).findById(wineIdArgumentCaptor.capture());

        assertTrue(result);

        verify(userService, times(1)).extractUserFromToken(any());
        verify(craftWineService, times(1)).findById(anyLong());

        assertEquals(token, stringArgumentCaptor.getValue());
        assertEquals(wineId, wineIdArgumentCaptor.getValue());
    }


    @Test
    void userCartService_addWineToTheCart_userNotFound() {

        doReturn(Optional.empty()).when(userService).extractUserFromToken(any());
        doReturn(Optional.ofNullable(craftWine)).when(craftWineService).findById(wineId);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userCartService.addWineToTheCart(token, wineItemForUserCart));

        verify(userService).extractUserFromToken(stringArgumentCaptor.capture());
        verify(craftWineService).findById(wineIdArgumentCaptor.capture());

        assertNotNull(exception);
        assertEquals(RuntimeException.class, exception.getClass());
        assertEquals("Couldn't find user", exception.getMessage());
        assertEquals(token, stringArgumentCaptor.getValue());
        assertEquals(wineId, wineIdArgumentCaptor.getValue());

        verifyNoMoreInteractions(craftWineService, userService);

    }

    @Test
    void userCartService_addWineToTheCart_craftWineNitFound() {
        doReturn(Optional.ofNullable(user)).when(userService).extractUserFromToken(token);
        doReturn(Optional.empty()).when(craftWineService).findById(wineId);


        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userCartService.addWineToTheCart(token, wineItemForUserCart));
        verify(userService).extractUserFromToken(stringArgumentCaptor.capture());
        verify(craftWineService).findById(wineIdArgumentCaptor.capture());

        assertNotNull(exception);
        assertEquals(RuntimeException.class, exception.getClass());
        assertEquals("Couldn't find wine", exception.getMessage());

        assertEquals(token, stringArgumentCaptor.getValue());
        assertEquals(wineId, wineIdArgumentCaptor.getValue());


        verify(userService, times(1)).extractUserFromToken(any());
        verify(craftWineService, times(1)).findById(anyLong());

        verifyNoMoreInteractions(userService, craftWineService);


    }

}