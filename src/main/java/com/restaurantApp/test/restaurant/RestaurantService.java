package com.restaurantApp.test.restaurant;

import com.restaurantApp.test.auth.AuthenticateContextService;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.user.User;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.descriptor.web.ContextService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RepositoryRepository repositoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final AuthenticateContextService authenticationContextService;

    public void deleteConnectionRestaurantAndRepository(RestaurantRepositoryRequest userRestaurantRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRestaurantList(userRestaurantRequest.getRestaurantId());
        authenticationContextService.validateRepositoryList(userRestaurantRequest.getRepositoryId());
        Repository repository = repositoryRepository.findById(userRestaurantRequest.getRepositoryId())
                .orElseThrow(() -> new RuntimeException("Repository nie znaleziony"));

        Restaurant restaurant = restaurantRepository.findById(userRestaurantRequest.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restauracji nie znaleziono"));

        restaurant.getRepositoryList().remove(repository);
        repository.getRestaurantList().remove(restaurant);
        restaurantRepository.save(restaurant);
    }

    public void connectRepositoryToRestaurant(RestaurantRepositoryRequest restaurantRepositoryRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRestaurantList(restaurantRepositoryRequest.getRepositoryId());
        authenticationContextService.validateRepositoryList(restaurantRepositoryRequest.getRepositoryId());
        Restaurant restaurant = restaurantRepository.findById(restaurantRepositoryRequest.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        Repository repository = repositoryRepository.findById(restaurantRepositoryRequest.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("Repozytorium nie istnieje"));
        restaurant.getRepositoryList().add(repository);
        repository.getRestaurantList().add(restaurant);
        restaurantRepository.save(restaurant);
    }


    public List<Restaurant> showRestaurantsConnectedToOneRepository(Integer repositoryId, Integer userId) {
        authenticationContextService.validateUserId(userId);
        Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new IllegalArgumentException("Repozytorium nie istnieje"));
        return repository.getRestaurantList();
    }

    public List<Restaurant> showAllRestaurants(Integer userId) {
        authenticationContextService.validateUserId(userId);
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(Integer restaurantId, Integer userId) {
        authenticationContextService.validateUserId(userId);
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
    }

    public void createRestaurant(RestaurantDto restaurantDto, Integer userId) {
        authenticationContextService.validateUserId(userId);
        var restaurant = restaurantMapper.dtoToRestaurant(restaurantDto);
        restaurantRepository.save(restaurant);
    }

    public void updateRestaurant(RestaurantDto restaurantDto, Integer restaurantId, Integer userId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRestaurantList(restaurantId);
        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        authenticationContextService.validateRestaurantList(restaurantId);
        Restaurant updatedRestaurant = restaurantMapper.dtoToRestaurant(restaurantDto);
        restaurant.setName(updatedRestaurant.getName());
        restaurant.setCity(updatedRestaurant.getCity());
        restaurant.setAddress(updatedRestaurant.getAddress());
        restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Integer restaurantId, Integer userId) {
        authenticationContextService.validateUserId(userId);
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new IllegalArgumentException("restauracja nie istnieje");
        }
        restaurantRepository.deleteById(restaurantId);
    }
}
