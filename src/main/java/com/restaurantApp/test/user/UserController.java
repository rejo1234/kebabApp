package com.restaurantApp.test.user;

import com.restaurantApp.test.auth.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(
            @RequestParam int userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateUser(
            @RequestBody CreateUserRequest createUserRequest, @RequestParam Integer userId
    ) {
        userService.updateUser(createUserRequest, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/connect-with-restaurant")
    public ResponseEntity<Void> connectUserAndRestaurant(
            @RequestBody UserRestaurantRequest userRestaurantRequest, @RequestParam Integer userId
    ) {
        userService.connectRestaurantToUser(userRestaurantRequest, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/connect-with-repository")
    public ResponseEntity<Void> connectUserAndRepository(
            @RequestBody UserRepositoryRequest userRepositoryRequest, @RequestParam Integer userId
    ) {
        userService.connectRepositoryToUser(userRepositoryRequest, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("delete-connection-with-repository")
    public ResponseEntity<Void> deleteConnectionUserAndRepository(
            @RequestBody UserRepositoryRequest userRepositoryRequest, @RequestParam Integer userId
    ) {
        userService.deleteConnectionUserAndRepository(userRepositoryRequest, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("delete-connection-with-restaurant")
    public ResponseEntity<Void> deleteConnectionUserAndRestaurant(
            @RequestBody UserRestaurantRequest userRestaurantRequest, @RequestParam Integer userId
    ) {
        userService.deleteConnectionUserAndRestaurant(userRestaurantRequest, userId);
        return ResponseEntity.ok().build();
    }
}
