package com.smart_warehouse_management.Authentication.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponseDto {

    private String token;

    private String tokenType;

}