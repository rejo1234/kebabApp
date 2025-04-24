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
        authenticationContextService.authenticateUserId(userId);
        authenticationContextService.authenticateRestaurantList(userRestaurantRequest.getRestaurantId());
        authenticationContextService.authenticateRepositoryList(userRestaurantRequest.getRepositoryId());
        Repository repository = repositoryRepository.findById(userRestaurantRequest.getRepositoryId())
                .orElseThrow(() -> new RuntimeException("Repository nie znaleziony"));

        Restaurant restaurant = restaurantRepository.findById(userRestaurantRequest.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restauracji nie znaleziono"));

        restaurant.getRepositoryList().remove(repository);
        restaurantRepository.save(restaurant);
    }

    public void connectRepositoryToRestaurant(RestaurantRepositoryRequest restaurantRepositoryRequest, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        Restaurant restaurant = restaurantRepository.findById(restaurantRepositoryRequest.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        Repository repository = repositoryRepository.findById(restaurantRepositoryRequest.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("Repozytorium nie istnieje"));

        restaurant.getRepositoryList().add(repository);
        restaurantRepository.save(restaurant);
    }


    public List<Restaurant> showRestaurantsConnectedToOneRepository(Integer repositoryId, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new IllegalArgumentException("Repozytorium nie istnieje"));
        return repository.getRestaurantList();
    }

    public List<Restaurant> showAllRestaurants(Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(Integer restaurantId, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
    }

    public void createRestaurant(RestaurantDto restaurantDto, Integer userId) {
            authenticationContextService.authenticateUserId(userId);
        var restaurant = restaurantMapper.dtoToRestaurant(restaurantDto);
        restaurantRepository.save(restaurant);
    }

    public void updateRestaurant(RestaurantDto restaurantDto, Integer restaurantId, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        authenticationContextService.authenticateRestaurantList(restaurantId);
        restaurant.setName(restaurantDto.getName());
        restaurant.setCity(restaurantDto.getCity());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Integer restaurantId, Integer userId) {
        authenticationContextService.authenticateUserId(userId);
        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        if (restaurant != null) {
            restaurantRepository.deleteById(restaurantId);
        }
    }
}
