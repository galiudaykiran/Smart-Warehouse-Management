package com.smart_warehouse_management.Authentication.Service;

import java.util.List;

import com.smart_warehouse_management.Authentication.Dto.ChangePasswordDto;
import com.smart_warehouse_management.Authentication.Dto.ForgotPasswordDto;
import com.smart_warehouse_management.Authentication.Dto.JwtResponseDto;
import com.smart_warehouse_management.Authentication.Dto.LoginRequestDto;
import com.smart_warehouse_management.Authentication.Dto.RegisterRequestDto;
import com.smart_warehouse_management.Authentication.Dto.UserResponseDto;

public interface AuthService {

    UserResponseDto register(RegisterRequestDto request);

    JwtResponseDto login(LoginRequestDto request);

    void forgotPassword(ForgotPasswordDto request);

    void changePassword(Long userId, ChangePasswordDto request);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    UserResponseDto updateUser(Long id, RegisterRequestDto request);

    void deleteUser(Long id);

    void activateUser(Long id);

    void deactivateUser(Long id);

}