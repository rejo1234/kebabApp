package com.restaurantApp.test.repository;

import com.restaurantApp.test.restaurant.CreateRestaurantRequest;
import com.restaurantApp.test.restaurant.Restaurant;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/repository")
@AllArgsConstructor
public class RepositoryController {
    private final RepositoryService repositoryService;
    @DeleteMapping("/deleteRepository")
    public ResponseEntity<Void> deleteRepository(
            @RequestParam int repositoryId
    ) {
        repositoryService.deleteRepository(repositoryId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/connectRepositoryAndProduct")
    public ResponseEntity<Void> connectRepositoryAndProduct(
            @RequestBody RepositoryProductRequest repositoryProductRequest
    ) {
        repositoryService.connectProductToRepository(repositoryProductRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/createRepository")
    public ResponseEntity<Void> createRepository(
            @RequestBody CreateRepositoryRequest createRepositoryRequest
    ) {
        repositoryService.createRepository(createRepositoryRequest);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/updateRestaurant")
    public ResponseEntity<Void> updateRepository(
            //ogarnac dlaczego mam tutaj restaurantRequestDto i zamienic na createRestaurantRequest
            @RequestBody CreateRepositoryRequest createRepositoryRequest
    ) {
        repositoryService.updateRepository(createRepositoryRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getRepositoriesConnectedToOneRestaurant")
    public ResponseEntity<List<Repository>> showRepositoriesConnectedToOneRestaurant(@RequestParam int idRestaurant) {
        return ResponseEntity.ok(repositoryService.showRepositoriesConnectedToOneRestaurant(idRestaurant));
    }
    @GetMapping("/getRepositories")
    public ResponseEntity<List<Repository>> showRepositories() {
        return ResponseEntity.ok(repositoryService.showAllRepositories());
    }
    @GetMapping("/getRepository")
    public ResponseEntity<Optional<Repository>> getRepository(@RequestParam int repositoryId) {
        return ResponseEntity.ok(repositoryService.getRepository(repositoryId));
    }
}
