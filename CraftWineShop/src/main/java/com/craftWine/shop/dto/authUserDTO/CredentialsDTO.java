package com.craftWine.shop.dto.authUserDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
@Schema(description = "сутність для аутентифікації")
public record CredentialsDTO(@NotBlank
                             @Email(message = "Email pattern is not correct")
                             @Schema(title = "емайл користувача", description = "емайл для здійснення аутентифікації", pattern = "має відповідати" +
                                     " стандартному паттерну емайла")
                             String email,

                             @Size(min = 4, max = 35, message = "Password should be minimum 4 characters and maximum 35")
                             @NotBlank
                             @Schema(title = "пароль користувача", description = "пароль для здійснення аутентифікації",
                                     minLength = 4, maxLength = 35, examples = {"correct: 1234", "correct: qw25481s@#sad", "wrong: 123"})
                             String password) {


}
