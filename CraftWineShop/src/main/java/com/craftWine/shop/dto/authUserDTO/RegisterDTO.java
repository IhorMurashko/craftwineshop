package com.craftWine.shop.dto.authUserDTO;

import com.craftWine.shop.enumTypes.NotificationType;
import com.craftWine.shop.models.User;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The {@code RegisterDTO} class represents a data transfer object (DTO) for {@link User} registration information.
 * It includes fields for email, password, confirmation password, phone number, first name, and last name.
 * The class is annotated with Lombok annotations for generating constructors, getters.
 * Additionally, it includes validation annotations for ensuring the correctness of the provided data.
 *
 * <p><strong>Validation Constraints:</strong></p>
 * <ul>
 *     <li>{@code email} must not be blank and should be a valid unique email address.</li>
 *     <li>{@code password} must not be blank and should have a length between 4 and 35 characters.</li>
 *     <li>{@code confirmationThePassword} is the confirmation of the password.</li>
 *     <li>{@code phoneNumber} must not be blank and should match the pattern "^\\d{12}$" and must be unique.</li>
 *     <li>{@code firstName} must not be blank and should match the pattern "^\\p{L}{2,}$".</li>
 *     <li>{@code lastName} must not be blank and should match the pattern "^\\p{L}{2,}$".</li>
 * </ul>
 *
 * @author Ihor Murashko
 * @version 1.0
 * @since 2023-12-17
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(description = "сутність для реєстрації")
public class RegisterDTO implements Serializable {
    /**
     * The email address of the user.
     */
    @NotBlank(message = "Email field can't be blank")
    @Email(message = "Email pattern is not correct", regexp = ".+@.+\\..+")
    @Schema(title = "емайл користовача", description = "має відповідати паттерну стандартного адресу для емайлу",
            examples = {"test@example.com"})
    private String email;

    /**
     * The password for the user account.
     */
    @Size(min = 4, max = 35, message = "Password should be minimum 4 characters and maximum 35")
    @NotBlank
    @Schema(title = "пароль користувача", description = "пароль може буди будь-яким, але містити більше 3 символів і менше 36",
            minLength = 4, maxLength = 35, examples = {"1234", "qw25481s@#sad"})
    private String password;

    /**
     * The confirmation of the user's password.
     */

    @Schema(description = "підтвердження паролю користувача")
    private String confirmationThePassword;

    /**
     * The phone number of the user.
     */
    @NotBlank
    @Pattern(regexp = "^\\d{12}$", message = "Phone number should have 12 digits")
    @Schema(title = "номер телефону користовача", description = "має містити 12 цифрових символів",
            example = "380632271010")
    private String phoneNumber;

    /**
     * The first name of the user.
     */
    @Pattern(regexp = "^\\p{L}{2,}$", message = "First name should start with a letter and have at least two characters")
    @NotBlank
    @Schema(title = "ім'я користувача", description = "має відповідати паттерну: містити тільки символи літер з таблиці " +
            "ЮНІКОД і довжиною від двух літер", example = "Дмитро")
    private String firstName;

    /**
     * The last name of the user.
     */
    @Pattern(regexp = "^\\p{L}{2,}$", message = "Last name should start with a letter and have at least two characters")
    @NotBlank
    @Schema(title = "прізвище користувача", description = "має відповідати паттерну: містити тільки символи літер з таблиці" +
            " ЮНІКОД і довжиною від двух літер", example = "Кириленко")
    private String lastName;
    @Hidden
    private NotificationType notificationType = NotificationType.EMAIL;
}
