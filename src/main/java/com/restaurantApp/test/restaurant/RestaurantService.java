package com.restaurantApp.test.restaurant;

import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.repository.RepositoryValidator;
import com.restaurantApp.test.user.UserValidator;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RestaurantService {
    private final RepositoryRepository repositoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantValidator restaurantValidator;
    private final UserValidator userValidator;
    private final RepositoryValidator repositoryValidator;

    public void deleteConnectionRestaurantAndRepository(RestaurantRepositoryRequest userRestaurantRequest, Integer userId) {
        userValidator.validateUserId(userId);
        restaurantValidator.validateRestaurantId(userRestaurantRequest.getRestaurantId());
        repositoryValidator.validateRepositoryId(userRestaurantRequest.getRepositoryId());
        Repository repository = repositoryRepository.findById(userRestaurantRequest.getRepositoryId())
                .orElseThrow(() -> new RuntimeException("Repository nie znaleziony"));

        Restaurant restaurant = restaurantRepository.findById(userRestaurantRequest.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restauracji nie znaleziono"));

        restaurant.getRepositoryList().remove(repository);
        repository.getRestaurantList().remove(restaurant);
        restaurantRepository.save(restaurant);
    }

    public void connectRepositoryToRestaurant(RestaurantRepositoryRequest restaurantRepositoryRequest, Integer userId) {
        userValidator.validateUserId(userId);
        Restaurant restaurant = restaurantRepository.findById(restaurantRepositoryRequest.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        Repository repository = repositoryRepository.findById(restaurantRepositoryRequest.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("Repozytorium nie istnieje"));
        restaurant.getRepositoryList().add(repository);
        repository.getRestaurantList().add(restaurant);
        restaurantRepository.save(restaurant);
    }


    public List<Restaurant> showRestaurantsConnectedToOneRepository(Integer repositoryId, Integer userId) {
        userValidator.validateUserId(userId);
        Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new IllegalArgumentException("Repozytorium nie istnieje"));
        return repository.getRestaurantList();
    }

    public List<Restaurant> showAllRestaurants(Integer userId) {
        userValidator.validateUserId(userId);
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(Integer restaurantId, Integer userId) {
        userValidator.validateUserId(userId);
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
    }

    public void createRestaurant(RestaurantDto restaurantDto, Integer userId) {
        userValidator.validateUserId(userId);
        var restaurant = restaurantMapper.dtoToRestaurant(restaurantDto);
        restaurantRepository.save(restaurant);
    }

    public void modifyRestaurant(RestaurantDto restaurantDto, Integer restaurantId, Integer userId) {
        if (!restaurantDto.getId().equals(restaurantId)) {
            throw new IllegalArgumentException("error idrestaurant is not equals");
        }
        userValidator.validateUserId(userId);
        restaurantValidator.validateRestaurantId(restaurantId);
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new IllegalArgumentException("Restauracja nie istnieje");
        }
        Restaurant updatedRestaurant = restaurantMapper.dtoToRestaurant(restaurantDto);
        restaurantRepository.save(updatedRestaurant);
    }

    public void deleteRestaurant(Integer restaurantId, Integer userId) {
        userValidator.validateUserId(userId);
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new IllegalArgumentException("restauracja nie istnieje");
        }
        restaurantRepository.deleteById(restaurantId);
    }
}
