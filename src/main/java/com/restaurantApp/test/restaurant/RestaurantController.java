package com.restaurantApp.test.restaurant;

import com.restaurantApp.test.repository.RepositoryService;
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

    @DeleteMapping("delete-connection-with-repository")
    public ResponseEntity<Void> deleteConnectionRestaurantAndRepository(
            @RequestBody RestaurantRepositoryRequest restaurantRepositoryRequest, @RequestParam Integer userId
    ) {
        restaurantService.deleteConnectionRestaurantAndRepository(restaurantRepositoryRequest, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/connect-with-repository")
    public ResponseEntity<Void> connectRestaurantAndRepository(
            @RequestBody RestaurantRepositoryRequest restaurantRepositoryRequest, @RequestParam Integer userId
    ) {
        restaurantService.connectRepositoryToRestaurant(restaurantRepositoryRequest, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createRestaurant(
            @RequestBody CreateRestaurantRequest createRestaurantRequest, Integer userId
    ) {
        restaurantService.createRestaurant(createRestaurantRequest.getRestaurantDto(), userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{restaurantId}")
    public ResponseEntity<Void> updateRestaurant(
            @RequestBody CreateRestaurantRequest createRestaurantRequest, @PathVariable Integer restaurantId
            , @RequestParam Integer userId
    ) {
        restaurantService.updateRestaurant(createRestaurantRequest.getRestaurantDto(), restaurantId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRestaurant(
            @RequestParam Integer restaurantId, @RequestParam Integer userId
    ) {
        restaurantService.deleteRestaurant(restaurantId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Restaurant>> showRepositories(@RequestParam Integer userId) {
        return ResponseEntity.ok(restaurantService.showAllRestaurants(userId));
    }

    @GetMapping("/get")
    public ResponseEntity<Restaurant> getRestaurant(@RequestParam Integer restaurantId, @RequestParam Integer userId) {
        return ResponseEntity.ok(restaurantService.getRestaurant(restaurantId, userId));
    }

    @GetMapping("/get-restaurants-connected-one-repository")
    public ResponseEntity<List<Restaurant>> showRestaurantsConnectedToOneRepository(@RequestParam Integer repositoryId
    , @RequestParam Integer userId) {
        return ResponseEntity.ok(restaurantService.showRestaurantsConnectedToOneRepository(repositoryId, userId));
    }
}
