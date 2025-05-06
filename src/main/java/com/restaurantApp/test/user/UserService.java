package com.restaurantApp.test.user;

import com.restaurantApp.test.auth.ContextService;
import com.restaurantApp.test.auth.CreateUserRequest;
import com.restaurantApp.test.auth.UserMapper;
import com.restaurantApp.test.repository.Repository;
import com.restaurantApp.test.repository.RepositoryRepository;
import com.restaurantApp.test.restaurant.Restaurant;
import com.restaurantApp.test.restaurant.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RepositoryRepository repositoryRepository;
    private final ContextService authenticationContextService;
    private final UserMapper userMapper;

    public void deleteConnectionUserAndRestaurant(UserRestaurantRequest userRestaurantRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRestaurantList(userRestaurantRequest.getRestaurantId());
        User user = userRepository.findById(userRestaurantRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User nie znaleziony"));

        Restaurant restaurant = restaurantRepository.findById(userRestaurantRequest.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restauracji nie znaleziono"));
        restaurant.getUserList().remove(user);
        user.getRestaurantList().remove(restaurant);
        userRepository.save(user);
    }

    public void deleteConnectionUserAndRepository(UserRepositoryRequest userRepositoryRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        authenticationContextService.validateRepositoryList(userRepositoryRequest.getRepositoryId());
        User user = userRepository.findById(userRepositoryRequest.getRepositoryId())
                .orElseThrow(() -> new RuntimeException("User nie znaleziony"));

        Repository repository = repositoryRepository.findById(userRepositoryRequest.getRepositoryId())
                .orElseThrow(() -> new RuntimeException("Repository nie znaleziono"));
        repository.getUserList().remove(user);
        user.getRepositoryList().remove(repository);
        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        authenticationContextService.validateUserId(userId);
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("user nie istnieje");
        }
        userRepository.deleteById(userId);
    }

    public void updateUser(CreateUserRequest createUserRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("user nie istnieje");
        }
        var updatedUser = userMapper.dtoToUser(createUserRequest.getUserDto());
        userRepository.save(updatedUser);
    }

    public void connectRestaurantToUser(UserRestaurantRequest userRestaurantRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        Restaurant restaurant = restaurantRepository.findById(userRestaurantRequest.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        User user = userRepository.findById(userRestaurantRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        restaurant.getUserList().add(user);
        user.getRestaurantList().add(restaurant);
        userRepository.save(user);
    }

    public void connectRepositoryToUser(UserRepositoryRequest userRepositoryRequest, Integer userId) {
        authenticationContextService.validateUserId(userId);
        Repository repository = repositoryRepository.findById(userRepositoryRequest.getRepositoryId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        User user = userRepository.findById(userRepositoryRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Restauracja nie istnieje"));
        repository.getUserList().add(user);
        user.getRepositoryList().add(repository);
        userRepository.save(user);
    }
}
