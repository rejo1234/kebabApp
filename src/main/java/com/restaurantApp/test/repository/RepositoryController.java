package com.restaurantApp.test.repository;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/repository")
@AllArgsConstructor
public class RepositoryController {
    private final RepositoryService repositoryService;

    @PatchMapping("delete-connection-with-product")
    public ResponseEntity<Void> deleteConnectionRepositoryAndProduct(
            @RequestBody RepositoryProductRequest repositoryProductRequest, @RequestParam Integer userId
    ) {
        repositoryService.deleteConnectionRepositoryAndProduct(repositoryProductRequest, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRepository(
            @RequestParam Integer repositoryId, @RequestParam Integer userId
    ) {
        repositoryService.deleteRepository(repositoryId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/connect-with-product")
    public ResponseEntity<Void> connectRepositoryAndProduct(
            @RequestBody RepositoryProductRequest repositoryProductRequest, @RequestParam Integer userId
    ) {
        repositoryService.connectProductToRepository(repositoryProductRequest, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createRepository(
            @RequestBody CreateRepositoryRequest createRepositoryRequest, @RequestParam Integer userId
    ) {
        repositoryService.createRepository(createRepositoryRequest.getRepositoryDto(), userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateRepository(
            @RequestBody CreateRepositoryRequest createRepositoryRequest, @RequestParam Integer userId
    ) {
        repositoryService.updateRepository(createRepositoryRequest.getRepositoryDto(), userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all-connected-to-restaurant")
    public ResponseEntity<List<RepositoryDto>> showRepositoriesConnectedToOneRestaurant(@RequestParam Integer restaurantId
            , @RequestParam Integer userId) {
        return ResponseEntity.ok(repositoryService.getRepositoriesForRestaurant(restaurantId, userId));
    }

    @GetMapping("/get-all-connected-to-user")
    public ResponseEntity<List<RepositoryDto>> showRepositoriesConnectedToOneUser(@RequestParam Integer userId) {
        return ResponseEntity.ok(repositoryService.getRepositoriesForUser(userId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<RepositoryDto>> showRepositories() {
        return ResponseEntity.ok(repositoryService.getAllRepositories());
    }

    @GetMapping("/get")
    public ResponseEntity<RepositoryDto> getRepository(
            @RequestParam Integer userId, @RequestParam Integer repositoryId) {
        return ResponseEntity.ok(repositoryService.getRepository(userId, repositoryId));
    }
}
