package com.restaurantApp.test.auth;

import com.restaurantApp.test.config.JwtService;
import com.restaurantApp.test.user.Role;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public RegisterResponse register(CreateUserRequest createUserRequest) {
        String password = passwordEncoder.encode(createUserRequest.getUserDto().getPassword());
        var user = UserMapper.mapToUser(createUserRequest.getUserDto());
        user.setPassword(password);
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return RegisterResponse.builder()
               .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var userId = user.getId();
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(userId)
                .build();
    }
}
