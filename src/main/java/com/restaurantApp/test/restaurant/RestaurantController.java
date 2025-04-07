package com.restaurantApp.test.restaurant;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryService;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepositoryRequest;
import com.restaurantApp.test.user.UserRestaurantRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant")
@AllArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final RepositoryService repositoryService;
    @DeleteMapping("deleteConnectionRestaurantAndRepository")
    public ResponseEntity<Void> deleteConnectionRestaurantAndRepository(
            @RequestBody RestaurantRepositoryRequest restaurantRepositoryRequest
    ) {
        restaurantService.deleteConnectionRestaurantAndRepository(restaurantRepositoryRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("deleteConnectionRestaurantAndProduct")
    public ResponseEntity<Void> deleteConnectionRestaurantAndProduct(
            @RequestBody RestaurantProductRequest restaurantProductRequest
    ) {
        restaurantService.deleteConnectionRestaurantAndProduct(restaurantProductRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/connectRestaurantAndRepository")
    public ResponseEntity<Void> connectRestaurantAndRepository(
            @RequestBody RestaurantRepositoryRequest restaurantRepositoryRequest
    ) {
        restaurantService.connectRepositoryToRestaurant(restaurantRepositoryRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/connectRestaurantAndProduct")
    public ResponseEntity<Void> connectRestaurantAndProduct(
            @RequestBody RestaurantProductRequest restaurantProductRequest
    ) {
        restaurantService.connectProductToRestaurant(restaurantProductRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/createRestaurant")
    public ResponseEntity<Void> createRestaurant(
            @RequestBody CreateRestaurantRequest createRestaurantRequest
    ) {
        restaurantService.createRestaurant(createRestaurantRequest);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/updateRestaurant")
    public ResponseEntity<Void> updateRestaurant(
            //ogarnac dlaczego mam tutaj restaurantRequestDto i zamienic na createRestaurantRequest
            @RequestBody CreateRestaurantRequest createRestaurantRequest
    ) {
        restaurantService.updateRestaurant(createRestaurantRequest);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/deleteRestaurant")
    public ResponseEntity<Void> deleteRestaurant(
            @RequestParam int restaurantId
    ) {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getRestaurants")
    public ResponseEntity<List<Restaurant>> showRepositories() {
        return ResponseEntity.ok(restaurantService.showAllRestaurants());
    }

    @GetMapping("/getRestaurant")
    public ResponseEntity<Restaurant> getRestaurant(@RequestParam int restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurant(restaurantId));
    }
    @GetMapping("/getRestaurantsConnectedToOneRepository")
    public ResponseEntity<List<Restaurant>> showRestaurantsConnectedToOneRepository(@RequestParam int repositoryId) {
        return ResponseEntity.ok(restaurantService.showRestaurantsConnectedToOneRepository(repositoryId));
    }
}
