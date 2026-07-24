package com.smart_warehouse_management.Authentication.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

}