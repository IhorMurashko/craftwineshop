//package com.craftWine.shop.controllers;
//
//import com.craftWine.shop.dto.authUserDTO.RegisterDTO;
//import com.craftWine.shop.enumTypes.SugarConsistency;
//import com.craftWine.shop.enumTypes.WineColor;
//import com.craftWine.shop.models.*;
//import com.craftWine.shop.service.*;
//import jakarta.validation.Valid;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RequiredArgsConstructor
//@Validated
//@RestController
//@RequestMapping("/")
//public class TestController {
//
//    private final UserService userService;
//    private final OrderService orderService;
//    private final CraftWineService craftWineService;
//    private final OrderDetailsService orderDetailsService;
//    private final ProducedCountryService produceCountryService;
//    private final RegionService regionService;
//    private final UserCartService userCartService;
//
//
//    @PostMapping(value = "/save_new_user", consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//            )
//    public ResponseEntity<HttpStatus> saveNewUser(@NonNull @Valid @RequestBody RegisterDTO registerDTO) {
//
//
//        User user = new User(registerDTO.getEmail(), registerDTO.getPassword(), registerDTO.getPhoneNumber(),
//                registerDTO.getFirstName(), registerDTO.getLastName());
//
//
//        userService.saveUser(user);
//
//        ProducedCountry indiaProducedCountry = new ProducedCountry("India");
//        produceCountryService.save(indiaProducedCountry);
//
//
//        Region regionOfIndia = new Region("some region of India");
//        regionOfIndia.setProducedCountry(indiaProducedCountry);
//
//        regionService.save(regionOfIndia);
//
//
//        CraftWine craftWine = new CraftWine(
//                "4585", "first wine", new BigDecimal("789"), "first description", (short) 425, "0.7",
//                "13%", false, false, false, "first winemaking", "first grapeVarieries",
//                "first testingNotes", "first storeAndServeAdvices",
//                "first foodPairing", "first reviewsAndAwards", WineColor.RED, SugarConsistency.SWEET, indiaProducedCountry,
//                regionOfIndia, LocalDateTime.now(), "first imageUrl"
//        );
//
//        CraftWine craftWine2 = new CraftWine(
//                "3162", "second wine", new BigDecimal("32"), "second description", (short) 31, "1.25",
//                "9%", false, false, false, "second winemaking", "second grapeVarieries",
//                "second testingNotes", "second storeAndServeAdvices",
//                "second foodPairing", "second reviewsAndAwards", WineColor.RED, SugarConsistency.SWEET, indiaProducedCountry,
//                regionOfIndia, LocalDateTime.now(), "second imageUrl"
//        );
//
//        craftWineService.save(craftWine);
//        craftWineService.save(craftWine2);
//
//        Order order = new Order(user, "address", "some user comment");
//
//
//        OrderDetails orderDetails = new OrderDetails(craftWine, (short) 5,
//                new BigDecimal(String.valueOf(craftWine.getPrice().multiply(BigDecimal.valueOf(5)))), order);
//
//
//        order.setSum(new BigDecimal(
//                String.valueOf(order.getSum()
//                        .add(
//                                orderDetails.getSum()
//                        ))
//        ));
//
//
//        OrderDetails orderDetails2 = new OrderDetails(craftWine2, (short) 2,
//                new BigDecimal(String.valueOf(craftWine2.getPrice().multiply(BigDecimal.valueOf(2)))), order);
//
//        order.setSum(new BigDecimal(
//                String.valueOf(order.getSum()
//                        .add(
//                                orderDetails2.getSum()
//                        ))
//        ));
//
//        order.setOrderDetails(new ArrayList<>(
//        ) {{
//            add(orderDetails);
//            add(orderDetails2);
//        }});
//
//        orderService.saveOrder(order);
//        orderDetailsService.saveOrderDetails(orderDetails);
//
//
//        Map<CraftWine, Short> craftWineShortMap = new HashMap<>(5, 0.75f);
//
//        craftWineShortMap.put(craftWine, (short) 4);
//        craftWineShortMap.put(craftWine2, (short) 2);
//
//
//        UserCart userCart = user.getUserCart();
//
//        userCart.setWinesWithQuantity(craftWineShortMap);
//
//
//        List<CraftWine> userFavoritesWine = user.getFavorites();
//
//        if (userFavoritesWine == null) {
//            userFavoritesWine = new ArrayList<CraftWine>();
//        }
//
//        userFavoritesWine.add(craftWine);
//        userFavoritesWine.add(craftWine2);
//
//
//        user.setFavorites(userFavoritesWine);
//        userService.saveUser(user);
//
//        userCartService.save(userCart);
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)).build();
//    }
//
//}
