package com.restaurantApp.test.repository;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import com.restaurantApp.test.user.User;
import com.restaurantApp.test.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RepositoryService {
    private final RepositoryRepository repositoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;
    private final RepositoryMapper repositoryMapper;
    private final ContextService authenticationContextService;
    private final UserRepository userRepository;

    @Transactional
    public void deleteConnectionRepositoryAndProduct(RepositoryProductRequest repositoryProductRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRepositoryId(repositoryProductRequest.getRepositoryId());
        Repository repository = repositoryRepository.findById(repositoryProductRequest.getRepositoryId())
                .orElseThrow(() -> new RuntimeException("Repository nie znaleziony"));

        Product product = productRepository.findById(repositoryProductRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product nie znaleziono"));
        if (!product.getRepository().getId().equals(repository.getId())) {
            throw new IllegalArgumentException(
                    "Produkt o id " + product.getId() + " NIE jest powiązany z repozytorium o id " + repository.getId()
            );
        }

        product.setRepository(null);
        repository.getProductList().remove(product);
    }

    public void deleteRepository(Integer repositoryId, Integer userId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRepositoryId(repositoryId);
        if (!repositoryRepository.existsById(repositoryId)) {
            throw new IllegalArgumentException("Repository nie istnieje");
        }
        repositoryRepository.deleteById(repositoryId);
    }

    @Transactional
    public void connectProductToRepository(RepositoryProductRequest repositoryProductRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRepositoryId(repositoryProductRequest.getRepositoryId());
        Repository repository = repositoryRepository.findById(repositoryProductRequest.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));

        Product product = productRepository.findById(repositoryProductRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Produkt nie istnieje"));
        product.setRepository(repository);
        repository.getProductList().add(product);
    }

    public void updateRepository(RepositoryDto repositoryDto, Integer userId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRepositoryId(repositoryDto.getId());
        var repository = repositoryMapper.dtoToRepository(repositoryDto);
        repositoryRepository.save(repository);
    }

    public void createRepository(RepositoryDto repositoryDto, Integer userId) {
        authenticationContextService.validateUserId(userId);
        var repository = repositoryMapper.dtoToRepository(repositoryDto);
        repositoryRepository.save(repository);
    }

    public List<RepositoryDto> getAllRepositories() {
        List<Repository> repositoryList = repositoryRepository.findAll();
        return repositoryList.stream()
                .map(repositoryMapper::repositoryToDto)
                .toList();
    }

    public List<RepositoryDto> getRepositoriesForRestaurant(Integer restaurantId, Integer userId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRestaurantId(restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));

        return restaurant.getRepositoryList().stream()
                .map(repositoryMapper::repositoryToDto)
                .toList();
    }

    public List<RepositoryDto> getRepositoriesForUser(Integer userId) {
        authenticationContextService.validateUserId(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User nie istnieje"));

        return user.getRepositoryList().stream()
                .map(repositoryMapper::repositoryToDto)
                .toList();
    }

    public RepositoryDto getRepository(Integer userId, Integer repositoryId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRepositoryId(repositoryId);
        Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow();
        return repositoryMapper.repositoryToDto(repository);
    }
}
