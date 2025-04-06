package com.restaurantApp.test.repository;

import com.restaurantApp.test.product.Product;
import com.restaurantApp.test.product.ProductRepository;
import com.restaurantApp.test.restaurant.CreateRestaurantRequest;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class RepositoryService {
    private final RepositoryRepository repositoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;
    private final RepositoryMapper repositoryMapper;
    public void updateRepository(CreateRepositoryRequest createRepositoryRequest) {
        var repository = repositoryRepository.findById(createRepositoryRequest.getRepositoryDto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Repozytorium nie istnieje"));
        //czemu tutaj nie używam mappera??
        repository.setName(createRepositoryRequest.getRepositoryDto().getName());
        repository.setAddress(createRepositoryRequest.getRepositoryDto().getAddress());
        repositoryRepository.save(repository);
    }
    public void deleteRepository(int repositoryId) {
        var repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new IllegalArgumentException("Repository nie istnieje"));
        if (repository != null){
            repositoryRepository.deleteById(repositoryId);
        }
    }
    public void connectProductToRepository(RepositoryProductRequest repositoryProductRequest) {
        Repository repository = repositoryRepository.findById(repositoryProductRequest.getIdRepository())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));

        Product product = productRepository.findById(repositoryProductRequest.getIdProduct())
                .orElseThrow(() -> new IllegalArgumentException("Produkt nie istnieje"));

        repository.getProductListRepository().add(product);
        repositoryRepository.save(repository);
    }

    public void createRepository(CreateRepositoryRequest createRepositoryRequest) {
        var repository = repositoryMapper.dtoToRepository(createRepositoryRequest);
        repositoryRepository.save(repository);
    }

    public List<Repository> showAllRepositories() {
        return repositoryRepository.findAll();
    }

    public List<Repository> showRepositoriesConnectedToOneRestaurant(@RequestParam int id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        return restaurant.getRepositoryList();
    }
    public Optional<Repository> getRepository(int id) {
        return repositoryRepository.findById(id);
    }
}
