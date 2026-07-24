package com.smart_warehouse_management.Authentication.Service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart_warehouse_management.Authentication.Dto.ChangePasswordDto;
import com.smart_warehouse_management.Authentication.Dto.ForgotPasswordDto;
import com.smart_warehouse_management.Authentication.Dto.JwtResponseDto;
import com.smart_warehouse_management.Authentication.Dto.LoginRequestDto;
import com.smart_warehouse_management.Authentication.Dto.RegisterRequestDto;
import com.smart_warehouse_management.Authentication.Dto.UserResponseDto;
import com.smart_warehouse_management.Authentication.Entity.User;
import com.smart_warehouse_management.Authentication.Exception.BadRequestException;
import com.smart_warehouse_management.Authentication.Exception.DuplicateResourceException;
import com.smart_warehouse_management.Authentication.Exception.ResourceNotFoundException;
import com.smart_warehouse_management.Authentication.Mapper.UserMapper;
import com.smart_warehouse_management.Authentication.Repository.UserRepository;
import com.smart_warehouse_management.Authentication.Security.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
	
	
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil) {

           this.userRepository = userRepository;
           this.userMapper = userMapper;
           this.passwordEncoder = passwordEncoder;
           this.authenticationManager = authenticationManager;
           this.jwtUtil = jwtUtil;
}
    

  
    
    
    // Register method 
    
    @Override
    public UserResponseDto register(RegisterRequestDto request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        // Check if mobile already exists
        if (userRepository.existsByMobile(request.getMobile())) {
            throw new DuplicateResourceException("Mobile number already exists.");
        }

        // Convert DTO to Entity
        User user = userMapper.toEntity(request);

        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save user
        User savedUser = userRepository.save(user);

        // Return response
        return userMapper.toResponseDto(savedUser);
    }

    
    
    
    // Login Method 
    
    
    @Override
    public JwtResponseDto login(LoginRequestDto request) {

        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( request.getEmail(),request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->new ResourceNotFoundException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());

        return new JwtResponseDto(token, "Bearer");
    }
    
    
    
    
    
    
    

    @Override
    public void forgotPassword(ForgotPasswordDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: "
                                + request.getEmail()));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }
    
    
    
  // Change Password Method 
    @Override
    public void changePassword(Long userId, ChangePasswordDto request) {

        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Verify old password
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Old password is incorrect.");
        }

        // Prevent same password reuse
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new BadRequestException("New password cannot be the same as the old password.");
        }

        // Encode new password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // Save updated user
        userRepository.save(user);
    }
    
    
    
    
    

    @Override
    public List<UserResponseDto> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

     
    
    
    
    
    
    @Override
    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id : " + id));

        return userMapper.toResponseDto(user);
    }
    

    
    
    
    
    
    
    @Override
    public UserResponseDto updateUser(Long id, RegisterRequestDto request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setRole(request.getRole());

        User updatedUser = userRepository.save(user);

        return userMapper.toResponseDto(updatedUser);
    }
    
    
    
    

    
    

    
    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setStatus(false);

        userRepository.save(user);
    }
    
    
    

    @Override
    public void activateUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setStatus(true);

        userRepository.save(user);
    }
  
    
    
    

    @Override
    public void deactivateUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        user.setStatus(false);

        userRepository.save(user);
    }
    @Override
    public UserResponseDto getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return userMapper.toResponseDto(user);
    }

}