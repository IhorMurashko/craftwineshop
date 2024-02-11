//package com.craftWine.shop.controllers;
//
//import com.craftWine.shop.service.imagineHandlerService.ImageHandlerServiceImpl;
//import io.swagger.v3.oas.annotations.Hidden;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//public class TestController {
//
//    @Autowired
//    private final ImageHandlerServiceImpl imageHandlerService;
//
//
//    @Hidden
//    @PostMapping(value = "/test_upload")
//    public ResponseEntity<String> testUpload(@RequestParam("wineImage") MultipartFile multipartFile) throws Exception {
//
//        Map map = imageHandlerService.saveImageIntoServerAndReturnPath(multipartFile, "22");
//
//        String url = (String) map.get("url");
//
//
//        return ResponseEntity.ok(url);
//    }
//
//}
