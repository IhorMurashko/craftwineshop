package com.craftWine.shop.dto.authUserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CredentialsDTO {

    private String email;
    private String password;
}
