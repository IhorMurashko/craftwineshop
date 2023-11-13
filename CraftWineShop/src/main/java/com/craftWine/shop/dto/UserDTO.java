package com.craftWine.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class UserDTO {


    public UserDTO(String email, String password, String confirmPassword, String phoneNumber,
                   String firstName, String lastName, String deliveryAddress) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deliveryAddress = deliveryAddress;
    }

    private String email;

    private String password;
    private String confirmPassword;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String deliveryAddress;


}
