package com.restaurantApp.test.auth;
import com.restaurantApp.test.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@AllArgsConstructor
@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User dtoToUser(CreateUserRequest createUserRequest){
        return  User.builder()
                .firstname(createUserRequest.getUserDto().getFirstname())
                .lastname(createUserRequest.getUserDto().getLastname())
                .email(createUserRequest.getUserDto().getEmail())
                //nwm czemu haslo dwa pierwsze losowo wygenerowane? (patrz baze danych)
                .password(passwordEncoder.encode(createUserRequest.getUserDto().getPassword()))
                .role(createUserRequest.getUserDto().getRole())
                .build();
    }
}
