package com.smart_warehouse_management.Authentication.Mapper;

import org.springframework.stereotype.Component;

import com.smart_warehouse_management.Authentication.Dto.RegisterRequestDto;
import com.smart_warehouse_management.Authentication.Dto.UserResponseDto;
import com.smart_warehouse_management.Authentication.Entity.User;

@Component
public class UserMapper {

    // DTO -> Entity
    public User toEntity(RegisterRequestDto dto) {

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setMobile(dto.getMobile());
        user.setRole(dto.getRole());

        return user;
    }

    // Entity -> DTO
    public UserResponseDto toResponseDto(User user) {

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getMobile(),
                user.getRole(),
                user.getStatus() );
    }
}