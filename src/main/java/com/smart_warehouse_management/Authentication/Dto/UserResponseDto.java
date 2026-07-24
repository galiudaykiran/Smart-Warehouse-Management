package com.smart_warehouse_management.Authentication.Dto;
import com.smart_warehouse_management.Authentication.Entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;

    private String name;

    private String email;

    private String mobile;

    private Role role;

    private Boolean status;

}