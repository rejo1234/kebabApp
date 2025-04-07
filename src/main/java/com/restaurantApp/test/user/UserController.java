package com.restaurantApp.test.user;

import com.restaurantApp.test.auth.CreateUserRequest;
import com.restaurantApp.test.product.CreateProductRequest;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.restaurant.RestaurantRepositoryRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final RepositoryRepository repositoryRepository;
    private final RestaurantRepository restaurantRepository;

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(
            @RequestParam int userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateUser")
    public ResponseEntity<Void> updateUser(
            @RequestBody CreateUserRequest createUserRequest
    ) {
        userService.updateUser(createUserRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/connectUserAndRestaurant")
    public ResponseEntity<Void> connectUserAndRestaurant(
            @RequestBody UserRestaurantRequest userRestaurantRequest
    ) {
        userService.connectRestaurantToUser(userRestaurantRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/connectUserAndRepository")
    public ResponseEntity<Void> connectUserAndRepository(
            @RequestBody UserRepositoryRequest userRepositoryRequest
    ) {
        userService.connectRepositoryToUser(userRepositoryRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("deleteConnectionUserAndRepository")
    public ResponseEntity<Void> deleteConnectionUserAndRepository(
            @RequestBody UserRepositoryRequest userRepositoryRequest
    ) {
        userService.deleteConnectionUserAndRepository(userRepositoryRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("deleteConnectionUserAndRestaurant")
    public ResponseEntity<Void> deleteConnectionUserAndRestaurant(
            @RequestBody UserRestaurantRequest userRestaurantRequest
    ) {
        userService.deleteConnectionUserAndRestaurant(userRestaurantRequest);
        return ResponseEntity.ok().build();
    }
}
