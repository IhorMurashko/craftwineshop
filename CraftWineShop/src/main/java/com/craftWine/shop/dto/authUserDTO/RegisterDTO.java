package com.craftWine.shop.dto.authUserDTO;

import com.craftWine.shop.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Registration DTO for {@link User}
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegisterDTO implements Serializable {

    @NotBlank
    @Email(message = "email patter is not correct")
    String email;

    @Size(min = 4, max = 35, message = "password should be minimum 4 characters and maximum 35")
    @NotBlank
    String password;

    String confirmationThePassword;


    @NotBlank
    @Pattern(regexp = "^\\d{12}$")
    String phoneNumber;


    //строка должна начинаться с буквы и состоять только из букв, длинна должна быть не менее двух символов.
    @Pattern(regexp = "^\\p{L}{2,}$")
    @NotBlank
    String firstName;

    //строка должна начинаться с буквы и состоять только из букв, и ее длинна должна быть не менее двух символов.
    @Pattern(regexp = "^\\p{L}{2,}$")
    @NotBlank
    String lastName;

}