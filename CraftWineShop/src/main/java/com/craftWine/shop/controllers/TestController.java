package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.UserDTO;
import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.models.*;
import com.craftWine.shop.service.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class TestController {

    private final UserService userService;
    private final OrderService orderService;
    private final CraftWineService craftWineService;
    private final OrderDetailsService orderDetailsService;
    private final ProducedCountryService produceCountryService;
    private final RegionService regionService;
    private final UserCartService userCartService;


    @PostMapping(value = "/save_new_user", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> saveNewUser(@NonNull @RequestBody UserDTO userDTO) {


        User user = new User(userDTO.getEmail(), userDTO.getPassword(), userDTO.getPhoneNumber(),
                userDTO.getFirstName(), userDTO.getLastName(), userDTO.getDeliveryAddress());


        userService.saveUser(user);

        ProducedCountry ukraineProducedCountry = new ProducedCountry("Ukraine");
        produceCountryService.save(ukraineProducedCountry);


        Region khersonRegion = new Region("Kherson");
        khersonRegion.setProducedCountry(ukraineProducedCountry);

        regionService.save(khersonRegion);


        CraftWine craftWine = new CraftWine(
                "4585", "name", new BigDecimal("789"), "description", (short) 425, "0.7",
                "13%", false, false, false, "winemaking", "grapeVarieries", "testingNotes", "storeAndServeAdvices",
                "foodPairing", "reviewsAndAwards", WineColor.RED, SugarConsistency.SWEET, ukraineProducedCountry,
                khersonRegion, 0, LocalDateTime.now(), "imageUrl"
        );

        CraftWine craftWine2 = new CraftWine(
                "3162", "name", new BigDecimal("32"), "description", (short) 425, "0.7",
                "13%", false, false, false, "winemaking", "grapeVarieries", "testingNotes", "storeAndServeAdvices",
                "foodPairing", "reviewsAndAwards", WineColor.RED, SugarConsistency.SWEET, ukraineProducedCountry,
                khersonRegion, 0, LocalDateTime.now(), "imageUrl"
        );

        craftWineService.save(craftWine);
        craftWineService.save(craftWine2);

        Order order = new Order(user, "address", "some user comment");


        OrderDetails orderDetails = new OrderDetails(craftWine, (short) 5,
                new BigDecimal(String.valueOf(craftWine.getPrice().multiply(BigDecimal.valueOf(5)))), order);


        order.setSum(new BigDecimal(
                String.valueOf(order.getSum()
                        .add(
                                orderDetails.getSum()
                        ))
        ));


        OrderDetails orderDetails2 = new OrderDetails(craftWine2, (short) 2,
                new BigDecimal(String.valueOf(craftWine2.getPrice().multiply(BigDecimal.valueOf(2)))), order);

        order.setSum(new BigDecimal(
                String.valueOf(order.getSum()
                        .add(
                                orderDetails2.getSum()
                        ))
        ));

        order.setOrderDetails(new ArrayList<>(
        ) {{
            add(orderDetails);
            add(orderDetails2);
        }});

        orderService.saveOrder(order);
        orderDetailsService.saveOrderDetails(orderDetails);


        Map<CraftWine, Short> craftWineShortMap = new HashMap<>(5, 0.75f);

        craftWineShortMap.put(craftWine, (short) 4);
        craftWineShortMap.put(craftWine2, (short) 2);


        UserCart userCart = user.getUserCart();

        userCart.setWinesWithQuantity(craftWineShortMap);


        userCartService.save(userCart);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
