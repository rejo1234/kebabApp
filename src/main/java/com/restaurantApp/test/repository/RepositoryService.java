package com.restaurantApp.test.repository;

import com.restaurantApp.test.auth.AuthenticateContextService;
import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RepositoryService {
    private final RepositoryRepository repositoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;
    private final RepositoryMapper repositoryMapper;
    private final AuthenticateContextService authenticationContextService;
    @Transactional
    public void deleteConnectionRepositoryAndProduct(RepositoryProductRequest repositoryProductRequest, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        authenticationContextService.authenticateRepositoryList(repositoryProductRequest.getRepositoryId());
        Repository repository = repositoryRepository.findById(repositoryProductRequest.getRepositoryId())
                .orElseThrow(() -> new RuntimeException("Repository nie znaleziony"));

        Product product = productRepository.findById(repositoryProductRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product nie znaleziono"));
        if (!product.getRepository().getId().equals(repository.getId())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Produkt o id " + product.getId() + " NIE jest powiązany z repozytorium o id " + repository.getId());
        }

        product.setRepository(null);
        repository.getProductList().remove(product);
    }

    public void deleteRepository(Integer repositoryId, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        authenticationContextService.authenticateRepositoryList(repositoryId);
        var repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new IllegalArgumentException("Repository nie istnieje"));
        if (repository != null) {
            repositoryRepository.deleteById(repositoryId);
        }
    }
    @Transactional
    public void connectProductToRepository(RepositoryProductRequest repositoryProductRequest, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        Repository repository = repositoryRepository.findById(repositoryProductRequest.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));

        Product product = productRepository.findById(repositoryProductRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Produkt nie istnieje"));
        product.setRepository(repository);
        repository.getProductList().add(product);
    }

    public void updateRepository(RepositoryDto repositoryDto, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        var repository = repositoryRepository.findById(repositoryDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Repozytorium nie istnieje"));
        authenticationContextService.authenticateRepositoryList(repository.getId());
        repository = repositoryMapper.dtoToRepository(repositoryDto);
        repositoryRepository.save(repository);
    }

    public void createRepository(RepositoryDto repositoryDto, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        var repository = repositoryMapper.dtoToRepository(repositoryDto);
        repositoryRepository.save(repository);
    }

    public List<Repository> getAllRepositories(Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        return repositoryRepository.findAll();
    }

    public List<Repository> getRepositoriesConnectedToOneRestaurant(Integer restaurantId, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        return restaurant.getRepositoryList();
    }

    public Optional<Repository> getRepository(Integer repositoryId, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        return repositoryRepository.findById(repositoryId);
    }
}
