package com.craftWine.shop.controllers;

import com.craftWine.shop.authentication.RegistrationService;
import com.craftWine.shop.authentication.UserRegisterAndAuthenticationService;
import com.craftWine.shop.dto.authUserDTO.CredentialsDTO;
import com.craftWine.shop.security.TokenProperties;
import com.craftWine.shop.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Validated
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final RegistrationService registrationService;
    private final UserRegisterAndAuthenticationService userService;
    private final TokenProvider tokenProvider;
    private final TokenProperties tokenProperties;


    @GetMapping("/")
    public ResponseEntity<String> homeController() {
        return ResponseEntity.ok("Daaa");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginController(@RequestBody CredentialsDTO credentialsDTO,
                                                  @RequestHeader(name = "Authorization", required = false) String headerToken
    ) {
        try {

            if (headerToken != null &&
                    headerToken.startsWith("Bearer ") &&
                    tokenProvider.validateToken(headerToken.substring(7))) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You have already logged in");
            }


            String token = userService.authenticate(credentialsDTO);

            log.info("new token: {}", token);

            HttpHeaders header = new HttpHeaders();
            header.setBearerAuth(token);

            return ResponseEntity.status(HttpStatus.OK)
                    .headers(header).build();

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
