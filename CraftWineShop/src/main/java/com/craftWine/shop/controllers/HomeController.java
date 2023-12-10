package com.craftWine.shop.controllers;

import com.craftWine.shop.authentication.RegistrationService;
import com.craftWine.shop.authentication.UserRegisterAndAuthenticationService;
import com.craftWine.shop.dto.authUserDTO.CredentialsDTO;
import com.craftWine.shop.dto.authUserDTO.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Validated
public class HomeController {

    private final RegistrationService registrationService;
    private final UserRegisterAndAuthenticationService userService;
//    private final TokenProvider tokenProvider;


    @Autowired
    public HomeController(RegistrationService registrationService,
                          UserRegisterAndAuthenticationService userService
//                          ,TokenProvider tokenProvider
    ) {
        this.registrationService = registrationService;
        this.userService = userService;
//        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/")
    public ResponseEntity<String> homeController() {
        return ResponseEntity.ok("Daaa");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginController(@RequestBody CredentialsDTO credentialsDTO) {
        try {
            String token = userService.authenticate(credentialsDTO);

            return ResponseEntity.ok(token);
        } catch (Exception runtimeException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials");

        }
    }


    @GetMapping(path = "/confirm")
    public ResponseEntity<HttpStatus> confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Da, esti admin");
    }

    @GetMapping("/user")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("Da, esti user");
    }

    @GetMapping("/oricare")
    public ResponseEntity<String> oricare() {
        return ResponseEntity.ok("Da, esti oricare");
    }
}
